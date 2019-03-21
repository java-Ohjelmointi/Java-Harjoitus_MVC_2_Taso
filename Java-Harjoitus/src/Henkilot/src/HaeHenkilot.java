package Henkilot.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HaeHenkilot {	
	private Lukija lukija = new Lukija();
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	
	public HaeHenkilot(){
		naytaValikko();
	}
	
	private void naytaValikko() {
		System.out.println("1. Näytä kaikki henkilöt");
		System.out.println("2. Lisää henkilö");
		System.out.println("3. Muuta henkilö");
		System.out.println("4. Poista henkilö");
		System.out.println("0. Lopeta");
		switch (lukija.lueKokonaisluku("Valintasi: ")) {
		case 1:
			listaaHenkilot();
			break;
		case 2:
			lisaaHenkilo();
			break;
		case 3:
			muutaHenkilo();
			break;
		case 4:
			poistaHenkilo();
			break;
		case 0:
			System.exit(0);
			break;
		default:
			System.out.println("Väärä valinta!");
			break;
		}
		naytaValikko();		
	}
	
	private void listaaHenkilot(){
		sql = "SELECT * FROM henkilot";       
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
						System.out.println();
					}
					System.out.println();
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void lisaaHenkilo() {
		int id = lukija.lueKokonaisluku("Anna uuden henkilön id: ");
		String etunimi = lukija.lueTeksti("Anna uuden henkilön etunimi: ");
		String sukunimi = lukija.lueTeksti("Anna uuden henkilön sukunimi: ");
		if(etunimi.length()>1 && sukunimi.length()>1){
			sql="INSERT INTO henkilot VALUES(?,?,?)";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setInt(1, id);
				stmtPrep.setString(2, etunimi);
				stmtPrep.setString(3, sukunimi);
				stmtPrep.executeUpdate();
		        con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}		
	}
	
	private void muutaHenkilo() {
		listaaHenkilot();
		int id = lukija.lueKokonaisluku("Anna muutettavan henkilön id: ");
		sql = "SELECT * FROM henkilot WHERE id=?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, id);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli id on käytössä
        			String etunimi = lukija.lueTeksti("Anna henkilön uusi etunimi: ");
        			String sukunimi = lukija.lueTeksti("Anna henkilön uusi sukunimi: ");
        			if(etunimi.length()>1 && sukunimi.length()>1){
        				sql="UPDATE henkilot SET etunimi=?, sukunimi=? WHERE id=?";	        				
    					stmtPrep=con.prepareStatement(sql); 
    					stmtPrep.setString(1, etunimi);
    					stmtPrep.setString(2, sukunimi);
    					stmtPrep.setInt(3, id);
    					stmtPrep.executeUpdate(); 
        			}
        			con.close();
        			listaaHenkilot();
				}else{
					System.out.println("Antamasi id ei ole käytössä!\n");
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void poistaHenkilo() {
		listaaHenkilot();
		int id = lukija.lueKokonaisluku("Anna poistettavan henkilön id: ");
		sql = "SELECT * FROM henkilot WHERE id=?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, id);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli id on käytössä
        			rs.next(); //siirrytään 1. tietueriville
        			if(lukija.lueTeksti("Haluatko varmasti poistaa henkilön "
        					+rs.getString("etunimi")+" "+rs.getString("sukunimi")
        					+ "(k/e): ").equalsIgnoreCase("k")){
        				sql="DELETE FROM henkilot WHERE id=?";	        				
    					stmtPrep=con.prepareStatement(sql);     					
    					stmtPrep.setInt(1, id);
    					stmtPrep.executeUpdate(); 
        			}       			
        			con.close();
        			listaaHenkilot();
				}else{
					System.out.println("Antamasi id ei ole käytössä!\n");
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
		
	private Connection yhdista(){
    	Connection tietokantayhteys = null;    	        	
    	String JDBCAjuri = "org.mariadb.jdbc.Driver";
    	String url = "jdbc:mariadb://localhost:3306/a1700387";   	
    	try {
	         Class.forName(JDBCAjuri);
	         tietokantayhteys = DriverManager.getConnection(url,"a1700387", "noFE8B57a");	        
	     }catch (Exception e){
	    	 e.printStackTrace();	
	     }
	     return tietokantayhteys;
	}
	
	public static void main(String[] args) {		
		new HaeHenkilot();		
    }	
}