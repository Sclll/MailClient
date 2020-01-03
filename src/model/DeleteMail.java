package model;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;

public class DeleteMail {
	public static void delete(Message message) {
		try {
			Store store = Authority.getPop3Session().getStore("pop3s");
			store.connect();
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			message.setFlag(Flags.Flag.DELETED, true);
			// expunges the folder to remove messages which are marked deleted
			emailFolder.close(true);
			store.close();
		} catch (NoSuchProviderException e) {
		    e.printStackTrace();
		} catch (MessagingException e) {
		   e.printStackTrace();
		} 
	}
}
