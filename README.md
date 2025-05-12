# Community and Events App for IPS Academy

## Implementation

This is a Kotlin Android application built using **Jetpack Compose** for the user interface and **Firebase** for cloud data storage and authentication.

## Features

The app provides features related to events and community interaction within the Institute of Engineering & Science, IPS Academy.

### Event

1.  View university events all at once.
2.  View detailed information about specific events.
3.  Register for and save events.

### Community

1.  View posts created by other students.
2.  Create new posts to share with the community.
3.  Read and write comments on posts.
4.  Save your favorite posts for easy access.
5.  Report inappropriate content to maintain a healthy community.

### Authentication

1.  Secure Sign-in powered by Firebase, including email verification.
2.  Login functionality using email and password.

## Screenshots

(Optional: Add screenshots here to showcase the app's UI)

![Screenshot 1](link_to_your_screenshot_1.png)
![Screenshot 2](link_to_your_screenshot_2.png)

*(Replace `link_to_your_screenshot_1.png` and `link_to_your_screenshot_2.png` with actual links to your screenshots, or place images directly in the repo and link them relative path)*

## Technologies Used

* **Kotlin**: The official programming language for Android development.
* **Jetpack Compose**: Android's modern toolkit for building native UIs.
* **Firebase**: Backend-as-a-Service providing:
    * Authentication (Email/Password).
    * Cloud Firestore (Data storage).
    * (Mention other Firebase services if used, e.g., Storage, Functions, etc.)
* **Android Architecture Components**: ViewModel, Navigation Compose, etc.
* **Gradle**: Build automation tool.

## Prerequisites

* Android Studio installed on your machine.
* A Firebase project set up and connected to this Android application (you'll need to add your `google-services.json` file to the `app/` directory - **make sure this file is NOT committed to public repositories if it contains sensitive keys**).
* Basic knowledge of Kotlin and Android development.

## Installation

1.  **Clone the repository:**

    ```bash
    git clone [https://github.com/aadishhere/ipsagram-minor-project](https://github.com/aadishhere/ipsagram-minor-project)
    ```

2.  **Open the project in Android Studio:**
    Select "Open an existing Android Studio project" and navigate to the cloned directory (`ipsagram-minor-project`).

3.  **Set up Firebase:**
    Follow the Firebase documentation to connect your project and add the `google-services.json` file to the `app/` module of the project.

4.  **Sync Gradle:**
    Android Studio should automatically sync the project with Gradle. If not, select `File > Sync Project with Gradle Files`.

## Running the App

1.  **Connect an Android Device or set up an Emulator:**
    Ensure you have an Android device connected via USB with Developer Options and USB Debugging enabled, or set up an Android Emulator in Android Studio's AVD Manager.

2.  **Select the device/emulator:**
    Choose your target device from the dropdown menu in the Android Studio toolbar.

3.  **Run the app:**
    Click the `Run` button (the green play icon) in the toolbar, or press `Shift + F10`.

The app will be built, installed, and launched on your selected device or emulator.

## Contributing

Contributions are welcome! If you would like to improve the app, fix bugs, or add new features, please:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature-name`).
3.  Make your changes.
4.  Commit your changes (`git commit -m 'feat: Add your feature'`).
5.  Push to the branch (`git push origin feature/your-feature-name`).
6.  Open a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
*(Note: You should create a LICENSE file in your repository if you don't have one, e.g., by adding one via the GitHub interface)*

## Contact

If you have any questions or feedback, feel free to contact me at [aadishhere@gmail.com] or open an issue on this repository: `https://github.com/aadishhere/ipsagram-minor-project`.
