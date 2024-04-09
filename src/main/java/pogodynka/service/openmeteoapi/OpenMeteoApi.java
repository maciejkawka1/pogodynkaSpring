package pogodynka.service.openmeteoapi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class OpenMeteoApi {

    private double longitude;
    private double latitude;

    private List<OpenMeteoVariables> variablesList;



    public OpenMeteoApi(double latitude, double longitude, List<OpenMeteoVariables> variablesList){
        this.latitude = latitude;
        this.longitude = longitude;
        this.variablesList = variablesList;
    }

    public String prepareUrl(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("https://api.open-meteo.com/v1/forecast?latitude=")
                .append(this.latitude)
                .append("&longitude=")
                .append(this.longitude)
                .append("&hourly=");

        for (int i = 0; i < variablesList.size(); i++) {
            stringBuilder.append(variablesList.get(i).getUrlCode()).append(",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        stringBuilder.append("&forecast_days=1");

        return stringBuilder.toString();
    }

    public OpenMeteoApiResults getAllData() throws IOException {

        URL url = new URL(this.prepareUrl());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        connection.disconnect();

        Gson gson = new Gson();
        OpenMeteoApiResults openMeteoApiResults = gson.fromJson(response.toString(), OpenMeteoApiResults.class);

        return openMeteoApiResults;

    }

}
