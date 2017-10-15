package model;

public class Freigabe {

	private int freigabeId;
	private int userId;
	private int beuteId;
	//rechtenummer 1=darf lesen und Stockkarten hinzufügen, 2=darf Beute Updaten und Stockkarten hinzufügen 3=darf Löschen
	private int rechtenummer;
	
	
	public Freigabe() {
		super();
	}
	public Freigabe(int userId, int beutenId, int rechtenummer) {
		super();
		this.userId = userId;
		this.beuteId = beutenId;
		this.rechtenummer = rechtenummer;
	}
	public Freigabe(int freigabeId, int userId, int beutenId, int rechtenummer) {
		super();
		this.freigabeId = freigabeId;
		this.userId = userId;
		this.beuteId = beutenId;
		this.rechtenummer = rechtenummer;
	}
	public int getFreigabeId() {
		return freigabeId;
	}
	public void setFreigabeId(int freigabeId) {
		this.freigabeId = freigabeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBeuteId() {
		return beuteId;
	}
	public void setBeuteId(int beuteId) {
		this.beuteId = beuteId;
	}
	public int getRechtenummer() {
		return rechtenummer;
	}
	public void setRechtenummer(int rechtenummer) {
		this.rechtenummer = rechtenummer;
	}

	
	
}
