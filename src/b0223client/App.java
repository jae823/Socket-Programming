package b0223client;
//app.java

//실행의 흐름을 담당
public class App {

	//싱글톤 패턴 코드 -------------------------------------------------
	//생성자 은닉!
	private App() {
		Init();
	}
	//자신의 static 객체 생성
	private static App Singleton = new App();
	
	//내부적으로 생성된 자신의 객체를 외부에 노출 메서드
	public static App getInstance() {
		return Singleton;
	}
	//---------------------------------------------------------------
	
	private Bank bank = new Bank();
	
	//메서드
	//초기화 영역
	private void Init() {
		BitGlobal.Logo();
	}
	
	//반복적 실행 - 엔진
	public void Run() {
		while(true) {		
			switch(BitGlobal.MenuPrint()) {
			case '0': return;
			case '1': bank.MakeAccount();	break;
			case '2': bank.SelectAccount(); break;
			case '3': bank.InputMoney(); 	break;
			case '4': bank.OutputMoney(); 	break;
			case '5': bank.DeleteAccount(); break;
			case '6': bank.PrintAll(); break;
			}
			BitGlobal.Pause();
		}
	}
	
	//종료처리 영역
	public void Exit() {
		BitGlobal.Ending();
	}
}












