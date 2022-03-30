package inMemory.copy;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Util {
	private static Scanner scanner;
	
	public static int readInt() {
    	scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException e) {
            throw new InputMismatchException("InputMismatchException");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("NoSuchElementException");
        }

    }
	
	public static String readLine() {
    	scanner = new Scanner(System.in);
        String line;
        try {
        	return scanner.nextLine();
        }
        catch (NoSuchElementException e) {
        	
            line = null;
        }
        return line;
    }

}
