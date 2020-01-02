package Model;

import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;

public class QueryMail {
	private ArrayList<Message> mails;
	
	public QueryMail() {
		mails = new ArrayList<Message>();
	}
	
	public ArrayList<Message> check() {
		try {
			//创建pop3存储对象
			Store store = Authority.getPop3Session().getStore("pop3s");
			store.connect(Authority.getHost(), Authority.getAddress(), Authority.getPassword());
			//创建folder对象
			Folder emailFolder = store.getFolder("");
			emailFolder.open(Folder.READ_ONLY);
			
			Message[] messages = emailFolder.getMessages();

			for (int i = 0, n = messages.length; i < n; i++) {
			   mails.add(messages[i]);
			}
			
		    emailFolder.close(false);
		    store.close();
	    } catch (NoSuchProviderException e) {
	       e.printStackTrace();
	    } catch (MessagingException e) {
	       e.printStackTrace();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
		return mails;
	}
}
