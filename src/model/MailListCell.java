package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MailListCell extends ListCell<MimeMessage>{
	@Override
	public void updateItem(MimeMessage message,boolean empty) {
		super.updateItem(message,empty);
		if (message != null) {
			BorderPane cell = new BorderPane();
			 
			String mailfrom = "";
			String maildate = "";
			String mailcontent = "";
			try {
				mailfrom = ParseMail.getFrom(message);
				maildate = ParseMail.getDate(message);
				mailcontent = ParseMail.getSubject(message);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace(); 
			}
			 
             Text from = new Text(mailfrom);
             from.setFont(Font.font(18));
             from.setFill(Color.BLACK);
             cell.setLeft(from);
             
             Text date = new Text(maildate);
             date.setFont(Font.font(16));
             date.setFill(Color.BLACK);
             cell.setRight(date);
              
             if (mailcontent.length()>24) mailcontent = mailcontent.substring(0, 23) +"...";
             Text content = new Text(mailcontent);
             content.setFont(Font.font(14));
             content.setFill(Color.BLACK);
             content.resize(100, 20);
             cell.setBottom(content);
             
             setGraphic(cell);
		}
	}
}
