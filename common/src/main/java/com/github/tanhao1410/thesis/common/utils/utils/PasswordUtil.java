package com.github.tanhao1410.thesis.common.utils.utils;




import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * @author hushawen
 * @date 2020/6/22 9:15
 */

public class PasswordUtil {


    /**
     * 解析rsa加密后的密码
     * @param rasEcoPpassword
     * @return
     */
    public static String deCodePassword(String rasEcoPpassword,String privateKey) throws Exception{

       if(rasEcoPpassword==null){
           return null;
       }
       try {
           return RSAUtils.decrypt(rasEcoPpassword,privateKey);
       }catch (IllegalArgumentException e){
            e.printStackTrace();
       }
       return rasEcoPpassword;
    }

    /**
     * 密码加密--不可逆
     * @param password
     * @return
     */
    public static String enCodePassword(String password){

        String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
        return md5Str;
    }

    /**
     * 获取RSA加密所需的公钥
     * @return
     */
    public static String getRsaPublicKey(){
        return RSAUtils.publicKey;
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        //生成随你用户名和密码
        for (int i = 0;i < 24;i ++){

            random.setSeed(System.currentTimeMillis());

            StringBuilder sb = new StringBuilder();

            sb.append(( char)('A' + random.nextInt(26)) );
            //首字母大写

            for (int j=0;j < 4;j ++){
                sb.append(( char)('a' + random.nextInt(26)));
            }

            for (int j=0;j < 4;j ++){
                sb.append(( char)('0' + random.nextInt(9)));
            }

            System.out.println( "" + (i + 1) +":"+sb.toString()+":"+enCodePassword(sb.toString()));

            Thread.sleep(100);
        }

    }
}
