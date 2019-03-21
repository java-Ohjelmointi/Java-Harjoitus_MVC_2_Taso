package Auto_Ohjelma_DB_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EkaRatkaisu {
	private Lukija lukija = new Lukija();
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	
	public EkaRatkaisu(){
		naytaValikko();
	}
	
	private void naytaValikko(){
		System.out.println("1. Näytä kaikki autot");
		System.out.println("2. Lisää uusi auto");
		System.out.println("3. Muuta auton tiedot");
		System.out.println("4. Poista auto");
		System.out.println("0. Lopeta ohjelma\n");
		
		switch (lukija.lueKokonaisluku("Valitse jokin toiminto: ")){
		
		case 1:
			listaaAutot();
			break;
		case 2:
			lisaaAuto();
			break;
		case 3:
			muutaAuto();
			break;
		case 4:
			poistaaAuto();
			break;
		case 0:
			System.exit(0);
			break;
		default:
			System.out.println("Valinta virheellinen");
			break;
		}
		naytaValikko();
	}
	
	//Listaa kaikki taulussa olvevat autot
	private void listaaAutot(){
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
	
	//Lisätään tauluun uusia auto
	private void lisaaAuto() {
		
		String rekno = lukija.lueTeksti("Aseta uudelle autolle rekkari: ");
		String merkki = lukija.lueTeksti("Aseta uudelle autolle merkki: ");
		String malli = lukija.lueTeksti("Aseta uudelle autolle malli: ");
		int vuosi = lukija.lueKokonaisluku("Aseta uudelle autolle vuosimalli: ");
		
		if(merkki.length()>1 && malli.length()>1 && rekno.length()>1){
			sql="INSERT INTO autot VALUES(?,?,?,?)";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, rekno);
				stmtPrep.setString(2, merkki);
				stmtPrep.setString(3, malli);
				stmtPrep.setInt(4, vuosi);
				stmtPrep.executeUpdate();
		        con.close();
		    	listaaAutot();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}		
	}
	//Muutetaan jonkun auton tiedot
	private void muutaAuto() {
		listaaAutot();
		String rekno = lukija.lueTeksti("Anna muutettavan auton rekno: ");
		sql = "SELECT * FROM autot WHERE rekno=?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekno);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli id on käytössä
        			String merkki = lukija.lueTeksti("Anna merkki: ");
        			String malli = lukija.lueTeksti("Anna malli: ");
        			int vuosi = lukija.lueKokonaisluku("Anna vmalli: ");
        			if(merkki.length()>1 && malli.length()>1 && rekno.length()>1){
        				sql="UPDATE autot SET merkki=?, malli=? vuosi=? WHERE rekno=?";	        				
    					stmtPrep=con.prepareStatement(sql); 
    					stmtPrep.setString(1, rekno);
    					stmtPrep.setString(2, merkki);
    					stmtPrep.setString(3, malli);
    					stmtPrep.setInt(4, vuosi);
    					stmtPrep.executeUpdate(); 
        			}
        			con.close();
        			listaaAutot();
				}else{
					System.out.println("Antamasi id ei ole käytössä!\n");
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	//poistetaan auto kannasta
	private void poistaaAuto() {
		listaaAutot();
		String rekno = lukija.lueTeksti("Anna poistettavan auton rekkari: ");
		sql = "SELECT * FROM autot WHERE rekno=?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekno);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli id on käytössä
        			rs.next(); //siirrytään 1. tietueriville
        			if(lukija.lueTeksti("Haluatko varmasti poistaa kysisen auton "+rs.getString("rekno")
        					+rs.getString("merkki")+" "+rs.getString("malli")
        					+ "(k/e): ").equalsIgnoreCase("k")){
        				sql="DELETE FROM autot WHERE rekno=?";	        				
    					stmtPrep=con.prepareStatement(sql);     					
    					stmtPrep.setString(1,rekno);
    					stmtPrep.executeUpdate(); 
        			}       			
        			con.close();
        			listaaAutot();
				}else{
					System.out.println("Rekisterinumeroa vastaava auto ei löydy järjestelmästä !\n");
					
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		System.out.println("!!Auton poistaminen järjestelmästä onnistui!! \n");
	}
	
		//Luodaan tietokanta yhteys eli "pistoke laitetaan seinään"
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
			new EkaRatkaisu();		
	    }	
	
	

	
	
	}

