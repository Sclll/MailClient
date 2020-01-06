package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import controller.LoginController;

public class Main extends Application {
 
   @Override
   public void start(Stage primaryStage) {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
       loader.setController(new LoginController());
       Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
       Scene scene = new Scene(root, 400, 300);
 
       primaryStage.setTitle("Niubility Mail");
       primaryStage.setScene(scene);
       primaryStage.show();
   }
 
   public static void main(String[] args) {
       launch(args);
   }
}