package MVC_2_taso;

import java.util.ArrayList;

public class AutoOhjelmaDB {
	private Lukija lukija = new Lukija();
	private Dao dao = new Dao();
	
	public AutoOhjelmaDB(){
		naytaValikko();
	}

	private void naytaValikko() {
		System.out.println("\n1. Lisää auto");
		System.out.println("2. Muuta autoa");
		System.out.println("3. Poista auto");
		System.out.println("4. Listaa kaikki autot");		
		System.out.println("0. Lopeta");
		switch (lukija.lueKokonaisluku("Valitasi: ")) {
		case 1:
			lisaaAuto();
			break;
		case 2:
			muutaAuto();
			break;
		case 3:
			poistaAuto();
			break;
		case 4:
			listaaKaikki();
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

	private void lisaaAuto() {
		Auto auto = new Auto();
		System.out.println("UUDEN AUTON LISÄÄMINEN");
		auto.setRekNo(lukija.lueTeksti("Anna rekisterinumero: "));
		auto.setMerkki(lukija.lueTeksti("Anna merkki: "));
		auto.setMalli(lukija.lueTeksti("Anna malli: "));
		auto.setVuosi(lukija.lueKokonaisluku("Anna valmistusvuosi: "));
		if(dao.lisaaAuto(auto)){
			System.out.println("Auto lisätty.");
		}else{
			System.out.println("Auton lisääminen epäonnistui.");
		}		
	}

	private void muutaAuto() {
		String rekNo;
		Auto auto=null;
		System.out.println("AUTON TIETOJEN MUUTTAMINEN");
		boolean autoLoytyi=false;		
		do{
			rekNo = lukija.lueTeksti("Anna rekisterinumero (-lopeta muutos): ");
			auto=dao.loytyykoAuto(rekNo);
			if(rekNo.equals("-")){
				return;
			}else if(auto!=null){
				autoLoytyi=true;
			}else{
				System.out.println("Rekisterinumeroa ei löytynyt!");
			}
		} while(!autoLoytyi);		
		String rekNoUusi = lukija.lueTeksti("\n"+auto.getRekNo() 
				+ " / Anna uusi rekisterinumero (tyhjä ohittaa): ");
		if(!rekNoUusi.equals("")){
			auto.setRekNo(rekNoUusi);
		}
		String merkki = lukija.lueTeksti(auto.getMerkki() 
				+ " / Anna uusi merkki (tyhjä ohittaa): ");
		if(!merkki.equals("")){
			auto.setMerkki(merkki);
		}
		String malli = lukija.lueTeksti(auto.getMalli() 
				+ " / Anna uusi malli (tyhjä ohittaa): ");
		if(!malli.equals("")){
			auto.setMalli(malli);
		}			
		String vuosi;
		boolean kelvollinen=false;
		do {
			vuosi = lukija.lueTeksti(auto.getVuosi() 
					+ " / Anna uusi valmistusvuosi (tyhjä ohittaa): ");
			if(vuosi.equals("")){
				kelvollinen=true;
			}
			try {
				auto.setVuosi(Integer.parseInt(vuosi));
				kelvollinen=true;
			} catch (Exception e) {
				System.out.println("Antamasi vuosi ei ole luku!");
			}	
		} while (!kelvollinen);
				
		if(dao.muutaAuto(auto, rekNo)){
			System.out.println("Auton tiedot muutettu.");
		}else{
			System.out.println("Auton tietojen muuttaminen epäonnistui.");
		}		
	}

	private void poistaAuto() {
		String rekNo;
		Auto auto=null;
		System.out.println("AUTON TIETOJEN POISTAMINEN");
		boolean autoLoytyi=false;		
		do{
			rekNo = lukija.lueTeksti("Anna rekisterinumero (-lopeta poisto): ");
			auto=dao.loytyykoAuto(rekNo);
			if(rekNo.equals("-")){
				return;
			}else if(auto!=null){
				autoLoytyi=true;
			}else{
				System.out.println("Rekisterinumeroa ei löytynyt!");
			}
		} while(!autoLoytyi);		
		if(dao.poistaAuto(rekNo)){
			System.out.println("Auton tiedot poistettu.");
		}else{
			System.out.println("Auton tietojen poistaminen epäonnistui.");
		}	
	}

	private void listaaKaikki() {
		ArrayList<Auto> autot = dao.listaaKaikki();		
		for(int i=0; i< autot.size(); i++){
			System.out.print(autot.get(i).getRekNo() + "\t");
			System.out.print(autot.get(i).getMerkki() + "\t");
			System.out.print(autot.get(i).getMalli() + "\t");
			System.out.print(autot.get(i).getVuosi() + "\t\n");
		}
	}
	
	public static void main(String[] args) {
		new AutoOhjelmaDB();
	}
}
