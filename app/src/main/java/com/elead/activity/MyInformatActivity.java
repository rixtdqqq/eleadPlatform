package com.elead.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.im.util.EaseUserUtils;
import com.elead.utils.ToastUtil;
import com.elead.views.CircularImageView;
import com.hyphenate.chat.EMClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class MyInformatActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rel_photo_change;
    private RelativeLayout rel_username;
    private RelativeLayout rel_gender;
    private RelativeLayout rel_birthday;
    private RelativeLayout rel_region;
    private RelativeLayout rel_qrcode;
    private RelativeLayout rel_sign_name;
    private CircularImageView photoImg;
    private ImageView title_back;
    private byte[] datas;
    private Bitmap bitmap;
    private int count;
    private String nickname;
    private TextView nickNameText;
    private String nicknameT;
    public static final int REQUESTCODE_PICK = 100;
    public static final int REQUESTCODE_CUTTING = 101;

    public static final String APP_NAME = "ImageSelector";

    public static final String POSTFIX = ".JPEG";

    public static final String CAMERA_PATH = "/" + APP_NAME + "/CameraImage/";

    public File f;
    public final static String BUNDLE_CAMERA_PATH = "CameraPath";
    private ProgressDialog dialog;

    public String cameraPath;

    public Uri uri;
    private TextView usernameTex;
    private TextView show_phone;
    private String name;
    private TextView partment_iv;
    private String hx_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information_activity);
//        datas = getIntent().getByteArrayExtra("bitmap");
        //Util.getLanguage(this);
        // StatusBarUtils.setWindowStatusBarColor(this, R.color.qianlv);
        setTitle(getResources().getString(R.string.my_informations), TitleType.NORMAL);
        if (savedInstanceState != null) {
            cameraPath = savedInstanceState.getString(BUNDLE_CAMERA_PATH);
        }
        inItView();
    }


    private void inItView() {

        //  Intent intent = getIntent();
        //   String username = intent.getStringExtra("username");

        rel_photo_change = (RelativeLayout) findViewById(R.id.rel_photo_id);
        rel_username = (RelativeLayout) findViewById(R.id.rel_name_id);
//        rel_gender = (RelativeLayout) findViewById(R.id.rel_gender_id);
//        rel_birthday = (RelativeLayout) findViewById(R.id.rel_birthday_id);
//        rel_region = (RelativeLayout) findViewById(R.id.rel_region_id);
//        rel_qrcode = (RelativeLayout) findViewById(R.id.rel_Qrcode_id);
//        rel_sign_name = (RelativeLayout) findViewById(R.id.rel_signname);
        nickNameText = (TextView) findViewById(R.id.nick_name_id);
        photoImg = (CircularImageView) findViewById(R.id.photo_id);
        //title_back = (ImageView) findViewById(R.id.my_information_title_back);
        show_phone = (TextView) findViewById(R.id.phone_tex);
        usernameTex = (TextView) findViewById(R.id.usernameId);
        partment_iv = (TextView) findViewById(R.id.partment_iv);

        // String currentUserAvatar = DemoHelper.getInstance().getUserProfileManager().getCurrentUserAvatar();
        // name = EMClient.getInstance().getCurrentUser();
        // user = EaseUI.getInstance().getUserProfileProvider().getUser(name);

        //  nickNameText.setText(DemoHelper.getInstance().getUserProfileManager().getCurrentUserNick());
        //nickNameText.setText(user.getNick());


        //   Glide.with(this).load(currentUserAvatar).dontAnimate().placeholder(R.drawable.em_default_avatar).into(photoImg);
        //EaseUserUtils.setUserAvatar(this, name, photoImg);
        //String name = EMClient.getInstance().getCurrentUser();
        // show_phone.setText(name);

        rel_photo_change.setOnClickListener(this);
        rel_username.setOnClickListener(this);
//
        photoImg.setOnClickListener(this);
        // title_back.setOnClickListener(this);
        /*if (datas != null) {
            bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);

            photoImg.setImageBitmap(bitmap);
        }*/
        // setTitle(getResources().getString(R.string.my_informations));
        asyncFetchUserInfo();


    }

    private void asyncFetchUserInfo() {

        // final MyApplication application = (MyApplication)(.getApplicationContext());
        MyApplication application = (MyApplication) (MyInformatActivity.this.getApplication());
        hx_name = EMClient.getInstance().getCurrentUser();

        if (!TextUtils.isEmpty(application.user.name)) {
            // photoImg.setText(application.user.name);
            usernameTex.setText(application.user.name);
            nickNameText.setText(application.user.name);
        }

        if (!TextUtils.isEmpty(MyApplication.user.dept_name)) {
            partment_iv.setText(MyApplication.user.dept_name);
        }

        if (!TextUtils.isEmpty(hx_name)) {
            EaseUserUtils.setUserNick(hx_name, photoImg);
        }
   /*     if (!TextUtils.isEmpty(user.getAvatar())) {
            nickNameText.setText(user.getAvatar());
        } else {
            nickNameText.setText(application.user.name);
        }*/

      /*  if (!TextUtils.isEmpty(user.getAvatar())) {
            try {
                //  int avatarResId = Integer.parseInt(easeUser.getAvatar());
                // Glide.with(context).load(easeUser.getAvatar()).dontAnimate().placeholder(R.drawable.em_default_avatar).into(my_photo);
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(mContext).load(avatarResId).into(photoImg);
            } catch (Exception e) {
                Log.i("eee", "异常===" + e);
                Glide.with(mContext).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(null).into(photoImg);
            }
        }*/

    }


    private void uploadHeadPhoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        builder.setTitle(R.string.dl_title_upload_photo);

        builder.setItems(new String[]{getString(R.string.dl_msg_take_photo), getString(R.string.dl_msg_local_upload)},
                new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                              /*  Toast.makeText(MyInformatActivity.this, getString(R.string.toast_no_support),
                                        Toast.LENGTH_SHORT).show();*/
                                String state = Environment.getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                                    f = createMediaFile();
                                    cameraPath = f.getAbsolutePath();
                                    uri = Uri.fromFile(f);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                    startActivityForResult(intent, 20);

                                } else {
                                    ToastUtil.showToast(MyInformatActivity.this, "无内存卡", Toast.LENGTH_SHORT);
                                }


                                break;
                            case 1:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;


            case 20:

                if (resultCode == RESULT_OK) {
                    File dir = new File(cameraPath);
                    // Log.i("uri===",Uri.parse(cameraPath)+"");

                    startPhotoZoom(Uri.fromFile(dir));
                }

                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 不加相机会根据不同的手机会产生闪退现象
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_CAMERA_PATH, cameraPath);
        //outState.putSerializable("BUNDLE_CAMERA_PATH",f);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    /**
     * save the picture data
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            photoImg.setImageDrawable(drawable);
        }

    }


    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rel_photo_id:
                uploadHeadPhoto();

                break;


            case R.id.rel_name_id:
