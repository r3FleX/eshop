package shop.server.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import bib.common.interfaces.BibliotheksInterface;
import bib.common.valueobjects.Buch;

/**
 * Klasse zur Verarbeitung der Kommunikation zwischen EINEM Client und dem
 * Server. Die Kommunikation folgt dabei dem "Protokoll" der Anwendung. Das
 * ClientRequestProcessor-Objekt f�hrt folgende Schritte aus: 
 * 0. Begr��ungszeile an den Client senden
 * Danach in einer Sschleife:
 * 1. Empfang einer Zeile vom Client (z.B. Aktionsauswahl, hier eingeschr�nkt); 
 *    wenn der Client die Abbruchaktion sendet ('q'), wird die Schleife verlassen
 * 2. abh�ngig von ausgew�hlter Aktion Empfang weiterer Zeilen (Parameter f�r ausgew�hlte Aktion)
 * 3. Senden der Antwort an den Client; die Antwort besteht je nach Aktion aus einer oder mehr Zeilen
 * 
 * @author teschke, eirund
 */
class ClientRequestProcessor implements Runnable {

	// Bibliotheksverwaltungsobjekt, das die eigentliche Arbeit machen soll
	private BibliotheksInterface bibV; 

	// Datenstrukturen f�r die Kommunikation
	private Socket clientSocket;
	private BufferedReader in;
	private PrintStream out;

	
	/**
	 * @param socket
	 * @param bibVerw
	 */
	public ClientRequestProcessor(Socket socket, BibliotheksInterface bibVerw) {

		bibV = bibVerw;
		clientSocket = socket;

		// I/O-Streams initialisieren und ClientRequestProcessor-Objekt als Thread starten:
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			try {
				clientSocket.close();
			} catch (IOException e2) {
			}
			System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
			return;
		}

		System.out.println("Verbunden mit " + clientSocket.getInetAddress()
				+ ":" + clientSocket.getPort());
	}

	/**
	 * Methode zur Abwicklung der Kommunikation mit dem Client gem�� dem
	 * vorgebenen Kommunikationsprotokoll.
	 */
	public void run() {

		String input = "";

		// Begr��ungsnachricht an den Client senden
		out.println("Server an Client: Bin bereit f�r Deine Anfragen!");

		// Hauptschleife zur wiederholten Abwicklung der Kommunikation
		do {
			// Beginn der Benutzerinteraktion:
			// Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
			try {
				input = in.readLine();
			} catch (Exception e) {
				System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
				System.out.println(e.getMessage());
				continue;
			}

			// Eingabe bearbeiten:
			if (input == null) {
				// input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
				// Einfach behandeln wie ein "quit"
				input = "q";
			}
			else if (input.equals("a")) {
				// Aktion "B�cher _a_usgeben" gew�hlt
				ausgeben();
			} else if (input.equals("e")) {
				// Aktion "Buch _e_inf�gen" gew�hlt
				einfuegen();
			} else if (input.equals("f")) {
				// Aktion "B�cher _f_inden" (suchen) gew�hlt
				suchen();
			}
			else if (input.equals("s")) {
				// Aktion "_s_peichern" gew�hlt
				speichern();
			}
			// ---
			// weitere Server-Dienste ...
			// ---

		} while (!(input.equals("q")));

		// Verbindung wurde vom Client abgebrochen:
		disconnect();		
	}

	private void speichern() {
		// Parameter sind in diesem Fall nicht einzulesen
		
		// die Arbeit macht wie immer Bibliotheksverwaltungsobjekt:
		try {
			bibV.schreibeBuecher();
			out.println("Erfolg");
		} catch (Exception e) {
			System.out.println("--->Fehler beim Sichern: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		}
	}

	private void suchen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// hier ist nur der Titel der gesuchten B�cher erforderlich:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out
					.println("--->Fehler beim Lesen vom Client (Titel): ");
			System.out.println(e.getMessage());
		}
		// Achtung: Objekte sind Referenzdatentypen:
		// Buch-Titel in neues String-Objekt kopieren, 
		// damit Titel nicht bei n�chster Eingabe in input �berschrieben wird
		String titel = new String(input);

		// die eigentliche Arbeit soll das Bibliotheksverwaltungsobjekt machen:
		List<Buch> buecher = null;
		if (titel.equals(""))
			buecher = bibV.gibAlleBuecher();
		else
			buecher = bibV.sucheNachTitel(titel);

		sendeBuecherAnClient(buecher);
	}

	private void ausgeben() {
		// Die Arbeit soll wieder das Bibliotheksverwaltungsobjekt machen:
		List<Buch> buecher = null;
		buecher = bibV.gibAlleBuecher();

		sendeBuecherAnClient(buecher);
	}

	private void sendeBuecherAnClient(List<Buch> buecher) {
		Iterator<Buch> iter = buecher.iterator();
		Buch buch = null;
		// Anzahl der gefundenen B�cher senden
		out.println(buecher.size());
		while (iter.hasNext()) {
			buch = iter.next();
			// Nummer des Buchs senden
			out.println(buch.getNummer());
			// Titel des Buchs senden
			out.println(buch.getTitel());
			// Verf�gbarkeit des Buchs senden
			out.println(buch.isVerfuegbar());
		}
	}

	private void einfuegen() {
		String input = null;
		// lese die notwendigen Parameter, einzeln pro Zeile
		// zuerst die Nummer des einzuf�genden Buchs:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out
					.println("--->Fehler beim Lesen vom Client (BuchNr): ");
			System.out.println(e.getMessage());
		}
		int buchNr = Integer.parseInt(input);

		// dann den Titel:
		try {
			input = in.readLine();
		} catch (Exception e) {
			System.out
					.println("--->Fehler beim Lesen vom Client (Titel): ");
			System.out.println(e.getMessage());
		}
		// Achtung: Objekte sind Referenzdatentypen:
		// Buch-Titel in neues String-Objekt kopieren, 
		// damit Titel nicht bei n�chste Eingabe in input �berschrieben wird
		String titel = new String(input);

		// die eigentliche Arbeit soll das Bibliotheksverwaltungsobjekt machen:
		boolean ok = bibV.fuegeBuchEin(titel, buchNr);

		// R�ckmeldung an den Client: war die Aktion erfolgreich?
		if (ok)
			out.println("Erfolg");
		else
			out.println("Fehler");
	}
	
	private void disconnect() {
		try {
			out.println("Tschuess!");
			clientSocket.close();

			System.out.println("Verbindung zu " + clientSocket.getInetAddress()
					+ ":" + clientSocket.getPort() + " durch Client abgebrochen");
		} catch (Exception e) {
			System.out.println("--->Fehler beim Beenden der Verbindung: ");
			System.out.println(e.getMessage());
			out.println("Fehler");
		}
	}
}
