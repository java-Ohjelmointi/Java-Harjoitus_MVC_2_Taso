package Tehtava5OikeaRatkaisu;

public class AutoOhjelma {
	
	private Lukija lukija = new Lukija();
	private Dao dao = new Dao();
	
	public AutoOhjelma(){
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
		System.out.println("UUDEN AUTON LISÄÄMINEN");
		String rekNo = lukija.lueTeksti("Anna rekisterinumero: ");
		String merkki = lukija.lueTeksti("Anna merkki: ");
		String malli = lukija.lueTeksti("Anna malli: ");
		int vuosi = lukija.lueKokonaisluku("Anna valmistusvuosi: ");
		if(dao.lisaaAuto(rekNo, merkki, malli, vuosi)){
			System.out.println("Auto lisätty.");
		}else{
			System.out.println("Auton lisääminen epäonnistui.");
		}		
	}

	private void muutaAuto() {
		String rekNo;
		System.out.println("AUTON TIETOJEN MUUTTAMINEN");
		boolean autoLoytyi=false;		
		do{
			rekNo = lukija.lueTeksti("Anna rekisterinumero (-lopeta muutos): ");
			if(rekNo.equals("-")){
				return;
			}else if(dao.loytyykoAuto(rekNo)){
				autoLoytyi=true;
			}else{
				System.out.println("Rekisterinumeroa ei löytynyt!");
			}
		} while(!autoLoytyi);
		String rekNoUusi = lukija.lueTeksti("\nAnna uusi rekisterinumero: ");
		String merkki = lukija.lueTeksti("Anna uusi merkki: ");
		String malli = lukija.lueTeksti("Anna uusi malli: ");
		int vuosi = lukija.lueKokonaisluku("Anna uusi valmistusvuosi: ");
		if(dao.muutaAuto(rekNo, rekNoUusi, merkki, malli, vuosi)){
			System.out.println("Auton tiedot muutettu.");
		}else{
			System.out.println("Auton tietojen muuttaminen epäonnistui.");
		}		
	}

	private void poistaAuto() {
		String rekNo;
		System.out.println("AUTON TIETOJEN POISTAMINEN");
		boolean autoLoytyi=false;		
		do{
			rekNo = lukija.lueTeksti("Anna rekisterinumero (-lopeta poisto): ");
			if(rekNo.equals("-")){
				return;
			}else if(dao.loytyykoAuto(rekNo)){
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
		dao.listaaKaikki();		
	}
	
	public static void main(String[] args) {
		new AutoOhjelma();
	}

}
