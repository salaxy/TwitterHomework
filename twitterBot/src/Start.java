import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;




public class Start {
	
	static Timer timer = new Timer();

	/**
	 * @param args
	 */
	public static void main(String[] args){	
		//Twitterbot erstellen
		TwitterBot bot= new TwitterBot();		
		//anmelden des eigenen Accounts
		bot.login();		
		timer.schedule(bot, 0, 5000);	
		menue(bot);
	}
	
	
	/**
	 * Zeigt dsa Menue an
	 */
	private static void showMenue(){
		System.out.println("-----------Drücke 1 zum Folgen eines Users-----------------------");
		System.out.println("-----------Drücke 2 zum erneuten anzeigen des Menues. -----------");
		System.out.println("-----------Drücke 3 zum Schließen des Programms.-----------------");
		System.out.println("-----------Die Auswahl mit Enter bestätigen.---------------------");
	}
	
	
	/**
	 * Einlesen und auswerten der Benutzereingabe
	 * 
	 * @param bot
	 */
	private static void menue(TwitterBot bot){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		showMenue();
		while(true){
			try {
				int auswahl = Integer.parseInt(br.readLine());
				switch(auswahl){
				case 1: System.out.println("Geben Sie den Names des zu Folgenden Users ein(@Username)");
					    bot.followTwitterUser(br.readLine());
					    showMenue();
						break;
				case 2: showMenue();
						break;
				case 3: System.exit(0);
						break;
				default:
					System.out.println("Falsche Eingabe");
				}				
			} catch (NumberFormatException e) { 
			} catch (IOException e) {
			} catch(NullPointerException e){
			}
		}
	}

}

