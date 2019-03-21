package Tehtava5OikeaRatkaisu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {

	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	
	private Connection yhdista(){
    	Connection con = null;    	        	
    	String JDBCAjuri = "org.mariadb.jdbc.Driver";
    	String url = "jdbc:mariadb://localhost:3306/a1700387";        	
    	try {
	         Class.forName(JDBCAjuri);
	         con = DriverManager.getConnection(url,"a1700387", "noFE8B57a");	        
	     }catch (Exception e){	         
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public boolean lisaaAuto(String rekNo, String merkki, String malli, int vuosi){
		boolean paluuArvo=true;
		sql="INSERT INTO autot VALUES(?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, rekNo);
			stmtPrep.setString(2, merkki);
			stmtPrep.setString(3, malli);
			stmtPrep.setInt(4, vuosi);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public void listaaKaikki(){
		sql = "SELECT * FROM autot";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					con.close();
					System.out.println();
					while(rs.next()){
						System.out.print(rs.getString(1) +"\t");
						System.out.print(rs.getString(2)+"\t");
						System.out.print(rs.getString(3)+"\t");	
						System.out.print(rs.getInt(4)+"\t");	
						System.out.println();
					}
					System.out.println();
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public boolean loytyykoAuto(String rekNo){
		boolean loytyi=false;
		sql = "SELECT * FROM autot WHERE rekNo=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekNo);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekNo on käytössä
        			rs.next();
        			System.out.print(rs.getString(1) +"\t");
					System.out.print(rs.getString(2)+"\t");
					System.out.print(rs.getString(3)+"\t");	
					System.out.print(rs.getInt(4)+"\t");
        			loytyi=true;
        			con.close();        			
				}			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return loytyi;		
	}
	
	public boolean muutaAuto(String rekNo, String rekNoUusi, String merkki, String malli, int vuosi){
		boolean paluuArvo=true;
		sql="UPDATE autot SET rekNo=?, merkki=?, malli=?, vuosi=? WHERE rekNo=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, rekNoUusi);
			stmtPrep.setString(2, merkki);
			stmtPrep.setString(3, malli);
			stmtPrep.setInt(4, vuosi);
			stmtPrep.setString(5, rekNo);
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean poistaAuto(String rekNo){
		boolean paluuArvo=true;
		sql="DELETE FROM autot WHERE rekNo=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, rekNo);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
}
