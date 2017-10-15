package rest;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.List;

import org.apache.log4j.Logger;

import configuration.Konfiguration;
import model.User;
import model.impl.UserImpl;
import logik.*;

public class RESTUser {

	/*
	 * Rest-Interface
	 * 
	 * GET /user -> liefert Liste mit allen Usern
	 * 
	 * POST /user -> erstellt neuen User und gibt ID zurück (von Datenbank
	 * generiert) GET /user
	 * 
	 * PUT /user -> updated User
	 * 
	 * Del /user -> löscht üser -> nur deaktivieren für Konsistenz der DB
	 * 
	 */

	// Entfernt alle PWs und Salts der Anderen User
	private static List<User> deletePasswordsFromList(String login) {
		List<User> temp = StartServer.getUsers();
		for (User elem : temp) {
			if (elem.getLogin() != login) {
				elem.setPassword("");
				elem.setSalt("");
			}
		}

		return temp;
	}

	public static void interfaces() {
		Gson gson = new Gson();
		// For Admin
		get("/xxx", (request, response) -> {
			// String param1 = request.params(":param");
			response.status(200);
			System.out.println("Anfrage XXX versendet!!!");
			String json = gson.toJson(StartServer.getUsers());
			return json;

		});
		// gibt alle User-Daten zurÃ¼ck -ohne Passwort und Salts. Nur um einen anderen
		// User zu Suchen und ggf. einen Stock an ihn freizugeben
		get("/user", (request, response) -> {
			// String param1 = request.params(":param");
			try {
				int userId = Integer.valueOf(request.headers("userId"));
				String login = request.headers("login");
				String userHashedPassword = request.headers("password");
				// Passwort Überprüfung!!
				if (StartServer.checkLogin(login, userHashedPassword)) {
					response.status(200);
					List<User> returnUsers = deletePasswordsFromList(login);
					String json = gson.toJson(returnUsers);
					return json;
				}
			} catch (Exception e) {
				StartServer.logger.error("Daten wurden im falschen Format uebergeben!");
			}
			response.status(400);
			return "Fehler bei der Anmeldung!";
		});

		// erstellt ein neuen User und gibt seine neu erzeugte ID zurück
		post("/user", (request, response) -> {
			try {
				JsonParser parser = new JsonParser();
				JsonElement mJson = parser.parse(request.body());
				User newUser = gson.fromJson(mJson, User.class);
				response.status(200);
				String json = gson.toJson(StartServer.addUser(newUser));
				return json;

			} catch (Exception e) {
				StartServer.logger.error("Es kam zu einem Error beim erstellen eines neuen Users. Data=" + request.body());
			}
			return "";
		});

		// Änderung an einem User objekt inklusive aller seiner unterobjekte
		put("/user", (request, response) -> {

			try {
				String login = request.headers("login");
				String userHashedPassword = request.headers("password");

				// Passwort Überprüfung!!
				if (StartServer.checkLogin(login, userHashedPassword)) {

					JsonParser parser = new JsonParser();
					JsonElement mJson = parser.parse(request.body());
					String json = "";
					User updatedUser = gson.fromJson(mJson, User.class);
					response.status(200);
					StartServer.updateUser(updatedUser);
					response.status(200);
					return true;

				}
			} catch (Exception e) {
				StartServer.logger.error("Es kam zu einem Error beim updating eines Users. Data=" + request.body());
			}
			response.status(400);
			return false;
		});
		// Löschung eines Users mit all seinen unterobjekten (zur Konsistenz wird er nur
		// deaktiviert)
		delete("/user", (request, response) -> {
			try {
				String login = request.headers("login");
				String userHashedPassword = request.headers("password");

				// Passwort Überprüfung!!
				if (StartServer.checkLogin(login, userHashedPassword)) {

					User u = StartServer.getUserByLogin(login);
					if (u != null) {
						response.status(200);
						u.setAktiv(false);
						StartServer.updateUser(u);
						return true;
					}
				}
			} catch (Exception e) {
				StartServer.logger.error("Es kam zu einem Error beim deaktivieren/loeschen eines Users. Data=" + request.headers().toString());
			}
			response.status(400);
			return false;
		});

	}

}
