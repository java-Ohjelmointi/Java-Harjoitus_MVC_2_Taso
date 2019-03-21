package Auto_Ohjelma_DB_1;

import java.util.Scanner;

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