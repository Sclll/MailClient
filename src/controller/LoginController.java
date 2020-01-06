package controller;

import java.io.IOException;

import com.sun.javafx.robot.impl.FXRobotHelper;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Authority;

public class LoginController {
	@FXML private Text hint;
	@FXML private TextField address;
	@FXML private PasswordField password;
	@FXML private Button login;
	
	@FXML protected void clickButton(ActionEvent event) {
		String result = Authority.mailLogin(address.getText(), password.getText());
		if (result == "success") {
			ObservableList<Stage> stage = FXRobotHelper.getStages();
			Scene scene = null;
			try {
				scene = new Scene(FXMLLoader.load(getClass().getResource("../view/MainView.fxml")));
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			stage.get(0).setScene(scene);
		}else {
			hint.setText("Login Wrong!");
		}
	}
	
}
