package inMemory;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Util {
	//Scanner 변수의 static 선언
	private static Scanner scanner;
	
	//숫자 입력 받기 
	public static int readInt() {
		scanner = new Scanner(System.in);
		try {
			return scanner.nextInt();
		} catch (InputMismatchException e) {
			throw new InputMismatchException("InputMismatchException");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("NoSuchElementException");
		}
	}
	
	//문자열 입력 받기
	public static String readLine() {
		scanner = new Scanner(System.in);
		String line;
		try {
			line = scanner.nextLine();
			
		} catch (NoSuchElementException e) {
			line = null;
		}
		return line;
		
	}

}
