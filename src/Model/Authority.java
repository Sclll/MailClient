package model;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

public class Authority {
	private static String myEmailAccount = "";
    private static String myEmailPassword = "";	
    private static Session _session;
    private static Session _smtpsession;
    private static Session _pop3session;
    private static Authenticator auth;
	
	public static String mailLogin(String mailaddress,String password){
		try {
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3s");
		    properties.put("mail.pop3s.host", "pop.163.com");
		    properties.put("mail.pop3s.port", "995");
		    properties.put("mail.pop3s.starttls.enable", "true");
		    properties.put("mail.pop3s.socketFactory.class",
		            "javax.net.ssl.SSLSocketFactory" ); 
		    properties.put("mail.pop3s.auth", "true");
		    Session tmpsession = Session.getDefaultInstance(properties,new Authenticator() {
		    	@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailaddress,password);
				}
		    });
			tmpsession.setDebug(true);
			//创建pop3存储对象
			Store store = tmpsession.getStore("pop3s");
			store.connect();
			//创建folder对象
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
		    emailFolder.close(false);
		    store.close();
	    } catch (NoSuchProviderException e) {
	       e.printStackTrace();
	       return "error1";
	    } catch (MessagingException e) {
	       e.printStackTrace();
	       return "error2";
	    } catch (Exception e) {
	       e.printStackTrace();
	       return "error3";
	    }
		myEmailAccount = mailaddress;
		myEmailPassword = password;
		return "success";
	}
	
	public static Authenticator getAuthor() {
		if (auth == null) {
			auth = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myEmailAccount,myEmailPassword);
				}
			};
		}
		return auth;
	}
	
	public static String sendMail(Message message) {
		Transport transport;
		try {
			transport = getSmtpSession().getTransport();
			transport.connect();
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
	
	public static String getPop3Host() {
		return "pop.163.com";
	}
	
	public static Session getSession() {
		if (_session == null) {
			Properties props = new Properties(); 
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.host", "smtp.163.com");   
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.store.protocol", "pop3");
		    props.put("mail.pop3s.host", "pop.163.com");
		    props.put("mail.pop3s.port", "995");
		    props.put("mail.pop3.starttls.enable", "true");
	        _smtpsession = Session.getDefaultInstance(props,getAuthor());
	        _smtpsession.setDebug(true);
		}
		return _session;
	}
	
	public static Session getSmtpSession() {
		if (_smtpsession == null) {
			Properties props = new Properties(); 
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.host", "smtp.163.com");   
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        _smtpsession = Session.getDefaultInstance(props,getAuthor());
	        _smtpsession.setDebug(true);
		}
        return _smtpsession;
	}
	
	public static Session getPop3Session() {
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "pop3");
	    properties.put("mail.pop3.host", "pop.163.com");
	    properties.put("mail.pop3.port", "995");
	    properties.put("mail.pop3.starttls.enable", "true");
	    _pop3session = Session.getDefaultInstance(properties,getAuthor());
	    return _pop3session;
	}
}
