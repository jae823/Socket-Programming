package b0223server;


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

public class Parser {

	public static String RecvData(String msg) {
		// TODO Auto-generated method stub
		String[] filter = msg.split("@");
		if(filter[0].equals("MakeAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			String name = filter1[1];
			int balance = Integer.parseInt(filter1[2]);
			return Manager.getInstance().MakeAccount(number, name, balance);
		} else if (filter[0].equals("SelectAccount")) {
			int number = Integer.parseInt(filter[1]);
			return Manager.getInstance().SelectAccount(number);
		} else if (filter[0].equals("InputAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			int balance = Integer.parseInt(filter1[1]);
			return Manager.getInstance().InputAccount(number, balance);
		} else if (filter[0].equals("OutputAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			int balance = Integer.parseInt(filter1[1]);
			return Manager.getInstance().OutputAccount(number, balance);
		} else if (filter[0].equals("DeleteAccount")) {
			String[] filter1 = filter[1].split("#");
			int number = Integer.parseInt(filter1[0]);
			return Manager.getInstance().DeleteAccount(number);
		} else if (filter[0].equals("SelectAllAccount")) {
			return Manager.getInstance().SelectAllAccount();
		}
		return null;
	}
}
