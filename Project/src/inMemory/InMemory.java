package inMemory;

import java.util.ArrayList;

public class InMemory {
	
	//관리자 아이디와 비밀번호
	String ADMIN_ID = "cjstk4";
	String ADMIN_PW = "1230";
	
	static ArrayList<Account> accountlist = new ArrayList<Account>();
	
	public void membership() {
		System.out.println("1.회원가입 진행 2.비밀번호 변경");
		int choice = Util.readInt();
		
		switch (choice) {
			case 1:
				creatAccount();
				break;
			case 2:
				changePassword();
				break;
		}
	}
	
	private void creatAccount() {
		System.out.print("-이름 : ");
		String name = Util.readLine();
		
		System.out.print("-생년월일(ex.19951230) : ");
		String birthDate = Util.readLine();
		
		
		System.out.print("-핸드폰 번호(ex.010-1230-0901) : ");
		String cellphone = Util.readLine();
		
		//중복 번호 검사
		
		if(isDuplicatedphone(cellphone)) {
			System.out.println("이미 등록된 회원입니다");
			return;
		}
		
		//아이디 중복검사
		boolean isNotDuplicatedId = false;
		String id = "";
		do {
			System.out.print("-아이디 : ");
			String tempId = Util.readLine();
			
			if (isDuplicatedId(tempId)) {
				System.out.println("이미 등록된 ID입니다.");
			} else {
				id = tempId;
				isNotDuplicatedId = true;
			}
		} while (!isNotDuplicatedId);
		
		System.out.print("-비밀번호 : ");
		String password = Util.readLine();
		
		//회원정보 저장
		saveAccount(name, birthDate, cellphone, id, password);
	}
	
	//회원정보 저장 메소드
	private void saveAccount(String name, String birthDate, String cellphone, String id, String password) {
		Account account = new Account(name, birthDate, cellphone, id, password);
		
		accountlist.add(account);
	}
	
	//이름, 생년월일 중복 검사
	private boolean isDuplicatedphone(String cellphone) {
		for(int i =0; i < accountlist.size(); i++) {
			if(accountlist.get(i).getCellphone().equals(cellphone)) {
				return true;
			}
		}
		return false;
	}

	
	//ID 중복검사
	private boolean isDuplicatedId(String id) {
		for(int i =0; i < accountlist.size(); i++) {
			if(accountlist.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	private Account login(String id, String password) {
		for(int i=0; i<accountlist.size(); i++) {
			if(accountlist.get(i).getId().equals(id) && accountlist.get(i).getPassword().equals(password)) {
				return accountlist.get(i);
			}
		}
		return null;
	}
	
	//비밀번호 변경
	private void changePassword() {
		System.out.print("-아이디 : ");
		String id = Util.readLine();
		
		System.out.print("-비밀번호 : ");
		String password = Util.readLine();
		
		Account loginAccount = login(id, password);
		
		if(loginAccount == null) {
			System.err.println("아이디 또는 패스워드가 일치하지 않습니다.");
			return;
		}
		
		System.out.print("-변경할 비밀번호 : ");
		String newPw = Util.readLine();
		
		savaPassword(id, newPw);
		
	}
	
	
	private void savaPassword(String id, String password) {
		for(int i=0; i < accountlist.size(); i++) {
			if(accountlist.get(i).getId().equals(id)) {
				accountlist.get(i).setPassword(password);
				return;
			}
		}
	}
	
//	-------------------------------------------------------------------
	
	public void login() {
		System.out.println("1.회원 2.관리자");
		int choice = Util.readInt();
		
		switch (choice) {
			case 1 : 
				
				break;
			case 2 :
				adminMenu();
				break;
			default:
				System.out.println("유효하지 않은 번호입니다. 다시 입력해주세요");
				break;
		}
	}
	
	private void adminMenu() {
		System.out.print("-관리자 아이디 : ");
		String adminId = Util.readLine();
		System.out.print("-관리자 비밀번호 : ");
		String adminPw = Util.readLine();
		
		if(!ADMIN_ID.equals(adminId) || !ADMIN_PW.equals(adminPw)) {
			System.err.println("관리자 아이디 또는 패스워드가 일치하지 않습니다.");
			return;
		}
		
		System.out.println("1.회원관리 2.도서관리");
		int choice = Util.readInt();
		
		switch (choice) {
			case 1:
				managerMember();
				break;
			case 2:
				break;
			default:
				System.err.println("유효하지 않은 번호입니다. 다시 입력해주세요");
				break;
		}
	}
	
	private void managerMember() {
		System.out.print("-회원 이름 : ");
		String name = Util.readLine();
		System.out.print("-회원 생년월일 : ");
		String birthDate = Util.readLine();
	
		memberReuslt(name, birthDate);
	}
	
	private void memberReuslt(String name, String birthDate) {
		String resultId = "";
		for(int i =0; i <accountlist.size(); i++) {
			if(accountlist.get(i).getName().equals(name) && accountlist.get(i).getBirthDate().equals(birthDate)) {
				resultId = accountlist.get(i).getId();
				break;
			}
		}
		if (resultId.equals("")) {
			System.err.println("일치하는 회원정보가 없습니다.");
			return;
		}
	}
	
	
	
	
	
	

}