//                final EditText editText = new EditText(this);
//                editText.setTextColor(Color.parseColor("#ffffff"));
//                AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
//                editText.setText(DemoHelper.getInstance().getUserProfileManager().getCurrentUserNick());
//
//                builder.setTitle(R.string.setting_nickname).setIcon(android.R.drawable.ic_dialog_info).setView(editText)
//                        .setPositiveButton(R.string.dl_ok, new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String nickString = editText.getText().toString();
//                                if (TextUtils.isEmpty(nickString)) {
//                                    Toast.makeText(MyInformatActivity.this, getString(R.string.toast_nick_not_isnull), Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                updateRemoteNick(nickString);
//                            }
//                        }).setNegativeButton(R.string.dl_cancel, null);
//                Dialog dialogl = builder.create();
//                dialogl.show();
//
//                final Button positiveButton = ((AlertDialog) dialogl).getButton(Dialog.BUTTON_POSITIVE);
//
////                if (editText.getTextSize() > 0) {
////                    positiveButton.setEnabled(false);
////                } else {
////                    positiveButton.setEnabled(true);
////                }
//
////                positiveButton.setFocusableInTouchMode(true);
////                positiveButton.requestFocus();
//                editText.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////                        if (s.length() <= 0) {
////                            positiveButton.setEnabled(true);
////                        } else {
////                            positiveButton.setEnabled(false);
////                        }
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });


                break;


//            case R.id.my_information_title_back:
//                //  new Intent(this,set)
//                // SelectPictureActivity.this.finish();
//                MyInformatActivity.this.finish();
//                break;

            /**
             * 查看头像
             */
            case R.id.photo_id:
                // Intent checkIntent = new Intent(this, CheckPhotoActivity.class);
                //startActivity(checkIntent);
                break;


        }


    }

    private void updateRemoteNick(final String nickName) {
        dialog = ProgressDialog.show(this, getString(R.string.dl_update_nick), getString(R.string.dl_waiting));
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean updatenick = DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
                if (MyInformatActivity.this.isFinishing()) {
                    return;
                }
                if (!updatenick) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MyInformatActivity.this, getString(R.string.toast_updatenick_fail), Toast.LENGTH_SHORT)
                                    .show();
                            dialog.dismiss();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(MyInformatActivity.this, getString(R.string.toast_updatenick_success), Toast.LENGTH_SHORT)
                                    .show();
                            nickNameText.setText(nickName);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }


    private File createMediaFile() {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : this.getCacheDir();

        File folderDir = new File(rootDir.getAbsolutePath() + CAMERA_PATH);
        if (!folderDir.exists() && folderDir.mkdirs()) {

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = APP_NAME + "_" + timeStamp + "";
        File tmpFile = new File(folderDir, fileName + POSTFIX);
        return tmpFile;
    }


}
