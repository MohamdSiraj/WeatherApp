import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The OpenWeather class represents a weather object that retrieves and stores weather data from the OpenWeatherMap API.
 * It provides methods to fetch current weather data and forecast data for a specific city.
 */
public class OpenWeather {

    private static final String API = "ed868950979539e0892994d438f53cc6";
    private String cityName;
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String description;
    private String currentTime;
    private String lat;
    private String lon;
    private String[] hoursTemp = new String[3];
    private String[] hoursHumidity = new String[3];
    private String[] hoursWindSpeed = new String[3];

    /**
     * Constructs an OpenWeather object with the specified city name.
     * It fetches the weather data for the city and populates the instance variables.
     *
     * @param cityName the name of the city
     */
    public OpenWeather(String cityName) {
        this.cityName = cityName;
        fetchData();
        forecast();
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getLatitude() {
        return lat;
    }

    public String getLongitude() {
        return lon;
    }

    public String[] getHoursTemp() {
        return hoursTemp;
    }

    public String[] getHoursHumidity() {
        return hoursHumidity;
    }

    public String[] getHoursWindSpeed() {
        return hoursWindSpeed;
    }
    

    /**
     * Fetches the forecast data for the city from the OpenWeatherMap API.
     * It retrieves the temperature, humidity, and wind speed for the next 3 hours.
     */
    private void forecast() {
        String urlString = "http://api.openweathermap.org/data/2.5/forecast?id=524901"+ "&appid=" + API;
        HttpURLConnection forecastConn = null;
        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            forecastConn = (HttpURLConnection) url.openConnection();
            forecastConn.setRequestMethod("GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader forecastRd = null;
        StringBuilder forecastResult = null;
        try {
            forecastRd = new BufferedReader(new InputStreamReader(forecastConn.getInputStream()));
            forecastResult = new StringBuilder();
            String forecastLine;
            while ((forecastLine = forecastRd.readLine()) != null) {
                forecastResult.append(forecastLine);
            }
            forecastRd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject forecastJson = new JSONObject(forecastResult.toString());
        JSONArray forecastList = forecastJson.getJSONArray("list");

        for (int i = 0; i < 3; i++) {
            JSONObject forecastData = forecastList.getJSONObject(i);
            double forecastTemperature = forecastData.getJSONObject("main").getDouble("temp");
            int forecastHumidity = forecastData.getJSONObject("main").getInt("humidity");
            double forecastWindSpeed = forecastData.getJSONObject("wind").getDouble("speed");

            hoursTemp[i] = String.valueOf(forecastTemperature);
            hoursHumidity[i] = String.valueOf(forecastHumidity);
            hoursWindSpeed[i] = String.valueOf(forecastWindSpeed);
        }
    }

    /**
     * Fetches the current weather data for the city from the OpenWeatherMap API.
     * It retrieves the temperature, humidity, wind speed, description, latitude, longitude, and current time.
     */
    private void fetchData() {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API;

        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JSONObject json = new JSONObject(result.toString());
            double temperature = json.getJSONObject("main").getDouble("temp");
            int humidity = json.getJSONObject("main").getInt("humidity");
            double windSpeed = json.getJSONObject("wind").getDouble("speed");
            String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            int lat = json.getJSONObject("coord").getInt("lat");
            int lon = json.getJSONObject("coord").getInt("lon");

            this.temperature = String.valueOf(temperature);
            this.humidity = String.valueOf(humidity);
            this.windSpeed = String.valueOf(windSpeed);
            this.description = description;
            this.lat = String.valueOf(lat);
            this.lon = String.valueOf(lon);

            int timezone = json.getInt("timezone");
            ZoneId zoneId = ZoneId.of("GMT" + (timezone >= 0 ? "+" : "") + timezone / 3600);
            LocalDateTime currentTime = LocalDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.currentTime = currentTime.format(formatter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches the weather data for a given city from the OpenWeatherMap API.
     * It retrieves the temperature, humidity, wind speed, description, latitude, longitude, and current time.
     *
     * @param cityName the name of the city
     * @return a JSONObject containing the weather data
     */
    public JSONObject fetchWeatherData(String cityName) {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API;
        JSONObject json = null;

        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            json = new JSONObject(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    /**
     * The main method to test the OpenWeather class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        OpenWeather ow = new OpenWeather("Paris");
        System.out.println("Temperature: " + ow.temperature);
        System.out.println("Humidity: " + ow.humidity);
        System.out.println("Wind Speed: " + ow.windSpeed);
        System.out.println("Description: " + ow.description);
        System.out.println("Current Time: " + ow.currentTime);
        System.out.println("Latitude: " + ow.lat);
        System.out.println("Longitude: " + ow.lon);

        for (int i = 0; i < 3; i++) {
            System.out.println("Hour " + (i + 1) + " Temperature: " + ow.hoursTemp[i]);
            System.out.println("Hour " + (i + 1) + " Humidity: " + ow.hoursHumidity[i]);
            System.out.println("Hour " + (i + 1) + " Wind Speed: " + ow.hoursWindSpeed[i]);
        }
    }
}
