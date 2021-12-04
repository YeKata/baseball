package base.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import base.main.HomeMain;
import base.vo.Member;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class LoginController  implements Initializable{

	@FXML private TextField txtId;
	@FXML private PasswordField txtPw;
	@FXML private Button btnLogin;
	@FXML private Hyperlink LkJoin;
	@FXML private Label txtError;
	
	public static Member user;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
	
		HomeMain.thread.loginController = this;
		setLoginEvent();
		

		LkJoin.setOnAction(event -> {
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(
					getClass().getResource("../join/Join.fxml")
				);
			} catch (Exception e) {}
			stage.setScene(new Scene(root));
			stage.show();
		});
		
	}
	
	public void setLoginEvent() {
		txtId.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				txtPw.requestFocus();
			}
		});
		// 로그인 패스워드 작성란에 Enter 키가 눌러지면
		// btnLogin Button action event 실행
		txtPw.setOnKeyPressed(event->{
			if(event.getCode() == KeyCode.ENTER) {
				btnLogin.fire();
			}
		});
		
		btnLogin.setOnAction(event->{
			String id = txtId.getText().trim();
			String pw = txtPw.getText().trim();
			
			if(id.equals("")) {
				txtId.requestFocus();
			}else if(pw.equals("")) {
				txtPw.requestFocus();
			}else {
				Member member = new Member(id,pw);
				member.setOrder(2);
				HomeMain.thread.sendData(member);
			}
			
				
		});
		
	}
	public void setLoginCheck(Member vo) {
		if(vo.isSuccess()) {
			System.out.println("로그인 성공");
			Platform.runLater(()->{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("SUCCESS");
				alert.setContentText("로그인이 완료되었습니다.");
				alert.showAndWait();
				((Stage)btnLogin.getScene().getWindow()).close();
				// 로그인 완료된 사용자 정보 저장
				user = vo;
				System.out.println(user.getmId());
				// 무대 이동
				showGameRoom();
			});
		}else {
			System.out.println("로그인 실패");
			// textField 초기화
			initLoginUI();
		}
	}
	
	public void initLoginUI() {
		Platform.runLater(()->{
			txtId.clear();
			txtPw.clear();
			txtId.requestFocus();
		});
	}

	public void receiveData(Member vo) {
		
		// 0  아이디 중복 체크  1 회원가입 2 로그인
		if(vo.getOrder() == 2) {
			System.out.println("로그인 요청 처리 결과");
			setLoginCheck(vo);
	}
}

	public void showGameRoom() {
		try {
			FXMLLoader loader = new FXMLLoader(
			getClass().getResource("/base/game/Root.fxml")
			);
			Parent root = loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("BaseBallGame");
			stage.setResizable(false);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
