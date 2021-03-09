package b0223client;

/*
[client -> Server]
   "MakeAccount@111#ccm#1000"	//계좌번호, 이름, 입금액
   "SelectAccount@111"			//계좌번호
   "InputAccount@111#1000"  	//계좌번호, 입금액
   "OutputAccount@111#1000"		//계좌번호, 출금액
   "DeleteAccount@111"			//계좌번호
   "SelectAllAccount@"			//없음.

[server -> Client]
	"MakeAccount_ck@111#S"
	"MakeAccount_ack@111#F"
*/



public class Packet {
	public static String MakeAccount(int id, String name, int balance) {
		String msg = "";
		
		msg += "MakeAccount" + "@";
		msg += id + "#";
		msg += name + "#";
		msg += balance;
		
		return msg;
	}
	
	public static String SelectAccount(int id) {

		String msg = "";
		
		msg += "SelectAccount" + "@";
		msg += id;
		
		return msg;
	}

	public static String InputAccount(int id, int balance) {

		String msg = "";
		
		msg += "InputAccount" + "@";
		msg += id + "#" ;
		msg += balance;
		
		return msg;
	}
	
	public static String OutputAccount(int id, int balance) {

		String msg = "";
		
		msg += "OutputAccount" + "@";
		msg += id + "#" ;
		msg += balance;
		
		return msg;
	}
	
	public static String DeleteAccount(int id) {

		String msg = "";
		
		msg += "DeleteAccount" + "@";
		msg += id;
		
		return msg;
	}
	
	public static String SelectAllAccount() {

		String msg = "";
		
		msg += "SelectAllAccount" + "@";

		return msg;
	}

}
