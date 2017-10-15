package model;

import java.io.Serializable;
public class User implements Serializable {


	private static final long serialVersionUID = 1L;

	private int userId;
	//login ist gleich email adresse
	private String login;
	private String vorname;
	private String nachname;
	
	private String strasse;
	private String plz;
	private String ort;
	
	private String telefonnummer;
	
	private String password;
	private String salt;
	private boolean aktiv;
	//Um Update-Überblick zu behalten
	private long version;
	
	private int anmeldeversuche;
	
	public User() {
		super();
	}
	//Fuer die erste Anlegung -> ohne ID (wird von DB generiert)
	public User(String login, String vorname, String nachname, String strasse, String plz, String ort,
			String telefonnummer, String password, String salt) {
		super();
		this.userId = userId;
		this.login = login;
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.telefonnummer = telefonnummer;
		this.password = password;
		this.salt = salt;
		this.aktiv = false;
		this.version = 0;
		this.anmeldeversuche = 0;
	}
	//Fuer restliche Erstellung
	public User(int userId, String login, String vorname, String nachname, String strasse, String plz, String ort,
			String telefonnummer, String password, String salt, boolean aktiv, long version, int anmeldversuche) {
		super();
		this.userId = userId;
		this.login = login;
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.telefonnummer = telefonnummer;
		this.password = password;
		this.salt = salt;
		this.aktiv = aktiv;
		this.version = version;
		this.anmeldeversuche = anmeldversuche;
	}
	public int getAnmeldeversuche() {
		return this.anmeldeversuche;
	}
	public void inkrementAnmeldeversuche() {
		this.anmeldeversuche++;
	}
	public void setAnmeldeversuche(int i) {
		this.anmeldeversuche = i;
	}
	public long getVersion() {
		return this.version;
	}
	public void setVersion(long v) {
		this.version = v;
	}
	
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
