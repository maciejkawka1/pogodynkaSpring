package pogodynka.service.accuweatherapi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccuWeatherApi {

    private final String API_KEY = "qGfrA3wfLNGXsbRoHFOW00ev3CNewyrj";

    private String language = "pl";

    public AccuWeatherApiLocationResponse[] getLocationsByCityName(String city) throws IOException {

        String url2 = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY
                + "&q=" + city + "&language=" + language;


        URL url = new URL(url2);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;

        StringBuffer response = new StringBuffer();

        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();

        connection.disconnect();

        Gson gson = new Gson();
        AccuWeatherApiLocationResponse[] accuWeatherApiLocationResponses = gson.fromJson(response.toString(), AccuWeatherApiLocationResponse[].class);


        return accuWeatherApiLocationResponses;

    }

}
