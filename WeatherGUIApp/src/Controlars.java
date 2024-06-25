import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;


/**
 * The Controlars class is responsible for controlling the GUI elements and handling user interactions in the WeatherGUIApp.
 * It provides methods for setting the background image based on the time, setting the weather data, and setting the forecast data.
 */
public class Controlars extends Application {

    private String location;
    private String cityTime;
    private String weatherdescription;
    private String temperature;
    private String fehraheitTemp;
    private String humidity;
    private String windSpeed;
    private String[] hoursTemp;
    private String[] hoursHumidity;
    private String[] hoursWindSpeed;



    public void setBackgroundBasedOnTime(String cityTime, VBox root) {
        // Check if cityTime is null
        if (cityTime == null) {
            // Handle the null case
            System.out.println("cityTime is null");
        } else {
            // Parse the hour from cityTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime cityDateTime = LocalDateTime.parse(cityTime, formatter);
            int hour = cityDateTime.getHour();
            

            // Determine the background image based on the hour
            String backgroundImagePath;
            if (hour >= 6 && hour < 12) {
                // Morning image from 6 to 11
                backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/morning.png";
            } else if (hour >= 12 && hour < 18) {
                // Afternoon image from 12 to 17
                backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/afternoon.png";
            } else if (hour >= 18 && hour < 21) {
                // Evening image from 18 to 20
                backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/afternoon.png";
            } else {
                // Night image from 21 to 5
                backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/night.png";
            }

            // Load the background image
            Image backgroundImage = new Image(backgroundImagePath);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            root.setBackground(new Background(background));
        }
    }

    public void setWeatherData(String weather, String temperature, String windSpeed, String humidity,
     Label weatherLabel, Label temperatureValueLabel, Label windSpeedValueLabel, Label humidityValueLabel) {
        
        weatherLabel.setText(weather);
        temperatureValueLabel.setText(temperature + "°C");
        windSpeedValueLabel.setText(windSpeed + " km/h");
        humidityValueLabel.setText(humidity + "%");

        
    }

    public void setForecastData(String[] hoursTemp, String[] hoursHumidity, String[] hoursWindSpeed, Label mondayLabel, Label tuesdayLabel, Label wednesdayLabel, Circle sunIcon, Circle cloudyIcon2, Circle cloudRainIcon, Label netx1TemperatureLabel, Label next2TemperatureLabel, Label next3TemperatureLabel) {
        netx1TemperatureLabel.setText(hoursTemp[0] + "° / " + hoursTemp[1] + "°");
        next2TemperatureLabel.setText(hoursTemp[1] + "° / " + hoursTemp[0] + "°");
        next3TemperatureLabel.setText(hoursTemp[1] + "° / " + hoursTemp[0] + "°");
    }

