package rs.ac.bg.fon.ai.JSONMenjacnica;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main1 {

	private static final String BASE_URL = "http://api.currencylayer.com";
	private static final String API_KEY = "77f19da688e0fd86539f8fe5342abf3a";
	private static final String SOURCE = "USD";
	private static final String CURRENCIES = "CAD";

	public static void main(String[] args) {
		Gson gson = new Gson();
		
		Transakcija t=new Transakcija();

		
		Date date=java.util.Calendar.getInstance().getTime();  
		

		try (FileWriter file=new FileWriter("prva_transakcija.json")){

			URL url = new URL(
					BASE_URL + "/live?access_key=" + API_KEY + "&source=" + SOURCE + "&currencies=" + CURRENCIES);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			JsonObject res = gson.fromJson(reader, JsonObject.class);

			System.out.println(res);
			if (res.get("success").getAsBoolean()) {

				double kurs = res.get("quotes").getAsJsonObject().get("USDCAD").getAsDouble();
				double ki = 349 * kurs;

				t.setIzvornaValuta("USD");
				t.setKrajnjaValuta("CAD");
				t.setPocetniIznos(349);
				t.setKonvertovaniIznos(ki);
				t.setDatumTransakcije(date);
				
				gson.toJson(t,file);
				

				System.out.println("Konvertovani iznos: " + ki);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
