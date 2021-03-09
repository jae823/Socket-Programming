package b0223client;
import java.util.Scanner;

//BitGlobal.java

public class BitGlobal {
	static Scanner scan = new Scanner(System.in);
	
	//메서드
	public static void Logo() {
		System.out.println("*************************************************");
		System.out.println(" Java 전문가 과정");
		System.out.println(" 2021.02.09");
		System.out.println(" 배열자료구조를 활용한 계좌 관리 프로그램");
		System.out.println(" 최창민");
		System.out.println("*************************************************\n");
		Pause();
	}
	
	public static void Ending() {
		System.out.println("*************************************************");
		System.out.println(" 프로그램을 종료합니다.");
		System.out.println("*************************************************");
	}

	//메뉴
	public static char MenuPrint() {
		System.out.println("*************************************************");
		System.out.println(" [0] 프로그램 종료");
		System.out.println(" [1] 계좌 생성");
		System.out.println(" [2] 계좌 검색");
		System.out.println(" [3] 입금");
		System.out.println(" [4] 출금");
		System.out.println(" [5] 계좌 삭제");
		System.out.println(" [6] 전체 계좌");
		System.out.println("*************************************************");
		System.out.print(" >> ");
		return scan.nextLine().charAt(0);
	}

	//Pause 멈추는 기능
	public static void Pause() {
		System.out.print("엔터키를 누르세요....");
		scan.nextLine();		
	}

	//입력 기능 함수
	public static int InputNumber(String msg) {
		System.out.print(msg + " : ");
		return Integer.parseInt(scan.nextLine());
	}
	
	public static String InputString(String msg) {
		System.out.print(msg + " : ");
		return scan.nextLine();
	}
	
	public static char InputChar(String msg) {
		System.out.print(msg + " : ");
		//String msg1= scan.nextLine();
		//char ch = msg1.charAt(0);
		//return ch;
		return scan.nextLine().charAt(0);
	}
}
















