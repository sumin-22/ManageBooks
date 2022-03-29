package inMemory;

public class ManageBooks {

	public static void main(String[] args) {
		Membership membership = new Membership();
		Login login = new Login();
		
		boolean isTerminae = false;
		do {
			System.out.println("1.회원가입 2.로그인 3.종료");
			int choice = Util.readInt();
			
			switch (choice) {
				case 1: 
					System.out.println(membership);
					break;
				case 2:
					System.out.println(login);
					break;
				case 3:
					System.exit(0);
					break;
				default:
					System.err.println("선택 불가능한 숫자입니다. 다시 입력해주세요");
			}
		} while(!isTerminae);

	}

}
