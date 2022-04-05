package BookManage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InMemory {
	
	//관리자 아이디와 비밀번호
	String ADMIN_ID = "admin";
	String ADMIN_PW = "1230";
	
	//관리자 로그인 상태
	boolean ADMIN_LOGIN_STATUS = false;
	ArrayList<Account> accountList = new ArrayList<Account>();
	ArrayList<Books> booksList = new ArrayList<Books>();
	
	
	public void membership() {
		System.out.println("----------------------");
		System.out.println("1.회원가입 진행 2.비밀번호 변경");
		System.out.print("➔ ");
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
		System.out.print("- 이름 : ");
		String name = Util.readLine();
		
		System.out.print("- 생년월일(ex.19951230) : ");
		String birthDate = Util.readLine();
		
		
		System.out.print("- 핸드폰 번호(ex.010-1230-0901) : ");
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
		
		System.out.print("- 비밀번호 : ");
		String password = Util.readLine();
		
		//회원정보 저장
		saveAccount(name, birthDate, cellphone, id, password);
	}
	
	//회원정보 저장 메소드
	private void saveAccount(String name, String birthDate, String cellphone, String id, String password) {
		Account account = new Account(name, birthDate, cellphone, id, password);
		
		accountList.add(account);
	}
	
	//폰번호 중복 검사
	private boolean isDuplicatedphone(String cellphone) {
		for(int i =0; i < accountList.size(); i++) {
			if(accountList.get(i).getCellphone().equals(cellphone)) {
				return true;
			}
		}
		return false;
	}

	
	//ID 중복검사
	private boolean isDuplicatedId(String id) {
		for(int i =0; i < accountList.size(); i++) {
			if(accountList.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	private Account login(String id, String password) {
		
		ADMIN_LOGIN_STATUS = false;
		
		for(int i=0; i<accountList.size(); i++) {
			if(accountList.get(i).getId().equals(id) && accountList.get(i).getPassword().equals(password)) {
				return accountList.get(i);
			}
		}
		return null;
	}
	
	//비밀번호 변경
	private void changePassword() {
		System.out.print("- 아이디 : ");
		String id = Util.readLine();
		
		System.out.print("- 비밀번호 : ");
		String password = Util.readLine();
		
		Account loginAccount = login(id, password);
		
		if(loginAccount == null) {
			System.err.println("아이디 또는 패스워드가 일치하지 않습니다.");
			return;
		}
		
		System.out.print("- 변경할 비밀번호 : ");
		String newPw = Util.readLine();
		System.out.println("비밀번호가 변경되었습니다.");
		
		savePassword(id, newPw);
		
	}
	
	
	private void savePassword(String id, String password) {
		for(int i=0; i < accountList.size(); i++) {
			if(accountList.get(i).getId().equals(id)) {
				accountList.get(i).setPassword(password);
				return;
			}
		}
	}
	
//	-------------------------------------------------------------------
	
	public void login() {
		System.out.println("------------");
		System.out.println("1.회원 2.관리자");
		System.out.print("➔ ");
		int choice = Util.readInt();
		
		
		switch (choice) {
			case 1 : 
				memberMenu();
				break;
			case 2 :
				adminMenu();
				break;
			default:
				System.out.println("유효하지 않은 번호입니다. 다시 입력해주세요");
				return;
		}
	}
	
	private void memberMenu() {
		System.out.print("- 아이디 : ");
		String id = Util.readLine();
		System.out.print("- 비밀번호 : ");
		String password = Util.readLine();
		
		Account loginAccount = login(id, password);
		
		if (loginAccount == null) {
			System.err.println("아이디 또는 패스워드가 일치하지 않습니다.");
			return;
		}
		System.out.println("-------------------------------");
		System.out.println("1.도서검색 2.대여현황 3.도서대여 4.도서반납");
		int choice = Util.readInt();
		
		switch (choice) {
			case 1 : 
				searchBooks();
				break;
			case 2:
				borrowStatus();
				break;
			case 3 :
				borrowBooks(loginAccount.getId());
				break;
			case 4 :
				returnBooks(loginAccount.getId());
				break;
			default :
				System.err.println("유효하지 않은 번호입니다. 다시 입력해주세요");
				break;
		}
		
	}
	
	private void searchBooks() {
    	System.out.print("- 도서명 : ");
    	String title = Util.readLine();
    	
    	System.out.print("- 저자 : ");
    	String author = Util.readLine();
    	
    	System.out.println("\t<도서 검색 결과>\t");
    	boolean isExistList = false; 
    	
    	for(int i=0; i<booksList.size(); i++) {
    		if(booksList.get(i).getTitle().equals(title)
    				|| booksList.get(i).getAuthor().equals(author)) {
    			String bookInfo = "> 도서명 : " + booksList.get(i).getTitle();
    			bookInfo += " | " + "저자 : " + booksList.get(i).getAuthor();
    			bookInfo += " | " + "출판사 : " + booksList.get(i).getPublisher();
      
    			//대여한 회원 id가 없으면 대여 가능
    			String borrowResult = booksList.get(i).getBorrower()=="" ? "대여가능" : "대여중"; 
    			bookInfo += " | " + "대여상태 : " + borrowResult;
    			System.out.println(bookInfo);
    			isExistList = true;
    		}
    	}
    	
    	if(!isExistList) {
    		System.out.println("검색된 도서가 없습니다.");
    	}
    }
	
	//대여현황
	private void borrowStatus() {
    	System.out.println("\t<대여 현황>\t");
    	boolean isExistList = false; 
    	
    	for(int i=0; i<booksList.size(); i++) {
    		//대여중인 상태만 골라서 리스트로 보여준다
    		if(!booksList.get(i).getBorrower().equals("")) {
    			String bookInfo = "> 도서명 : " + booksList.get(i).getTitle();
    			bookInfo += " | " + "저자 : " + booksList.get(i).getAuthor();
    			bookInfo += " | " + "출판사 : " + booksList.get(i).getPublisher();
    			bookInfo += " | " + "대여일 : " + booksList.get(i).getRentalDate();
    			bookInfo += " | " + "반납일 : " + booksList.get(i).getReturnDate();
      
    			System.out.println(bookInfo);
    			isExistList = true;
    		}
    	}
    	
    	if(!isExistList) {
    		System.out.println("대여중인 도서가 없습니다.");
    	}
    }	
	
	//대여도서 수
	private int countBorrowBooks(String id) {
		int countBooks = 0;
		for (int i =0; i <booksList.size(); i++) {
			if(booksList.get(i).getBorrower().equals(id)) {
				countBooks++;
			}
		}
		return countBooks;
	}
	
	//도서 대여
	private void borrowBooks(String id) {
		if(countBorrowBooks(id) > 2) {
			System.err.println("인당 대여가능한 권수는 3권 입니다. 반납후에 대여 가능합니다.");
			return;
		}
		
    	System.out.print("- 도서명 : ");
    	String title = Util.readLine();
    	
    	System.out.print("- 저자 : ");
    	String author = Util.readLine();
    	
    	for(int i=0; i<booksList.size(); i++) {
			if(booksList.get(i).getTitle().equals(title)
					&& booksList.get(i).getAuthor().equals(author)) {
				
				if(!booksList.get(i).getBorrower().equals("")) {
					System.err.println("현재 대여중인 도서 입니다.");
					return;
				}
				
				//대여 정보를 입력
				booksList.get(i).setBorrower(id);
				
				Date today = new Date();
				booksList.get(i).setRentalDate(Util.changeDateFormat(today));
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(today);
				cal.add(Calendar.DATE, 14); //14일 더하기
				Date twoWeeksAfter = cal.getTime();
				booksList.get(i).setReturnDate(Util.changeDateFormat(twoWeeksAfter));
				System.out.println(title + "을(를) 대여하였습니다.");
				return; //borrowBooks 매소드를 끝내도록 처리
			}
		}
    	
    	System.err.print("검색된 도서가 없습니다.");
    }
	
	private void returnBooks(String id) {
		if (countBorrowBooks(id) < 1) {
			System.out.println("대여중인 도서가 없습니다.");
			return;
		}
		
		System.out.print("- 도서명 : ");
		String title = Util.readLine();
		
		System.out.print("- 저자 : ");
		String author = Util.readLine();
		
		for(int i = 0; i < booksList.size(); i++) {
			if (booksList.get(i).getTitle().equals(title) && booksList.get(i).getAuthor().equals(author)&& booksList.get(i).getBorrower().equals(id)) {
				booksList.get(i).setBorrower(null);
				booksList.get(i).setReturnDate(null);
				booksList.get(i).setRentalDate(null);
				System.out.println(title + "을(를) 반납하였습니다.");
				return;
			}
		}
		System.err.println("반납할 수 있는 도서가 아닙니다.");
	}
	

	
// -------------------------------------------------------------------
	private void adminMenu() {
		//관리자 로그인 상태가 아닐때만 로그인 처리 
		if(!ADMIN_LOGIN_STATUS) {
			 System.out.print("관리자 아이디 : ");
			 String adminId = Util.readLine();
			 System.out.print("관리자 비밀번호 : ");
			 String adminPw = Util.readLine();
		    	
			 if(!ADMIN_ID.equals(adminId) || !ADMIN_PW.equals(adminPw)) {
				 System.err.println("관리자 아이디 또는 비밀번호가 일치하지 않습니다.");
				 return;
			 } else {
				 ADMIN_LOGIN_STATUS = true;
			 }
		}
    	
		System.out.println("-------------------------");
		System.out.println("1.회원관리\t2.도서등록\t3.도서삭제");
		System.out.print("➔");
		int choice = Util.readInt();
		
		switch (choice) {
			case 1:
				manageMember();
				break;
			case 2:
				managerBook();
				break;
			case 3:
				managerBook2();
				break;
				
			default:
				System.err.println("유효하지 않은 번호입니다. 다시 입력해주세요");
				break;
		}
	}
	

	private void manageMember() {
		System.out.print("-회원 이름 : ");
		String name = Util.readLine();
		System.out.print("-회원 생년월일 : ");
		String birthDate = Util.readLine();
	
		memberReuslt(name, birthDate);
	}
	
	private void memberReuslt(String name, String birthDate) {
		Account resultAccount = null;
		for(int i =0; i <accountList.size(); i++) {
			if(accountList.get(i).getName().equals(name) && accountList.get(i).getBirthDate().equals(birthDate)) {
				resultAccount = accountList.get(i);
				break;
			}
		}
		if (resultAccount.equals("")) {
			System.err.println("일치하는 회원정보가 없습니다.");
			return;
		}
		
		System.out.println("< 회원 정보 >");
		System.out.println("> 회원명 : " + resultAccount.getName());
		System.out.println("> 생년월일 : " + resultAccount.getBirthDate());
		System.out.println("> 휴대폰 : " + resultAccount.getCellphone());
		System.out.println("> 아이디 : " + resultAccount.getId());
		
		System.out.println();
		System.out.println("< 도서 대여 정보 >");
		boolean isExitList = false;
		for(int i =0; i< booksList.size(); i++) {
			if (booksList.get(i).getBorrower().equals(resultAccount.getId())) {
				String bookInfo = "> 도서명 : " +  booksList.get(i).getTitle();
				bookInfo += " | 저자 : " + booksList.get(i).getAuthor();
				bookInfo += " | 출판사 : " + booksList.get(i).getPublisher();
				bookInfo += " | 대여일 : " + booksList.get(i).getRentalDate();
				bookInfo += " | 반납일 : " + booksList.get(i).getReturnDate();
				
				System.out.println(bookInfo);
				isExitList = true;
			}
		}
		if (!isExitList) {
			System.out.println("대여 중인 도서가 없습니다.");
		}
		
	}
	
	//도서관리
	private void managerBook() {
		System.out.print("- 도서명 : ");
		String title = Util.readLine();
		System.out.print("- 저자 : ");
		String author = Util.readLine();
		System.out.print("- 출판사 : ");
		String publisher = Util.readLine();
		
		saveBooks(title, author, publisher);
		
	}

	private void saveBooks(String title, String author, String publisher) {
		if(title != "" && author != "" &&  publisher != "") {
			for(int i =0; i <booksList.size(); i++) {
				if (booksList.get(i).getTitle().equals(title) && booksList.get(i).getAuthor().equals(author) && booksList.get(i).getPublisher().equals(publisher)) {
					System.err.println("이미 등록된 도서입니다.");
					return;
				}
			}
			
			Books books = new Books(title, author, publisher, "", "", "");
			booksList.add(books);
			System.out.println("등록 되었습니다.");
		} else {
			System.err.println("미입력 항목이 있습니다.");
		}
		System.out.println("< 동록된 도서 리스트 >");
		for(int i =0; i < booksList.size(); i++) {
			System.out.println(booksList.get(i).toString());
		}
		
	}
	
	private void managerBook2() {
		System.out.print("- 도서명 : ");
		String title = Util.readLine();
		System.out.print("- 저자 : ");
		String author = Util.readLine();
		System.out.print("- 출판사 : ");
		String publisher = Util.readLine();
		
		deletBooks(title, author, publisher);
	}
	
	private void deletBooks(String title, String author, String publisher) {
		if(title != "" && author != "" &&  publisher != "") {
			for(int i =0; i <booksList.size(); i++) {
				if (booksList.get(i).getTitle().equals(title) && booksList.get(i).getAuthor().equals(author) 
						&& booksList.get(i).getPublisher().equals(publisher)) {
					booksList.remove(i);
					return;
				}
			}
			System.out.println("hello");
		
	}
			
			
	}
	
	
	
	
	
}
