# Task Control Application

📱 **Overview**: This Android application is designed to manage and control tasks effectively. It allows users to create, edit, and monitor their tasks while providing notifications and alerts to ensure timely reminders.

## Architecture

🏗️ **Design Principles**: The application is built using the **MVVM (Model-View-ViewModel)** pattern combined with **Clean Architecture**. This approach separates business logic from the user interface, improving code testability and maintainability.

**MVVM**
- **Model**: Handles data and business logic.
- **View**: Displays the user interface and data to the user.
- **ViewModel**: Acts as a bridge between the Model and View, preparing and managing data for display.

---

**Clean Architecture**
- **Presentation Layer**:
  - Manages the user interface and handles user interactions.
  - Utilizes ViewModel to maintain UI-related data and state.
  - Communicates with the Domain Layer to execute business logic.
- **Domain Layer**:
  - Encapsulates core business logic and rules.
  - Defines repository interfaces to abstract data operations.
  - Contains UseCase classes that represent specific business actions.
- **Data Layer**:
  - Maps data models to domain models and vice versa.
  - Implements repository interfaces defined in the Domain Layer.
  - Handles data retrieval and storage, such as network calls or database operations.

In this architecture, the Presentation Layer depends on the Domain Layer, which in turn depends on the Data Layer. This direction of dependency ensures a separation of concerns, making the codebase more maintainable and testable.

---

💻 **Programming Languages**: The core codebase is written in **Kotlin**, with **Java** used in specific components (e.g., `AlarmService`) as per the project manager's requirements.

## Key Features

- ⚙️ **Theme**: The app support of **Dark** and **Light** Mode.
- 🌟 **Real-Time Data Updates**: The app leverages **Kotlin Coroutine Flow** to provide a continuously updated data stream, ensuring users always see the latest information.
- 🔔 **Notifications and Alerts**: The app uses **Notifications** to keep users informed and **AlarmManager** to schedule precise alerts at designated times.
- 🔄 **Background Synchronization**: **WorkManager** enables periodic data synchronization in the background, occurring every 15 minutes when an internet connection is available.

## Challenges and Solutions

🤔 **Dependency Injection (DI) and Network Service Implementation**: A significant challenge was integrating two distinct sets of libraries for DI and network communication while keeping the core code optimized with minimal changes. This was resolved using **Android Flavors**:

- **Prefer Flavor**: Utilizes **Koin** for DI and **KtorClient** for API communication.
- **Requested Flavor (default)**: Uses **Hilt** for DI and **Retrofit2** for API communication.

💡 **Outcome**: This dual-flavor approach allowed the development team to explore two different technical stacks, enhancing flexibility and testing architectural adaptability.

## How to Build and Run the Project

🔧 **Steps to Build and Run**:

1. **Choose a Build Variant** in Android Studio:
    - For **Prefer Flavor (Koin + KtorClient)**: Select `preferDebug` or `preferRelease`.
    - For **Requested Flavor (Hilt + Retrofit2)**: Select `requestedDebug` or `requestedRelease`.

2. **Build the Project**:
    - Via Android Studio: Go to the **Build** menu and select **Build Bundle(s) / APK(s)**.
    - Via Terminal: Use Gradle commands for Debug:
      ```bash
      ./gradlew :app:assemblePreferDebug
      ./gradlew :app:assembleRequestedDebug
      ```
    - Via Terminal: Use Gradle commands for Release:
      ```bash
      ./gradlew :app:assemblePreferRelease
      ./gradlew :app:assembleRequestedRelease
      ```

3. **Run the Application**:
    - After selecting the desired variant, launch the app on an emulator or physical Android device.

## Download and Install the Application

📥 **Installation Instructions**:

- **Prefer Flavor APK**: For the **Prefer Flavor (Koin + KtorClient)**, download **Task Manager App-x.x.prefer** .
- **Requested Flavor APK**: For the **Requested Flavor (Hilt + Retrofit2)**, download **Task Manager App-x.x.requested**.

📲 **Installation**: Once downloaded, install the APK file on your Android device.

## Additional Information

### Prerequisites:
- 🛠️ **Android Studio**: Version 2024.x.x or higher.
- 📱 **Device Requirements**: Android device or emulator running API 24 or higher.

### Permissions:
- 🌐 **Internet**: Required for background data synchronization.
- 🔔 **Notifications**: Required to display alerts and reminders.

### Useful Resources:
- 📚 [WorkManager Documentation](https://developer.android.com/topic/libraries/architecture/workmanager)
- 📚 [AlarmManager Documentation](https://developer.android.com/develop/background-work/services/alarms/schedule)
- 📚 [Kotlin Coroutines Documentation](https://kotlinlang.org/docs/coroutines-guide.html)
