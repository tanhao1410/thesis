package com.github.tanhao1410.thesis.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author tanhao
 * @date 2021/02/25 15:07
 */
@Component
public class EMailService {

    public void sendMail(String title,String content,String receiver) throws Exception {

        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host", "smtp.qq.com");

        properties.setProperty("mail.transport.protocol", "smtp");

        properties.setProperty("mail.smtp.auth", "true");


        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("619046217@qq.com", "16位授权码");
            }
        });

        //开启debug模式
        session.setDebug(true);

        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器
        transport.connect("smtp.qq.com", "619046217@qq.com", "16位授权码");

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("619046217@qq.com"));

        //邮件接收人
        //mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(reciver));

        if (StringUtils.isNotEmpty(receiver)) {
            String[] split = receiver.split(",");
            for (int i = 0; i < split.length; i++) {
                String recPer1Address = split[i].substring(split[i].indexOf("<") + 1, split[i].indexOf(">"));
                String recPer1Name = split[i].substring(0, split[i].indexOf("<"));
                mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recPer1Address, recPer1Name, "UTF-8"));
            }
        }

        //邮件标题
        mimeMessage.setSubject(title);

        //邮件内容
        mimeMessage.setContent(content, "text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();


    }
}
