package test.homework;

// 메뉴 관리 (전적, 전체 랭킹)
// 로그인 후에 사용자가 하는 모든 행동 관리

import javax.swing.JOptionPane;
import java.util.List;

public class GameManager {
	
    private User currentUser;			// 현재 로그인한 사람
    private UserManager userManager;	// 회원 전체 관리

    public GameManager(User user, UserManager um) {
        this.currentUser = user;
        this.userManager = um;
    }

    public void menu() throws Exception {

        while (true) {

            String input = JOptionPane.showInputDialog("1. 로그아웃\n2. 게임 시작\n3. 내 전적\n4. 전체 랭킹\n5. 비밀번호 변경");

            if (input == null) { 
            	userManager.save();  // 로그아웃 처리
            	return;	// 취소 누르면 나가짐
            }

            try {
           
                int menu = Integer.parseInt(input);		// 문자열을 숫자로 변환

                if (menu < 1 || menu > 5) {
                    JOptionPane.showMessageDialog(null, "1~5 중 입력하세요!");
                    continue;
                }
            
            switch (menu) {

                case 1: // 로그아웃
                    userManager.save();   // 프로그램의 모든 변경사항을 파일에 저장
                    return;

                case 2: // 게임 시작
                    RSPGame game = new RSPGame(currentUser); // RSPGame에게 현재 유저를 넘김
                    game.startGame();
                    break;

                case 3: // 전적
                    showRecord();
                    break;

                case 4: // 전체 랭킹
                    showRanking();
                    break;

                case 5: // 비밀번호 변경
                	// 새 비밀번호 입력 받음
                	String newPw = JOptionPane.showInputDialog("새 비밀번호 입력");

                    // 취소 눌렀을 경우 메뉴로 되돌아감
                    if (newPw == null) {
                    	break;
                    }

                    String msg = userManager.changePassword(currentUser, newPw);

                    JOptionPane.showMessageDialog(null, msg);
                    
                    break;
                    
            }
            
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "숫자만 입력하세요!");
            }
            
        }
        
    }
    
    // 전적
    private void showRecord() {
    	
        String msg =
                "아이디: " + currentUser.getId() + "\n" +
                "총 횟수: " + currentUser.getTotal() + "\n" +
                "승: " + currentUser.getWin() + "\n" +
                "패: " + currentUser.getLose() + "\n" +
                "무: " + currentUser.getDraw();

        JOptionPane.showMessageDialog(null, msg);
        
    }

    // 전체 랭킹
    private void showRanking() {
    	
		// UserManager에게 회원 전체 리스트 달라고 요청
        List<User> list = userManager.getUsers();	

        StringBuilder sb = new StringBuilder("=========== 랭킹 ===========\n");

        list.sort((a, b) -> b.getWin() - a.getWin());	// 이긴 횟수 기준으로 내림차순 정렬

        for (User u : list) {	// 전체 회원을 돌면서 랭킹 문자열 생성
            sb.append(u.getId())
              .append(" | 승: ").append(u.getWin())
              .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
        
    }
    
}