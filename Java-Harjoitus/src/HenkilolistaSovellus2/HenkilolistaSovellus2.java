package HenkilolistaSovellus2;

import java.text.SimpleDateFormat;
import java.util.Scanner;



import java.util.ArrayList;

public class HenkilolistaSovellus2 {
    private Lukija lukija = new Lukija();
    private ArrayList<Henkilo> henkilot = new ArrayList<Henkilo>();
    public HenkilolistaSovellus2(){
        naytaValikko();
    }
    
    private void naytaValikko() {
        System.out.println("1. Lisää henkilö");
        System.out.println("2. Näytä henkilön tiedot");
        System.out.println("3. Muuta henkilön nimi ja osoite");
        System.out.println("4. Muuta henkilön koko");
        System.out.println("5. Näytä kaikki henkilöt");
        System.out.println("0. Lopetus");
        switch (lukija.lueKokonaisluku("Anna valintasi (0-5): ")) {
        case 1:
            lisaaHenkilo();
            break;
        case 2:
            naytaTiedot();
            break;
        case 3:
            muutaNimiOsoite();
            break;
        case 4:
            muutaKoko();
            break;
        case 5:
            naytaKaikki();
            break;
        case 0:
            System.exit(0);
            break;
        default:
            System.out.println("Väärä valinta");
            break;
        }
        naytaValikko();        
    }

    private void lisaaHenkilo() {
        Henkilo hlo = new Henkilo();
        hlo.setNimi(lukija.lueTeksti("Anna nimi: "));
        hlo.setOsoite(lukija.lueTeksti("Anna osoite: "));
        hlo.getKoko().setPituus(lukija.lueDesimaaliluku("Anna pituus: "));
        hlo.getKoko().setPaino(lukija.lueKokonaisluku("Anna paino: "));
        henkilot.add(hlo);        
    }

    private void naytaTiedot() {
        int idx = etsiIndex("Anna näytettävän henkilön nimi: ");
        if(idx==-1){
            System.out.println("Henkilöä ei ole");
        }else{
            System.out.println("Nimi: " + henkilot.get(idx).getNimi());
            System.out.println("Osoite: " + henkilot.get(idx).getOsoite());
            System.out.println("Pituus: " + henkilot.get(idx).getKoko().getPituus());
            
        }        
    }

    private void muutaNimiOsoite() {
        int idx = etsiIndex("Anna perustietoja muutettavan henkilön nimi: ");
        if(idx==-1){
            System.out.println("Henkilöä ei ole");
        }else{
            henkilot.get(idx).setNimi(lukija.lueTeksti("Anna nimi: "));
            henkilot.get(idx).setOsoite(lukija.lueTeksti("Anna osoite: "));            
        }        
    }

    private void muutaKoko() {
        int idx = etsiIndex("Anna perustietoja muutettavan henkilön nimi: ");
        if(idx==-1){
            System.out.println("Henkilöä ei ole");
        }else{
            henkilot.get(idx).getKoko().setPituus(lukija.lueDesimaaliluku("Anna pituus: "));
            henkilot.get(idx).getKoko().setPaino(lukija.lueKokonaisluku("Anna paino: "));
        }            
    }

    private void naytaKaikki() {
        for(int i=0; i<henkilot.size();i++){
            System.out.println("Nimi: " + henkilot.get(i).getNimi());
            System.out.println("Osoite: " + henkilot.get(i).getOsoite());
            System.out.println("Pituus: " + henkilot.get(i).getKoko().getPituus());
           
        }        
    }
    
    private int etsiIndex(String ilmo){
        int paluu=-1;
        String nimi = lukija.lueTeksti(ilmo);        
        for(int i=0; i<henkilot.size();i++){
            if(henkilot.get(i).getNimi().equalsIgnoreCase(nimi)){
                paluu=i;
                break;
            }
        }            
        return paluu;
    }

    public static void main(String[] args) {
        new HenkilolistaSovellus2();
    }

}


class Koko {

    private double pituus;
    private int paino;
    
    public Koko() {
        super();
       
    }

    public Koko(double pituus, int paino) {
        super();
        this.pituus = pituus;
        this.paino = paino;
    }

    public double getPituus() {
        return pituus;
    }

    public void setPituus(double pituus) {
        this.pituus = pituus;
    }

    public int getPaino() {
        return paino;
    }

    public void setPaino(int paino) {
        this.paino = paino;
    }

    @Override
    public String toString() {
        return "Koko [pituus=" + pituus + ", paino=" + paino + "]";
    }
    
    
}


 class Henkilo {
    private String nimi, osoite;
    private Koko koko = new Koko();

    public Henkilo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Henkilo(String nimi, String osoite) {
        super();
        this.nimi = nimi;
        this.osoite = osoite;
    }

   

    public Henkilo(String nimi, String osoite, Koko koko) {
        super();
        this.nimi = nimi;
        this.osoite = osoite;        
        this.koko = koko;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getOsoite() {
        return osoite;
    }

    public void setOsoite(String osoite) {
        this.osoite = osoite;
    }

 

    public Koko getKoko() {
        return koko;
    }

    public void setKoko(Koko koko) {
        this.koko = koko;
    }

    @Override
    public String toString() {
        return "Henkilo [nimi=" + nimi + ", osoite=" + osoite +  ", koko=" + koko + "]";
    }
    
}

class Lukija {
    private Scanner input = new Scanner(System.in);
    
    public int lueKokonaisluku(String ilmo){
        String sArvo;
        int paluuArvo=0;
        boolean kelvollinen=false;
        do{
            System.out.print(ilmo);
            sArvo=input.nextLine();
            try {
                paluuArvo = Integer.parseInt(sArvo);
                kelvollinen=true;
            } catch (Exception e) {
                System.out.println("Antamasi arvo ei ole kokonaisluku!");
            }            
        }while(kelvollinen==false);
        return paluuArvo;
    }
    
    public double lueDesimaaliluku(String ilmo){
        String sArvo;
        double paluuArvo=0;
        boolean kelvollinen=false;
        do{
            System.out.print(ilmo);
            sArvo=input.nextLine();
            sArvo=sArvo.replace(",", ".");
            try {
                paluuArvo = Double.parseDouble(sArvo);
                kelvollinen=true;
            } catch (Exception e) {
                System.out.println("Antamasi arvo ei ole desimaaliluku!");
            }            
        }while(kelvollinen==false);
        return paluuArvo;
    }
    
    public String lueTeksti(String ilmo){
        System.out.print(ilmo);
        return input.nextLine();
    }
    
  
}

