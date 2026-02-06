package test.homework;

// 가위바위보 게임

import javax.swing.JOptionPane;

public class RSPGame {

    private User user;	// 현재 로그인한 사람

    // 이번 게임에서의 결과 임시 저장
    // 전적의 최종 저장소는 User.java임. RSPGame.java는 계산만 함
    private int total;
    private int win;
    private int lose;
    private int draw;

    // 로그인한 User를 RSPGame에게 넘겨줌. 그래야 게임 끝나고 이 User의 전적 증가시킴
    public RSPGame(User user) {
        this.user = user;
    }

    public void startGame() {

        boolean gameStart = true;

        // gameStart가 true인 동안 계속 게임 반복
        while (gameStart) {
        	
        	try {

	            String input = JOptionPane.showInputDialog("1. 가위 2. 바위 3. 보");
	
	            if (input == null) break;	// 취소 누르면 while문 즉시 종료
	
	            int myNum = Integer.parseInt(input);	// 계산하기 위해 문자열을 숫자로 변환
	            
	            // 다른 숫자 입력한 경우
	            if (myNum < 1 || myNum > 3) {
	                JOptionPane.showMessageDialog(null, "1~3 중 입력하세요!");
	                continue;	// while 처음으로 돌아감
	            }
	            
	            int comNum = (int)(Math.random()*3)+1;	// 컴퓨터 1~3 중 랜덤 숫자 생성
	
	            // 화면 출력
	            showResult(myNum, comNum);
	
	            // 게임 계속 할지 물어봄
	            // askContinue()가 true면 계속, false면 종료.
	            gameStart = askContinue();
	            
	        } catch (NumberFormatException e) {		// 문자 입력한 경우 경고창 띄우고 다시 while 처음으로 돌아감
	            JOptionPane.showMessageDialog(null, "숫자만 입력하세요!");
	        }
        	
        }

        // 게임 끝나면 User의 누적 전적에 더해줌
        user.setTotal(user.getTotal() + total);
        user.setWin(user.getWin() + win);
        user.setLose(user.getLose() + lose);
        user.setDraw(user.getDraw() + draw);

        JOptionPane.showMessageDialog(null, "게임 종료!");
        
    }

    // 숫자를 사람이 읽을 수 있는 문자열로 바꿔서 출력
    private void showResult(int myNum, int comNum) {
    	
    	// 숫자 1,2,3을 그대로 쓰려고 배열의 0번은 버림
    	String[] arr = {"", "가위", "바위", "보"};
    	
    	String me = arr[myNum];
        String com = arr[comNum];
    
        String result;

        if (myNum == comNum) {
            draw++;
            result = "무승부!";
        }
        else if ((myNum==1&&comNum==3)||(myNum==2&&comNum==1)||(myNum==3&&comNum==2)) {
            win++;
            result = "당신 승리!";
        }
        else {
            lose++;
            result = "컴퓨터 승리!";
        }

        total++;
        
        String msg = "컴퓨터: " + com + "\n당신: " + me + "\n\n결과: " + result;
        JOptionPane.showMessageDialog(null, msg);
        
    }

    // 게임 계속할지 물어보는 메서드
    private boolean askContinue() {
        int again = JOptionPane.showConfirmDialog(null, "계속 하시겠습니까?");
        return again == 0;	// "예"(값 0) 누르면 true 반환, "아니오"(값 1), "취소"(값 2) 누르면 false 반환
    }
    
}