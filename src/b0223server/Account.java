package b0223server;

import java.sql.Timestamp;
import java.util.Calendar;

public class Account {
	private int accid;
	private String name;
	private int balance;
	Calendar newtime;
	
	public Account(int accid, String name) {
		this(accid, name, 0);
	}
	
	public Account(int accid, String name, int balance, Timestamp dt) {
		this(accid, name, balance);
		this.newtime.setTime(dt);
	}
	
	public Account(int accid, String name, int balance) {
		this.setAccid(accid);
		this.setName(name);
		this.setBalance(balance);
		newtime = Calendar.getInstance();
	}
	

	public int getAccid() {
		return accid;
	}

	private void setAccid(int accid) {
		this.accid = accid;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getBalance() {
		return balance;
	}

	private void setBalance(int balance) {
		this.balance = balance;
	}
	
	public Calendar getNewTime() {
		return newtime;
	}
	
	@SuppressWarnings("unused")
	private void setNewTime(Calendar newtime) {
		this.newtime =newtime;
	}
	
	//�޼���
	public void InputMoney(int money) throws Exception {
		if(money<0) 
			throw new Exception("�߸��� �ݾ��Դϴ�.");
		balance += money;
	}
	//����� : ����� ������ ã�� ����!-> �ذ�
	// Ȯ���� �ڵ忡 breakpoint�� ������ �� -> ������� ����
	public void OutputMoney(int money) throws Exception {
		if(money<0) 
			throw new Exception("�߸��� �ݾ��Դϴ�.");
		if(money > balance) 
			throw new Exception("�ܾ��� �����մϴ�.");
		balance -= money;
	}
	
	public String GetDate() {
		String temp = String.format("%04d-%02d-%02d", 
				newtime.get(Calendar.YEAR) ,newtime.get(Calendar.MONTH), newtime.get(Calendar.DAY_OF_MONTH));
		return temp;
	}
	
	public String GetTime() {
		String temp = String.format("%02d:%02d", 
				newtime.get(Calendar.HOUR_OF_DAY),newtime.get(Calendar.MINUTE));
		return temp;
	}
	
	public void Println() {
		System.out.println("[계좌번호]" + accid);
		System.out.println("[이름]" + name);
		System.out.println("[금액]" + balance + "��");
		System.out.println("[개설날짜] " + GetDate());
		System.out.println("[개설시간] " + GetTime());
	}
	
	public void Print() {		
		System.out.print("[" + accid + "] ");
		System.out.print(name + " ");
		System.out.print(balance + "�� ");
		System.out.print(GetDate() + " ");
		System.out.println(GetTime());
	}



	
}
