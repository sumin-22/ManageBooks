package BookManage;

public class FileSystemMain {

	public static void main(String[] args) {
		FileSystem actionClass = new FileSystem();
		
		boolean isTerminate = false;
		do {
			System.out.println("======== 도서관리 =======");
			System.out.println("1.회원가입 2.로그인 3.종료");
			System.out.print("➔ ");
			int choice = Util.readInt();
			
			switch (choice) {
				case 1: 
					actionClass.membership();
					break;
				case 2:
					actionClass.login();
					break;
				case 3:
					System.out.println("종료 합니다.");
					isTerminate = true;
					break;
					
				default:
					System.err.println("선택 불가능한 숫자입니다. 다시 입력해주세요");
					
			}
		} while(!isTerminate);
	}

}
