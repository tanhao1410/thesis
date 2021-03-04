package com.github.tanhao1410.thesis.common.constants;

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

	public static final String USER_LOGIN_TOKEN_PRE = "RK_LOGIN_TOKEN_PRE_";


}
