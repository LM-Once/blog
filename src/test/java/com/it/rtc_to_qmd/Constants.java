package com.it.rtc_to_qmd;


public class Constants {

    /**
     *测试环境
     */
    private static final  String CONFIG_ERP_API_URL = "http://rtchelpertest.myoppo.com";

    /**
     * app_id
     * 统一帐号系统下发给业务系统的标识
     */
    private static final  String CONFIG_ERP_API_APPID = "rtc";

    /**
     *secret
     * 统一帐号系统下发给业务系统的密钥
     */
    private static final  String CONFIG_ERP_API_SECRET = "rtcapi";

    /**
     * 编号 IREQ_001
     * moduleUrl 接口名称
     *创建用例缺陷
     */
    private static final  String QMDAPI_REQUEST_CREATE = "/rtcapi/qmdapi/request/create";

    /**
     * 编号 IREQ_002
     * moduleUrl 接口名称
     *获取项目区域
     */
    private static final  String QMDAPI_PROJECT = "/rtcapi/qmdapi/project";

    /**
     * 编号 IREQ_003
     * moduleUrl 接口名称
     *获取团队
     */
    private static final String QMDAPI_TEAM = "/rtcapi/qmdapi/team";

    /**
     * 编号 IREQ_004
     * moduleUrl 接口名称
     *获取工作项
     */
    private  static final String QMDAPI_REQUEST = "/rtcapi/qmdapi/request";

    /**
     * 编号 IREQ_005
     * moduleUrl 接口名称
     *获取时间线iteration
     */
    private  static final String QMDAPI_ITERATION = "/rtcapi/qmdapi/iteration";

    /**
     * 编号 IREQ_006
     * moduleUrl 接口名称
     *获取类型数据(如归档依据)
     */
    private static final  String QMDAPI_CATEGORY = "/rtcapi/qmdapi/category";

    /**
     * 编号 IREQ_007
     * moduleUrl 接口名称
     *获取工作项属性
     */
    private static final  String QMDAPI_ATTRIBUTE = "/rtcapi/qmdapi/attribute";

    /**
     * 编号 IREQ_008
     * moduleUrl 接口名称
     *获取项目区域属性枚举
     */
    private static final  String QMDAPI_ENUMERATION = "/rtcapi/qmdapi/enumeration";

    /**
     * 编号 IREQ_009
     * moduleUrl 接口名称
     *获取工作项类型
     */
    private static  final String QMDAPI_REQUEST_TYPE = "/rtcapi/qmdapi/request/type";

    /**
     * 编号 IREQ_010
     * moduleUrl 接口名称
     *获取工作项状态
     */
    private static final  String QMDAPI_REQUEST_STATE = "/rtcapi/qmdapi/request/state";

    /**
     * 编号 IREQ_011
     * moduleUrl 接口名称
     *获取工作项历史记录
     */
    private  static final String QMDAPI_REQUEST_HISTORY = "/rtcapi/qmdapi/request/history";

    /**
     * 编号 IREQ_012
     * moduleUrl 接口名称
     *获取工作项评论
     */
    private static final  String QMDAPI_REQUEST_COMMENT = "/rtcapi/qmdapi/request/comment";

    /**
     * app_id 标识
     * @return
     */
    public static String getConfigErpApiAppid(){
        return CONFIG_ERP_API_APPID;
    }

    /**
     * secret 秘钥
     * @return
     */
    public static String getConfigSecret(){
        return CONFIG_ERP_API_SECRET;
    }

    /**
     * 请求路径
     * @return
     */
    public static String getConfigApiUrl(){
        return CONFIG_ERP_API_URL;
    }

    public static  String getApiRequest (){
        return QMDAPI_REQUEST;
    }

    public static String getApiProject(){
        return QMDAPI_PROJECT;
    }

    public static String getApiComment(){

        return QMDAPI_REQUEST_COMMENT;
    }
}
