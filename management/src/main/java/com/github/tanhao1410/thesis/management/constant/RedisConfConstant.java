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
	 * 秒级别
	 */
	public static final Integer EXPIRED_TIME_SECOND_1 = 1;  // 1秒
	public static final Integer EXPIRED_TIME_SECOND_10 = 10; // 10秒
	public static final Integer EXPIRED_TIME_SECOND_30 = 30; // 30秒

	
	//RK   ACT  RC
	/**
	 * 短信-登陆前缀.
	 */
	public static final String USER_LOGIN_TOKEN_PRE = "RK_LOGIN_TOKEN_PRE_";

	public static final String USER_LOGIN_TOKEN_OPEN_PRE = "USER_LOGIN_TOKEN_OPEN_PRE";

	public static final String USER_LOGIN_LOCK_KEY = "USER_LOGIN_LOCK_KEY_";




	public static final String REDIS_MESSAGE_DEL_CATLOG = "REDIS_MESSAGE_DEL_CATLOG";
	public static final String AIDECISION_FINE_RK_LOGIN_TOKEN_PRE = "xx";
}
