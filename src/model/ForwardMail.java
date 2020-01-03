package model;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ForwardMail {         
	public static void forwordMail(Message message,String to) {
		 try {
		   Message forward = new MimeMessage(Authority.getSmtpSession());
		   // Fill in header
		   forward.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		   forward.setSubject("Fwd: " + message.getSubject());
		   forward.setFrom(message.getRecipients(Message.RecipientType.TO)[0]);
		   // Create the message part
		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   // Create a multipart message
		   Multipart multipart = new MimeMultipart();
		   // set content
		   messageBodyPart.setContent(message, "message/rfc822");
		   // Add part to multi part
		   multipart.addBodyPart(messageBodyPart);
		   // Associate multi-part with message
		   forward.setContent(multipart);
		   forward.saveChanges();
		   // Send the message
		   Authority.sendMail(message);	   
	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	}
}
