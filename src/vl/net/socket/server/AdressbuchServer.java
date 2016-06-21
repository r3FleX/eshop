package vl.net.socket.server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import vl.net.socket.Adresse;

/**
 * Serverseitige Anwendung, die Verbindungsanfragen von Client-Prozessen 
 * entgegennimmt.
 * Falls sich ein Client über einen Socket verbindet, wird ein "ClientRequestProcessor"-
 * Objekt als eigener Prozess (Thread) gestartet, der dann (in seiner run()-Methode) 
 * die weitere Kommunikation mit dem Client über das mitgegebene Socket-Objekt 
 * übernimmt. 
 * Danach wartet der Server weiter auf Verbindungen und wiederholt obigen
 * Prozess.
 * 
 * @author teschke
 */
public class AdressbuchServer { 					
	
	public final static int DEFAULT_PORT = 6789;

	private int port;
	private ServerSocket serverSocket;

	// Datenstruktur für das Adressbuch
	private Hashtable<String, Adresse> adressen;

	
	/**
	 * Konstruktor zur Erzeugung des Adressbuch-Servers.
	 * 
	 * @param optPort Portnummer, auf der auf Verbindungen gewartet werden soll
	 * (wenn 0, wird Default-Port verwendet)
	 */
	public AdressbuchServer(int optPort) {

		this.port = (optPort == 0) ? DEFAULT_PORT : optPort;
		
		try {
			// Server-Socket anlegen
			serverSocket = new ServerSocket(port);
			
			// Serverdaten ausgeben
			InetAddress ia = InetAddress.getLocalHost();
			System.out.println("Host: " + ia.getHostName());
			System.out.println("Server *" + ia.getHostAddress()	+ "* lauscht auf Port " + port);
		} catch (IOException e) {
			System.err.println("Eine Ausnahme trat beim Anlegen des Server-Sockets auf: " + e);
			System.exit(1);
		}

		// Interne Adress(test)daten erzeugen
		adressen = new Hashtable<String, Adresse>();
		adressen.put("Meier", new Adresse("Flughafenallee 10", 28199, "Bremen"));
		adressen.put("Schmidt", new Adresse("Hauptstraße 28", 28357, "Bremen"));
		adressen.put("Hinz", new Adresse("Elbchaussee 101", 20123, "Hamburg"));
		adressen.put("Kunz", new Adresse("Weinsteige 12", 70711, "Stuttgart"));
	}

	/**
	 * Methode zur Entgegennahme von Verbindungswünschen durch Clients.
	 * Die Methode fragt wiederholt ab, ob Verbindungsanfragen vorliegen
	 * und erzeugt dann jeweils ein ClientRequestProcessor-Objekt mit dem 
	 * fuer diese Verbindung erzeugten Client-Socket.
	 */
	public void acceptClientConnectRequests() {

		try {
			while (true) {
				// Auf Verbindungswünsche warten...
				Socket clientSocket = serverSocket.accept();
				// ... und dann Verarbeitung von Dienstanfragen starten:
				ClientAdressRequestProcessor c 
					= new ClientAdressRequestProcessor(clientSocket, adressen);
//				Thread t = new Thread(c);
//				t.start();
				c.verarbeiteAnfragen();
			}
		} catch (IOException e) {
			System.err.println("Fehler während des Wartens auf Verbindungen: " + e);
			System.exit(1);
		}
	}


	/**
	 * main()-Methode zum Starten des Servers
	 * 
	 * @param args kann optional Portnummer enthalten, auf der Verbindungen entgegengenommen werden sollen
	 */
	public static void main(String[] args) {
		int port = 0;
		if (args.length == 1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				port = 0;
			}
		}
		AdressbuchServer server = new AdressbuchServer(port);
		// Ab jetzt auf eingehende Verbindungswünsche von Clients warten
		server.acceptClientConnectRequests();
	}
}



//// in gesondertem Thread starten:
//ClientAdressRequestProcessor c 
//	= new ClientAdressRequestProcessor(clientSocket, adressen);
//Thread t = new Thread(c);
//t.start();

