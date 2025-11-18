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
- JDK 17

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
- In Play Console, create products:
  - `novella_monthly_15` (subscription, 15 AED/month)
  - `novella_yearly_135` (subscription, 135 AED/year)
  - `novella_single_5` (in-app, 5 AED)
- Implement purchase flow in `BillingManager` and persist `PurchaseEntity` locally.

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
- Add subscription and one-time purchase flows with UI badges and gating in Details/Reader.
- Add retry/error surfaces and network monitoring.

## Firestore Security (example)
Configure Firestore/Storage security rules appropriate for your public content and purchases.
