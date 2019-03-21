package HenkilolistaSovellus;

import java.util.Scanner;

public class HenkilolistaSovellus {
    private Scanner input = new Scanner(System.in);
    private Henkilo hlo;
    public HenkilolistaSovellus(){
        naytaValikko();
    }
    
    private void naytaValikko() {
        System.out.println("\n1. Lisää henkilö");
        System.out.println("2. Näytä henkilön tiedot");
        System.out.println("3. Muuta henkilön tietoja");
        System.out.println("0. Lopetus");
        System.out.print("Anna valintasi (0-3):");        
        switch (Integer.parseInt(input.nextLine())) {
        case 1:
            annaHenkilo();
            break;
        case 2:
            naytaTiedot();
            break;
        case 3:
            muutaTiedot();
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

    private void annaHenkilo() {
        hlo = new Henkilo();
        System.out.print("Anna nimi: ");
        hlo.setNimi(input.nextLine());
        System.out.print("Anna osoite: ");
        hlo.setOsoite(input.nextLine());            
    }

    private void naytaTiedot() {
        if(hlo==null){
            System.out.println("Henkilöä ei ole");
            return;
        }
        System.out.println(hlo);        
    }

    private void muutaTiedot() {
        if(hlo==null){
            System.out.println("Henkilöä ei ole");
            return;
        }
        System.out.print("Anna nimi: ");
        hlo.setNimi(input.nextLine());
        System.out.print("Anna osoite: ");
        hlo.setOsoite(input.nextLine());        
    }

    public static void main(String[] args) {
        new HenkilolistaSovellus();
    }
}

class Henkilo {
    private String nimi, osoite;

    public Henkilo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Henkilo(String nimi, String osoite) {
        super();
        this.nimi = nimi;
        this.osoite = osoite;
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

    @Override
    public String toString() {
        return "nimi=" + nimi + ", osoite=" + osoite;
    }    
}