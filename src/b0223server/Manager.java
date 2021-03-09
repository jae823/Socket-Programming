package b0223server;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
	//싱글톤 패턴 코드 --------------------------
	//생성자 은닉!
	private Manager() {	}
	
	//자신의 static 객체 생성
	private static Manager Singleton = new Manager();
	
	
	//내부적으로 생성된 자신의 객체를 외부에 노출 메서드
	public static Manager getInstance() {
		return Singleton;
	}
	//통신
	private TcpIpMultiServer server = new TcpIpMultiServer();
	//데이터베이스
	private AccountDB1 db = new AccountDB1();
	
	//----------------
	//계좌번호, 계좌
	private HashMap<Integer, Account> accountlist = new HashMap<Integer, Account>();
	
	public void Run() {
		if(db.Run() == false) {
			System.out.println("서버 종료");
			System.exit(0);	//강제 종료함수
		}
		server.Run();
		
	}
	
	//통신모듈에서 전달 ---> 파서에게 전달
	public String RecvData(String msg) {
		return Parser.RecvData(msg);		
	}
	
	//파서에서 분석된 결과에 따라 해당 함수를 호출 -------------------
	public String MakeAccount(int id, String name, int balance) {
		//저장!
		String msg = "";
		
		Account acc = new Account(id, name, balance);
		System.out.println("[수신메시지]");
		acc.Print();
		if(db.MakeAccount(id, name, balance) == true) {
			//저장성공
			msg = Packet.MakeAccount_ack(id, true);
		}
		else
			//저장실패
			msg = Packet.MakeAccount_ack(id, false);
		
		//클라이언트에 전송!
		return msg;
	}

	public String SelectAccount(int id) {
		Account acc = db.SelectAccount(id);
		
		//패킷 생성
		String msg = "";
		if(acc == null) {
			msg = Packet.SelectAccount_ack(id, "-", 0, "-", "-", false);
		}
		else {
			msg = Packet.SelectAccount_ack(id, acc.getName(), acc.getBalance(), acc.GetDate(), acc.GetTime(), true);
		}
		return msg;
	}

	public String InputAccount(int id, int balance) {
		String msg = "";
		if(db.InputAccount(id, balance)) {
			msg = Packet.InputAccount_ack(id, balance, true);
		} else {
			msg = Packet.InputAccount_ack(id, balance, false);
		}
		return msg;
	}

	public String OutputAccount(int id, int balance) {
		String msg = "";
		if(db.OutputAccount(id, balance)) {
			msg = Packet.OutputAccount_ack(id, balance, true);
		} else {
			msg = Packet.OutputAccount_ack(id, balance, false);
		}
		return msg;
	}

	public String DeleteAccount(int id) {
		String msg = "";
		if(db.DeleteAccount(id)) {
			msg = Packet.DeleteAccount_ack(id, true);
		} else {
			msg = Packet.DeleteAccount_ack(id, false);
		}
		return msg;
	}
	
	public String SelectAllAccount() {
		String msg = "";
		ArrayList<Account> acclist = db.SelectAllAccount();
		if(acclist != null)
			msg = Packet.SelectAllAccount_ack(acclist, true);
		else 
			msg = Packet.SelectAllAccount_ack(acclist, false);
		
		return msg;
	}

}
