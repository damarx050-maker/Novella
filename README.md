# Novella (Android)

Kotlin + Jetpack Compose + Room + Firebase (Auth, Firestore, Storage) + Google Play Billing. RTL/LTR switch (Arabic/English) with immediate layout change.

## Features (scaffolded)

- Login screen with Google button (wire to Firebase Auth)
- Onboarding pager (3 slides), shown after first login
- Home with categorized horizontal sections (New, Popular, Philosophy, Horror, Romance, Historical, Fantasy)
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
     `category:String (NEW|POPULAR|PHILOSOPHY|HORROR|ROMANCE|HISTORICAL|FANTASY)`, `language:String (AR|EN)`, `price:Int (5)`

### Categories (التصنيفات)

Allowed category values for any novel document (must match exactly, case-sensitive):

| English      | Arabic            | Value |
|--------------|-------------------|-------|
| New          | الجديدة           | NEW   |
| Popular      | الشائعة           | POPULAR |
| Philosophy   | الفلسفة           | PHILOSOPHY |
| Horror       | الرعب             | HORROR |
| Romance      | الرومانسية (حب)   | ROMANCE |
| Historical   | التاريخي          | HISTORICAL |
| Fantasy      | الفنتازيا         | FANTASY |

Removed (no longer accepted): ARABIC, GLOBAL. Any existing local entries with these categories are purged during refresh.

When adding a new novel ensure its `category` field equals one of the allowed values above; other values will be ignored and deleted on sync.

## CI/CD Secrets (الأسرار المطلوبة للنشر التجريبي)

يستخدم مسار النشر التجريبي (Beta Deployment) وأي نشر لاحق ثلاثة أسرار يجب تعريفها في المستودع. إذا لم تكن موجودة سيتم تخطي خطوة الرفع تلقائياً بدون فشل كامل للبناء.

| Secret | Purpose | Where to get it | Notes |
|--------|---------|-----------------|-------|
| `FIREBASE_APP_ID_ANDROID` | يحدد تطبيق Firebase Android الهدف للتوزيع | Firebase Console → Project Settings → Your Apps (App ID) | يبدأ عادة بـ: `1:XXXXXXXXXXXX:android:...` |
| `FIREBASE_TOKEN` | صلاحية سطر الأوامر للرفع إلى Firebase App Distribution | عبر Firebase CLI: تثبيت الأداة ثم `firebase login:ci` | الناتج سلسلة قصيرة؛ خزّنها كسِر |
| `GOOGLE_PLAY_SERVICE_ACCOUNT_JSON` | حساب خدمة للوصول إلى Google Play Internal Testing | Google Cloud Console: أنشئ Service Account بصلاحية Play Android Developer API ثم أنشئ مفتاح JSON | احفظ كل محتوى ملف الـ JSON كنص في السر |

### خطوات إنشاء الأسرار في GitHub

1. افتح المستودع في GitHub.
2. Settings → Secrets and variables → Actions.
3. اضغط "New repository secret" لكل عنصر.
4. أدخل الاسم تماماً كما في الجدول والقيمة المناسبة ثم حفظ.

### الحصول على FIREBASE_TOKEN

```bash
npm install -g firebase-tools
firebase login
firebase login:ci
# انسخ التوكن الناتج وأضفه كسِر FIREBASE_TOKEN
```

### ملاحظات تشغيل المسارات

- إذا كان أي سر مفقود يتم عرض رسالة تخطي في الـ workflow بدلاً من فشل.
- المسار `beta-deploy.yml` يبني AAB/APK أولاً ثم يحاول الرفع (Firebase أو Play) حسب الإدخال `track`.
- الأسرار لا تُطبع في السجل؛ تأكد من عدم وضعها في الكود أو README.
- يمكن تحديث أو تدوير `FIREBASE_TOKEN` بإعادة تنفيذ الأمر `firebase login:ci`.

### سياسة البيانات غير الصحيحة

أي رواية تحتوي تصنيفاً خارج القيم السبعة المسموحة سيتم حذفها أثناء المزامنة (عمليات `refreshAll` / `refreshCategory` تقوم بـ purge وفحص). أزل التصنيفات القديمة من مصدر البيانات في Firestore لتفادي إعادة إدراجها.

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

- `java-check.yml`: يتحقق من إصدار JDK ويشغّل البناء الأساسي.
- `test.yml`: يشغّل اختبارات الوحدة ويطبع النتائج.
- `build-size.yml`: يبني نسخة Release ويحسب حجم الحزمة، ويفشل إذا تجاوزت 20MB.
- `lint-check.yml`: يشغّل فحص الكود (Lint) ويطبع التحذيرات والأخطاء.
- `coverage.yml`: يشغّل Jacoco ويحسب نسبة التغطية، ويفشل إذا كانت أقل من 80%.
- `translation-check.yml`: يتحقق من تساوي مفاتيح الترجمات بين جميع اللغات.
- `security-check.yml`: يفحص عدم وجود أسرار أو مفاتيح حساسة في الكود.
- `beta-deploy.yml`: ينشر نسخة تجريبية إلى Firebase App Distribution أو Google Play Internal Testing.
- `ui-tests.yml`: يشغّل اختبارات UI على محاكيات بشاشات صغيرة.

### روابط

- صفحة Actions: [GitHub Actions](https://github.com/damarx050-maker/Novella/actions)

### Badges

- ✅ Tests: [badge](https://github.com/damarx050-maker/Novella/actions/workflows/test.yml/badge.svg)
- 📊 Coverage: [badge](badges/jacoco.svg) (generated by coverage workflow)
- 📦 Build Size: [badge](https://github.com/damarx050-maker/Novella/actions/workflows/build-size.yml/badge.svg)

Embed badges:

![Tests](https://github.com/damarx050-maker/Novella/actions/workflows/test.yml/badge.svg)
![Coverage](badges/jacoco.svg)
![Build Size](https://github.com/damarx050-maker/Novella/actions/workflows/build-size.yml/badge.svg)
