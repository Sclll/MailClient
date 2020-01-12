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
	//	String result = Authority.mailLogin(address.getText(), password.getText());
		String result = Authority.mailLogin("rogerscl@163.com", "scl1123");
		if (result == "success") {
			new MailViewController();
		}else if (result == "error1") {
			hint.setText("error1"); 
		}else if (result == "error2") {
			hint.setText("用户名或密码错误！");
		}else if (result == "error3") {
			hint.setText("error3");
		}
	}
	
}
