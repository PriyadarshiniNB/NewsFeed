News App

This is an Android News Application built using modern Android development practices and architecture. The app displays news articles using API integration and follows best practices for performance, security, and scalability.

Overview

The application fetches and displays the latest news from a remote API. It is built using Jetpack Compose for UI and follows MVVM architecture. The project includes multiple Android components and focuses on clean code and efficient data handling.

Features

Displays latest news articles from API
Clean and modern UI using Jetpack Compose
Navigation between screens using Navigation component
Background tasks using Alarm Manager
Data communication using Content Provider
System event handling using Broadcast Receiver
Secure network communication with SSL pinning
Code obfuscation and optimization using Proguard
Debugging and performance monitoring using StrictMode

Tech Stack

Language Kotlin
Architecture MVVM
UI Jetpack Compose
Dependency Injection Hilt
Networking Retrofit
Concurrency Kotlin Coroutines
Navigation Jetpack Navigation
Local Data Handling Content Provider
Background Processing Alarm Manager
Security SSL Pinning
Debugging StrictMode

Key Components

API Integration
Implemented using Retrofit to fetch news data from a remote server

Broadcast Receiver
Used to listen for system events and trigger actions inside the app

Content Provider
Used to share data securely between applications

Alarm Manager
Used for scheduling background tasks like periodic updates

StrictMode
Used during development to detect bad practices like network calls on main thread

Proguard
Used to secure and optimize the application for release builds

Coroutines
Used to handle asynchronous operations efficiently

Hilt
Used for dependency injection to manage app components

Navigation
Used to manage screen transitions in a structured way

Project Structure

Follows MVVM architecture with clear separation of concerns
View layer built with Compose
ViewModel handles business logic
Repository manages data sources

How to Run

Clone the repository
Open the project in Android Studio
Add your API key in the required place
Run the app on emulator or real device

Conclusion

This project demonstrates the use of modern Android tools and best practices including clean architecture, secure networking, background processing, and efficient UI development.
