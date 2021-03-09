package b0223server;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BitGlobalStatement {

	//사용할 쿼리문들을 정의
	public static PreparedStatement state_Insert;
//	public static PreparedStatement	state_select;
	public static PreparedStatement	state_update;
//	public static PreparedStatement	state_delete;
	
	public static void Init(Connection con) {
		try {
			String Insert = "insert into account(accid,name) values(?,?);";;
			state_Insert = con.prepareStatement(Insert);
			
			String Update = "update account set balance= balance + ? where accid= ?;";
			state_update = con.prepareStatement(Update);			
		}
		catch(Exception ex) {
			
		}
	}
	
}
