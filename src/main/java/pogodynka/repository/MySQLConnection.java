package pogodynka.repository;



import org.springframework.stereotype.Service;
import pogodynka.dao.Location;
import pogodynka.dao.TrackedLocations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MySQLConnection {

    private static final String URL = "jdbc:mysql://109.241.162.43:33306/sda_pogodynka";
    private static final String USER = "sda";
    private static final String PASSWORD = "sda2024MySQL";

    private static Connection connection;

    // Metoda do nawiązania połączenia z bazą danych
    public void connect() {
        try {
            // Ładowanie sterownika JDBC dla MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Nawiązanie połączenia
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Połączono z bazą danych MySQL.");
        } catch (ClassNotFoundException e) {
            System.out.println("Nie można znaleźć sterownika JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Błąd podczas nawiązywania połączenia z bazą danych.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Metoda do zamykania połączenia z bazą danych
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Zamknięto połączenie z bazą danych MySQL.");
            } catch (SQLException e) {
                System.out.println("Błąd podczas zamykania połączenia z bazą danych.");
                e.printStackTrace();
            }
        }
    }

    //executeUpdate
    public List<Location> getAllLocations() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM Locations");
            List<Location> locations = new ArrayList<>();
            while (resultSet.next()) {

                locations.add(new Location(
                        UUID.fromString(resultSet.getString("id")),
                        resultSet.getDouble("longtitude"),
                        resultSet.getDouble("latitude"),
                        resultSet.getString("city"),
                        resultSet.getString("region"),
                        resultSet.getString("country")
                ));
            }
            return locations;
        } catch (CreationException e) {
            throw new RuntimeException(e);
        }
    }

    //create table
    public void createTableLocations() {
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Locations(id INT, " +
                    "longtitude DOUBLE, " +
                    "latitude DOUBLE, " +
                    "city VARCHAR(50), " +
                    "region VARCHAR(50), " +
                    "country VARCHAR(50));";
            statement.execute(sql);
            System.out.println("Utworzono tabelę Locations");
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToDatabase(List<Location> list) {
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i < list.size(); i++)
            {
                String sql = "INSERT INTO Locations" +
                        "(id,longtitude,latitude,city,region,country)" +
                        " VALUES('" +
                        list.get(i).getId() + "','"
                        + list.get(i).getLongtitude() + "',"
                        + list.get(i).getLatitude() + ",'"
                        + list.get(i).getCity() + "','"
                        + list.get(i).getRegion() + "','"
                        + list.get(i).getCountry() + "')";
                System.out.println(sql);
                statement.execute(sql);
                System.out.println("Dodano rekord do bazy danych");
            }
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToDatabase(Location location) {
        try {
            Statement statement = connection.createStatement();

                String sql = "INSERT INTO Locations" +
                        "(id,longtitude,latitude,city,region,country)" +
                        " VALUES('" +
                        location.getId() + "','"
                        + location.getLongtitude() + "',"
                        + location.getLatitude() + ",'"
                        + location.getCity() + "','"
                        + location.getRegion() + "','"
                        + location.getCountry() + "')";
                System.out.println(sql);
                statement.execute(sql);
                System.out.println("Dodano rekord do bazy danych");

            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRowByCityName(String city){

        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM Locations WHERE city = '"+city+"';";
            statement.execute(sql);
            System.out.println("Usunięto miasto: " + city);
            statement.close();
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void main(String... args) throws SQLException, CreationException {

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.connect();
        //mySQLConnection.createTableLocations();
        TrackedLocations trackedLocations = new TrackedLocations();

        Location location = new Location(null,56,44,"miasto", "region","22222222");

        trackedLocations.addLocation(location);
        List<Location> locationList = trackedLocations.getLocations();

        mySQLConnection.addToDatabase(locationList);


    }
}