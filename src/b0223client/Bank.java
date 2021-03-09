//bank.java
package b0223client;

import java.util.ArrayList;

public class Bank {
	//통신 모듈
	//client의 Run메서드 호출 시 서버 접속 연결!
	private TcpIpMultiClient2 client;	

	//Bank생성시 서버 접속 요청!
	public Bank() {
		client = new TcpIpMultiClient2(this);	//자신을 넘긴다.
		client.Run();
	}

	
	//서버로 요청정보 전송-----------------------
	public void PrintAll() {
		try {
			String msg = Packet.SelectAllAccount();
			client.SendMessage(msg);
			System.out.println("서버로 전체계좌리스트 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void MakeAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			String name = BitGlobal.InputString("이름");
			int money = BitGlobal.InputNumber("입금액");

			String msg = Packet.MakeAccount(number, name, money);
			//출력! 패킷을통해 parsing된 데이터 값
			System.out.println(msg);
			client.SendMessage(msg);
			System.out.println("서버로 신규계좌정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void SelectAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");

			String msg = Packet.SelectAccount(number);
			client.SendMessage(msg);
			System.out.println("서버로 검색할 계좌번호 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void InputMoney() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			int money = BitGlobal.InputNumber("입금액");

			String msg = Packet.InputAccount(number, money);
			client.SendMessage(msg);
			System.out.println("서버로 계좌입금 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void OutputMoney() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");
			int money = BitGlobal.InputNumber("출금액");

			String msg = Packet.OutputAccount(number, money);
			client.SendMessage(msg);
			System.out.println("서버로 계좌출금 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}

	public void DeleteAccount() {
		try {
			int number = BitGlobal.InputNumber("계좌번호");

			String msg = Packet.DeleteAccount(number);
			client.SendMessage(msg);
			System.out.println("서버로 삭제계좌 정보를 전송");
		}
		catch(Exception ex) {
			System.out.println("[전송실패] " + ex.getMessage());
		}
	}
	//-------------------------------------
	
	//-- 통신모듈에서 수신된 데이터를 받는 기능 -------
	
	
	/*
	 *[server -> Client]
	 *		"MakeAccount_ck@111#S"
	 *		"MakeAccount_ack@111#F" 
	 */
	
	public void RecvData(String msg) {
		Parser.RecvData(msg, this);
	}
	
	public void MakeAccount_Ack(int number, String result) {
		if(result.equals("S"))
			System.out.println(number + "계좌 생성 성공");
		else
			System.out.println(number + "계좌 생성 실패");
	}


	public void SelectAccount_Ack(String result, int number, String name, int balance, String date, String time) {
		if(result.equals("F")) {
			System.out.println(number + "계좌 번호는 없는 번호입니다");
			return;
		} 
	
		System.out.println("계좌번호 : " + number);
		System.out.println("이름 : " + name);
		System.out.println("잔액 : " + balance);
		System.out.println("개설날짜 : " + date);
		System.out.println("개설시간 : " + time);
	}
	
	public void InputAccount_ack(String result, int number, int balance) {
		if(result.equals("F")) {
			System.out.println(number + "계좌에 입금이 실패하였습니다.");
			return;
		} 
	
		System.out.println("계좌번호 : " + number);
		System.out.println("잔액 : " + balance);
	}
	
	public void OutputAccount_ack(String result, int number, int balance) {
		if(result.equals("F")) {
			System.out.println(number + "계좌에 출금이 실패하였습니다.");
			return;
		} 
	
		System.out.println("계좌번호 : " + number);
		System.out.println("잔액 : " + balance);
	}


	public void SelectAllAccount_ack(String result, ArrayList<Account> acclist) {
		System.out.println("저장개수 : " + acclist.size());
		for(Account ac : acclist) {
			ac.Print();
		}
		
	}

}









