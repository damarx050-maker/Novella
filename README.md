# Novella (Android)

Kotlin + Jetpack Compose + Room + Firebase (Auth, Firestore, Storage) + Google Play Billing. RTL/LTR switch (Arabic/English) with immediate layout change.

## Features (scaffolded)

- Login screen with Google button (wire to Firebase Auth)
- Onboarding pager (3 slides), shown after first login
- Home with five horizontal sections (New, Popular, Arabic, Global, Philosophy)
- Details screen with cover, metadata, Start Reading + Download Offline buttons
- Reader skeleton with day/night toggle and progress text
- Library skeleton sections (Continue, Offline, Favorites)
- Search with prefix behavior and friendly empty state
- Room database for offline entities and progress
- Firebase Firestore service for novels and search; Storage service for PDF download
- Billing product IDs and skeleton manager/repository
- Localization utility and instant RTL/LTR switch

## Getting Started

### Prerequisites

- Android Studio Giraffe or newer
- JDK 21

### 1) Firebase setup

1. Create Firebase project and Android app `com.novella.app`.
2. Download `google-services.json` and place it at `app/google-services.json`.
3. Enable: Authentication (Google), Firestore, and Storage.
4. Firestore collection `Novels` documents should include fields:
   - `id:String`, `title:String`, `author:String`, `description:String`, `coverUrl:String`, `pdfUrl:String`,
     `category:String (NEW|POPULAR|ARABIC|GLOBAL|PHILOSOPHY)`, `language:String (AR|EN)`, `price:Int (5)`

### 2) Google Sign-In (Auth)

- In Firebase console, enable Google provider and set SHA-1.
- In your LoginScreen, wire the button to start Google Sign-In flow and call `FirebaseAuth.signInWithCredential(...)`.

### 3) Google Play Billing

- In Play Console, create subscription products only:
  - `novella_monthly`
  - `novella_yearly`
- Implement subscription flow in `BillingManager` and persist `PurchaseEntity` locally as SUBSCRIPTION.

## Billing Overview

- Products (configure in Play Console):
  - Subscriptions only: `novella_monthly`, `novella_yearly`

- Client Integration:
  - Dependency: `com.android.billingclient:billing-ktx` (already added via Gradle)
  - `BillingManager` exposes:
    - `isSubscribed: StateFlow<Boolean>`
    - `billingErrors: SharedFlow<String>`
  - Use `launchSubscription(activity, productId)` for subscription purchase flows.

- Access Rules:
  - Free novels: Open content directly.
  - Premium novels: Require active subscription (monthly/yearly). Otherwise show a paywall (BottomSheet) offering Subscribe Monthly or Subscribe Yearly.

## Design System (Material3)

- Colors (Dark):
  - Primary `#9E8BF5`, Secondary `#64D2FF`, Background `#0B0B0D`, Surface `#121214`, SurfaceVariant `#1A1A1D`, OnSurface `#E8E9ED`, Outline `#2A2A2E`, AccentGold `#FFD54F`, Error `#FF4D5A`.
- Shapes:
  - Card radius 16dp, Button radius 12dp, Badge capsule 999dp.
- Motion:
  - Enter/Exit 200ms, Pressed 120ms, easing FastOutSlowIn.

## Screenshots

Add screenshots before merging: Home, Detail, Subscription, Premium Badge.

### 4) Build & Run

Open in Android Studio and click Run. If you haven't added `google-services.json`, the project still builds due to conditional plugin, but Firebase calls will no-op.

### 5) Offline PDFs

- PDFs are stored under `filesDir/novels/{novelId}.pdf`.
- Covers are cached by Coil (disk cache enabled by default).

## Localization

- Arabic and English string resources in `values-ar/` and `values/`.
- Toggle language via the top bar globe icon; switches RTL/LTR instantly and typography.

## Next Steps (implementation)

- Hook Google Sign-In intent and result in `LoginScreen` and persist login.
- Replace `dummyRepo()` usages with DI wiring (Hilt or manual) to real Room + Firestore.
- Implement PDF rendering via `PdfRenderer` or a 3rd-party viewer.
- Add subscription flows with UI badges and gating in Details/Reader.
- Add retry/error surfaces and network monitoring.

## Firestore Security (example)

Configure Firestore/Storage security rules appropriate for your public content and purchases.

## Build Status

![Verify Java Version](https://github.com/damarx050-maker/Novella/actions/workflows/java-check.yml/badge.svg)

## 📦 Continuous Integration

This project uses GitHub Actions to validate code quality, build health, and release readiness on every push/PR to `main`.

### Workflows

- Verify Java Version: Ensures JDK 21 is used and builds the app.
  - Runs: [java-check.yml](https://github.com/damarx050-maker/Novella/actions/workflows/java-check.yml)
- Run Unit Tests: Executes unit tests and summarizes results.
  - Runs: [test.yml](https://github.com/damarx050-maker/Novella/actions/workflows/test.yml)
- Check Build Size: Builds release APK and fails if size > 20MB.
  - Runs: [build-size.yml](https://github.com/damarx050-maker/Novella/actions/workflows/build-size.yml)
- Android Lint Check: Runs lint on debug variant, fails on errors.
  - Runs: [lint-check.yml](https://github.com/damarx050-maker/Novella/actions/workflows/lint-check.yml)
- Code Coverage (JaCoCo): Generates coverage report; fails if < 80% and publishes a badge.
  - Runs: [coverage.yml](https://github.com/damarx050-maker/Novella/actions/workflows/coverage.yml)
- Translation Keys Check: Verifies all locales have the same string keys as base.
  - Runs: [translation-check.yml](https://github.com/damarx050-maker/Novella/actions/workflows/translation-check.yml)
- Secrets Scan: Scans repository for potential secrets.
  - Runs: [security-check.yml](https://github.com/damarx050-maker/Novella/actions/workflows/security-check.yml)
- Beta Deployment: Manual release to Firebase App Distribution or Google Play Internal track.
  - Runs: [beta-deploy.yml](https://github.com/damarx050-maker/Novella/actions/workflows/beta-deploy.yml)
- UI Tests (Emulator): Runs connected tests on ~4" and ~5" emulators.
  - Runs: [ui-tests.yml](https://github.com/damarx050-maker/Novella/actions/workflows/ui-tests.yml)

### Badges

- ✅ Tests: [badge](https://github.com/damarx050-maker/Novella/actions/workflows/test.yml/badge.svg)
- 📊 Coverage: [badge](badges/jacoco.svg) (generated by coverage workflow)
- 📦 Build Size: [badge](https://github.com/damarx050-maker/Novella/actions/workflows/build-size.yml/badge.svg)

Embed badges:

![Tests](https://github.com/damarx050-maker/Novella/actions/workflows/test.yml/badge.svg)
![Coverage](badges/jacoco.svg)
![Build Size](https://github.com/damarx050-maker/Novella/actions/workflows/build-size.yml/badge.svg)
