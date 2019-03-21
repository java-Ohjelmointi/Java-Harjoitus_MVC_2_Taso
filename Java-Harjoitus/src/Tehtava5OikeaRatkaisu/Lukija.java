package Tehtava5OikeaRatkaisu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Lukija {
	
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
	
	public Date luePaivamaara(String ilmo, String format){
		Date paluu = null;
		SimpleDateFormat fdate=new SimpleDateFormat(format);
		String sArvo;
		boolean kelvollinen=false;
		do {
			System.out.print(ilmo);
			sArvo = input.nextLine();
			try {
				paluu = fdate.parse(sArvo);
				kelvollinen=true;
			} catch (ParseException e) {
				System.out.println("Antamasi arvo ei ole päiväys!");
			}			
		} while (!kelvollinen);
		return paluu;		
	}	

}
