package base.join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JoinMain extends Application {

	 Connection conn = null;
	 PreparedStatement pstmt = null;
	 ResultSet rs = null;
	
	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			Parent root = FXMLLoader.load(
					getClass().getResource("Join.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("Join");
			primaryStage.show();
			
		} catch (Exception e) {}
		
		
	}

	public static void main(String[] args) {
		
		launch(args);
		
		
	}
}
