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
			Pop3Session session = Pop3Session.getFactorySession();
			store = session.getSession().getStore("pop3s");
			store.connect();
			//创建folder对象
			emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			Message[] messages = emailFolder.getMessages();
			for (int i = messages.length-1; i >= 0; i--) {
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
