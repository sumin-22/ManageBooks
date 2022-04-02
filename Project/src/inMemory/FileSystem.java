package inMemory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FileSystem {
	
	//관리자 아이디와 비밀번호
	String ADMIN_ID = "cjstk4";
	String ADMIN_PW = "1230";
		
	//관리자 로그인 상태
	boolean ADMIN_LOGIN_STATUS =  false;
	ArrayList<Account> accountlist = new ArrayList<Account>();
	ArrayList<Books> booksList = new ArrayList<Books>();
	
	
	public void membership() {
		System.out.println("1.회원가입 진행 2.비밀번호 변경");
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
	
	private void createAccount() {
		System.out.print("- 이름 : ");
		String name = Util.readLine();
		System.out.print("- 생년월일(8자리) : ");
		String birthDate = Util.readLine();
		
		System.out.print("- 핸드폰 번호(ex.010-1230-0901) : ");
		String cellphone = Util.readLine();
		
		if(isDuplicatedphone(cellphone)) {
			System.out.println("이미 등록된 회원입니다");
			return;
		}
		
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
		
		saveAccount(name, birthDate, cellphone, id, password);
	}

	
	//회원정보 저장
	private void saveAccount(String name, String birthDate, String cellphone, String id, String password) {
		Account account = new Account(name, birthDate, cellphone, id, password);
		
		String inputText = account.toString() + "\r\n";
		if (Util.writerFileLine("./file/account.txt", inputText)) {
			System.out.println("회원 정보가 저장되었습니다.");
		}
	}
	//폰번호 중복 검사
	private boolean isDuplicatedphone(String cellphone) {
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		for(int i =0; i < accountlist.size(); i++) {
			if(accountlist.get(i).getCellphone().equals(cellphone)) {
				return true;
			}
		}
		return false;
	}

		

	//ID 중복검사
	private boolean isDuplicatedId(String id) {
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		for(int i =0; i < accountlist.size(); i++) {
			if(accountlist.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	private Account login(String id, String password) {
	
		ADMIN_LOGIN_STATUS = false;
		
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		for(int i =0; i< accountArray.size(); i++) {
			if(accountArray.get(i).getId().equals(id) && accountArray.get(i).getPassword().equals(password)) {
				return accountArray.get(i);
			}
		}
		return null;
	}
	
	
	private void changePassword() {
		System.out.print("- 아이디 : ");
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
	
	private void savePassword(String id, String password) {
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		ArrayList<String> writeArray = new ArrayList<>();
		for(int i =0; i <accountArray.size(); i++) {
			if(accountArray.get(i).getId().equals(id)) {
				accountArray.get(i).setPassword(password);
			}
			writeArray.add(accountArray.get(i).toString() + "\r\n");
		}
		if(Util.updateFile("./file/account.txt", writeArray)) {
			System.out.println("비밀번호가 변경되었습니다.");
		}
	}
//	-------------------------------------------------------------------
	
	public void login() {
		System.out.println("1.회원 2.관리자");
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
//	-------------------------------------------------------------------
	private void memberMenu() {
    	System.out.print("아이디 : ");
    	String id = Util.readLine();
    	System.out.print("비밀번호 : ");
    	String password = Util.readLine();
    	
    	Account loginAccount = login(id, password);
    	
    	if(loginAccount == null) {
			System.err.println("아이디 또는 패스워드가 일치하지 않습니다");
			return;
		}
    	
    	System.out.println("1.도서검색 2.대여현황 3.도서대여 4.도셔반납 ");
        int choice = Util.readInt();
        
        switch (choice) {
	        case 1:	//1.도서검색
	        	searchBooks();
	        	break;
	        case 2: //2.대여현황
	        	borrowStatus();
	        	break;
	        case 3:	//3.도서대여
	        	borrowBooks(loginAccount.getId());
	            break;
	        case 4: //4.도셔반납
	        	returnBooks(loginAccount.getId());
	        	break;
	        default:
	        	System.err.println("유효한 번호가 입력되지 않았습니다.");
	        	break;
        }        
    }
	
	private void searchBooks() {
		System.out.print("- 도서명 : ");
		String title = Util.readLine();
		
		System.out.print("- 저자 : ");
		String author = Util.readLine();
		
		System.out.println("< 도서 검색 결과 >");
		boolean isExistList = false;
		
		ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
		
		for(int i =0; i< booksArray.size();i++) {
			if(booksArray.get(i).getTitle().equals(title)|| booksList.get(i).getAuthor().equals(author)) {
				String bookInfo = "> 도서명 : " + booksArray.get(i).getTitle();
    			bookInfo += " | " + "저자 : " + booksArray.get(i).getAuthor();
    			bookInfo += " | " + "출판사 : " + booksArray.get(i).getPublisher();
      
    			String borrowResult = booksArray.get(i).getBorrower()=="none" ? "대여가능" : "대여중"; 
    			bookInfo += " | " + "대여상태 : " + borrowResult;
    			System.out.println(bookInfo);
    			isExistList = true;
			}
		}
		if(!isExistList) {
			System.out.println("검색된 도서가 없습니다.");
		}
	}
	private void borrowStatus() {
    	System.out.println("<대여 현황>");
    	boolean isExistList = false; 
    	
    	ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
    	
    	for(int i=0; i<booksArray.size(); i++) {
    		//대여중인 상태만 골라서 리스트로 보여준다
    		if(!booksArray.get(i).getBorrower().equals("none")) {
    			String bookInfo = "> 도서명 : " + booksArray.get(i).getTitle();
    			bookInfo += " | " + "저자 : " + booksArray.get(i).getAuthor();
    			bookInfo += " | " + "출판사 : " + booksArray.get(i).getPublisher();
    			bookInfo += " | " + "대여일 : " + booksArray.get(i).getRentalDate();
    			bookInfo += " | " + "반납일 : " + booksArray.get(i).getReturnDate();
      
    			System.out.println(bookInfo);
    			isExistList = true;
    		}
    	}
    	
    	if(!isExistList) {
    		System.out.println("대여중인 도서가 없습니다.");
    	}
    }	
	private void borrowBooks(String id) {
		if(countBorrowBooks(id) > 2) {
			System.err.println("인당 대여가능한 권수는 3권 입니다. 반납후에 대여 가능합니다.");
			return;
		}
		
    	System.out.print("- 도서명 : ");
    	String title = Util.readLine();
    	
    	System.out.print("- 저자 : ");
    	String author = Util.readLine();
    	
    	ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
    	
    	boolean isUpdate = false;
    	ArrayList<String> writeArray = new ArrayList<>();
    	for(int i=0; i<booksArray.size(); i++) {
			if(booksArray.get(i).getTitle().equals(title)
					&& booksArray.get(i).getAuthor().equals(author)) {
				
				if(!booksArray.get(i).getBorrower().equals("none")) {
					System.err.println("현재 대여중인 도서 입니다.");
					return;
				}
				
				//대여 정보를 입력
				booksArray.get(i).setBorrower(id);
				
				Date today = new Date();
				booksArray.get(i).setRentalDate(Util.changeDateFormat(today));
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(today);
				cal.add(Calendar.DATE, 14); //14일 더하기
				Date twoWeeksAfter = cal.getTime();
				booksArray.get(i).setReturnDate(Util.changeDateFormat(twoWeeksAfter));
				isUpdate = true;
			}
			
			writeArray.add(booksArray.get(i).toString() + "\r\n");
		}
    	
    	if(!isUpdate) {
    		System.err.print("검색된 도서가 없습니다.");
    	} else {
    		if(Util.updateFile("./file/books.txt", writeArray)) {
    			System.out.println("대여가 정상적으로 완료되었습니다.");
    		}
    	}
    }
	
	//대여 도서 수를 리턴
	private int countBorrowBooks(String id) {
		int countBooks = 0;
		ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
		for(int i=0; i<booksArray.size(); i++) {
			if(booksArray.get(i).getBorrower().equals(id)) {
				countBooks++;
			}
		}
		return countBooks;
	}
		
	private void returnBooks(String id) {
		if(countBorrowBooks(id) < 1) {
			System.err.println("대여 중인 도서가 없습니다.");
			return;
		}
	    
		System.out.print("- 도서명 : ");
	    String title = Util.readLine();
	    	
	    System.out.print("- 저자 : ");
	    String author = Util.readLine();
	    	
	    ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
	    
	    boolean isUpdate = false;
	    String returnTitle = "";
    	ArrayList<String> writeArray = new ArrayList<>();
	    for(int i=0; i<booksArray.size(); i++) {
	    	if(booksArray.get(i).getTitle().equals(title)
	    		&& booksArray.get(i).getAuthor().equals(author)
	    		&& booksArray.get(i).getBorrower().equals(id)) {
	    	
	    		//대여 정보를 초기화
	    		booksArray.get(i).setBorrower("none");
	    		booksArray.get(i).setRentalDate("none");
	    		booksArray.get(i).setReturnDate("none");
	    		returnTitle = title;
	    		isUpdate = true;
	    	}
	    
			writeArray.add(booksArray.get(i).toString() + "\r\n");
	    }
	    	
	    if(!isUpdate) {
	    	System.err.println("반납할 수 있는 도서가 아닙니다.");
	    } else {
    		if(Util.updateFile("./file/books.txt", writeArray)) {
    			System.out.println(returnTitle + "을(를) 반납하였습니다.");
    		}
	    }
	}
// -------------------------------------------------------------------	
	private void adminMenu() {
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
    	
    	System.out.println("1.회원관리 2.도서관리 ");
        int choice = Util.readInt();
        
        switch (choice) {
	        case 1:	
	        	manageMember();
	            break;
	        case 2: 
	            manageBooks();
	            break;
	        default:
	        	System.err.println("유효한 번호가 입력되지 않았습니다.");
	        	break;
        }        
    }
	
	private void manageMember() {
    	System.out.print("- 회원 이름 : ");
    	String name = Util.readLine();
    	
    	System.out.print("- 생년월일 : ");
    	String birthDate = Util.readLine();
    	
    	memberReuslt(name, birthDate);
    }
    
	private void memberReuslt(String name, String birthDate) {
		Account resultAccount = null;
    	for(int i=0; i< accountlist.size(); i++) {
    		if(accountlist.get(i).getName().equals(name) && accountlist.get(i).getBirthDate().equals(birthDate)) {
    			resultAccount = accountlist.get(i);
    			break;	
    		}
    	}
    	
    	if(resultAccount == null) {
    		System.err.println("일치하는 회원정보가 없습니다.");
    		return;
    	}
    	
    	//회원 조회 결과 출력
    	System.out.println("<회원 개인 정보>");
    	System.out.println("> 회원명 : " + resultAccount.getName());
    	System.out.println("> 생년월일 : " + resultAccount.getBirthDate());
    	System.out.println("> 휴대폰 : " + resultAccount.getCellphone());
    	System.out.println("> 아이디 : " + resultAccount.getId());
    	
    	System.out.println("<도서 대여 정보>");
    	boolean isExistList = false; 
    	for(int i=0; i<booksList.size(); i++) {
    		
    		if(booksList.get(i).getBorrower().equals(resultAccount.getId())) {
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
    		System.out.println("> 대여 중인 도서가 없습니다.");
    	}
    }
	
	private void manageBooks() {
    	System.out.print("- 도서명 : ");
    	String title = Util.readLine();
    	
    	System.out.print("- 저자 : ");
    	String author = Util.readLine();
    	
    	System.out.print("- 출판사 : ");
    	String publisher = Util.readLine();
    	
    	saveBooks(title, author, publisher);
    }
	
	private void saveBooks(String title, String author, String publisher) {
		if(title != "" && author != "" && publisher != "") {
			//중복 check
			for(int i=0; i<booksList.size(); i++) {
				if(booksList.get(i).getTitle().equals(title)
						&& booksList.get(i).getAuthor().equals(author)
						&& booksList.get(i).getPublisher().equals(publisher)) {
					System.err.println("이미 등록된 도서 입니다.");
					return;
				}
			}
			
		
			Books books = new Books(title, author, publisher, "", "", "");
			booksList.add(books);
			System.out.println("등록 되었습니다.");
		} else {
			System.err.println("미입력 값이 있습니다.");
		} 
		
		System.out.println("<등록된 도서 리스트>");
		for(int i=0; i<booksList.size(); i++) {
			System.out.println(booksList.get(i).toString());
		}
	}
	
	
	

}
