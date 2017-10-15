package rest;


import java.net.InetAddress;
import java.net.UnknownHostException;
import configuration.*;
import spark.Spark;

public class REST implements Runnable {

	public REST() {
	}

	public void run() {
		Spark.port(Konfiguration.RESTPORT);
		//Spark.secure(Konfiguration.KEYSTOREPATH, Konfiguration.KEYSTOREPASSWORD, null, null);
		try {
			System.out.println("Server: Rest-Service gestartet unter Port " + InetAddress.getLocalHost().toString() + Konfiguration.RESTPORT);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Server: Rest-Service konnte nicht gestartet werden. Eventuell ist der schon Port belegt");
		}

		//User REST Schnittstellen:
		RESTUser.interfaces();

	}

}
