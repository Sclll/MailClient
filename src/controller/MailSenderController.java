package controller;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.javafx.robot.impl.FXRobotHelper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import model.Authority;

public class MailSenderController {
	@FXML private TextField receiver;
	@FXML private TextField copy;
	@FXML private TextField subject;
	@FXML private HTMLEditor editor;
	@FXML private Button send;
	private Scene scene;
	
	public MailSenderController() {
		
	}
	
	public Scene getScene() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMail.fxml"));
		loader.setController(this);
		try {
			Parent parent = (Parent)loader.load();
			scene = new Scene(parent,1280,720);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scene;
	}
	
	@FXML
	public void sendEvent(ActionEvent event) throws MessagingException {
		MimeMessage message = new MimeMessage(Authority.getSmtpSession());
		message.setFrom(Authority.getAddress());
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver.getText()));
		message.setRecipients(Message.RecipientType.CC, copy.getText());
		message.setSubject(subject.getText());
		BodyPart part = new MimeBodyPart();
		MimeMultipart multipart = new MimeMultipart();
		part.setContent(editor.getHtmlText(),"text/html");
		multipart.addBodyPart(part);
		message.setContent(multipart);
		String result = Authority.sendMail(message);
		System.out.println(result);
	}
	
	public void setTo(String to) {
		receiver.setText(to);
	}
	
	public void setSubject(String sub) {
		subject.setText(sub);
	}
	
	public void setContent(String content) {
		editor.setHtmlText(content);
	}
}
