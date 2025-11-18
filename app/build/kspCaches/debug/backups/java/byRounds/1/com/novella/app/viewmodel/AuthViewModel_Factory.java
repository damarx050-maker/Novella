package com.novella.app.viewmodel;

import android.app.Application;
import com.novella.app.data.repo.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<Application> appProvider;

  private final Provider<SettingsRepository> settingsProvider;

  public AuthViewModel_Factory(Provider<Application> appProvider,
      Provider<SettingsRepository> settingsProvider) {
    this.appProvider = appProvider;
    this.settingsProvider = settingsProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(appProvider.get(), settingsProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<Application> appProvider,
      Provider<SettingsRepository> settingsProvider) {
    return new AuthViewModel_Factory(appProvider, settingsProvider);
  }

  public static AuthViewModel newInstance(Application app, SettingsRepository settings) {
    return new AuthViewModel(app, settings);
  }
}
