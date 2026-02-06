package test.homework;

// 파일 입출력 역할. (User.java를 파일에 저장하고 꺼내오는 역할)
// users.txt 파일과 User 객체들(List<User>)을 서로 변환해주는 역할
// 회원 정보, 전적을 파일에 저장해놔야 해서 필요함

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // 회원 정보를 저장할 파일 이름
	// 항상 users.txt 파일만 사용
	// 이 클래스 안에서만 사용하기 때문에 private, 절대 바뀌지 않는 값이기 때문에 final
    private static final String FILE = "users.txt";

    // List<User>: User 객체들의 리스트를 반환
    // loadUsers(): 파일에서 유저들을 읽어오는 기능
    public static List<User> loadUsers() throws Exception {

    	// List<User>: User 객체들을 담을 수 있는 리스트 자료형
    	// new ArrayList<>(): 실제로 메모리에 빈 리스트를 만들어서 list에 할당
        List<User> list = new ArrayList<>();
        
        // users.txt를 new File()을 해서 파일처럼 다룰 수 있도록 함
        File file = new File(FILE);

        // 파일이 처음이라 존재하지 않으면 빈 리스트 반환
        if(!file.exists()) {
            return list;
        }

        // 파일 읽기
        // FileReader: 파일을 글자로 읽음, BufferedReader: FileReader를 감싸서 한 줄씩 읽게 함
        BufferedReader br = new BufferedReader(new FileReader(file));

        // 한 줄이 String 형태로 들어와서 담아둘 변수
        String line;
        
        // 한 줄씩 읽기
        // 파일에서 한 줄 읽고 String으로 주고 더 이상 읽을 줄이 없으면 null
        // (읽을 줄이 있는 동안 계속 반복)
        while((line = br.readLine()) != null) {

            // 한 줄을 , 기준으로 나눔
            String[] arr = line.split(",");

            // 잘못된 데이터가 있을 경우 대비 (데이터가 이상하면 무시하고 다음 줄 읽음)
            if(arr.length < 7) continue;

            // User 객체 만들기
            User u = new User(
            			arr[0],                 	// id
	                    arr[1],                 	// password
	                    arr[2],                 	// lastLogin
	                    Integer.parseInt(arr[3].trim()), 	// total. 파일에서 읽은 건 전부 String이기 때문에 숫자로 변환
	                    Integer.parseInt(arr[4].trim()), 	// win
	                    Integer.parseInt(arr[5].trim()), 	// lose
	                    Integer.parseInt(arr[6].trim())  	// draw
            		);

            // 리스트에 담기
            list.add(u);
            
        }

        br.close();
        
        return list;	// 다 읽었으면 이 리스트가 UserManager로 넘어감
        
    }

    // 메모리에 있는 User들(List<User>의 내용)을 파일에 저장
    // saveUsers: 유저들을 저장하는 기능
    // List<User> list: User 여러 명을 받아옴.
    public static void saveUsers(List<User> list) throws Exception {

    	// 파일 쓰기
    	// FileWriter: FILE 경로에 있는 파일에 문자 단위로 쓸 수 있는 통로를 만듦
    	// BufferedWriter: 메모리(버퍼)에 모아놨다가 한 번에 쓰게 함 (속도 빨라짐)
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));

        // list 안에 있는 User를 하나씩 꺼내서 u 임시 변수로 사용
        for(User u : list) {
        	
            bw.write(
            		// 한 줄 문자열로 만들기 (객체를 텍스트로 변환)
	                u.getId() + ", " +
	                u.getPassword() + ", " +
	                u.getLastLogin() + ", " +
	                u.getTotal() + ", " +
	                u.getWin() + ", " +
	                u.getLose() + ", " +
	                u.getDraw()
            		);
            
            bw.newLine();	// 줄바꿈
            
        }

        bw.close();
        
    }
    
}