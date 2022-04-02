package inMemory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		try {
			return scanner.nextLine();
		
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("NoSuchElementException");
		}
	}
	
	//날짜를 String으로 리턴
	public static String changeDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
		String returnString = simpleDateFormat.format(date);
		return returnString;
	}
	
	//파일 텍스트 읽은 후 ArrayList로 리턴 (저장)
	public static ArrayList<String> readFileLine(String path){
		ArrayList<String> returnArray = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while(true) {
				String line = br.readLine();
				if (line != null) {
					returnArray.add(line);
				} else {
					break;
				}
			}
			br.close();
		} catch (IOException e) {
			System.err.println(e);
		}
		return returnArray;
	}
	
	//파일에 텍스트 쓰기
	public static boolean writerFileLine(String path, String text) {
		boolean result = true;
		try {
			FileWriter fw = new FileWriter(path, true);
			fw.write(text);
			fw.close();
		} catch (IOException e) {
			System.err.print(e);
			result = false;
		}
		return result;
	}
	
	//파일에 텍스트 업데이트
	public static boolean updateFile(String path, ArrayList<String> arrayList) {
		boolean result = true;
		try {
			FileWriter fw = new FileWriter(path);
			for (int i =0; i <arrayList.size(); i++) {
				fw.write(arrayList.get(i));
			}
			fw.close();
			
		} catch (IOException e) {
			System.err.print(e);
			result =  false;
		}
		return result;
	}
	
	public static ArrayList<Account> fileToAccount(String path) {
		File f = new File(path);
		if(!f.exists()) {
			System.err.println("파일이 존재하지 않습니다.");
			return null;
		}
		
		ArrayList<Account> returnArray = new ArrayList<>();
		ArrayList<String> arrayFile = readFileLine(path);
		
		for(int i=0; i<arrayFile.size(); i++) {
			String[] split = arrayFile.get(i).split(",");
			String name = split[0];
			String birthDate = split[1];
			String cellphone = split[2];
			String id = split[3];
			String password = split[4];
			returnArray.add(new Account(name, birthDate, cellphone, id, password));
		}
		
		return returnArray;
	}
	
	public static ArrayList<Books> fileToBooks(String path) {
		File f = new File(path);
		if(!f.exists()) {
			System.err.println("파일이 존재하지 않습니다.");
			return null;
		}
		
		ArrayList<Books> returnArray = new ArrayList<>();
		ArrayList<String> arrayFile = readFileLine(path);
		
		for(int i=0; i<arrayFile.size(); i++) {
			String[] split = arrayFile.get(i).split(",");
			String title = split[0];
			String author = split[1];
			String publisher = split[2];
			String borrower = split[3];
			String rentalDate = split[4];
			String returnDate = split[5];
			returnArray.add(new Books(title, author, publisher, borrower, rentalDate, returnDate));
		}
		
		return returnArray;
	}
	

}
