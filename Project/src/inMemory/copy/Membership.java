package inMemory.copy;

import java.util.ArrayList;

public class Membership  {
	

//	static ManageBooks manageBooks = new ManageBooks();
	static ArrayList<Account> accountList = new ArrayList<>();
	
	
	static void membership() {
		System.out.println("1. 회원가입 진행 2. 비밀번호 변경");
		int choice = Util.readInt();
		
		switch (choice) {
			case 1:
				createAccount();
				break;
			case 2:
				changePassword();
				break;
		}
	}
	
	private static void createAccount() {
		System.out.print("- 이름 : ");
		String name = Util.readLine();
		System.out.print("- 생년월일(8자리) : ");
		String birthDate = Util.readLine();
		
		
		//중복회원 검사 
		if(isDuplicatedNameBirth(name, birthDate)) {
			System.out.println("이미 등록된 회원입니다.");
			return;
		}
		
		System.out.print("- 핸드폰 번호(ex.010-1230-0901) : ");
		String cellphone = Util.readLine();
		
		//아이디 중복검사
		boolean isNotDuplicatedId = false;
		String id = "";
		do {
			System.out.print("- 아이디 : ");
			String tempId = Util.readLine();
			
			if(isDuplicatedId(tempId)) {
				System.err.println("이미 등록된 ID 입니다.");
			} else {
				id = tempId;
				isNotDuplicatedId = true;
			}
		} while (!isNotDuplicatedId);
		
		System.out.print("- 비밀번호 :");
		String password = Util.readLine();
		
		savaAccount(name, birthDate, cellphone, id, password);
	}
	
	static void changePassword() {
		System.out.print("- 아이디 :");
		String id = Util.readLine();
		
		System.out.print("- 비밀번호 : ");
		String password = Util.readLine();
		
		Account loginAccount = login(id, password);
		
		if(loginAccount == null) {
			System.err.println("아이디 또는 패스워드가 일치하지 않습니다");
			return;
		}
		
		System.out.print("- 변경할 비밀번호 : ");
		String postPw = Util.readLine();
		
		savePassword(id, postPw);
	}
	
	
	//회원정보 저장
	public static void savaAccount(String name, String birthDate, String cellphone, String id, String password) {
		Account account = new Account(name, birthDate, cellphone, id, password);
		
		accountList.add(account);
	}
	
	
	
	//이름, 생년월일 중복 검사
	public static boolean isDuplicatedNameBirth(String name, String birthDate) {
		for(int i =0; i<accountList.size(); i++) {
			if(accountList.get(i).getName().equals(name) && accountList.get(i).getBirthDate().equals(birthDate)) {
				return true;
			}
		}
		return false;
	}
	
	//ID 중복검사
	public static boolean isDuplicatedId(String id) {
		for (int i =0; i<accountList.size(); i++) {
			if(accountList.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	public static Account login(String id, String password) {
		for(int i =0; i< accountList.size(); i++) {
			if(accountList.get(i).getId().equals(id) && accountList.get(i).getPassword().equals(password)) {
				return accountList.get(i);
			}
		}
		return null;
	}
	
	public static void savePassword(String id, String password) {
		for(int i =0; i <accountList.size(); i++) {
			if(accountList.get(i).getId().equals(id)) {
				accountList.get(i).setPassword(password);
				return;
			}
		}
	}
	
	static void login() {
		
	}

}
