package base.vo;

import java.io.Serializable;

public class Member implements Serializable{
	
	private static final long serialVersionUID = 1154443971658405902L;

	
	private int order;	// 메뉴 번호

	private String mId; // 아이디
	
	private String mPw; // 비밀번호
	
	private String mName; // 닉네임
	
	private boolean success; // 성공 여부 확인
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public Member() {}
	
	// 아이디 중복체크용
	public Member(String mId) {
		this.mId = mId;
	}

	// 회원 로그인 및 정보 검색용 생성자
	public Member(String mId, String mPw) {
		this.mId = mId;
		this.mPw = mPw;
	}
	// 회원 가입용
	public Member(String mId, String mPw, String mName) {
		this.mId = mId;
		this.mPw = mPw;
		this.mName = mName;
	}

	public String getmId() {
		return mId;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public void setmPw(String mPw) {
		this.mPw = mPw;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmPw() {
		return mPw;
	}

	public String getmName() {
		return mName;
	}
	

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	@Override
	public String toString() {
		return "Member [order=" + order + ", mId=" + mId + ", mPw=" + mPw + ", mName=" + mName + ", success=" + success
				+ "]";
	}
	
}