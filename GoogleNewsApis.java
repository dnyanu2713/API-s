import java.io.IOException;

import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import javax.tools.JavaFileObject;
import org.json.JSONException;
import org.json.JSONObject;
import ch.qos.logback.core.net.SyslogOutputStream;

public class GoogleNewsRestApi {
	public static void main(String[] args) throws IOException, JSONException {
		String apiKey = "**************************************";
		String[] keywords = new String[] { "Dengue", "Malaria", "Covid-19", "Hidoc Dr" };

		Scanner scanner = new Scanner(System.in);
		System.out.println("From date (Date Format: YYYY-MM-DD)");
		String fromDate = scanner.nextLine();

		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			String jsonString = getGoogleNewArticalFromGivenDateByKeyword(apiKey, keyword, fromDate);
			JSONObject jsonObject = new JSONObject(jsonString);
			System.out.println(keyword + " articles: " + jsonObject);
		}

		scanner.close();
	}

	public static String getGoogleNewArticalFromGivenDateByKeyword(String apiKey, String keyword, String fromDate)
			throws IOException {

		HttpURLConnection connection = (HttpURLConnection) new URL("https://newsapi.org/v2/everything?q=" + keyword
				+ "&from=" + fromDate + "&sortBy=publishedAt&apiKey=" + apiKey).openConnection();

		connection.setRequestMethod("GET");

		int responseCode = connection.getResponseCode();

		if (responseCode == 200) {
			String response = "";
			Scanner scanner = new Scanner(connection.getInputStream());

			while (scanner.hasNextLine()) {
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();
			return response;
		}
		return null;
	}
}
