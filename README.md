# Interactive Standard Test

A Kotlin-based Android application designed to fetch and display a set of points from an API, render them as a chart, and save the chart to a file. The project follows **Clean Architecture**, **SOLID principles**, and uses **MVP** for presentation layer management.

---

## Features

- Fetch points from an API with dynamic user input.
- Display points in a table and as a line chart.
- Save the chart as an image file.
- Handle runtime permissions for storage.
- Responsive design for portrait and landscape orientations.
- Progress indicator for fetching points and saving the chart.

---

## Tech Stack

### Languages & Frameworks
- **Kotlin**: Primary language for the app.
- **RxJava**: Reactive programming for API calls and background operations.
- **Retrofit**: HTTP client for API communication.
- **AnyChart**: Charting library for rendering charts.
- **Dagger**: Dependency injection.

### Architecture
- **Clean Architecture**: Separation of concerns with domain, data, and presentation layers.
- **MVP (Model-View-Presenter)**: Ensures testability and clean separation between UI and business logic.
- **Single Activity Architecture**: The app uses a single `MainActivity` to host multiple fragments for navigation and UI display.

---

## Unit Tests

- **Use Cases**:
  - Tests for `GetPointsUseCase` to ensure the correct handling of data fetching from the repository.
  - Tests for `SaveChartToFileUseCase` to validate file-saving operations.
 
---

## Screenshots

### Main Screen
<img src="https://github.com/user-attachments/assets/c4054e2a-4313-4fae-bbb7-0a7d60e6ca03" alt="Main Screen" width="320" height="480">
<img src="https://github.com/user-attachments/assets/e46bbacd-a3c2-4caf-9345-fcd577041739" alt="Main Screen" width="320" height="480">
<img src="https://github.com/user-attachments/assets/05a02a09-0591-4c63-a057-885ea99299e4" alt="Main Screen" width="320" height="480">


### Chart Screen
<img src="https://github.com/user-attachments/assets/fd9871ef-d0d2-4384-968c-2f8c2cb8a561" alt="Chart Screen" width="320" height="480">
<img src="https://github.com/user-attachments/assets/197e25ff-1a2e-4296-919f-bd5ab5e2cd39" alt="Chart Screen" width="320" height="480">
<img src="https://github.com/user-attachments/assets/fd9871ef-d0d2-4384-968c-2f8c2cb8a561" alt="Chart Screen" width="320" height="480">

### Files(Storage)
<img src="https://github.com/user-attachments/assets/b9a5118b-ebfc-468d-a668-73c0861c3e6b" alt="Chart Screen" width="320" height="480">

---
