package model;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

public class ReplyToEmail {
	public static void replToEmail(Message message,String content) {
		try {
          Message replyMessage = new MimeMessage(Authority.getSmtpSession());
          replyMessage = (MimeMessage) message.reply(false);
          replyMessage.setFrom(message.getAllRecipients()[0]);
          replyMessage.setText(content);
          replyMessage.setReplyTo(message.getReplyTo());
          // Send the message
	      Authority.sendMail(replyMessage);
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	}
}
