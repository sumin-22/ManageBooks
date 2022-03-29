package inMemory.copy;

import java.util.ArrayList;

public class ManageBooks {
	
//	static ManageBooks manageBooks = new ManageBooks();
//	private static ArrayList<Account> acoountList = new ArrayList<>();
//	static ManageBooks manageBooks = new ManageBooks();
	public static void main(String[] args) {
		
		Membership membership = new Membership();
		Login login = new Login();
		
		boolean isTerminate = false;
		do {
			System.out.println("1.회원가입 2.로그인 3.종료");
			int choice = Util.readInt();
			
			switch (choice) {
				case 1: 
					membership.membership();
					break;
				case 2:
					
					break;
				case 3:
					for(int i =0; i<membership.accountList.size(); i++) {
						System.out.println(membership.accountList.get(i).toString());
					}
					isTerminate = true;
					break;
					
				default:
					System.err.println("선택 불가능한 숫자입니다. 다시 입력해주세요");
					
			}
		} while(!isTerminate);
	}

}
