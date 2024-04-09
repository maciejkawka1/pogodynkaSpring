package pogodynka.repository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pogodynka.MySQLConnectionConfig;
import pogodynka.dao.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class MySQLConnection {

    @Autowired
    @Qualifier("mySQLConnectionConfig")
    private MySQLConnectionConfig config;

    private static Connection connection;

    // Metoda do nawiązania połączenia z bazą danych
    public void connect() {
        try {
            // Ładowanie sterownika JDBC dla MySQL
            Class.forName(config.getDRIVER());

            // Nawiązanie połączenia
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUSER(),
                    config.getPASSWORD());

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
                statement.execute(sql);
                System.out.println("Dodano rekord do bazy danych");
            }
            statement.close();
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


}