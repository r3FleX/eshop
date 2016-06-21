package shop.server.net;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import bib.common.interfaces.BibliotheksInterface;
import bib.server.domain.Bibliothek;

/**
 * Serverseitige Anwendung, die Verbindungsanfragen von Client-Prozessen 
 * entgegennimmt.
 * Falls sich ein Client �ber einen Socket verbindet, wird ein "ClientRequestProcessor"-Objekt 
 * als eigener Prozess (Thread) gestartet, der dann (in seiner run()-Methode) die
 * weitere Kommunikation mit dem Client �ber das mitgegebene Socket-Objekt 
 * �bernimmt. 
 * Danach wartet der Server weiter auf Verbindungen und wiederholt obigen
 * Prozess.
 * 
 * @author eirund, teschke
 */
public class MiniBibServer { 					
	
	public final static int DEFAULT_PORT = 6789;

	protected int port;
	protected ServerSocket serverSocket;
	private BibliotheksInterface bibV; 

	
	/**
	 * Konstruktor zur Erzeugung des Bibliotheksservers.
	 * 
	 * @param port Portnummer, auf der auf Verbindungen gewartet werden soll
	 * (wenn 0, wird Default-Port verwendet)
	 */
	public MiniBibServer(int port) throws IOException {
		
		bibV = new Bibliothek("BIB"); 
		
		if (port == 0)
			port = DEFAULT_PORT;
		this.port = port;
		
		try {
			// Server-Socket anlegen
			serverSocket = new ServerSocket(port);
			
			// Serverdaten ausgeben
			InetAddress ia = InetAddress.getLocalHost();
//			Diese Anweisung liefert zu meiner �berraschung nicht das Gew�nschte:
//			InetAddress ia = serverSocket.getInetAddress();
			System.out.println("Host: " + ia.getHostName());
			System.out.println("Server *" + ia.getHostAddress()	+ "* lauscht auf Port " + port);
		} catch (IOException e) {
			fail(e, "Eine Ausnahme trat beim Anlegen des Server-Sockets auf");
		}
	}

	/**
	 * Methode zur Entgegennahme von Verbindungsw�nschen durch Clients.
	 * Die Methode fragt wiederholt ab, ob Verbindungsanfragen vorliegen
	 * und erzeugt dann jeweils ein ClientRequestProcessor-Objekt mit dem 
	 * fuer diese Verbindung erzeugten Client-Socket.
	 */
	public void acceptClientConnectRequests() {

		try {
			while (true) {
				Socket clientSocket = serverSocket.accept();
				ClientRequestProcessor c = new ClientRequestProcessor(clientSocket, bibV);
				Thread t = new Thread(c);
				t.start();
			}
		} catch (IOException e) {
			fail(e, "Fehler w�hrend des Lauschens auf Verbindungen");
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
		try {
			MiniBibServer server = new MiniBibServer(port);
			server.acceptClientConnectRequests();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e, " - MiniBibServer-Erzeugung");
		}
	}
	
	// Standard-Exit im Fehlerfall:
	private static void fail(Exception e, String msg) {
		System.err.println(msg + ": " + e);
		System.exit(1);
	}
}


