package com.elead.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.elead.entity.UpdataInfo;
import com.elead.eplatform.R;
import com.elead.fragment.ContactFragment;
import com.elead.fragment.HomeRssFragment;
import com.elead.fragment.UserFragment;
import com.elead.fragment.WorkFragment;
import com.elead.im.activity.ChatActivity;
import com.elead.im.activity.NewGroupActivity;
import com.elead.im.db.DemoHelper;
import com.elead.im.db.InviteMessgeDao;
import com.elead.im.fragment.ConversationListFragment;
import com.elead.im.util.EaseUserUtils;
import com.elead.im.util.RedPacketUtil;
import com.elead.im.widget.EaseConstant;
import com.elead.module.EpUser;
import com.elead.module.EpUserInfo;
import com.elead.module.TbSelecterInfo;
import com.elead.popuJarLib.popuJar.PopuItem;
import com.elead.popuJarLib.popuJar.PopuJar;
import com.elead.service.EPlamformServices;
import com.elead.utils.AppManager;
import com.elead.utils.Constants;
import com.elead.utils.EloadingDialog;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.NetWorkUtils;
import com.elead.utils.PermissionUtil;
import com.elead.utils.SharedPreferencesUtil;
import com.elead.utils.StatusBarUtils;
import com.elead.utils.ToastUtil;
import com.elead.utils.UpdateManager;
import com.elead.utils.Util;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import com.elead.viewpagerutils.FViewPagerAdapter;
import com.elead.views.AlertDialog;
import com.elead.views.JazzyViewPager;
import com.elead.views.TbSelector;
import com.elead.views.TitleView;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.elead.application.MyApplication.user;


/**
 * The type Main ui.
 */
public class MainActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.vp_main)
    JazzyViewPager vpMain;
    @BindView(R.id.ntb_main)
    TbSelector ntbMain;

    TitleView ttbMain;
    private List<Fragment> views;
    private FViewPagerAdapter adapter;
    //    private MessageListFragment messageFragment;
    private ConversationListFragment conversationListFragment;
    private ContactFragment contactFragment;
    private WorkFragment workFragment;
    private UserFragment userFragment;
    private final int REQUEST_READ_CONSTANT = 101;//请求联系人权限
    private final int REQUEST_CAMERA = 102;//请求照相机权限

    private HomeRssFragment homeRssFragment;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private InviteMessgeDao inviteMessgeDao;

    private ProgressDialog progressDialog;

    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;
    private BroadcastReceiver internalDebugReceiver;
    private AlertDialog builder;
    private GroupChangeListener groupChangeListener;