    @Override
    public void start(Stage primaryStage) {

        //Declare the labels
        Label weatherLabel = new Label("Sunny");
        weatherLabel.setFont(Font.font("Arial", 20));
        Label temperatureLabel = new Label("Temperature");
        temperatureLabel.setFont(Font.font("Arial", 12));
        Label temperatureValueLabel = new Label("");
        temperatureValueLabel.setFont(Font.font("Arial", 20));
        Label windSpeedLabel = new Label("Wind Speed");
        windSpeedLabel.setFont(Font.font("Arial", 12));
        Label windSpeedValueLabel = new Label("");
        windSpeedValueLabel.setFont(Font.font("Arial", 20));
        Label humidityLabel = new Label("Humidity");
        humidityLabel.setFont(Font.font("Arial", 12));
        Label humidityValueLabel = new Label("");
        humidityValueLabel.setFont(Font.font("Arial", 20));

        //Forcast labels
        Label mondayLabel = new Label("Saturday");
        Label tuesdayLabel = new Label("Sunday");
        Label wednesdayLabel = new Label("Monday");
        Circle sunIcon = new Circle(4, Color.YELLOW);
        Circle cloudyIcon2 = new Circle(4, Color.GRAY);
        Circle cloudRainIcon = new Circle(4, Color.BLUE);
        Label netx1TemperatureLabel = new Label("");
        Label next2TemperatureLabel = new Label("");
        Label next3TemperatureLabel = new Label("");

        // Create the main layout
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        setBackgroundBasedOnTime(cityTime, root);
        

        // Create the above top section for displaying the time and city name
        HBox aboveTopSection = new HBox();
        aboveTopSection.setAlignment(Pos.CENTER);
        aboveTopSection.setSpacing(10);

        Label timeLabel = new Label("Time");
        timeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set the font weight and size
        Label timeValueLabel = new Label("12:00 PM");
        timeValueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set the font weight and size

        aboveTopSection.getChildren().addAll(timeLabel, timeValueLabel);

        // Create the city name label
        Label cityNameLabel = new Label("City Name");
        cityNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set the font weight and size

        // Add the above top section and city name label to the main layout
        root.getChildren().addAll(aboveTopSection, cityNameLabel);


        

        

        // Create the top section
        HBox topSection = new HBox();
        topSection.setAlignment(Pos.CENTER);
        topSection.setSpacing(10);

        TextField locationTextField = new TextField();
        ComboBox<String> historyComboBox = new ComboBox<>();
        historyComboBox.setPromptText("History");
        

        topSection.getChildren().addAll( historyComboBox);

        // Create the middle section
        HBox middleSection = new HBox();
        middleSection.setAlignment(Pos.CENTER);
        middleSection.setSpacing(10);

        Circle cloudyIcon = new Circle(6, Color.GRAY);
        TextField locationInput = new TextField();
        locationInput.setPromptText("Enter your city name");
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        searchButton.setOnAction(e -> {
            location = locationInput.getText();
            historyComboBox.getItems().add(location);
            locationInput.clear();
            cityNameLabel.setText(location);
            OpenWeather openWeather = new OpenWeather(location);
            timeValueLabel.setText(openWeather.getCurrentTime());
            cityTime = openWeather.getCurrentTime();
            setBackgroundBasedOnTime(cityTime, root);
            weatherdescription = openWeather.getDescription();
            double temperatureDouble = Double.parseDouble(openWeather.getTemperature()) - 273.15;
            temperature = String.format("%.2f", temperatureDouble);
            double fehraheitDouble = (temperatureDouble * 9/5) + 32;
            fehraheitTemp = String.format("%.2f", fehraheitDouble);
            humidity = openWeather.getHumidity();
            windSpeed = openWeather.getWindSpeed();
            hoursTemp = openWeather.getHoursTemp(); 
            hoursHumidity = openWeather.getHoursHumidity();
            hoursWindSpeed = openWeather.getHoursWindSpeed();
            setWeatherData(weatherdescription, temperature, windSpeed, humidity, weatherLabel, temperatureValueLabel, windSpeedValueLabel, humidityValueLabel);
            setForecastData(hoursTemp, hoursHumidity, hoursWindSpeed, mondayLabel, tuesdayLabel, wednesdayLabel, sunIcon, cloudyIcon2, cloudRainIcon, netx1TemperatureLabel, next2TemperatureLabel, next3TemperatureLabel);

        });
        


        middleSection.getChildren().addAll(cloudyIcon, locationInput, searchButton);

        // Create the bottom section
        GridPane bottomSection = new GridPane();
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setHgap(10);
        bottomSection.setVgap(5);

        

        bottomSection.add(weatherLabel, 0, 0, 2, 1);
        bottomSection.add(temperatureLabel, 0, 1);
        bottomSection.add(temperatureValueLabel, 0, 2);
        bottomSection.add(windSpeedLabel, 1, 1);
        bottomSection.add(windSpeedValueLabel, 1, 2);
        bottomSection.add(humidityLabel, 0, 3);
        bottomSection.add(humidityValueLabel, 0, 4);

        // Create the forecast section
        GridPane forecastSection = new GridPane();
        forecastSection.setAlignment(Pos.CENTER);
        forecastSection.setHgap(10);
        forecastSection.setVgap(5);

        

        forecastSection.add(mondayLabel, 0, 0);
        forecastSection.add(sunIcon, 1, 0);
        forecastSection.add(netx1TemperatureLabel, 2, 0);
        forecastSection.add(tuesdayLabel, 0, 1);
        forecastSection.add(cloudyIcon2, 1, 1);
        forecastSection.add(next2TemperatureLabel, 2, 1);
        forecastSection.add(wednesdayLabel, 0, 2);
        forecastSection.add(cloudRainIcon, 1, 2);
        forecastSection.add(next3TemperatureLabel, 2, 2);

        // Create the temperature section
        HBox temperatureSection = new HBox();
        temperatureSection.setAlignment(Pos.CENTER);
        temperatureSection.setSpacing(10);

        Label temperatureSectionLabel = new Label("Temperature");
        temperatureSectionLabel.setStyle("-fx-font-weight: bold;");
        ToggleGroup temperatureToggleGroup = new ToggleGroup();
        RadioButton celsiusRadioButton = new RadioButton("Celsius");
        celsiusRadioButton.setToggleGroup(temperatureToggleGroup);
        celsiusRadioButton.setSelected(true);
        celsiusRadioButton.setOnAction(e -> {
            if (celsiusRadioButton.isSelected()) {

                temperatureValueLabel.setText(temperature + "°C");

            }
        });
        
        RadioButton fahrenheitRadioButton = new RadioButton("Fahrenheit");
        fahrenheitRadioButton.setToggleGroup(temperatureToggleGroup);
        fahrenheitRadioButton.setOnAction(e -> {
            if (fahrenheitRadioButton.isSelected()) {
                temperatureValueLabel.setText(fehraheitTemp);
            }
});
        
        
        
        

        temperatureSection.getChildren().addAll(temperatureSectionLabel, celsiusRadioButton, fahrenheitRadioButton);

        
        


        // Add all sections to the root layout
        root.getChildren().addAll(topSection, middleSection, bottomSection, forecastSection, temperatureSection);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Weather GUI App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}