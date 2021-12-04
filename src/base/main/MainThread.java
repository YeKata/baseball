package base.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import base.game.BaseBallGameController;
import base.join.JoinController;
import base.login.LoginController;
import base.vo.Member;
import base.vo.Rank;

public class MainThread extends Thread {
	
	public HomeController homeController;
	public LoginController loginController;
	public JoinController joinController;
	public BaseBallGameController baseballgameController;

	// Server에서 발신한 내용을 Receive
	@Override
	public void run() {
		ObjectInputStream ois = null;
		try {
			while(true) {
				if(isInterrupted()) {break;}
				Object obj =null;
				ois = new ObjectInputStream(
						HomeMain.socket.getInputStream()
					);
				if((obj = ois.readObject()) != null) {
					if(obj instanceof Member) {
						// 회원관련 요청 처리 결과
						if(((Member) obj).getOrder() == 0 || ((Member) obj).getOrder() == 1) {
						joinController.receiveData((Member)obj);
						} else if(((Member) obj).getOrder() == 2 ) {
						loginController.receiveData((Member)obj);
						}
					}else if(obj instanceof Rank) {
						// 기록관련 요청
						baseballgameController.receiveData((Rank)obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(Object o) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(
					HomeMain.socket.getOutputStream()
				);
			oos.writeObject(o);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
