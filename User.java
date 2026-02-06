package test.homework;

// 회원 정보를 저장하는 객체. 정보만 있음!! 데이터 보관 역할임

public class User {

	// 회원이 가지고 있어야 할 정보
	// 다른 클래스에서 이 변수에 직접 접근하지 못하게 막기 위해 private 씀
    private String id;					// 아이디
    private String password;			//  비밀번호
    private String lastLogin;			// 마지막 로그인 시간
    private int total, win, lose, draw;	// 총 횟수, 이긴 횟수, 진 횟수, 비긴 횟수

    
    // 파일에서 읽어온 한 줄의 데이터를 User 객체로 만드는 역할 (텍스트 데이터를 객체로 바꾸는 통로)
    // 괄호 안 내용은 객체가 만들어질 때 반드시 가져야 할 값들
    public User(String id, String password, String lastLogin, int total, int win, int lose, int draw) {
    	// 매개변수로 들어온 값을 필드에 저장
    	// this.id: 클래스 안에 있는 필드(진짜 저장공간), id: 생성자 매개변수(들어온 값)
        this.id = id;
        this.password = password;
        this.lastLogin = lastLogin;
        this.total = total;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    
    // getter 메서드
    // 다른 클래스에서 회원 정보를 읽어갈 때 사용
    // private 이라서 직접 접근 못 하기 때문에 getter가 필요함!!
    // ex) id 값을 꺼내서 돌려주겠다~
    public String getId() { return id; }

    public String getPassword() { return password; }

    public String getLastLogin() { return lastLogin; }

    public int getTotal() { return total; }

    public int getWin() { return win; }

    public int getLose() { return lose; }

    public int getDraw() { return draw; }

    
    // setter 메서드
    // 회원 정보가 변경될 때 값을 수정하기 위해 사용 (게임 끝나거나, 로그인하거나, 비밀번호 변경하는 상황같은거..)
    // 돌려줄 값(반환값) 없어서 void 사용
    // 새로 바꿀 값을 받아오기 위해 (String password) 이런 식으로 작성
    public void setPassword(String password) { this.password = password; }

    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
    
    public void setTotal(int total) { this.total = total; }

    public void setWin(int win) { this.win = win; }

    public void setLose(int lose) { this.lose = lose; }

    public void setDraw(int draw) { this.draw = draw; }

    
    // 새로 로그인하기 직전의 마지막 로그인 시간을 잠깐 보관하는 임시 보관함
    private String tempLastLogin;

    // UserManager가 새로 로그인하기 직전의 마지막 로그인 시간을 넣어줌
    public void setTempLastLogin(String t) {
    	// 밖에서 들어온 값 t를 이 User 객체 안의 tempLastLogin에 저장
        this.tempLastLogin = t;		
    }

    // Main이 User 객체 안의 값을 꺼내서 화면에 보여줌
    public String getTempLastLogin() {
        return tempLastLogin;
    }
    
}