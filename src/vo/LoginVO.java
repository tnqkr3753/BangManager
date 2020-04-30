package vo;

public class LoginVO { //로그인 누른 사람의 패스워드와 영업사원 여부 저장
	private String password;
	private boolean boolSales;
	private String name;
	public LoginVO(String password, boolean boolSales,String name) {
		this.password = password;
		this.boolSales = boolSales;
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public boolean isBoolSales() {
		return boolSales;
	}
	public String getName() {
		return name;
	}
}
