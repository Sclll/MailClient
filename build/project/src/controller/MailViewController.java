package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

import com.sun.javafx.robot.impl.FXRobotHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Authority;
import model.MailListCell;
import model.ParseMail;
import model.QueryMail;

public class MailViewController {
	@FXML private ListView maillist;
	@FXML private Button sendmail;
	@FXML private Button replymail;
	@FXML private Button forwardmail;
	@FXML private Button deletemail;
	@FXML private Label address;
	@FXML private WebView webview;
	private int index;
	private ObservableList list = FXCollections.observableArrayList();
	
	public MailViewController() {
		ObservableList<Stage> stage = FXRobotHelper.getStages();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		loader.setController(this);
		try {
			Parent parent = (Parent)loader.load();
			Scene scene = new Scene(parent,1280,720);
			stage.get(0).setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		address.setText(Authority.getAddress());
		initListView();
	}
	
	public void initListView() {
		list.setAll(QueryMail.getAllMail());
		maillist.setItems(list);
		maillist.setCellFactory(new Callback<ListView<MimeMessage>,ListCell<MimeMessage>>(){
			@Override
			public ListCell<MimeMessage> call(ListView<MimeMessage> arg0) {
				return new MailListCell();
			}
			
		});
		
		maillist.getSelectionModel().selectedItemProperty().addListener((Observable,oldValue,newValue) -> {
			setContent((Message)newValue);
			index = maillist.getItems().indexOf(newValue);
		});
	}

	public void setContent(Message msg) {
		
		WebEngine engine = webview.getEngine();
		StringBuffer contents = new StringBuffer(30);
        try {
			ParseMail.getContent(msg, contents);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
		engine.loadContent(contents.toString());
	}
	
	@FXML 
	public void sendEvent(ActionEvent event) {
		MailSenderController controller = new MailSenderController();
		Stage stage = new Stage();
		stage.setTitle("发送邮件");
		stage.setScene(controller.getScene());
		stage.show();
	}
	
	@FXML 
	public void replyEvent(ActionEvent event) {
		MailSenderController controller = new MailSenderController();
		Stage stage = new Stage();
		stage.setTitle("发送邮件");
		stage.setScene(controller.getScene());
		try {
			controller.setTo(ParseMail.getFromAddress((MimeMessage)maillist.getItems().get(index)));
			controller.setSubject("回复："+ParseMail.getSubject((MimeMessage)maillist.getItems().get(index)));
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		stage.show();
	}
	
	@FXML 
	public void forwardEvent(ActionEvent event) {
		MailSenderController controller = new MailSenderController();
				Stage stage = new Stage();
		stage.setTitle("发送邮件");
		stage.setScene(controller.getScene());
		try {
			controller.setSubject("转发："+ParseMail.getSubject((MimeMessage)maillist.getItems().get(index)));
			StringBuffer content = new StringBuffer(30);
			ParseMail.getContent((Part)maillist.getItems().get(index), content);
			controller.setContent(content.toString());
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.show();
	}
	
	@FXML 
	public void deleteEvent(ActionEvent event) {
		maillist.getItems().remove(index);
		maillist.refresh();
		try {
			ParseMail.deleteMessage((MimeMessage)maillist.getItems().get(index));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
