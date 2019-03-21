package MVC_2_taso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public boolean lisaaAuto(Auto auto){
		boolean paluuArvo=true;
		sql="INSERT INTO autot VALUES(?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, auto.getRekNo());
			stmtPrep.setString(2, auto.getMerkki());
			stmtPrep.setString(3, auto.getMalli());
			stmtPrep.setInt(4, auto.getVuosi());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public ArrayList<Auto> listaaKaikki(){
		ArrayList<Auto> autot = new ArrayList<Auto>();
		sql = "SELECT * FROM autot";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					con.close();					
					while(rs.next()){
						Auto auto = new Auto();
						auto.setRekNo(rs.getString(1));
						auto.setMerkki(rs.getString(2));
						auto.setMalli(rs.getString(3));	
						auto.setVuosi(rs.getInt(4));	
						autot.add(auto);
					}					
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return autot;
	}
	
	public Auto loytyykoAuto(String rekNo){
		Auto auto = null;
		sql = "SELECT * FROM autot WHERE rekNo=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekNo);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekNo on käytössä
        			rs.next();
        			auto = new Auto();        			
        			auto.setRekNo(rs.getString(1));
					auto.setMerkki(rs.getString(2));
					auto.setMalli(rs.getString(3));	
					auto.setVuosi(rs.getInt(4));        			
        			con.close();        			
				}			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return auto;		
	}
	
	public boolean muutaAuto(Auto auto, String rekNo){
		boolean paluuArvo=true;
		sql="UPDATE autot SET rekNo=?, merkki=?, malli=?, vuosi=? WHERE rekNo=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, auto.getRekNo());
			stmtPrep.setString(2, auto.getMerkki());
			stmtPrep.setString(3, auto.getMalli());
			stmtPrep.setInt(4, auto.getVuosi());
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
