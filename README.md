# SampleApk — Mobile Automation Playground

A complete, **fully offline** Android sample application built with **Kotlin**, **Jetpack Compose**,
**Material 3**, **Room**, and **Navigation Compose**.

It is intentionally designed to *look* like a realistic, moderately complex app so it can be used as a
target for:

- 📱 Mobile UI automation frameworks (Appium, UiAutomator, Espresso, etc.)
- ♿ Accessibility testing
- 👆 Gesture / swipe / scroll automation
- 🔎 Screen scraping & OCR testing
- 🤖 Agent-based mobile interaction testing

> There is **no backend, no Firebase, no external API, and no internet requirement**. Everything runs
> locally on-device with a seeded Room (SQLite) database.

---

## ✨ Features

| Screen | What it demonstrates |
|--------|----------------------|
| **Splash** | App logo, name, loading animation, auto-navigation after ~2.5s |
| **Authentication** | `Login` / `Sign Up` tabs, input forms, basic validation, local Room storage |
| **Terms & Conditions** | Long scrollable content — **Accept is disabled until you scroll to the bottom**; acceptance stored locally |
| **Optional Setup** | Username, phone, city, profile-picture placeholder, **Skip** / **Continue** |
| **Dashboard** | Top bar + avatar + notification badge, **horizontal swipe carousel**, statistics cards, **scrollable feed of 50+ items**, quick-action grid, **FAB → modal dialog** |
| **Profile** | User info, profile image, membership level, sectioned options, logout |
| **Settings** | Dark-mode toggle, notifications toggle, language dropdown, About — all persisted |
| **Navigation** | Bottom navigation (Home / Activity / Profile / Settings) **and** a navigation drawer |

UI building blocks intentionally included for automation coverage: **cards, lists, dialogs, navigation
drawer, bottom navigation, horizontal scrolling, vertical scrolling, and input forms.** Most interactive
elements expose a stable Compose `testTag` (e.g. `btn_login`, `home_feed`, `fab_create`, `toggle_dark_mode`)
to make automation reliable.

---

## 🔑 Demo Accounts

Two accounts are **seeded automatically on first launch** so you can log in without
signing up. They are already onboarded (terms accepted + profile filled), so logging
in goes **straight to the dashboard**:

| Email | Password | Name | Membership | City |
|-------|----------|------|------------|------|
| `demo@example.com` | `demo1234` | Demo User | Gold | San Francisco |
| `jane@example.com` | `jane1234` | Jane Cooper | Platinum | London |

You can also create your own account via the **Sign Up** tab (new accounts go through
the Terms → Setup flow). All credentials are stored locally in Room — nothing leaves the device.

---

## 🗄️ Local Database (Room)

Three tables, seeded automatically on first launch:

- **users** — `id`, `name`, `email`, `password` (+ optional `username`, `phone`, `city`, `membershipLevel`, `acceptedTerms`)
- **preferences** — `darkMode`, `notificationsEnabled`, `language`
- **activities** — **54 dummy rows** spanning Activities, Notifications, Transactions, and Orders

All dummy data (accounts you create, transactions, activities, notifications) is generated locally and
deterministically — no network calls of any kind.

---

## 🧱 Tech Stack

- Kotlin 2.0
- Android Gradle Plugin 8.7, Gradle 8.11.1
- `compileSdk` / `targetSdk` 34, `minSdk` 24
- Jetpack Compose (BOM 2024.12.01) + Material 3
- Navigation Compose
- Room (with KSP)
- Lifecycle / ViewModel
- Clean-ish layering: `data` (entities / DAOs / repository) → `ui` (ViewModel / screens / navigation)

---

## 📦 Download / Install the APK

Pre-built APKs are published automatically to **[GitHub Releases](../../releases)** by CI.

1. Open the latest release under the repository's **Releases** section.
2. Download **`SampleApk-release.apk`** (signed) or **`SampleApk-debug.apk`**.
3. Transfer to an Android device (API 24+) and install. You may need to enable
   *“Install unknown apps”* for your file manager / browser.

APKs are also available as workflow **artifacts** on each run (Actions → run → Artifacts).

---

## 🛠️ Build Instructions (local)

```bash
git clone https://github.com/zalakamal08/sampleapk.git
cd sampleapk

# Debug APK  -> app/build/outputs/apk/debug/app-debug.apk
./gradlew assembleDebug

# Release APK (signed) -> app/build/outputs/apk/release/app-release.apk
./gradlew assembleRelease
```

Requirements: JDK 17, Android SDK with `platforms;android-34` and `build-tools;34.0.0`.
Point the build at your SDK via `local.properties` (`sdk.dir=/path/to/Android/Sdk`) or the
`ANDROID_HOME` environment variable.

### Release signing

The release build is signed with a **committed sample keystore** (`app/release.keystore`,
alias `sampleapk`). This is intentional so that CI produces an installable signed APK with **zero
manual configuration / secrets**. ⚠️ This keystore is for testing only — never reuse it for a real
production release.

---

## 🤖 CI/CD Workflow

Workflow file: [`.github/workflows/android-build.yml`](.github/workflows/android-build.yml)

On every **push** (and on manual `workflow_dispatch`), the workflow:

1. Checks out the repo and sets up **JDK 17** + the **Android SDK**.
2. Installs `platforms;android-34` and `build-tools;34.0.0`.
3. Builds the **Debug APK** (`./gradlew assembleDebug`).
4. Builds the **Release APK** (`./gradlew assembleRelease`, signed with the sample keystore).
5. Uploads both APKs as **build artifacts**.
6. Creates a **GitHub Release** (tag `build-<run_number>`) and **attaches both APKs** to it.

This satisfies: trigger on push → build APK → upload artifact → create release → attach APK.

---

## 📸 Screenshots

> Screenshots can be captured from any emulator/device once the app is installed.
> Suggested captures:

| Splash | Auth | Terms |
|--------|------|-------|
| _splash.png_ | _auth.png_ | _terms.png_ |

| Dashboard | Profile | Settings |
|-----------|---------|----------|
| _dashboard.png_ | _profile.png_ | _settings.png_ |

_(Placeholders — drop real captures into a `screenshots/` folder and update the links.)_

---

## 🧭 Navigation Flow

```
Splash
  └─► Login / Sign Up
        └─► Terms & Conditions (scroll → Accept)
              └─► Optional Setup (Skip allowed)
                    └─► Dashboard
                          ├─ Home      (carousel, stats, feed, quick actions, FAB)
                          ├─ Activity  (filterable list)
                          ├─ Profile
                          └─ Settings
```

---

## 📂 Project Structure

```
app/src/main/java/com/example/sampleapp/
├── SampleApplication.kt        # App entry, DB + dummy-data seeding
├── MainActivity.kt             # Compose host, theme, splash install
├── data/
│   ├── entity/                 # UserEntity, PreferencesEntity, ActivityEntity
│   ├── dao/                    # UserDao, PreferencesDao, ActivityDao
│   ├── AppDatabase.kt          # Room database
│   ├── SampleRepository.kt     # Single source of truth
│   └── DummyData.kt            # Offline dummy-data generator (54 rows)
└── ui/
    ├── AppViewModel.kt         # Shared session + preferences state
    ├── theme/                  # Material 3 theme
    ├── navigation/             # Routes + NavHost
    └── screens/                # splash, auth, terms, setup, dashboard (+ tabs)
```

---

## 📝 License

Sample / educational project — provided as-is for testing purposes.
