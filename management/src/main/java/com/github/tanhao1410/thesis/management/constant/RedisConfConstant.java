package com.github.tanhao1410.thesis.management.constant;

/**
 * Redis key 常量管理<br/>
 * RD:Redis DB Key<br/>
 * RC:Redis Cache Key<br/>
 * 
 * @author hushawen
 *
 */
public class RedisConfConstant {
	
	
	public static final String LOCKED="locked";
	public static final String UNLOCK="unlock";
	

	/**
	 * Redis Key 分隔符
	 */
	public static final String REDIS_KEY_SEP = "_";

	public static final String USER_LIST_LOCK_KEY = "USER_LIST_LOCK_KEY";

	public static final String USER_ADD_LOCK_KEY = "USER_ADD_LOCK_KEY";

	public static final String USER_EDIT_LOCK_KEY = "USER_EDIT_LOCK_KEY";

	public static final String RESET_USER_PASS_LOCK_KEY = "RESET_USER_PASS_LOCK_KEY";

	public static final String DISABLE_USER_LOCK_KEY = "DISABLE_USER_LOCK_KEY";

	public static final String ADMIN_AUTHO_LOCK_KEY = "ADMIN_AUTHO_LOCK_KEY";

	public static final String SAVE_ROLE_LOCK_KEY = "SAVE_ROLE_LOCK_KEY";

	public static final String SAVE_DEPT_LOCK_KEY = "SAVE_DEPT_LOCK_KEY";

	public static final String EDIT_DEPT_LOCK_KEY = "EDIT_DEPT_LOCK_KEY";

	public static final String AUTH_DEPT_LOCK_KEY = "AUTH_DEPT_LOCK_KEY";

	
	/**
	 * 秒级别
	 */
	public static final Integer EXPIRED_TIME_SECOND_1 = 1;  // 1秒
	public static final Integer EXPIRED_TIME_SECOND_10 = 10; // 10秒
	public static final Integer EXPIRED_TIME_SECOND_30 = 30; // 30秒

	/**
	 * 分钟级别
	 */
	public static final Integer EXPIRED_TIME_MIN_1 = 60;// 1分钟失效
	public static final Integer EXPIRED_TIME_MIN_3 = 180;// 3分钟失效
	public static final Integer EXPIRED_TIME_MIN_5 = 300;// 5分钟失效
	public static final Integer EXPIRED_TIME_MIN_10 = 600;// 10分钟失效
	public static final Integer EXPIRED_TIME_MIN_15 = 900;// 15分钟失效
	public static final Integer EXPIRED_TIME_MIN_30 = 1800;// 30分钟失效

	/**
	 * 小时级别
	 */
	public static final Integer EXPIRED_TIME_HOUR_1 = 60 * 60;// 1个小时

	public static final Integer EXPIRED_TIME_HOUR_2 = 2 * 60 * 60;// 2个小时

	public static final Integer EXPIRED_TIME_HOUR_3 = 3 * 60 * 60;// 3个小时

	public static final Integer EXPIRED_TIME_HOUR_12 = 60 * 60 * 12; // 12个小时

	/**
	 * 周级别.
	 */
	public static final Integer EXPIRED_TIME_WEEK_1 = 60 * 60 * 24 * 7;

	public static final Integer EXPIRED_TIME_WEEK_2 = 60 * 60 * 24 * 14;

	public static final Integer EXPIRED_TIME_WEEK_3 = 60 * 60 * 24 * 21;

	/**
	 * 天级别
	 */
	public static final Integer EXPIRED_TIME_DAY_1 = 60 * 60 * 24; // 一天过期

	/**
	 * 月级别
	 */
	public static final Integer EXPIRED_TIME_MONTH_1 = 60 * 60 * 24 * 30; // 一个月过期
	
	
	//RK   ACT  RC
	/**
	 * 短信-登陆前缀.
	 */
	public static final String USER_LOGIN_TOKEN_PRE = "RK_LOGIN_TOKEN_PRE_";

	public static final String USER_LOGIN_TOKEN_OPEN_PRE = "USER_LOGIN_TOKEN_OPEN_PRE";

	public static final String USER_LOGIN_LOCK_KEY = "USER_LOGIN_LOCK_KEY_";


	/**
	 * api登陆token前缀
	 */
	public static final String AIDECISION_FINE_RK_LOGIN_TOKEN_PRE = "AIDECISION_FINE_RK_LOGIN_TOKEN_PRE_";



	public static final String USER_ROLE_AUTH="USER_ROLE_AUTH_";
	public static final String USER_DEPT_AUTH="USER_DEPT_AUTH_";
}
