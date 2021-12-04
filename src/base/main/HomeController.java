package base.main;

import java.net.URL;
import java.util.ResourceBundle;

import base.login.LoginController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class HomeController implements Initializable {

	@FXML private Label txtMs;
	@FXML private TextField txtF2;
	
	Timeline tm1 = new Timeline();
	
	LoginController lc;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HomeMain.thread.homeController = this;
		
		KeyValue kv1 = new KeyValue(txtMs.opacityProperty(), 0);
		KeyFrame kf1 = new KeyFrame(Duration.millis(1000), kv1);
		tm1.getKeyFrames().add(kf1);
		tm1.setCycleCount(Timeline.INDEFINITE);
		tm1.setAutoReverse(true);
		tm1.play();
		Platform.runLater(()->{
			txtF2.requestFocus();
		});
		txtF2.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {


			@Override
			public void handle(KeyEvent arg0) {
				
				if(arg0.getCode() == KeyCode.F2) {
							tm1.stop();							txtMs.setOpacity(1);
							txtMs.setStyle("-fx-background-color:black");
							
							
									Stage stage = new Stage();
									Parent root = null;
									try {
										root = FXMLLoader.load(
											getClass().getResource("../login/Login.fxml")
										);
									} catch (Exception e) {}
									
									stage.setScene(new Scene(root));
									Platform.runLater(()-> {
										try {
											Thread.sleep(300);
										} catch (InterruptedException e1) {}
										stage.show();
										((Stage)txtF2.getScene().getWindow()).close();	
									});			
				}
			}
		});
	}
}
