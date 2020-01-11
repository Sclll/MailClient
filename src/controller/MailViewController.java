package controller;

import java.io.IOException;

import javax.mail.Message;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Authority;
import model.MailListCell;
import model.QueryMail;

public class MailViewController {
	@FXML private ListView maillist;
	@FXML private Button sendmail;
	@FXML private Button replymail;
	@FXML private Button forwardmail;
	@FXML private Button deletemail;
	@FXML private Label address;
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
		
	}
	
	@FXML 
	public void forwardEvent(ActionEvent event) {
		
	}
	
	@FXML 
	public void deleteEvent(ActionEvent event) {
		
	}
}
