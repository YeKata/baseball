package base.join;
import java.net.URL;
import java.util.ResourceBundle;

import base.main.HomeMain;
import base.vo.Member;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class JoinController implements Initializable{
		
	@FXML private TextField txtId, txtNick;
	@FXML private PasswordField txtPw, txtPw2;
	@FXML private Button btnJoin;
	@FXML private Label PwChk,ChkId;
	
	boolean isJoin;
	
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HomeMain.thread.joinController = this;
		setJoinEvent();
		
		/*
		txtPw2.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == false) {
					if(!txtPw.getText().equals(txtPw2.getText())) {
						PwChk.setText("비밀번호가 일치하지 않습니다.");
						PwChk.setStyle("-fx-text-fill:red");
						PwChk.requestFocus();
					}else {
						PwChk.setText("비밀번호 일치");
						PwChk.setStyle("-fx-text-fill:green");
						
					}
				}
			}
			
		});
		
		
		txtNick.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue == false) {
					if(!txtNick.getText().equals("") && chkName(txtNick.getText()) == true) {
						NChk.setText("사용가능한 닉네임 입니다.");
						NChk.setStyle("-fx-text-fill:green");
						
					}else {
						NChk.setText("중복된 닉네임 입니다.");
						NChk.setStyle("-fx-text-fill:red");
					}
				}
			}
			
		});
		 */
	}
	
	public void initJoinUI() {
		Platform.runLater(()->{
			txtId.clear();
			txtPw.clear();
			txtNick.clear();
			txtPw2.clear();
			txtId.requestFocus();
		});
	}
	
	public void setJoinEvent() {
		txtId.textProperty().addListener((o,b,text)->{
			System.out.println(text);
			if(!text.trim().equals("")) {
				Member member = new Member(text);
				// 아이디 중복 체크
				member.setOrder(0);
				HomeMain.thread.sendData(member);
			}else {
				setJoinIDCheck(false);
			}
		});
		
		btnJoin.setOnAction(event->{
			String id = txtId.getText().trim();
			String pw = txtPw.getText().trim();
			String repw = txtPw2.getText().trim();
			String nick = txtNick.getText().trim();
			
			if(id.equals("") || !isJoin) {
				txtId.clear();
				txtId.requestFocus();
			}else if(!pw.equals(repw)) {
				txtPw.clear();
				txtPw2.clear();
				txtPw.requestFocus();
				PwChk.setText("비밀번호가 일치하지 않습니다.");
				PwChk.setStyle("-fx-text-fill:red");
			}else {
				PwChk.setText("비밀번호 일치");
				PwChk.setStyle("-fx-text-fill:green");
				Member member = new Member(id,pw,nick);
				member.setOrder(1);
				HomeMain.thread.sendData(member);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("SUCCESS");
				alert.setContentText("회원가입이 완료되었습니다.");
				alert.showAndWait();
				((Stage)btnJoin.getScene().getWindow()).close();
			}
					
		});
	}
	
	public void setJoinIDCheck(boolean isChecked) {
		Platform.runLater(()->{
				String style = isChecked ? "-fx-text-fill:green;" : "-fx-text-fill:red;" ;
				String text = isChecked ? "사용가능합니다." : "사용할 수 없는 아이디입니다." ;
				ChkId.setStyle(style);
				ChkId.setText(text);
		});
	}

	
	public void setJoinCheck(boolean isSuccess) {
		if(isSuccess) {
			System.out.println("회원가입 성공");
		}else {
			System.out.println("회원가입 실패");
			initJoinUI();
		}
	}
	
	public void receiveData(Member vo) {
		
		// 0  아이디 중복 체크  1 회원가입 2 로그인
		switch(vo.getOrder()) {
		case 0 :
			System.out.println("아이디 중복 체크");
			isJoin = vo.isSuccess();
			setJoinIDCheck(isJoin);
			break;
		case 1 : 
			System.out.println("회원가입 요청 처리 결과");
			setJoinCheck(vo.isSuccess());
			break;
		
	}

}	
	}
	
