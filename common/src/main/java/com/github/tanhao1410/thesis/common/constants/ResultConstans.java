package com.github.tanhao1410.thesis.common.constants;

/**
 * @author hushawen
 * @date 2020/5/25 10:56
 */
public class ResultConstans {

    /**
     * 0:成功,-1，请求异常，-99：需要登陆;1:当前普通用户，无权限，2：当前管理员用户，无权限，3：当前超管用户，无权限
     */
    public static final Integer SUCCESS_CODE=0;
    public static final  Integer FIAL_CODE=-1;
    public static final  Integer FIAL_NO_TOKEN_CODE=-99;
    public static final  Integer FIAL_NOR_NO_AUTH_CODE=1;
    public static final  Integer FIAL_ADMIN_NO_AUTH_CODE=2;
    public static final  Integer FIAL_SUP_ADMIN_NO_AUTH_CODE=3;

    public static final String SUCCESS_MSG="SUCCESS";
    public static final String FIAL_APARAM="参数异常";
    public static final String FIAL_USER_IS_HAD="用户名已经存在";
    public static final String FIAL_ROLE_IS_HAD="角色名已存在";
    public static final String FIAL_ROLE_NOT_HAD="角色不存在";
    public static final String NO_USER="指定用户不存在";
    public static final String NO_DATA="没有查询到数据";
    public static final String FIAL_API_FAIL="接口异常，请稍后再试";
    public static final String FIAL_APPID="缺少appId，或者appId不合法";
    public static final String FIAL="fail";
    public static final String SAME_PASSWORD="重置密码与原密码相同";
    public static final String WRONG_PASSWORD="密码错误";
    public static final String NOT_SAME_PASSWORD="密码不一致";
    public static final String NOT_NORMAL_MOBILENUMBER="手机号码不合法";
    public static final String FIAL_ADMIN_NO_AUTH_MSG="没有权限";
    public static final String FTIL_ADMIN_CONTACT_MSG = "您的帐户已被禁用，请您及时联系管理员";
    public static final String FITL_USER_BAN_MSG = "用户不可用";



    /**
     * 无对象返回值指定  0成功1失败2-不允许删除
     */
    public static final Integer RESULT_SUCCESS_CODE=0;
    public static final Integer RESULT_FIAL_CODE=1;
    public static final Integer RESULT_NOT_ALLOW_DEL_CODE=2;


}
