package com.elead.card;


/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class ResConstants {
    public static final String VISITS_CARD = "list_item_multiplecirclecard";
    public static final String THE_PEO_PLE_STABLE_OF = "list_item_corrugatelinecard";
    public static final String TB_SELECT_CARD = "list_item_select_tb";
    public static final String SIMPLE_ARC_CARD = "list_item_simple_arc";
    public static final String PROJECT_CARD = "list_item_project";
    public static final String MOREAPP_CARD = "list_item_moreappcard";
    public static final String LINECHART_CARD = "list_item_line_chart";
    public static final String HOTSALE_CARD = "list_item_hotsale";
    public static final String DISTRIBUTION_CARD = "list_item_distribution";
    public static final String ANNULAR_CHART = "";

    public static final String REQUIRE_TB_TAB = "list_require_item_tab";
    public static final String DELIVERY_QUALITY_TAB = "list_card_deliverry_quality_tab";
    public static final String DELIVERY_DESTORY_TAB = "list_quality_destory_tab";
    public static final String REQUIRE_SIMPLE_ARC = "list_require_simple_arc_tab";
    public static final String PO_PROJECT_TB = "list_po_project_tab";
    public static final String PO_pROJECT_INFORMATION_TAB = "list_item_po_information_card";

    public static final String SPECIAL_PUBCH_CARD = "list_item_special_pubch_card";


    public static final String INDUSTRIAL_DESIGIN = "card_industrial_design";


    public static final String TEXT_IMAGE_CARD = "list_item_infodesrightpiccard";//左边文字又图片card


    /*   //列表
        TextListCard,              //文本列表
        SubmenuListCard,           //子菜单列表
        ProgressListCard,          //进度条列表
        EntryListCard,             //条目列表（多行）
        MiniCardsCard,             //mini卡组
        ColumnCard,                //栏目(九宫格)
        FormCard,                  //表格（多列）

        //绘图类
        SingleCircleCard,          //单圆环
        MultipleCircleCard,        //多圆环
        LineChartCard,             //折线图,可带通过不通过
        FillLineChartCard,         //折线图带底部填充
        CurvesCard,                //曲线图
        CorrugateLineCard,         //波纹线
        PieChartCard,              //饼状图,顶部带日期
        PieChartTouchCard,         //饼状图,带点击效果
        BarChartCard,              //柱状图
        LinePieChartCard,          //饼状图带直线标注

        //特殊卡
        HorizonProgressCard,       //水平进度条
        TabItemCard,               //tab切换
        ClockInCard,               //打卡
        SeparatorCard,             //分隔线
        AdvertCard,                //广告卡

        //文本类
        InfoCard,                  //纯文本带标题


        InfoChartCard,             //带图表文本
        InfoThreePicsCard,         //资讯卡（3张图）
        InfoDesThreePicsCard,      //资讯卡（带描述，3张图）
        InfoRightPicCard,          //资讯卡（右侧图）
        InfoDesRightPicCard,       //资讯卡（带描述,右侧图）


        ParameterListCard,         //参数卡,单排横向列表
        ParameterMixCard,          //参数卡,混合卡
        ParameterCategoryCard,     //参数卡,标识组+文本描述


        //多媒体
        MediaCard                 //多媒体卡（视频，pdf等）*/

    public static String cardType2Res(String cardType) {
        if (cardType.equals(CardEnums.InfoRightPicCard)) {

        }
        if (cardType.equals(CardEnums.TabItemCard.toString())) {//tab切换
            return "list_item_select_tb";
        } else if (cardType.equals(CardEnums.MultipleCircleCard.toString())) { //多圆环
            return "list_item_multiplecirclecard";
        } else if (cardType.equals(CardEnums.SeparatorCard.toString())) { //分割线
            return "list_item_separatorcard";
        } else if (cardType.equals(CardEnums.CorrugateLineCard.toString())) { //波纹线
            return "list_item_corrugatelinecard";
        } else if (cardType.equals(CardEnums.SubmenuListCard.toString())) {//更多应用
            return "list_item_moreappcard";
        } else if (cardType.equals(CardEnums.InfoRightPicCard.toString())) {//
            return "list_item_uc_one";//首页带一个图片的卡片
        } else if (cardType.equals(CardEnums.InfoThreePicsCard.toString())) {
            return "list_item_uc_three";//首页带三个图片的卡片
        } else if (cardType.equals(CardEnums.MultipleCircleCard.toString())) {
            return "list_require_simple_arc_tab";//需求里面的圆环(需求规范)
        } else if (cardType.equals(CardEnums.ColumnCard.toString())) {
            return "list_card_deliverry_quality_tab";//交付质量
        } else if (cardType.equals(CardEnums.EntryListCard.toString())) {
            return "list_item_po_information_card";//基本信息
        } else if (cardType.equals(CardEnums.FormCard.toString())) {
            return "list_item_operate_demand";//list_item_operate_quality两个相同
        } else if (cardType.equals(CardEnums.HorizonProgressCard.toString())) {
            return "list_item_horiaonprogress_point";//PO概况里面的状态点线图
        } else if (cardType.equals(CardEnums.LinePieChartCard.toString())) {
            return "list_item_special_pubch_card";//特殊打卡
        } else if (cardType.equals(CardEnums.MediaCard.toString())) {
            return "list_item_mediacard";
        } else if (cardType.equals(CardEnums.ParameterListCard.toString())) {
            return "list_item_parameterlistcard";
        } else if (cardType.equals(CardEnums.InfoDesRightPicCard.toString())) {
            return "list_item_infodesrightpiccard";
        } else if (cardType.equals(CardEnums.InfoDesThreePicsCard.toString())) {
            return "list_item_infodesthreepicscard";
        } else if (cardType.equals(CardEnums.InfoChartCard.toString())) {
            return "list_item_infochartcard";
        } else if (cardType.equals(CardEnums.InfoCard.toString())) {
            return "list_item_infocard";
        } else if (cardType.equals(CardEnums.AdvertCard.toString())) {
            return "list_item_advertcard";
        } else if (cardType.equals(CardEnums.ParameterMixCard.toString())) {
            return "list_item_parameter_mixcard";//技术参数
        } else if (cardType.equals(CardEnums.ParameterCategoryCard.toString())) {
            return "list_item_parametercate_category";//设计亮点
        } else if (cardType.equals(CardEnums.LineChartCard.toString())) {//高温或压力测试
            return "list_item_ep_card_type_line_chart";//目前写死2个，之后根据数据类型设置
        } else if (cardType.equals(CardEnums.BarChartCard.toString())) {//柱状图，温湿度
            return "list_item_ep_card_type_bar_chart";
        } else if (cardType.equals(CardEnums.SignCard.toString())) {//打卡卡片
            return "list_item_sign_card";
        }
        return "";
    }

}