//    public  String serviceCodeVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }

        //make sure activity will not in background if user is logged into another device or removed
        if (savedInstanceState != null && savedInstanceState.getBoolean(EaseConstant.ACCOUNT_REMOVED, false)) {
            DemoHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        setContentView(R.layout.activity_main);

        Util.getLanguage(this);
        StatusBarUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.qianlv));
        initTitleView();
        getEpUserInfo();
        ButterKnife.bind(this);
        inviteMessgeDao = new InviteMessgeDao(this);
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        groupChangeListener = new GroupChangeListener();
        EMClient.getInstance().groupManager().addGroupChangeListener(groupChangeListener);
        inItData();

        // 检测版本
        requestVersion();

    }

    /**
     * 更新apk
     *
     * @param versionVN
     * @param url
     * @param description
     */
    private void updateApk(String versionVN, String url, String description) {
        UpdateManager manager = new UpdateManager(this, "e_platform.apk", url);
        if (TextUtils.isEmpty(description)) {
            description = UpdataInfo.description;
        }
        manager.checkUpdate(versionVN, description);
    }

    /***
     * 检测版本
     */
    private void requestVersion() {

        if (!NetWorkUtils.isNetworkConnected(this)) {
            ToastUtil.showToast(this, getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        String url = EPlamformServices.version_update;
        VHttpUrlConnectionUtil.postRequest(url, null,
                new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(String url, String result) {

                        JSONObject dataJson = null;
                        int ret_code = 0;
                        String serviceCodeVersion = "";
                        String description = "";
                        String down_apk_url = "";
                        String ret_message = "";
                        try {
                            dataJson = new JSONObject(result);
                            ret_code = dataJson.getInt("ret_code");
                            if (dataJson.has("ret_message")) {
                                ret_message = dataJson.getString("ret_message");
                            }
                            serviceCodeVersion = dataJson.getString("version");
                            down_apk_url = dataJson.getString("url");
                            description = dataJson.getString("point");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //后台返回是否是新用户的标志
                        if (ret_code > 0) {
                            updateApk(serviceCodeVersion, down_apk_url, description);
                        }
                    }

                    @Override
                    public void onFail(String result) {
                    }
                });

    }

    private void initTitleView() {
        ttbMain = new TitleView(this);
        ttbMain.updateHeadPhoto(user);
        setTitleView(ttbMain);
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EaseConstant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(EaseConstant.ACTION_GROUP_CHANAGED);
        intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                    conversationListFragment.refreshNewFriend();
                }
                /*if (contactFragment != null) {
                    contactFragment.refresh();
                }*/
                String action = intent.getAction();
//                if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
//                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
//                        GroupsActivity.instance.onResume();
//                    }
//                }
                //red packet code : 处理红包回执透传消息
                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
                //end of red packet code
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(EaseConstant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
                if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    RedPacketUtil.receiveRedPacketAckMessage(message);
                }
            }
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void getEpUserInfo() {
        HashMap<String, String> parameters = new HashMap<>();
//        Log.d("DateLodingFinishReciver", "result");
        parameters.put("work_no", user.work_no);
        String url = EPlamformServices.get_epuser_info_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                Log.d("DateLodingFinishReciver", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String ep_user_info = jsonObject.getString("ep_user_info");
                    EpUserInfo epUserInfo = JSON.parseObject(ep_user_info, EpUserInfo.class);
                    user.setEmail(epUserInfo.email);
                    user.setName(epUserInfo.name);
                    EaseUserUtils.updateRemoteNick(epUserInfo.name);
                    user.setDept_name(epUserInfo.dept_name);
                    user.setProject_name(epUserInfo.project_name);
                    SharedPreferencesUtil.putString(mContext, EpUser.class.getSimpleName(), JSON.toJSONString(user));
                    userFragment.initHeadView();
                    ttbMain.updateHeadPhoto(user);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String url) {
            }
        });

    }

    private void inItData() {
        initTitle();
        initFragment();
        inItSelectBar();
        inItJazzView();
    }

    private void initTitle() {
        ttbMain.setOnClickListenerM(new TitleView.OnClickListenerM() {
            @Override
            public void onQcCodeClick(View view) {
                //jumpingCaptureActivity();
                EloadingDialog.showDailog();
            }

            @Override
            public void onTelPhoneClick(View view) {
                EloadingDialog.showDailog();
            }

            @Override
            public void onSearchClick(View view) {
                EloadingDialog.showDailog();
            }

            @Override
            public void onAddlinkClick(View view) {
                jumpToAddFriendActivity();
            }

            @Override
            public void onAddClick(View view) {

                final PopuJar popuJar = new PopuJar(mContext);
                popuJar.setMarginT(true);
                popuJar.addPopuItem(new PopuItem(1, getResources().getString(R.string.scan_friend), getResources().getDrawable(R.drawable.ic_actbar_more_menu_icon_scan)));
                popuJar.addPopuItem(new PopuItem(2, getResources().getString(R.string.create_groud), getResources().getDrawable(R.drawable.ic_actbar_more_menu_icon_create_chat)));
                popuJar.addPopuItem(new PopuItem(3, getResources().getString(R.string.add_friends), getResources().getDrawable(R.drawable.ic_actbar_more_menu_icon_add_firends)));
                popuJar.setAnimStyle(PopuJar.ANIM_REFLECT);
                popuJar.setBackground(R.drawable.sanjiaoxing_popup);
                //  设置屏幕的背景透明度(需要在PopupWindows类打开backgroundAlpha()方法)
                popuJar.setOnDismissListener(new PopuJar.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        popuJar.backgroundAlpha((Activity) mContext, 1f);//1.0-0.0
                    }
                });

                popuJar.setOnPopuItemClickListener(new PopuJar.OnPopuItemClickListener() {
                    @Override
                    public void onItemClick(PopuJar source, int pos, int actionId) {
                        switch (pos) {
                            case 0:
                                //打开扫描界面扫描条形码或二维码
                                jumpingCaptureActivity();

                                break;
                            case 1:
                                // EloadingDialog.showDailog();
                                startActivityForResult(new Intent(MainActivity.this, NewGroupActivity.class), 0);
                                break;
                            case 2:
                                jumpToAddFriendActivity();
                                break;
                            case 3:
                                EloadingDialog.showDailog();
                                break;

                            default:
                                break;
                        }
                    }
                });
                popuJar.show(view);
            }
        });
    }

    /**
     * 跳转到添加好友界面
     */
    private void jumpToAddFriendActivity() {
        Intent i = new Intent(MainActivity.this, AddFriendActivity.class);
        startActivity(i);
    }

    /**
     * 跳转扫描二维码
     */
    private void jumpingCaptureActivity() {
        if (PermissionUtil.requestCameraPermissions(MainActivity.this, REQUEST_CAMERA)) {
            Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(openCameraIntent, 0);
        }
    }


    private void inItSelectBar() {
        ArrayList<TbSelecterInfo> tbSelecterInfos = new ArrayList<>();
        int defColor = getResources().getColor(R.color.fontcolors4);
        int pressColor = getResources().getColor(R.color.develop_tb_line);
        tbSelecterInfos.add(new TbSelecterInfo(getResources().getString(R.string.home_page), defColor, pressColor, R.drawable.homepage_false, R.drawable.homepage_true));
        tbSelecterInfos.add(new TbSelecterInfo(getResources().getString(R.string.message_page), defColor, pressColor, R.drawable.news_false, R.drawable.news_true));
        tbSelecterInfos.add(new TbSelecterInfo(getResources().getString(R.string.work_page), defColor, pressColor, R.drawable.work_false, R.drawable.work_true));
        tbSelecterInfos.add(new TbSelecterInfo(getResources().getString(R.string.contact_page), defColor, pressColor, R.drawable.contact_false, R.drawable.contact_true));
        tbSelecterInfos.add(new TbSelecterInfo(getResources().getString(R.string.me_page), defColor, pressColor, R.drawable.me_false, R.drawable.me_true));
        ntbMain.inItTabS(tbSelecterInfos);
        ntbMain.setOnTbSelectListener(new TbSelector.onTbSelectListener() {
            @Override
            public void onSelectChange(int position) {
                vpMain.setCurrentItem(position, false);
            }
        });
    }


    private void initFragment() {
        views = new ArrayList<Fragment>();
//        homeFragment = new HomeFragment();
        homeRssFragment = new HomeRssFragment();

        conversationListFragment = new ConversationListFragment();
        workFragment = new WorkFragment();
        contactFragment = new ContactFragment();
        userFragment = new UserFragment();
//        views.add(homeFragment);
        views.add(homeRssFragment);
        views.add(conversationListFragment);
        views.add(workFragment);
        views.add(contactFragment);
        views.add(userFragment);
    }


    private void inItJazzView() {
//        vpMain.setEnabled(false);
        vpMain.setTransitionEffect(JazzyViewPager.TransitionEffect.CubeOut);
        adapter = new FViewPagerAdapter(getSupportFragmentManager(), views);
        vpMain.setAdapter(adapter);
        vpMain.setOffscreenPageLimit(4);
        vpMain.addOnPageChangeListener(this);
        vpMain.setPageMargin(30);
        vpMain.setCurrentItem(2, false);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ttbMain.setSlectTitle(position);
        ntbMain.setCurrItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private long startBackTime = 0l;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - startBackTime <= 2000) {
                ToastUtil.close();
                AppManager.getAppManager().AppExit(this);
            } else {
                ToastUtil.showToast(MainActivity.this, getResources().getString(R.string.click_again_exit), 0).show();
            }
            startBackTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONSTANT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent(MainActivity.this, AddressActivity.class);
                    startActivity(intent);
                } else {
                    // Permission Denied
                    ToastUtil.showToast(MainActivity.this, "无读取联系人权限，无法使用", Toast.LENGTH_SHORT).show();
                }

                break;
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(openCameraIntent, 0);
                } else {
                    ToastUtil.showToast(MainActivity.this, "无照相机权限，无法使用", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
                if (vpMain.getCurrentItem() == 1) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
            }
        });
    }


    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            ntbMain.setMessageTip(1, count);
        }
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                final String scanResult = bundle.getString("result");
                ToastUtil.showToast(mContext, scanResult, 0).show();

                //只能由数字和字母组成匹配
                Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
                //邮箱匹配
                Pattern email = Pattern.compile("^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
                Matcher phone_p = p.matcher(scanResult);
                Matcher email_p = email.matcher(scanResult);

                //网址匹配
                Pattern pm = Util.setMatcher();
                Matcher Web_p = pm.matcher(scanResult);

                if (phone_p.find() || email_p.find()) {
//                    final String name = EMClient.getInstance().getCurrentUser();
//                    EaseUser user = EaseUI.getInstance().getUserProfileProvider().getUser(name);
                    final String userName = EMClient.getInstance().getCurrentUser();
                    Log.i("TAGh", "name==" + userName + "user===" + user);

                    if (!userName.equals(scanResult)) {
                        Intent intentValidation = new Intent(this, ValidationFriendActivity.class);
                        intentValidation.putExtra(Constants.VALIDATION_FRIEND_KEY, scanResult);
                        startActivity(intentValidation);
                    } else {
                        //ToastUtil.showToast(mContext, "不能添加自己为好友！", 0).show();
                        showDailog("不能添加自己为好友！", "温馨提醒");
                    }
                    //匹配网址
                } else if (Web_p.find()) {

                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            (Uri.parse(scanResult))
                    ).addCategory(Intent.CATEGORY_BROWSABLE)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //扫描不到的
                } else {

//                    new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK).setTitle("未识别的二维码")
//                            .setPositiveButton(R.string.sure, null).show();
//                    ToastUtil.showToast(mContext, scanResult, 0).show();
                    showDailog(scanResult, "扫描结果");
                }
            }
