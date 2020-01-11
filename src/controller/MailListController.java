package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;

public class MailListController {
	@FXML private GridPane item;
	@FXML private Label fromlabel;
	@FXML private Label datelabel;
	@FXML private Label contentlabel;
	private Parent root;
	
	public MailListController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MailListCells.fxml"));
		loader.setController(this);
		
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root,400,100);
	}
	
	public void setFrom(String from) {
		fromlabel.setText(from);
	}
	
	public void setDate(String date) {
		datelabel.setText(date);
	}
	
	public void setContent(String content) {
		contentlabel.setText(content);
	}
	
	public GridPane getItem() {
		return item;
	}
}
