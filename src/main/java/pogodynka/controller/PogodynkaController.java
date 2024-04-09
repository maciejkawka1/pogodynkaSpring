package pogodynka.controller;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import pogodynka.service.accuweatherapi.AccuWeatherApi;
import pogodynka.service.accuweatherapi.AccuWeatherApiLocationResponse;
import pogodynka.service.openmeteoapi.OpenMeteoApi;
import pogodynka.service.openmeteoapi.OpenMeteoApiResults;
import pogodynka.service.openmeteoapi.OpenMeteoVariables;
import pogodynka.dao.ChartData;
import pogodynka.dao.Location;
import pogodynka.repository.CreationException;
import pogodynka.repository.MySQLConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class PogodynkaController {






    @GetMapping("/")
    public String getIndex(Model model) throws SQLException {

        MySQLConnection connection = new MySQLConnection();
        connection.connect();

        List<Location> locations = connection.getAllLocations();

        connection.disconnect();

        model.addAttribute("locations", locations);
        return "index.html";

    }


    @GetMapping("/search")
    public String search(@RequestParam("city") String city, Model model) throws IOException {

        List<OpenMeteoVariables> listParams = new ArrayList<>();
        listParams.add(OpenMeteoVariables.TEMPERATURE);
        listParams.add(OpenMeteoVariables.RELATIVE_HUMIDITY);
        listParams.add(OpenMeteoVariables.SEA_LEVEL_PRESSURE);
        listParams.add(OpenMeteoVariables.WIND_SPEED);
        listParams.add(OpenMeteoVariables.WIND_DIRECTION);


        AccuWeatherApiLocationResponse[] locations = new AccuWeatherApi().getLocationsByCityName(city);

        List<ChartData> data = new ArrayList<>();

        if (locations.length >= 1) {
            OpenMeteoApi meteo = new OpenMeteoApi(locations[0].getGeoPosition().getLatitude(), locations[0].getGeoPosition().getLongitude(), listParams);
            OpenMeteoApiResults result = meteo.getAllData();
            if (result != null) {

                data.add(new ChartData(OpenMeteoVariables.TEMPERATURE.getUserInfo(),
                        OpenMeteoVariables.TEMPERATURE.getUnit(),
                        result.getHourly().getTime(),
                        result.getHourly().getTemperature_2m()));

                data.add(new ChartData(OpenMeteoVariables.SEA_LEVEL_PRESSURE.getUserInfo(),
                        OpenMeteoVariables.SEA_LEVEL_PRESSURE.getUnit(),
                        result.getHourly().getTime(),
                        result.getHourly().getPressure_msl()));

                data.add(new ChartData(OpenMeteoVariables.WIND_SPEED.getUserInfo(),
                        OpenMeteoVariables.WIND_SPEED.getUnit(),
                        result.getHourly().getTime(),
                        result.getHourly().getWind_speed_10m()));

                data.add(new ChartData(OpenMeteoVariables.WIND_DIRECTION.getUserInfo(),
                        OpenMeteoVariables.WIND_DIRECTION.getUnit(),
                        result.getHourly().getTime(),
                        result.getHourly().getWind_direction_10m()));

                data.add(new ChartData(OpenMeteoVariables.RELATIVE_HUMIDITY.getUserInfo(),
                        OpenMeteoVariables.RELATIVE_HUMIDITY.getUnit(),
                        result.getHourly().getTime(),
                        result.getHourly().getRelative_humidity_2m()));


                model.addAttribute("charts", data);
                model.addAttribute("city", locations[0].getLocalizedName());
                model.addAttribute("country", locations[0].getCountry().getLocalizedName());
                model.addAttribute("region", locations[0].getRegion().getLocalizedName());
                model.addAttribute("longtitude", locations[0].getGeoPosition().getLongitude());
                model.addAttribute("latitude", locations[0].getGeoPosition().getLatitude());


            }
        } else
            throw new IOException("Ups");


        return "searchResults"; // np. nazwa widoku dla wyników wyszukiwania
    }

    @PostMapping("/save")
    public RedirectView saveToDatabase(@RequestParam("city") String city,
                                       @RequestParam("country") String country,
                                       @RequestParam("region") String region,
                                       @RequestParam("longtitude") Double longtitude,
                                       @RequestParam("latitude") Double latitude)
            throws CreationException {
        MySQLConnection connection = new MySQLConnection();
        connection.connect();

        Location location = new Location(null, longtitude, latitude, city, region, country);

        connection.addToDatabase(location);


        connection.disconnect();

        return new RedirectView("/");
    }


}
