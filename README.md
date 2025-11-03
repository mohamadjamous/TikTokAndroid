# TikTokAndroid

A fully functional **TikTok-like Android app** built with **Kotlin**, following **Clean Architecture** and **MVVM** principles.  
It leverages **Jetpack Compose**, **Firebase**, and **modern Android development** tools to deliver a smooth, real-time short-video experience.

---

## Features

- 🎥 Watch and upload short videos  
- ❤️ Like, comment, and follow users  
- ☁️ Video storage with **Firebase Storage**  
- 🔐 Authentication with **Email & Phone Number** via **Firebase Auth**  
- 🧭 Seamless in-app navigation using **Jetpack Navigation Compose**  
- ⚙️ Built with **Kotlin Coroutines** and **Flow** for reactive, async data handling  
- 🧱 Modular and scalable **Clean Architecture** (Data, Domain, UI layers)  
- 🧩 Dependency Injection with **Dagger Hilt**  
- ✅ Unit testing with **JUnit**  

---

## Architecture Overview

This project is structured following **Clean Architecture + MVVM**:

presentation/ → Jetpack Compose UI, ViewModels
domain/ → UseCases, Entities, Repository Interfaces
data/ → Firebase & Local Data Sources, Repository Implementations
di/ → Dagger Hilt modules and dependency graph


- **MVVM** pattern ensures a clear separation between UI and business logic.  
- **Kotlin Flow** handles reactive streams of data for UI updates.  
- **Dagger Hilt** manages dependencies and lifecycle-aware injection.  

---

## Tech Stack

| Category | Technology |
|-----------|-------------|
| Language | **Kotlin** |
| UI | **Jetpack Compose**, **Material 3** |
| Architecture | **Clean Architecture**, **MVVM** |
| Navigation | **Jetpack Navigation Compose** |
| Backend | **Firebase Firestore**, **Firebase Auth**, **Firebase Storage** |
| Dependency Injection | **Dagger Hilt** |
| Concurrency | **Kotlin Coroutines**, **Flow** |
| Testing | **JUnit** |

---

## Modules

- **core** — Shared utilities, constants, and base classes  
- **data** — Repositories, mappers, DTOs  
- **domain** — Use cases and entities  
- **presentation** — UI built with Jetpack Compose  
- **di** — Dependency injection setup  

---

## Screenshots

<img width="1920" height="1080" alt="tiktokandroidpng" src="https://github.com/user-attachments/assets/888b1a17-22fa-4c2b-9346-04d86b6b41a1" />


---

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/TikTokAndroid.git
   cd TikTokAndroid
   
2. Open in Android Studio
    Use the latest version of Android Studio.

3. Configure Firebase
    - Create a new Firebase project in the Firebase Console.
    - Add your Android app package name.
    - Download google-services.json and place it inside app/.
    
    Enable:
      - Firestore
      - Authentication (Email/Phone)
      - Firebase Storage

4. Build & Run
    - Make sure Gradle syncs successfully.
    - Run on a real device or emulator (Android 10+ recommended).
   
---

⭐ If you like this project, consider giving it a star!
