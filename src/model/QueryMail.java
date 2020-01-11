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
import javax.mail.internet.MimeMessage;

public class QueryMail {
	
	private static ArrayList<MimeMessage> list = new ArrayList<>();
	private static Store store;
	private static Folder emailFolder;
	
	
	public static ArrayList<MimeMessage> getAllMail(){
		try {
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3s");
		    properties.put("mail.pop3s.host", "pop.163.com");
		    properties.put("mail.pop3s.port", "995");
		    properties.put("mail.pop3s.starttls.enable", "true");
		    properties.put("mail.pop3s.socketFactory.class",
		            "javax.net.ssl.SSLSocketFactory" ); 
		    properties.put("mail.pop3s.auth", "true");
		    Session tmpsession = Session.getDefaultInstance(properties,Authority.getAuthor());
			tmpsession.setDebug(true);
			//创建pop3存储对象
			store = tmpsession.getStore("pop3s");
			store.connect();
			//创建folder对象
			emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			Message[] messages = emailFolder.getMessages();
			for (int i = 0; i < messages.length; i++) {
	            list.add((MimeMessage)messages[i]);
	         }
			
	    } catch (NoSuchProviderException e) {
	       e.printStackTrace();
	    } catch (MessagingException e) {
	       e.printStackTrace();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
		
		return list;
	}
	
	public static void finish() {
		try {
			emailFolder.close(false);
			store.close();
		} catch (MessagingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
}
