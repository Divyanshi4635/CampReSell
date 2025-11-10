## BuyAndSell — Android App

BuyAndSell is an Android application for listing items for sale and discovering items to buy. This repository contains the Android app source built with Android Studio and Gradle.

### Key Features
- Browse and search listings
- Create and manage your own listings
- Image support for products (via camera/gallery depending on implementation)
- Basic authentication flows (login/register) if configured
- Modern Android app architecture with Navigation and Data Binding

### Tech Stack
- **Language**: Java (Android)
- **Build System**: Gradle (`gradlew`)
- **IDE**: Android Studio
- **Architecture/Libs**:
  - AndroidX
  - Jetpack Navigation
  - Data Binding / View Binding
  - Material Components
  - JUnit (tests)

> Note: Exact library versions are managed in `gradle/libs.versions.toml` and module build files.

---

## Getting Started

### Prerequisites
- Android Studio (latest stable recommended)
- Android SDK and platform tools
- JDK 17 (or the version configured in `gradle.properties` / Android Studio JDK)
- An Android device or emulator (API level based on your project configuration)

### Clone the Repository
```bash
git clone https://github.com/<your-username>/BuyAndSell.git
cd BuyAndSell
```

### Open in Android Studio
1. Open Android Studio.
2. Choose “Open an Existing Project” and select the `BuyAndSell` directory.
3. Let Gradle sync complete.

### Build from Command Line
```bash
# Windows PowerShell
.\gradlew.bat clean assembleDebug

# macOS/Linux
./gradlew clean assembleDebug
```

Artifacts are generated under:
- `app/build/outputs/apk/debug/*.apk` (debug)
- `app/build/outputs/apk/release/*.apk` (release)

### Run on Device/Emulator
1. Enable Developer Options and USB debugging on your Android device, or start an emulator from Android Studio.
2. Click “Run” ▶ in Android Studio, choose the `app` configuration and the target device.

---

## Project Structure

High-level layout (simplified):
```
BuyAndSell/
├─ app/
│  ├─ src/
│  │  ├─ main/
│  │  │  ├─ AndroidManifest.xml
│  │  │  ├─ java/            # App source code (activities, fragments, viewmodels, adapters, etc.)
│  │  │  └─ res/             # Layouts, drawables, navigation graphs, strings, themes
│  │  ├─ androidTest/        # Instrumented tests
│  │  └─ test/               # Unit tests
│  ├─ build.gradle           # Module-level Gradle config
│  └─ proguard-rules.pro     # R8/ProGuard rules
├─ build.gradle              # Project-level Gradle config
├─ settings.gradle.kts       # Gradle settings
├─ gradle/                   # Version catalog, wrapper
├─ gradlew / gradlew.bat     # Gradle wrapper scripts
└─ README.md
```

Notable files and directories:
- `app/src/main/AndroidManifest.xml`: App manifest and permissions.
- `app/src/main/res/navigation/`: Navigation graphs (if used).
- `gradle/libs.versions.toml`: Dependency versions via Version Catalog.
- `gradle.properties`: Gradle and JVM settings.

---

## Configuration

### App ID, Versioning, and SDK Levels
These are managed in the `app/build.gradle` module file. Update:
- `applicationId`
- `minSdk`, `targetSdk`, and `compileSdk`
- `versionCode` and `versionName`

### Secrets and API Keys
If your app integrates with remote services, do not hardcode secrets. Use one of:
- Gradle properties + `local.properties`
- BuildConfig fields via `build.gradle`
- Encrypted resources or Android Keystore when applicable

Example (Gradle BuildConfig field):
```groovy
buildTypes {
    debug {
        buildConfigField "String", "API_BASE_URL", "\"https://api.example.com/\""
    }
}
```
Then in code:
```java
String baseUrl = BuildConfig.API_BASE_URL;
```

---

## Building a Release

1. Configure signing in `app/build.gradle` or via Android Studio (Build > Generate Signed Bundle / APK).
2. Set up your keystore safely (do not commit it).
3. Build:
```bash
# Windows
.\gradlew.bat assembleRelease
# macOS/Linux
./gradlew assembleRelease
```
4. Output: `app/build/outputs/apk/release/`

---

## Testing

Run unit tests:
```bash
./gradlew testDebugUnitTest
```

Run instrumented tests (requires emulator/device):
```bash
./gradlew connectedDebugAndroidTest
```

Reports are generated under `app/build/reports/`.

---

## Screenshots

Add screenshots or screen recordings to showcase key flows.

Suggested locations:
- `docs/images/home.png`
- `docs/images/listing_details.png`
- `docs/images/create_listing.png`

Reference them here in the README:
```markdown
![Home](docs/images/home.png)
![Details](docs/images/listing_details.png)
![Create Listing](docs/images/create_listing.png)
```

---

## Troubleshooting

- Gradle sync issues:
  - Ensure a stable internet connection.
  - In Android Studio: File > Invalidate Caches / Restart.
  - Delete `.gradle` and `build/` directories and re-sync if needed.
- SDK version/compile errors:
  - Install required SDK platforms and tools in Android Studio > SDK Manager.
  - Match `compileSdk`/`targetSdk` in `app/build.gradle` with installed SDKs.
- Java/JDK mismatch:
  - Ensure Android Studio uses the same JDK as configured in `gradle.properties` or Gradle toolchain.

---

## Contributing

1. Fork the repository.
2. Create a feature branch:
```bash
git checkout -b feat/short-description
```
3. Commit your changes with clear messages:
```bash
git commit -m "feat: add listing filtering by price"
```
4. Push and open a Pull Request.

Please follow conventional commits (`feat`, `fix`, `docs`, `refactor`, etc.) when possible.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Acknowledgements

- Android Jetpack and Material Components teams
- Open-source libraries used in this project (see Gradle build files)

---

## Maintainers

- Your Name (@your-handle)

---

## Notes for Reviewers

- Check Navigation flows defined under `res/navigation` (if present).
- Review `AndroidManifest.xml` for permissions (e.g., camera, storage, internet).
- Verify Data Binding/View Binding usage and binding adapters if any exist.


