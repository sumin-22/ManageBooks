package BookManage;

public class InMemoryMain {
	public static void main(String[] args) {
		
		InMemory membership = new InMemory();
		
		boolean isTerminae = false;
		do {
			System.out.println("======== 도서관리 =======");
			System.out.println("1.회원관리\t2.로그인\t3.종료 ");
			System.out.print("➔ ");
			int choice = Util.readInt();
			
			switch (choice) {
				case 1: 
					membership.membership();
					break;
				case 2:
					membership.login();
					break;
				case 3:
					isTerminae = true;
					break;
				default:
					System.err.println("유효하지 않은 번호입니다. 다시 입력해주세요");
			}
		} while(!isTerminae);

	}

}
