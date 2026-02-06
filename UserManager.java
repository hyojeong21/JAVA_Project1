package test.homework;

// 회원과 관련된 모든 로직 (로그인, 회원가입, 비밀번호 변경)

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserManager {

	// 프로그램에 존재하는 모든 회원들을 저장하는 공간
    private List<User> users;

    // 프로그램 시작 시 파일에서 회원정보 불러옴
    public UserManager() throws Exception {
        users = FileManager.loadUsers();
    }

    // 로그인
    // User[] loginUser: 모든 회원 목록 (이 중에서 id 찾아야 함)
    // String id, pw: 입력한 아이디와 비밀번호 (누구 찾을지, 비밀번호 맞는지 검사하기 위해 필요함)
    public String login(User[] loginUser, String id, String pw) throws Exception {

        for(User u : users) {	// users를 돌면서 회원 한 명씩 검사
        	
        	if(u.getId().equals(id)) {	// 아이디 맞게 입력하면
        		
        		if(!u.getPassword().equals(pw)) {	// 근데 비밀번호는 잘못 입력하면
                    return "비밀번호가 틀렸습니다.";
                }
            	
            	// 새로 로그인하기 직전의 마지막 로그인 시간 가져옴
                String last = u.getLastLogin();
            	
                // 현재 로그인 시간으로 업데이트
                LocalDateTime now = LocalDateTime.now();	// 컴퓨터 현재 시간을 가져옴
                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 	// 시간 출력 형식
                u.setLastLogin(now.format(f));		// 윗줄 형식으로 바꿔서 현재 로그인 시간을 User 객체의 lastLogin에 덮어씀
                
                // 새로 로그인하기 직전의 마지막 로그인 시간 임시 저장
                u.setTempLastLogin(last);
                
                save();
                
                // 로그인 성공한 User 객체를 Main으로 보내기 위해 배열의 첫 칸에 넣음
                // loginUser[0] = 로그인한 유저로 바뀜
                loginUser[0] = u;
                
                return "로그인 성공!";

            }
            
        }
        
        // 아이디 다르게 입력하면
        return "존재하지 않는 ID입니다.";
        
    }

    // 이메일 형식 검사 (users와 상관없는 검사 기능이니 static)
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // 비밀번호 형식 검사
    public static boolean isValidPassword(String pw) {
        return pw.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,12}$");
    }

    // ID 중복 체크
    public boolean isDuplicateId(String id) {
    	
        for(User u : users) {	// users를 돌면서 같은 id가 있는지 확인
            if(u.getId().equals(id)) {	// 같은 ID 있다면
                return true;	
            }
        }
        
        return false;
        
    }

    // 회원가입
    // 아직 User 객체 없으니 새로 만들 아이디와 비밀번호만 필요함 (String id, pw)
    // 모든 검사 통과시 User 객체 생성 -> users에 추가
    public String register(String id, String pw) throws Exception {

    	if(!isValidEmail(id)) { return "이메일 형식이 아닙니다."; }
    	
    	if(isDuplicateId(id)) { return "이미 존재하는 ID입니다."; }
    	
        if(!isValidPassword(pw)) { return "비밀번호 형식이 올바르지 않습니다."; }

        // User 클래스로 진짜 회원 한 명을 만듦
        // 그래서 User newUser: 새로 만든 회원 한 명을 담을 변수
        // (User 타입의 변수를 하나 만들고 이름을 newUser로 함)
        // 그래서 괄호 안이 생성자에 들어가는 값들임
        // 처음 가입자는 전적 0
        User newUser = new User(id, pw, "첫 로그인", 0, 0, 0, 0);
        users.add(newUser);

        // 바로 파일 저장
        save();
        
        return "회원가입 되었습니다!";
        
    }

    // 비밀번호 변경
    // 이미 로그인 된 상태의 한 명만 바꾸는 거기 때문에 그냥 User user
    // 새 비밀번호만 필요함 (String newPw)
    public String changePassword(User user, String newPw) throws Exception {

    	if(!isValidPassword(newPw)) { return "비밀번호 형식이 올바르지 않습니다."; }

        user.setPassword(newPw);	// User 객체 내부 값만 변경

        // 변경 후 저장
        save();

        return "비밀번호가 변경되었습니다!";
        
    }

    // 파일 저장
    // 이 save()를 호출하는 순간 users 리스트의 현재 상태를 users.txt 파일에 덮어써서 저장함
    // 그래서 모든 변경사항이 파일에 반영됨
    public void save() throws Exception {
        FileManager.saveUsers(users);
    }

    // 회원 리스트 반환 (전체 랭킹, 전적, 승률 정렬에 사용)
    public List<User> getUsers(){
        return users;
    }
    
}