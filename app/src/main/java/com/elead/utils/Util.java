package com.elead.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.elead.application.MyApplication;
import com.elead.approval.entity.ApprovalClassicModelEntity;
import com.elead.eplatform.R;
import com.elead.module.EpUser;
import com.elead.views.FixedSpeedScrollerViewPager;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import static com.elead.application.MyApplication.mContext;

public class Util {

    public final static String LOGIN_COUNT = "login_count";
    public final static String REQUEST_COUNT = "request_count";
    private static String language;

    /**
     * 设置图片背景
     *
     * @param img
     * @param resId
     */
    public static void setBackGround(Context mContext, ImageView img, int resId) {
        img.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(),
                Util.readBitMap(mContext, resId)));
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight) {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }

    /**
     * 非空判断
     *
     * @param entity
     * @return true 不为空，false 为空
     */
    public static boolean isNotEmpty(Object entity) {
        boolean empty = false;
        if (entity instanceof Collection) {
            if (null != entity && ((Collection<?>) entity).size() > 0) {
                return true;
            }
        }
        return empty;
    }

    /**
     * BannerBar第一张图片
     */
    public static Drawable bananerBarFiretBG = null;
    /**
     * BannerBar第一张图片路径
     */
    public static String APP_DOWNLOAD_DIR_PNG = Environment.getExternalStorageDirectory()
            + "/ELeadDownload/firsticon.png";

    public static String APP_DOWNLOAD_DIR = Environment.getExternalStorageDirectory()
            + "/ELeadDownload/Banner/";

    public static boolean loadDrawableSuccess = false;

    /**
     * 如果本地url和服务器传过来的URL不一样，删除
     *
     * @param iconurl
     * @return
     */
    public static boolean deleteIcon(Context context, String iconurl, int pos) {
        boolean delete = false;
        String firsticon = SharedPreferencesUtil.getString(context, getAfterPrefix(pos));
        try {
            if (!TextUtils.equals(iconurl, firsticon)) {
                File file = new File(APP_DOWNLOAD_DIR + getAfterPrefix(pos) + ".png");
                if (file.exists()) {
                    file.delete();
                    delete = true;
                }
            }
        } catch (Exception e) {
            Log.d("ELead_Util", e.toString());
        }
        return delete;
    }

    public static String getAfterPrefix(int pos) {
        return "bannericon" + pos;
    }

    /**
     * 获取当前模块语言字符串
     *
     * @return
     */
    public static boolean getCurrentModuleLanguage() {
        String language = getCurrentLanguage();

        if (TextUtils.equals("zh", language.toLowerCase()) || TextUtils.equals("cn", language.toLowerCase())) { // 中文
            return true;
        }
        return false;
    }

    /**
     * 获取系统当前语言
     */
    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 用于设置ViewPager的滑动速度
     *
     * @param viewPager
     * @param scroller
     */
    public static void setViewPagerSpeed(ViewPager viewPager,
                                         FixedSpeedScrollerViewPager scroller) {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("ViewPagerScroller", "DeclaredFiled is fail");
        }
    }

    /**
     * 创建下载目录
     */
    public static void createDownLoadDirectory() {
        // 判断文件夹是否存在，不存在就创建
        File f = new File(APP_DOWNLOAD_DIR);
        if (!f.exists()) {
            f.mkdir();
        }
    }

    /**
     * 获取应用程序版本名称
     *
     * @param c
     * @return
     */
    public static int queryVersionCode(Context c) {
        PackageInfo info;
        try {
            info = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            // 当前版本的版本号
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 字符串转换为数字
     *
     * @param value
     * @return
     */
    public static int parseInt(Object value, int defalut) {
        if (value == null) {
            return defalut;
        }
        try {
            return Integer.parseInt(getStringNotNull(value));
        } catch (NumberFormatException e) {
        }
        return defalut;
    }

    /**
     * 获取一个不为空的字符串
     * <p>
     * //@param String
     * s
     *
     * @return String
     */
    public static String getStringNotNull(Object s) {
        if (null == s || "null".equals(s)) {
            return "";
        }
        return s.toString();
    }

    public static Pattern setMatcher() {
        // all domain names
        String[] ext = {
                "top", "com", "net", "org", "edu", "gov", "int", "mil", "cn", "tel", "biz", "cc", "tv", "info",
                "name", "hk", "mobi", "asia", "cd", "travel", "pro", "museum", "coop", "aero", "ad", "ae", "af",
                "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "az", "ba", "bb", "bd",
                "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz",
                "ca", "cc", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cq", "cr", "cu", "cv", "cx",
                "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "es", "et", "ev", "fi",
                "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gh", "gi", "gl", "gm", "gn", "gp",
                "gr", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "in", "io",
                "iq", "ir", "is", "it", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw",
                "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md",
                "mg", "mh", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mv", "mw", "mx", "my", "mz",
                "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nt", "nu", "nz", "om", "qa", "pa",
                "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "pt", "pw", "py", "re", "ro", "ru", "rw",
                "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st",
                "su", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tm", "tn", "to", "tp", "tr", "tt",
                "tv", "tw", "tz", "ua", "ug", "uk", "us", "uy", "va", "vc", "ve", "vg", "vn", "vu", "wf", "ws",
                "ye", "yu", "za", "zm", "zr", "zw"
        };

//	static {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < ext.length; i++) {
            sb.append(ext[i]);
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        // final pattern str
        String pattern = "((https?|s?ftp|irc[6s]?|git|afp|telnet|smb)://)?((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|((www\\.|[a-zA-Z\\.]+\\.)?[a-zA-Z0-9\\-]+\\." + sb.toString() + "(:[0-9]{1,5})?))((/[a-zA-Z0-9\\./,;\\?'\\+&%\\$#=~_\\-]*)|([^\\u4e00-\\u9fa5\\s0-9a-zA-Z\\./,;\\?'\\+&%\\$#=~_\\-]*))";
        // Log.v(TAG, "pattern = " + pattern);
        Pattern WEB_URL = Pattern.compile(pattern);

        //}

        return WEB_URL;
    }

    /**
     * 获取文字一半的高度
     *
     * @param paint
     * @param text
     * @return
     */
    public static float getHalfTextHeigth(Paint paint, String text) {
        try {
            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);
            return rect.height() / 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取文字一半的长度
     *
     * @param paint
     * @param text
     * @return
     */
    public static float getHalfTextWidth(Paint paint, String text) {
        try {
            return paint.measureText(text) / 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Map<String, Integer> APPROVAL_IMAGES = new HashMap<>();
    public static List<ApprovalClassicModelEntity> CLASSIC_MODEL_LIST = new ArrayList<ApprovalClassicModelEntity>();
    public static boolean IS_REFRESH_APPROVAL = false;
    public static boolean IS_FIRST_LOADING = true;
    public static String APPROVAL_LIST_OBJECT_ID;

    static {
        APPROVAL_IMAGES.put("请假", R.drawable.leave);
        APPROVAL_IMAGES.put("报销", R.drawable.reimbursement);
        APPROVAL_IMAGES.put("出差", R.drawable.a_business_travel);
        APPROVAL_IMAGES.put("外出", R.drawable.go_out);
        APPROVAL_IMAGES.put("物品领用", R.drawable.res_receive);
        APPROVAL_IMAGES.put("通用审批", R.drawable.common_apprival);
        APPROVAL_IMAGES.put("添加审批", R.drawable.add_icon);
        APPROVAL_IMAGES.put("部门协作", R.drawable.department_cooperation);
        APPROVAL_IMAGES.put("立项申请", R.drawable.project_apply);
        APPROVAL_IMAGES.put("备用金申请", R.drawable.payment);
        APPROVAL_IMAGES.put("订货审批", R.drawable.order_apply);
        APPROVAL_IMAGES.put("物品申购", R.drawable.res_apply);
        APPROVAL_IMAGES.put("收款", R.drawable.payment);
        APPROVAL_IMAGES.put("采购", R.drawable.become_a_regular_worker);
        APPROVAL_IMAGES.put("新闻爆料", R.drawable.news_broke);
        APPROVAL_IMAGES.put("会议审批", R.drawable.project_apply);
        APPROVAL_IMAGES.put("资质使用", R.drawable.car_apply);
        APPROVAL_IMAGES.put("合同审批", R.drawable.overtime);
        APPROVAL_IMAGES.put("工作请示", R.drawable.leave_office);
        APPROVAL_IMAGES.put("调课报备", R.drawable.department_cooperation);
        APPROVAL_IMAGES.put("快递寄送", R.drawable.res_apply);
        APPROVAL_IMAGES.put("绩效自评", R.drawable.department_cooperation);
        APPROVAL_IMAGES.put("用车申请", R.drawable.car_apply);
        APPROVAL_IMAGES.put("用印申请", R.drawable.seal_apply);
        APPROVAL_IMAGES.put("离职", R.drawable.leave_office);
        APPROVAL_IMAGES.put("转正", R.drawable.become_a_regular_worker);
        APPROVAL_IMAGES.put("加班", R.drawable.overtime);
        APPROVAL_IMAGES.put("付款", R.drawable.payment);
        APPROVAL_IMAGES.put("招聘", R.drawable.recruit);
    }

    /**
     * 切换语言
     */
    public static void switchLanguage(Context context, String language) {
        // 设置应用语言类型
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
        } else {
            // 简体中文
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }
        resources.updateConfiguration(config, dm);

        // 保存设置语言的类型
        //PreferenceUtil.commitString("language", language);
        SharedPreferencesUtil.putString(context, Constants.LANGUAGE, language);
    }


    public static void getLanguage(Context mContext) {
        language = SharedPreferencesUtil.getString(mContext, Constants.LANGUAGE);
        if (language != null) {

            Util.switchLanguage(mContext, language);
        } else {
            Util.switchLanguage(mContext, "zh");
        }

    }


    /**
     * 判断是否在白名单内
     */

    public static boolean isExitWhiteList() {
        EpUser user = ((MyApplication) mContext.getApplicationContext()).user;
        Log.i("TAG", "emii==" + user.getWork_no());
        if (user.getWork_no().equals("0608") || user.getWork_no().equals("0560") ||
                user.getWork_no().equals("0961") || user.getWork_no().equals("0963") ||
                user.getWork_no().equals("0959") || user.getWork_no().equals("0566") ||
                user.getWork_no().equals("0795") || user.getWork_no().equals("0922") ||
                user.getWork_no().equals("0809") || user.getWork_no().equals("0805") ||
                user.getWork_no().equals("0796") || user.getWork_no().equals("1605") ||
                user.getWork_no().equals("0801") || user.getWork_no().equals("1020") ||
                user.getWork_no().equals("0815") || user.getWork_no().equals("1008") ||
                user.getWork_no().equals("0812")
                ) {
            return true;
        } else {
            return false;
        }
    }

}
