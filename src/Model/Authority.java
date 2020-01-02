package Model;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

public class Authority {
	private static String myEmailAccount = "";
    private static String myEmailPassword = "";	
    private static Session _smtpsession;
    private static Session _pop3session;
	
	public String mailLogin(String mailaddress,String password){
		Transport transport;
		try {
			transport = getSmtpSession().getTransport();
			transport.connect(mailaddress, password);
			transport.close();
		} catch (MessagingException e) {
			return "error!";
		}
		myEmailAccount = mailaddress;
		myEmailPassword = password;
		return "success";
	}
	
	public static String sendMail(Message message) {
		Transport transport;
		try {
			transport = getSmtpSession().getTransport();
			transport.connect(myEmailAccount, myEmailPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			return "error!";
		}
		return "success";
	}
	
	public static String getAddress() {
		return myEmailAccount;
	}
	
	public static String getHost() {
		return "smtp.163.com";
	}
	
	public static String getPassword() {
		return myEmailPassword;
	}
	
	public static Session getSmtpSession() {
		if (_smtpsession == null) {
			Properties props = new Properties(); 
	        props.setProperty("mail.transport.protocol", "smtp");
	        props.setProperty("mail.smtp.host", "smtp.163.com");   
	        props.setProperty("mail.smtp.auth", "true"); 
	        
	        _smtpsession = Session.getInstance(props);
	        _smtpsession.setDebug(true);
		}
        return _smtpsession;
	}
	
	public static Session getPop3Session() {
		Properties properties = new Properties();
	    properties.put("mail.pop3.host", "pop.163.com");
	    properties.put("mail.pop3.port", "995");
	    properties.put("mail.pop3.starttls.enable", "true");
	    _pop3session = Session.getDefaultInstance(properties);
	    return _pop3session;
	}
}
