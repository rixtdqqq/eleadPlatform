package com.elead.card;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public enum CardEnums {

    //列表
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

    SignCard,//打卡卡片

    //多媒体
    MediaCard                 //多媒体卡（视频，pdf等）
}
