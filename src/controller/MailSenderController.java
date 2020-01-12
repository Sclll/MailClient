package controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		Properties props = new Properties(); 
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.163.com");   
        props.put("mail.smtp.port","25");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Session smtpsession = Session.getInstance(props,Authority.getAuthor());
        smtpsession.setDebug(true);
		
		MimeMessage message = new MimeMessage(smtpsession);
		message.setFrom(Authority.getAddress());
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver.getText()));
		message.setRecipients(Message.RecipientType.CC, copy.getText());
		message.setSubject(subject.getText());
		BodyPart part = new MimeBodyPart();
		MimeMultipart multipart = new MimeMultipart();
		part.setContent(editor.getHtmlText(),"text/html");
		multipart.addBodyPart(part);
		message.setContent(multipart);
		message.saveChanges();
		
		try {
			Transport transport = smtpsession.getTransport();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.titleProperty().set("错误");
            alert.headerTextProperty().set("发送邮件失败，请检查关键信息！");
            alert.showAndWait();
            return;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.titleProperty().set("成功");
        alert.headerTextProperty().set("发送邮件成功！");
        alert.showAndWait();
		Stage stage = (Stage)send.getScene().getWindow();
		stage.close();
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
