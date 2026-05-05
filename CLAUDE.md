# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Android debug build
./gradlew :composeApp:assembleDebug

# Run all tests
./gradlew test

# Run tests for a specific module
./gradlew :shared:test
./gradlew :data:test
./gradlew :feature:auth:test

# iOS: Open iosApp/iosApp.xcodeproj in Xcode and run from there
```

## Module Architecture

The project is a Kotlin Multiplatform (KMP) app with Compose Multiplatform UI, targeting Android and iOS. It uses **8 Gradle modules**:

| Module | Role |
|---|---|
| `:composeApp` | App entry point; Android `Application` + `MainActivity`, iOS framework |
| `:shared` | Domain models, design system, `RequestState`, `Screen` routes, logging |
| `:data` | Firebase Firestore/Auth repository implementations |
| `:di` | Koin DI module wiring — all providers registered in one `sharedModule` |
| `:navigation` | `SetupNavGraph()` — top-level `NavHost` with typed routes |
| `:feature:auth` | Google sign-in UI + `AuthViewModel` (customer creation) |
| `:feature:home` | Main scaffold with bottom bar + drawer + `HomeGraphViewModel` (sign-out) |
| `:feature:profile` | Placeholder — module skeleton exists, no implementation yet |

## Key Patterns

### RequestState — Async State Management
All async operations return `RequestState<T>` (sealed class in `shared/util/RequestState.kt`):
- `Idle`, `Loading`, `Success<T>`, `Error`
- Use `DisplayResult()` composable for animated state-driven UI rendering
- Repository suspend functions return `RequestState` via `flow` or direct value

### Navigation — Typed Routes
Routes are `@Serializable` objects/data classes inside the `Screen` sealed class (`shared/navigation/Screen.kt`). Navigation uses Jetpack Navigation Compose with `composable<Screen.X>()` typed destinations. Use `popUpTo(...) { inclusive = true }` when replacing the back stack (e.g., auth → home).

### Dependency Injection — Koin
- All providers registered in `di/KoinModule.kt` inside a single `sharedModule`
- `initializeKoin()` is called from `NutriSportApplication.onCreate()` on Android
- Composables inject via `koinViewModel()` / `koinInject()`
- ViewModels must be registered in `sharedModule` to be accessible cross-platform

### Feature Module Structure
Each feature follows the same layout:
```
feature/<name>/src/commonMain/kotlin/com/nutrisport/<name>/
  <Name>Screen.kt       # Top-level @Composable; injects ViewModel, handles navigation callbacks
  <Name>ViewModel.kt    # MVVM ViewModel; uses viewModelScope + RequestState
  component/            # Reusable UI components for this feature
  domain/               # Feature-local enums/models (e.g., BottomBarDestination)
```

### Design System (`:shared`)
- **Colors**: `com.nutrisport.shared.Alpha`, `com.nutrisport.shared.Colors` — semantic color tokens + brand colors (Yellowish, Orange, Red) + category palette
- **Typography**: `com.nutrisport.shared.Fonts` — Bebas Neue (headings), Roboto Condensed (body); standardized sizes via `FontSize` object
- **Icons/Images**: referenced via `com.nutrisport.shared.Resources` (Compose Resources drawables)
- **Constants**: `Constant.kt` — Google Web Client ID, PayPal endpoints, cart quantity limits (`MAX_QUANTITY = 10`, `MIN_QUANTITY = 1`)

### Data Layer
- `CustomerRepository` interface in `:data`, implemented by `CustomerRepositoryImpl` using Firebase Firestore
- Firebase Auth (KMP) + Firestore initialized per-platform; on Android done in `NutriSportApplication`
- Repository is a Koin singleton; inject via `CustomerRepository` interface, never the impl directly

## Important Context

- **Profile feature** is intentionally empty — the module scaffolding exists but awaits implementation. Follow the auth/home pattern when building it.
- **iOS builds** require Xcode; KMP produces a static framework consumed by `iosApp/`. Compose Multiplatform shares all UI code including navigation and ViewModels.
- **Logging**: `:shared` uses LogKat (`com.nutrisport.shared.logging`); `:feature:home` and `:feature:profile` use Kermit. Keep these consistent within their module boundaries.
- **Version catalog**: All dependency versions are centralized in `gradle/libs.versions.toml`. Add new dependencies there, not inline in `build.gradle.kts`.
