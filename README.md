# Weather GUI Application

This project is a simple weather GUI application developed in Java. It displays different background images based on the current time of the day.

## Features

- Displays different background images for morning, afternoon, evening, and night.
- The background image changes automatically based on the system's current hour.
![image](https://github.com/Moh2001-abd/WeatherApp/assets/128303383/ff9abfe2-bd66-4979-b7b9-006adbe2796d)
![image](https://github.com/Moh2001-abd/WeatherApp/assets/128303383/24e6790d-5464-473e-8491-b29e66bc1330)

## How it works

The application checks the current hour of the system. Depending on the hour, it selects a corresponding image from the `background_images` directory. The selected image is then set as the background of the application.

Here is a snippet of how the application selects the background image:

```java
if (hour >= 6 && hour < 12) {
    // Morning image from 6 to 11
    backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/morning.png";
} else if (hour >= 12 && hour < 18) {
    // Afternoon image from 12 to 17
    backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/afternoon.png";
} else if (hour >= 18 && hour < 21) {
    // Evening image from 18 to 20
    backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/evening.png";
} else {
    // Night image from 21 to 5
    backgroundImagePath = "file:/C:/Users/××Owner/Desktop/UoPeople/JAVA/Projects/WeatherGUIApp/src/background_images/night.png";
}
```

## Requirements

- Java Development Kit (JDK)
- An IDE like Visual Studio Code, IntelliJ IDEA, or Eclipse

## How to run

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Run the [``Controlars.java``](command:_github.copilot.openRelativePath?%5B%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2F%C3%97%C3%97Owner%2FDesktop%2FUoPeople%2FJAVA%2FProjects%2FWeatherGUIApp%2Fsrc%2FControlars.java%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%5D "c:\Users\××Owner\Desktop\UoPeople\JAVA\Projects\WeatherGUIApp\src\Controlars.java") file to start the application.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
