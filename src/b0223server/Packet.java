package b0223server;

import java.util.ArrayList;

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
	"SelectAccount_ack@S#111#ccm#1000#날짜#시간"
	"SelectAccount_ack@F#111#-#0#-#-"
	"InputAccount_ack@S#111#입금액"				"SelectAccount_ack@F#111#입금액"
	"OutputAccount_ack@S#111#출금액"				"SelectAccount_ack@F#111#출금액"
 */

public class Packet {
	public static String MakeAccount_ack(int id, boolean b) {
		String msg = "";
		msg += "MakeAccount_ack" + "@";
		msg += id + "#";
		if(b)
			msg += "S";
		else 
			msg += "F";
		return msg;
	}

	public static String SelectAccount_ack(int id, String name, int balance,String date, String time, boolean b) {
		String msg = "";
		msg +=  "SelectAccount_ack" + "@";
		if(b)	msg += "S" + "#";
		else	msg += "F" + "#";
		msg += id + "#";
		msg += name + "#";
		msg += balance + "#";
		msg += date + "#";
		msg += time + "#";

		return msg;
	}


	public static String InputAccount_ack(int id, int balance, boolean b) {
		String msg = "";

		msg += "InputAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else  msg += "F" + "#";
		msg += id + "#" ;
		msg += balance;

		return msg;
	}

	public static String OutputAccount_ack(int id, int balance, boolean b) {
		String msg = "";

		msg += "OutputAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else  msg += "F" + "#";
		msg += id + "#" ;
		msg += balance;

		return msg;
	}

	public static String DeleteAccount_ack(int id, boolean b) {
		String msg = "";

		msg += "DeleteAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else  msg += "F" + "#";
		msg += id + "#" ;

		return msg;
	}

	//헤더(flag)@성공/실패#[첫번째사람부가정보%...%]#[두번째사람부가정보%..%]
	public static String SelectAllAccount_ack(ArrayList<Account> acclist, boolean b) {
		String msg = "";

		msg += "SelectAllAccount_ack" + "@";
		if(b) msg += "S" + "#";
		else  msg += "F" + "#";

		for(Account ac : acclist) {
			msg += ac.getAccid() + "%";
			msg += ac.getBalance() + "%";
			msg += ac.getBalance() + "%";
			msg += ac.GetDate() + "%";
			msg += ac.GetTime() + "#";
		}
		
		return msg;
	}

}
