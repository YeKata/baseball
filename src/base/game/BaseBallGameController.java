package base.game;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import base.login.LoginController;
import base.main.HomeMain;
import base.vo.Rank;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class BaseBallGameController implements Initializable {
	@FXML Group numberGroup;
	@FXML Label s_result;
	@FXML Label b_result;
	@FXML Button resultBtn,Showrk;
	@FXML Label selNum;
	@FXML Group labelGroup;
	@FXML Label countLabel,lblTime;
	@FXML ImageView underlineImg;
	@FXML StackPane stackPane;
	
	private List<Integer> userSelect;
	private List<Integer> answers;
	private Button[] btn;
	private List<Label> labels;
	private int strikeCnt = 0;
	private int ballCnt = 0;
	private int cnt = 0;
	
	boolean isTimer = true;
	long clearTime;
	// lblTime 에 시간 text 출력용
	String time;
	
	LoginController loginController;

	private void newGame() {
		startTimer();
		answers = new ArrayList<Integer>();
		btn = new Button[10];

		strikeCnt = 0;
		ballCnt = 0;
		cnt = 0;
		countLabel.setText(cnt + "");
		b_result.setText(ballCnt + "");
		s_result.setText(strikeCnt + "");

		Random random = new Random();
		do {
			int v_num = random.nextInt(10);
			boolean v_check = false;
			for (int i = 0; i < answers.size(); i++) {
				if (answers.get(i) == v_num) {
					v_check = true;
					break;
				}
			}
			if (!v_check) {
				answers.add(v_num);
			}

			for (int i = 0; i <= 9; i++) {
				btn[i] = (Button) numberGroup.getChildren().get(i); // 버튼 배열에 넣기
			}

		} while (answers.size() < 3);

		System.out.println(answers);

		for (int delIndex = userSelect.size() - 1; delIndex >= 0; delIndex--) {
			int delNum = userSelect.get(delIndex);
			btn[delNum].setDisable(false); // 눌렀던 버튼 활성화
			userSelect.remove(delIndex);
			labels.get(delIndex).setText("");
		}

		for (int i = 0; i < 3; i++) {
			System.out.println(labelGroup.getChildren().get(i));
			labels.add((Label) labelGroup.getChildren().get(i)); // 라벨 넣기
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		HomeMain.thread.baseballgameController = this;
		
		labels = new ArrayList<Label>();
		userSelect = new ArrayList<Integer>();
		newGame();
		
		labels.get(2).textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (observable.getValue().isEmpty()) {
					resultBtn.setDisable(true);
				} else {
					resultBtn.setDisable(false);
				}
			}
		}); 
	
		underlineImg.setVisible(false);
		countLabel.setVisible(false);
	}

	@FXML
	public void removeAction() {
		int delIndex = userSelect.size() - 1;
		if (delIndex < 0) {
			Alert alert = new Alert(AlertType.WARNING); // 3개 초과 숫자 입력시 경고
			alert.setTitle("경고");
			alert.setContentText("선택된 숫자가 없습니다.");
			alert.show();
		} else {
			int delNum = userSelect.get(delIndex);
			btn[delNum].setDisable(false); // 삭제 눌렀을 때 눌렀던 버튼 활성화
			userSelect.remove(delIndex);
			labels.get(delIndex).setText("");
		}
	}

	@FXML
	public void resultAction() throws Exception {
		for (int i = 0; i <= 2; i++) {
			if (userSelect.get(i) == answers.get(i)) { // strike 조건
				strikeCnt++;
			} else { // ball 조건
				if (userSelect.get(i) == answers.get((i + 1) % 3) || userSelect.get(i) == answers.get((i + 2) % 3)) {
					ballCnt++;
				}
			}
		}
		s_result.setText(strikeCnt + "");
		b_result.setText(ballCnt + "");
		countLabel.setText(++cnt + " 번");
		underlineImg.setVisible(true);
		countLabel.setVisible(true);
		if (strikeCnt == 3) { // 정답을 맞췄을 때
			isTimer = false;
			String id = LoginController.user.getmId();
			Rank rk = new Rank(id,cnt,clearTime);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("성공하셨습니다.");
			alert.show();
			HomeMain.thread.sendData(rk);
			
			newGame();
			countLabel.setText("");
			underlineImg.setVisible(false);
			countLabel.setVisible(false);
		}
		strikeCnt = 0;
		ballCnt = 0;
		selNum.setText(userSelect.toString()); // 이전 숫자 출력
		userSelect.clear();

		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setText("");
		}
		for (int i = 0; i <= 9; i++) {
			btn[i].setDisable(false);
		}

	}

	@FXML
	public void clickAction(ActionEvent event) {
		if (userSelect.size() == 3) {
			new Alert(AlertType.WARNING, "3개의 숫자만 선택하세요", ButtonType.OK).show(); // 3개 초과 숫자 입력시 경고
		} else {
			int num = Integer.parseInt(((Button) event.getTarget()).getText());
			userSelect.add(num);

			btn[num].setDisable(true);
			labels.get(userSelect.size() - 1).setText(num + "");
		}
	}


	@FXML
	public void restartAction() {
		newGame();
	}
	
	public void showDialog(Rank obj) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText("성공하셨습니다");
		
	}
	public void startTimer() {
		// time flag
		isTimer = true;
		
		// clear시간 초기값 지정 
		clearTime = 1000*60*60*15;
		// new Date(0) == 1970 1 1 : 00:00:00
		Thread t = new Thread(()->{
			while(isTimer) {
				clearTime += 1;
				Platform.runLater(()->{
					if(clearTime % 1000 == 0) {
						time = getTime(clearTime);
						
						lblTime.setText(time);
					}
				});
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					isTimer = false;
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public String getTime(long time) {
		return new SimpleDateFormat("HH:mm:ss:SS")
				.format(time);
	}
	

	// 기록 정보 receive
	public void receiveData(Rank obj) {
		Platform.runLater(()->{
			showDialog(obj);
		});
	}

}