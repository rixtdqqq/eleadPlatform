package com.elead.service;

/**
 * Created by lm806 on 2016/12/15.
 * 服务地址
 */

public class EPlamformServices {

    public static boolean IS_HTTPS = true;//区分是否是https请求,true是,false否

    private static String Enviroment = "http://hr.e-lead.cn:8080/eplat-pro/";

    /**
     * 初始化请求环境
     */
    static {
        if (IS_HTTPS) {//https请求
            Enviroment = "https://hr.e-lead.cn:8443/eplat-pro/";
        }
    }

    /**
     * 正式环境地址
     */
    public static final String E_PLATFORM_SIT = "http://hr.e-lead.cn:8080/eplat-pro/";

    /**
     * 测试环境地址
     */
    public static final String E_PLAMFORM_UAT = "http://192.168.1.225:8080/eplat-uat/";

    /**
     * 登录服务地址
     */
    public static final String login_service = Enviroment + "epUserController.do?loginCheck";

    /**
     * 获取用户信息
     */
    public static final String get_epuser_info_service = Enviroment + "epUserController.do?simpleUser";

    /**
     * 修改密码
     */
    public static final String change_password_service = Enviroment + "epUserController.do?changePassword";

    /**
     * 是否是新用户
     */
    public static final String is_new_user_service = Enviroment + "epUserController.do?isNewUser";

    /**
     * 获取验证码
     */
    public static final String get_verification_code_service = Enviroment + "epUserController.do?getVerificationCode";

    /**
     * 重置密码
     */
    public static final String reset_password_service = Enviroment + "epUserController.do?resetPassword";

    /**
     * 获取打卡记录
     */
    public static final String get_punchcard_records_custom_service = Enviroment + "epAttenController.do?getPunchCardRecordsCustom";

    /**
     * 多设备绑定用户
     */
    public static final String is_muti_devices_service = Enviroment + "epDeviceController.do?getIfExceedLimit";

    /**
     * 设备绑定状态
     */
    public static final String device_bind_statu_service = Enviroment + "epDeviceController.do?getIfDeviceBound";

    /**
     * 绑定设备列表
     */
    public static final String bind_device_list_service = Enviroment + "epDeviceController.do?getDeviceBoundList";

    /**
     * 绑定设备
     */
    public static final String bind_device_service = Enviroment + "epDeviceController.do?bindDevice";

    /**
     * 解绑设备
     */
    public static final String unbind_device_service = Enviroment + "epDeviceController.do?unbindDevice";

    /**
     * 打卡
     */
    public static final String punchcard_records_service = Enviroment + "epAttenController.do?punchCard";

    /**
     * 获取用户信息
     */
    public static final String get_user_info = Enviroment + "epUserController.do?simpleUser";

    /**
     * 获取好友昵称
     */
    public static final String get_epchatuser_info_service = Enviroment + "emUserController.do?getUserInfoByEmUsernamesJaKv";

    /**
     * 获取未处理异常打卡信息
     */
    public static final String get_error_punch_the_card_unhandled_service = Enviroment + "epAttenController.do?getUnHandledExceptionAttenDatas";

    /**
     * 获取已处理异常打卡信息
     */
    public static final String get_error_punch_the_card_handled_service = Enviroment + "epAttenController.do?getHandledExceptionAttenDatas";
    /**
     * 版本更新
     */
    public static final String version_update = Enviroment + "upLoadFileController.do?getLatestVersionNumber";

    /**
     * 搜索用户（根据姓名）
     */
    public static final String search_user = Enviroment + "epUserController.do?searchEpUserByName";

    /**
     * 切换环境
     *
     * @param e
     */
    public void ChangeEnviroment(int e) {
        if (e == 0) {
            Enviroment = "http://192.168.1.225:8080/eplat-uat/";
        } else if (e == 1) {
            Enviroment = "http://hr.e-lead.cn:8080/eplat-pro/";
        }
    }
}
