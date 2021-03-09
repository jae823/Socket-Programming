package b0223server;

//8 1대다채팅 서버
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

//8

//쓰레드 클래스
//run이 동작메서드
class ServerReceiver1 extends Thread{   //이 안에는 Receiver 서버만 받는다.
	private Socket socket;			   //통신 소켓(클라이언트와 연결된 상태)
	private BufferedReader reader;	   //read객체(클라이언트가 보낸 정보를 읽을 수 있다.)
	private PrintWriter writer;		   //write객체(클라이언트에게 정보를 보낼 수 있다.)
	//----------------------------------------
	private HashMap<String, PrintWriter> clients;
	
	public ServerReceiver1(Socket socket,HashMap<String, PrintWriter>clients) {
		//6. Socket으로부터 InputStream, OutputStream을 얻음
		this.socket= socket;
		this.clients=clients;
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		} catch(Exception e){
			
		}
	}
	
	void sendToAll(String msg){
		//저장소 순회 객체
		//clients라는 저장소의 key값을 순회할 수 있는 iterator를 반환해줘
		Iterator<String> itr = clients.keySet().iterator();
		//1대다통신
		while(itr.hasNext()){
			PrintWriter writer = clients.get(itr.next());	//key를통해 value값을 획득
			writer.println(msg);		//전송[출력버퍼에 저장]
			writer.flush();				//[출력버퍼에 있는 정보를 밀어내는 역할]
		}
	}
	
	//쓰레드 함수
	@Override
	public void run(){
		String name="";
		try{
			//Init ---------------------------------------------
			name = reader.readLine();		//1)첫번째 Receive
			clients.put(name,writer);		//<----- [저장]
			sendToAll(name+"들어옴");			//2)전체 전송(1대 다 통신)
			System.out.println("현재 접속자수 "+clients.size());
			//---------------------------------------------------
			
			//Run -----------------------------------
			//7 InputStream, OutputStream을 이용한 통신
			//8 연결이 끊어질 때까지 통신
			while(reader!=null){
				String msg = reader.readLine(); // 1)데이터 수신
												// 2)데이터 처리
				sendToAll(reader.readLine());	// 3)결과를 전송
			}
			//---------------------------------------

		}catch(Exception e){e.printStackTrace();}
		finally {	//무조건 실행이 필요한 코드가 존재할 경우![더 이상 해당 소켓은 필요없다.]
			sendToAll(name+"나갔어");		
			clients.remove(name);		//<-----------[삭제!]
			System.out.println("[클라이언트 해제] "+socket.getInetAddress()+":"+socket.getPort());
			System.out.println("현재 서버접속자수"+clients.size());
			try{
				//9 소켓 닫음.
				socket.close();
			}catch(Exception e){e.printStackTrace();}
		}
	}
}

public class TcpIpMultiServertemp {

	final int PORT = 4000;						//대기소켓에서 사용할 포트번호
	HashMap<Socket, PrintWriter>clients;		//접속한 클라이언트들과 대화하기 위한 정보 저장
	private ServerSocket serverSocket = null;	//대기소켓 
	
	//생성자
	public TcpIpMultiServertemp(){
		Run();
	}
	
	//1. 대기소켓(소켓생성, 바인드, 리슨)
	private void InitWaitSocket() {
		try {
			serverSocket = new ServerSocket(PORT);		// Socket(), Bind(), Listen까지 다, 14.32.18.252
			System.out.println("서버시작,접속 기다림");	
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Run() {
		clients = new HashMap<Socket, PrintWriter>();
		//1. ServerSocket 생성
		//1. Socket생성, 2. Bind, 3. Listen 처리
		try{
			InitWaitSocket();
			while(true){
				//클라이언트 접속
				Socket socket = serverSocket.accept();
				System.out.println("[클라이언트접속] " + socket.getInetAddress()+":"+socket.getPort());

				//현재 연결된 소켓의 IO를 수행할 전용 thread
				//clients : 접속한 클라이언트와 사용하게될 데이터
				ServerReceiver thread = new ServerReceiver(socket, clients);
				thread.start();	//thread클래스의 run메서드가 호출
			}

			//2. ServerSocket 의 accept() 실행 및 대기(클라이언트가 접속할 때까지)
			//5. 클라이언트가 접속을 시도하면 accept() 메소드가 클라이언트의 socket을 리턴
		}catch(Exception e){
			System.out.println("exception: "+e.getMessage());
		}
	}

	public static void main(String[] args) {
		new TcpIpMultiServertemp();

	}

}



