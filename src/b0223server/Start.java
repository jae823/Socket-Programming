package b0223server;

public class Start {
	//Primary Thread
	public static void main(String[] args) {
		//서버를 테스팅하기위해
//		TcpIpMultiServer server = new TcpIpMultiServer();
//		server.Run();
		
		Manager.getInstance().Run();
		
		//DB를 테스팅하기위해
//		try {
//			AccountDB1 db = new AccountDB1();	
//		} catch (Exception ex) {
//			
//		}
	}
}
