package base.vo;

import java.io.Serializable;

public class Rank implements Serializable {

	private static final long serialVersionUID = 1443660948554977971L;
	
	private int ranking;
	private int count;
	private String mid;
	private long regDate;
	
	public Rank(String mid, int count, long regDate) {
		this.mid = mid;
		this.count = count;
		this.regDate = regDate;
	};
	
	
	public String getMid() {
		return mid;
	}


	public void setMid(String mid) {
		this.mid = mid;
	}


	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public long getRegDate() {
		return regDate;
	}


	public void setRegDate(long regDate) {
		this.regDate = regDate;
	}


	@Override
	public String toString() {
		return "Rank [ranking=" + ranking + ", count=" + count + ", mid=" + mid + ", regDate=" + regDate + "]";
	}





	
	
	
	

}
