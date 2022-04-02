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
	ArrayList<Account> accountList = new ArrayList<Account>();
	ArrayList<Books> booksList = new ArrayList<Books>();
	
	
	public void membership() {
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
	
	private void createAccount() {
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
	
	//회원정보 저장
	private void savaAccount(String name, String birthDate, String cellphone, String id, String password) {
		Account account = new Account(name, birthDate, cellphone, id, password);
		
		//txt 파일에 들어갈 한줄을 만들고 마지막에 줄내립을 위한 계행 코드 추가
		String inputText = account.toString()+"\r\n";
		
		if(Util.writeFileLine("./file/account.txt", inputText)) {
			System.out.println("회원 정보가 저장 되었습니다.");
		}
		
	}
	
	//이름, 생년월일 중복 검사
	private boolean isDuplicatedNameBirth(String name, String birthDate) {
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		for(int i =0; i<accountArray.size(); i++) {
			if(accountArray.get(i).getName().equals(name) && accountArray.get(i).getBirthDate().equals(birthDate)) {
				return true;
			}
		}
		return false;
	}
	
	//ID 중복검사
	private boolean isDuplicatedId(String id) {
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		for (int i =0; i<accountArray.size(); i++) {
			if(accountArray.get(i).getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	private Account login(String id, String password) {
		//사용자 로그인을 시도하는 순간 관리자 로그오프
		ADMIN_LOGIN_STATUS = false;
		
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		for(int i =0; i< accountArray.size(); i++) {
			if(accountArray.get(i).getId().equals(id) && accountArray.get(i).getPassword().equals(password)) {
				return accountArray.get(i);
			}
		}
		return null;
	}
	
	private void savePassword(String id, String password) {
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
		ArrayList<String> writeArray = new ArrayList<>();
		for(int i =0; i <accountArray.size(); i++) {
			if(accountArray.get(i).getId().equals(id)) {
				accountArray.get(i).setPassword(password);
			}
			//writeArray에 그대로 업데이트 해서 파일을 지우고 다시 입력하는데 사용
			writeArray.add(accountArray.get(i).toString() + "\r\n");
		}
		
		//accountArray는 비밀번호 변경을 적용 후 전체 리스트를 가지고 있기 때문에 그대로 overwrite 저장
		if(Util.updateFile("./file/account.txt", writeArray)) {
			System.out.println("비밀번호가 변경되었습니다.");
		}
		
	}
	
	public void login() {
    	System.out.println("1.회원 2.관리자 ");
        int choice = Util.readInt();
        
        switch (choice) {
	        case 1:	//1.회원
	        	memberMenu();
	        	break;
	        case 2: //2.관리자
	            adminMenu();
	            break;
	        default:
	        	System.err.println("유효한 번호가 입력되지 않았습니다.");
	        	break;
        }        
    }
	
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
    	
    	System.out.println("<도서 검색 결과>");
    	boolean isExistList = false; 
    	
    	ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
    	
    	for(int i=0; i<booksArray.size(); i++) {
    		//도서명이나 저자중 하나만 일치하면 검색결과가 나오도록
    		//그러나 이렇게 조건을 설정하면 도서명과 저자를 다르게 입력했을때 합집합으로 보여주게 된다.
    		//입력을 안하면 하나만 조회하고 둘다 입렵하면 && 조건으로 되게 하려면 좀더 복잡하게 if 부분을 바꿔야 한다.
    		if(booksArray.get(i).getTitle().equals(title)
    				|| booksArray.get(i).getAuthor().equals(author)) {
    			String bookInfo = "> 도서명 : " + booksArray.get(i).getTitle();
    			bookInfo += " | " + "저자 : " + booksArray.get(i).getAuthor();
    			bookInfo += " | " + "출판사 : " + booksArray.get(i).getPublisher();
      
    			//대여한 회원 id가 없으면 대여 가능
    			String borrowResult = booksArray.get(i).getBorrower()=="none" ? "대여가능" : "대여중"; 
    			bookInfo += " | " + "대여상태 : " + borrowResult;
    			System.out.println(bookInfo);
    			isExistList = true;
    		}
    	}
    	
    	if(!isExistList) {
    		System.out.println("> 검색된 도서가 없습니다.");
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
    		System.out.println("> 대여중인 도서가 없습니다.");
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
			
			//writeArray에 그대로 업데이트 해서 파일을 지우고 다시 입력하는데 사용
			writeArray.add(booksArray.get(i).toString() + "\r\n");
		}
    	
    	if(!isUpdate) {
    		System.err.print("검색된 도서가 없습니다.");
    	} else {
    		//변경을 적용 후 전체 리스트를 가지고 있기 때문에 그대로 overwrite 저장
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
		//"반납 기간은 대여일로부터 2주 후" 라는 조건은 2주가 지나면 반납을 못하게 하는게 말이 안되는거 같아서 특별한 처리를 하지 않음
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
	    	
	    	//writeArray에 그대로 업데이트 해서 파일을 지우고 다시 입력하는데 사용
			writeArray.add(booksArray.get(i).toString() + "\r\n");
	    }
	    	
	    if(!isUpdate) {
	    	System.err.println("반납할 수 있는 도서가 아닙니다.");
	    } else {
	    	//변경을 적용 후 전체 리스트를 가지고 있기 때문에 그대로 overwrite 저장
    		if(Util.updateFile("./file/books.txt", writeArray)) {
    			System.out.println(returnTitle + "을(를) 반납하였습니다.");
    		}
	    }
	}
	
	private void adminMenu() {
		//관리자 로그인 상태가 아닐때만 로그인 처리 
		if(!ADMIN_LOGIN_STATUS) {
			 System.out.print("관리자 아이디 : ");
			 String adminId = Util.readLine();
			 System.out.print("관리자 비밀번호 : ");
			 String adminPw = Util.readLine();
			 
			 ArrayList<String> adminAccount = Util.readFileLine("./file/admin.txt");
			 
			 if(adminAccount==null || adminAccount.size() < 1) {
				 System.err.println("관리자 계정이 설정되지 않았습니다.");
				 return;
			 }
			 
			 String fileAdminId = adminAccount.get(0);
			 String fileAdminPw = adminAccount.get(1);
			 
			 if(!fileAdminId.equals(adminId) || !fileAdminPw.equals(adminPw)) {
				 System.err.println("관리자 아이디 또는 비밀번호가 일치하지 않습니다.");
				 return;
			 } else {
				 ADMIN_LOGIN_STATUS = true;
			 }
		}
    	
    	System.out.println("1.회원관리 2.도서관리 ");
        int choice = Util.readInt();
        
        switch (choice) {
	        case 1:	//1.회원관리
	        	manageMember();
	            break;
	        case 2: //2.도서관리
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
		ArrayList<Account> accountArray = Util.fileToAccount("./file/account.txt");
    	for(int i=0; i<accountArray.size(); i++) {
    		//루프를 돌면서 조건에 맞으면 ID를 뽑아 낸다
    		if(accountArray.get(i).getName().equals(name) && accountArray.get(i).getBirthDate().equals(birthDate)) {
    			resultAccount = accountArray.get(i);
    			break;	//for문 종료
    		}
    	}
    	//검색된 Account 클래스가 없으면 종료
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
    	ArrayList<Books> booksArray = Util.fileToBooks("./file/books.txt");
    	for(int i=0; i<booksArray.size(); i++) {
    		//도서 목록의 대여자 정보가 검색한 회원의 아이디와 일치
    		if(booksArray.get(i).getBorrower().equals(resultAccount.getId())) {
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
		//파일에서 읽어오는 books 정보를 저장하기 위한 클래스 변수 선언
		//이건 Util에서 new로 클래스를 생성해서 줄꺼기 때문에 여기선 생성자처리(new Books() 같은 작업) 이 필요 없다.
		ArrayList<Books> booksArray;
		
		// 편안한 테스트를 위해서 아무것도 입력 안하면 전체 리스트를 그냥 보여준다
		if(title != "" && author != "" && publisher != "") {
			//중복 check
			booksArray = Util.fileToBooks("./file/books.txt");
			for(int i=0; i<booksArray.size(); i++) {
				if(booksArray.get(i).getTitle().equals(title)
						&& booksArray.get(i).getAuthor().equals(author)
						&& booksArray.get(i).getPublisher().equals(publisher)) {
					System.err.println("이미 등록된 도서 입니다.");
					return;
				}
			}
			
			//Books 클래스 생성
			Books books = new Books(title, author, publisher, "none", "none", "none");
			
			//파일 저장을 위한 String 타입 값
			String inputText = books.toString()+"\r\n";
			
			if(Util.writeFileLine("./file/books.txt", inputText)) {
				System.out.println("도서 정보가 저장 되었습니다.");
			}
		} else {
			System.err.println("미입력 값이 있습니다.");
		} 
		
		//저장이 완료 되면 도서 별 대여 여부 확인 겸 전제 리스트를 출력
		System.out.println("<등록된 도서 리스트>");
		booksArray = Util.fileToBooks("./file/books.txt");
		for(int i=0; i<booksArray.size(); i++) {
			System.out.println(booksArray.get(i).toString());
		}
	}
	
	

}
