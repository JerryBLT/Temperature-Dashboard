# Temperature-Dashboard
CS501 (Asign4-Q3) Temperature Dashboard that simulates real-time temperature updates and visualizes them in a Compose UI, including a chart and summary stats. Use coroutines to simulate streaming data and StateFlow for reactive updates

## Features
- Simulates real-time temperature readings every 2 seconds with random float values between 65°F and 85°F.
- Stores and displays the last 20 temperature readings with timestamps.
- Shows current, average, minimum, and maximum temperature statistics.
- Visualizes temperature data with a simple line chart using Jetpack Compose Canvas.
- Allows pausing and resuming of the temperature data simulation.
- Uses Kotlin Coroutines for continuous data simulation and StateFlow for reactive UI updates.
- Built with Jetpack Compose and Material 3 theming.

## Getting Started
### Prerequisites
- Android Studio (latest version recommended)

### How to Run
1. Clone the repository:
2. Open the project in Android Studio.

## Project Structure
- `MainActivity.kt`: Contains main Compose UI and state code.
- `TemperatureViewModel.kt`: Contains the ViewModel responsible for simulating real-time temperature data updates using Kotlin Coroutines. It manages the state of temperature readings with StateFlow, stores the latest 20 readings, tracks whether the simulation is running or paused, and exposes this reactive data to the UI for seamless real-time updates.
- `ui.theme`: Contains app theme and styling.

## Usage
- The app shows the current temperature along with average, minimum, and maximum values calculated from the last 20 readings.
- The temperature readings update every 2 seconds automatically.
- The chart visually represents temperature trends over the stored readings.
- Tap the "Pause" button to stop data updates, and "Resume" to continue.
- The list shows recent temperature readings with timestamps for real-time insights.

## Contributing
Contributions are welcome! Feel free to open issues or submit pull requests.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Author
Jerry Teixeria  
BU Email: jerrybt@bu.edu
