package b0223server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AccountDB1 {
	Connection con = null;
	
	//미리 쿼리문의 포맷을 미리 설정...
	PreparedStatement stmt_insert = null;
	
	//생성자
	public AccountDB1() {
		
	}
	
	//DB연결 (Connection 객체 생성)
	public boolean Run() {
		try {			
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로딩 성공");
			//"jdbc:mysql://localhost:3306?serverTimezone=UTC","root","1234");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleDB?serverTimezone=UTC","root","root");
			con.setAutoCommit(false);
			System.out.println("데이터베이스 연결성공");
			return true;
		} catch (Exception e) {
			System.out.println("[데이터베이스 초기화 에러] "  + e.getMessage());
			return false;
		}	
	}
	
	private void InitStatement() {
		BitGlobalStatement.Init(con);
	}
	
	
	//기능
	public boolean MakeAccount(int accnum, String name, int balance) {
		try {
			String Insert = "insert into account(accid, name, balance) values (?, ?, ?);";
			PreparedStatement sment = con.prepareStatement(Insert);
			sment.setInt(1, accnum);
			sment.setString(2, name);
			sment.setInt(3, balance);			
			int i = sment.executeUpdate();
			sment.close();   //
			if(i > 0) {
				con.commit();				 
				return true;
			} 
			return false;	
		} catch (Exception ex) {
			System.out.println("Insert실패" + ex.getMessage());
			return false;
		}
	}
	
	public Account SelectAccount(int accnum) {
		try {
			String sql = "select * from account where accid = ?;";
			PreparedStatement sment = con.prepareStatement(sql);		
			sment.setInt(1, accnum);
			//--------------------------------------------------
			ResultSet rs = sment.executeQuery();
			int accid=0;
			String name="";
			int balance=0;
			Timestamp ntime=null;
			if(rs.next()) {
				accid = rs.getInt(1);				
				name = rs.getString(2);
				balance = rs.getInt(3);
				ntime = rs.getTimestamp(4);
			}
			sment.close();
			Account acc = new Account(accid, name, balance, ntime);
			return acc;
		} catch (Exception ex) {
			System.out.println("Select실패 " + ex.getMessage());
			return null;
		}
	}

	public boolean InputAccount(int id, int balance) {
		try {
			String sql = "update account set balance = balance + ? where accid = ?;";
			PreparedStatement sment = con.prepareStatement(sql);		
			sment.setInt(1, balance);
			sment.setInt(2, id);
			//--------------------------------------------------
			int i = sment.executeUpdate();
			sment.close();
			if(i > 0) {
				con.commit();				 
				return true;
			} 
			return false;	
		} catch (Exception ex) {
			System.out.println("Update실패" + ex.getMessage());
			return false;
		}
	}
	
	public boolean OutputAccount(int id, int balance) {
		try {
			//잔액이 부족한 경우??
			String sql = "update account set balance = balance - ? where accid = ? and balance >= ?;";
			
			
			PreparedStatement sment = con.prepareStatement(sql);		
			sment.setInt(1, balance);
			sment.setInt(2, id);
			sment.setInt(3, balance);
			//--------------------------------------------------
			int i = sment.executeUpdate();
			sment.close();
			if(i > 0) {
				con.commit();				 
				return true;
			} 
			return false;	
		} catch (Exception ex) {
			System.out.println("Update실패" + ex.getMessage());
			return false;
		}
		
	}

	public boolean DeleteAccount(int id) {
		try {
			String sql = "delete from account where acc_id = ?;";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			int i = stmt.executeUpdate();			
			stmt.close();
			if(i > 0) {
				con.commit();
				return true;	
			}
			return false;
		} catch (Exception ex) {
			System.out.println("Delete 실패 " + ex.getMessage());
			return false;		
		}
	}

	public ArrayList<Account> SelectAllAccount() {
		try {
			String sql = "select * from account";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Account> acclist = new ArrayList<Account>();
			while(rs.next()) {
				int accid = rs.getInt(1);				
				String name = rs.getString(2);
				int balance = rs.getInt(3);
				Timestamp ntime = rs.getTimestamp(4);
				acclist.add(new Account(accid, name, balance, ntime));
			}
			stmt.close();
			return acclist;
		} catch (Exception ex) {
			System.out.println("SelectAll 실패 " + ex.getMessage());
			return null;		
		}
	}
	

//	public boolean Insert(int id, String name) {		
//		try {	
//			BitGlobalStatement.state_Insert.setInt(1,  id);
//			BitGlobalStatement.state_Insert.setString(2,  name);
//			int i = BitGlobalStatement.state_Insert.executeUpdate();
//			if( i > 0) {
//				con.commit();
//				return true;
//			}
//			else       
//				throw new Exception("");	
//		}
//		catch(Exception ex) {
//			System.out.println(ex.getMessage());
//			try {
//			con.rollback();			//<=-------------------------------
//			}
//			catch(Exception ex1) {}
//			return false;
//		}
//	}

	

}











