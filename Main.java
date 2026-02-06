package test.homework;

// 프로그램 시작 & 연결 (다른 클래스들을 호출만 함)

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws Exception {

		// users.txt 파일을 읽어서 모든 회원을 메모리로 올리기
		// (파일을 List<User> 로 만들어두는 작업)
		UserManager um = new UserManager();

		while (true) {

			// 로그인 또는 회원가입 선택
			String select = JOptionPane.showInputDialog("1. 로그인\n2. 회원가입");

			if (select == null)	break;

			if (!select.equals("1") && !select.equals("2")) {
                JOptionPane.showMessageDialog(null, "1, 2 중 입력하세요!");
                continue;
            }
				
			// ID 입력 & 즉시 검증
            String id;
            while (true) {
            	
                id = JOptionPane.showInputDialog("ID(이메일) 입력");
                
                if (id == null) return;	// 취소 누르면 나가짐
                
                // 로그인이던 회원가입이던 어쨌거나 이메일 형식이 아님
                if (!UserManager.isValidEmail(id)) {
                    JOptionPane.showMessageDialog(null, "이메일 형식이 아닙니다!");
                    continue;
                }
                
                // 로그인 선택 & 이메일 형식은 맞는데 잘못 입력한 경우
                if (select.equals("1") && !um.isDuplicateId(id)) {
                    JOptionPane.showMessageDialog(null, "존재하지 않는 ID입니다!");
                    continue;
                }
                
                // 회원가입 선택 & 이메일 형식은 맞는데 이미 아이디 존재하는 경우
                if (select.equals("2") && um.isDuplicateId(id)) {
                    JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다!");
                    continue;
                }

                break;
            }
            
            // PW 입력 & 즉시 검증
            String pw;
            while (true) {
            	
                pw = JOptionPane.showInputDialog("PW 입력");
                
                if (pw == null) return;	// 취소 누르면 나가짐

                if (!UserManager.isValidPassword(pw)) {
                    JOptionPane.showMessageDialog(null, "비밀번호 형식이 올바르지 않습니다!");
                    continue;
                }

                break;
                
            }
				
			// 로그인 선택
            if (select.equals("1")) {

            	// 로그인한 User를 받아오기 위한 상자 준비
            	// 한 명만 담으면 돼서 배열 크기 1
                User[] loginUser = new User[1];
                
                // um에게 로그인 시키고 결과 메세지 result로 받고 로그인한 User는 loginUser[0]에 넣어놔라
                // um.login(): return으로 문자열을 주고 배열을 통해 User 객체를 밖으로 보내는 메서드
                String result = um.login(loginUser, id, pw);

                JOptionPane.showMessageDialog(null, result);

                if (result.equals("로그인 성공!")) {
                	
                	// 상자에서 로그인한 진짜 User 나옴
                	// UserManager에서 loginUser[0] = 로그인한 유저(u)로 바뀌고
                	// 다시 Main으로 돌아오면 배열 상태가 이렇게 바뀌는거임
                    User user = loginUser[0];
                    
                    // 새로 로그인하기 직전의 마지막 로그인 시간 출력
                    JOptionPane.showMessageDialog(null, "마지막 로그인 시간:\n" + user.getTempLastLogin());
                    
                    // GameManager에게 지금 로그인한 사람과 전체 유저를 관리하는 UserManager(um) 넘김
                    GameManager gm = new GameManager(user, um);
                    gm.menu();	// gm 안에 있는 menu() 실행
                    
                }
                
            }
            
			// 회원가입 선택
            else {
            	String result = um.register(id, pw);
            	
                JOptionPane.showMessageDialog(null, result);
            }
            
        }
		
    }
}