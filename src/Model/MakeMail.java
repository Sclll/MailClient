package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MakeMail {
    private String receiverAddress;
    private String myNickname;
    private String receiverNickname;
    private ArrayList<String> recipients;
    private String subject;
    private String content;
    private MimeMessage message;
    
    public MakeMail() {
    	receiverAddress = "";
    	myNickname = "网络用户";
    	receiverNickname = "网络用户";
    	recipients = new ArrayList<String>();
    	subject = "";
    	content = "";
    	message = new MimeMessage(Model.Authority.getSmtpSession());
    }
	
    public String sendMessage() throws Exception {
    	// 1. From： 发信人
        message.setFrom(new InternetAddress(Model.Authority.getAddress(), myNickname, "UTF-8"));
 
        // 2. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverAddress, receiverNickname, "UTF-8"));
 
        // 3. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
 
        // 4. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        
        // 5. 设置发件时间
        message.setSentDate(new Date());
 
        // 6. 保存设置
        message.saveChanges();
 
        return Model.Authority.sendMail(message);
    }
    
    public void setReceiverAddress(String address) {
    	receiverAddress = address;
    }
    
    public void setMyNickname(String nickname) {
    	myNickname = nickname;
    }
    
    public void setReceiverNickname(String nickname) {
    	receiverNickname = nickname;
    }
    
    public void setRecipients(ArrayList<String> list) {
    	recipients = (ArrayList<String>) list.clone();
    }
    
    public void setSubject(String subject) {
    	this.subject = subject;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
}