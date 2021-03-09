package b0223client;

//9  1대다채팅 클라이언트
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

class ClientSender2 extends Thread{
	//private Socket socket;
	private PrintWriter writer;
	
	public ClientSender2(Socket socket){
		//6.소켓으로부터 인풋스크림, 아웃풋 스크림 얻음
	//	this.socket=socket;
		try{
			writer = new PrintWriter(socket.getOutputStream());
		}catch(Exception e) {}
	}	
	
	public void SendMessage(String msg) {
		writer.println(msg);
		writer.flush();
		System.out.println(msg + "msg출력");
	}
}

//수신 전용 thread
class ClientReceiver2 extends Thread{
	private Socket socket;
	private BufferedReader reader;
	private Bank bank;
	
	public ClientReceiver2(Socket socket, Bank bank){
		this.socket=socket;
		this.bank = bank;
		try{
			//6. 소켓으로부터 인풋, 아웃풋 스크림 얻음
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(IOException e) {}
	}
	
	@Override
	public void run(){
		//7.인풋,아웃풋 이용한 통신
		//8. 연결이 끊어질때까지 통신
		while(reader !=null){
			try{
			//System.out.println(reader.readLine());		//1. 수신
				bank.RecvData(reader.readLine());			//2. 연산
			}catch(IOException e){}
		}
		try{ //소켓 close()
			socket.close();
		}catch(IOException e) {e.printStackTrace(); }
	}
}


public class TcpIpMultiClient2 {
	final int PORT = 4000;
	final String SERVER_IP = "localhost";	//14.32.18.42
	private ClientSender2 clientsender = null;
	private Bank bank;
	
	
	//생성자, 쌍방참조(서로가 서로의 메소드를 필요로 할때)
	public TcpIpMultiClient2(Bank bank) {
		this.bank=bank;
	}
	
	//메서드 
	public void Run() {
		try{
			//소켓생성 및 연결!
			Socket socket = new Socket(SERVER_IP, PORT);
			System.out.println("서버 연결 됨");
			

			clientsender = new ClientSender2(socket);
			Thread receiver = new ClientReceiver2(socket, bank); 

			clientsender.start();
			receiver.start();
		}catch(ConnectException e){
			e.printStackTrace();
		}catch(Exception e) { }
	}
	
	//데이터 전송 기능
	public void SendMessage(String msg) {
		try {
			clientsender.SendMessage(msg);	
		} catch (Exception ex) {
			System.out.println("전송오류");
		}
		
	}

}
