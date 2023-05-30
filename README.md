# REST Android Project

## REST Android Project
This project is a school project designed to demonstrate the integration of an Android application with a dummy backend. The app allows you to interact with the backend and perform various operations such as retrieving, adding, modifying, and deleting users.

## Introduction
The REST Android Project is an Android application developed as a school project to showcase the integration of a dummy backend (https://dummyjson.com) with an Android app. The app connects to the dummy backend via RESTful APIs and provides functionalities to manage user data virtually. This project serves as an educational resource for understanding how to connect an Android app to a backend and perform CRUD (Create, Read, Update, Delete) operations.

## Features
- **User Interface**: Simple two view user interface for viewing an modifying retrieved data
- **Pagination**: Pagination to ease the load on the backend and viewer experience
- **CRUD Operations**: Methods to retrieve, add, modify and delete user integrated into the user interface

## Tech/Framework used
Built with:
- Android Studio
- Kotlin

Dependencies used:
- [OkHttp3](https://square.github.io/okhttp/): Http client for retrieving and posting data to the server
- [Jackson](https://github.com/FasterXML/jackson): Main JSON parser for parsing retrieved user data
- [Jetpack Compose](https://developer.android.com/jetpack/compose): Main UI built with Android's native Jetpack Compose library

## Requirements
To run the REST Android Project on your phone through Android Studio, ensure that you have the following:
- Android Studio 3.0 or later.
- Android device or emulator running Android 7.0 (API level 24) or higher.

## Installation
Follow these steps to get the project up and running on your local machine:
1. Clone this repository to your local machine using the following command: ```git clone https://github.com/ottokyosti/rest-android-project.git```
2. Open Android Studio (3.0 or later)
3. Click on "Open an existing Android Studio project" and navigate to the cloned repository directory.
4. Select the rest-android-project directory and click "OK".
5. Android Studio will build the project and set up the necessary dependencies automatically.

## Usage
To build and use the REST Android Project on your own phone or emulator, follow these steps:
1. Launch the Android Studio and open the project.
2. Connect your Android device or start an emulator.
3. Build and run the application.
4. The app will launch on your device or emulator, and you will be able to view, add, update, and delete users from the dummy backend.

## Documentation
Generated documentation can be found [here](app/doc/dokka/html)

## Screencast
Link to a screencast of this project as well as iOS version of this project: [Screencast](https://www.youtube.com/watch?v=OEuH21UGc_w)