// resultTextView.setText(scanResult);
        }


    }

    private void showDailog(String msg, String title) {
        if (builder == null) {
            builder = new com.elead.views.AlertDialog(MainActivity.this).builder();
        }
        builder.setTitle(title);
        builder.setMsg(msg);
        builder.setPositiveButton(getResources().getString(R.string.ok), 0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        builder.setCancelable(false);
        builder.show();
    }


    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 = getResources().getString(R.string.have_you_removed);
                        Toast.makeText(MainActivity.this, ChatActivity.activityInstance.getToChatUsername() + st10, Toast.LENGTH_LONG)
                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onContactAgreed(String username) {
        }

        @Override
        public void onContactRefused(String username) {
        }
    }


    private class GroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName,
                                         String inviter, String reason) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onApplicationReceived(String groupId, String groupName,
                                          String applyer, String reason) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onApplicationAccept(String groupId, String groupName,
                                        String accepter) {
            updateGroup(groupId);
        }

        @Override
        public void onApplicationDeclined(String groupId, String groupName,
                                          String decliner, String reason) {
            updateGroup(groupId);
        }

        @Override
        public void onInvitationAccepted(String groupId, String userId, String s2) {
            updateGroup(groupId);
        }

        @Override
        public void onInvitationDeclined(String groupId, String invitee,
                                         String reason) {
            // TODO Auto-generated method stub
            updateGroup(groupId);
        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            updateGroup(groupId);

        }

        @Override
        public void onGroupDestroyed(String groupId, String s1) {
            updateGroup(groupId);
        }


        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
            // TODO Auto-generated method stub
            updateGroup(groupId);
        }

    }

    private void updateGroup(final String groupId) {
        new Thread() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().getGroupFromServer(groupId);
                    conversationListFragment.refresh();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}

