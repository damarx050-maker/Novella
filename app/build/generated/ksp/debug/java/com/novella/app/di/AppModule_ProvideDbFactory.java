package com.novella.app.di;

import android.app.Application;
import com.novella.app.data.local.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideDbFactory implements Factory<AppDatabase> {
  private final Provider<Application> appProvider;

  public AppModule_ProvideDbFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public AppDatabase get() {
    return provideDb(appProvider.get());
  }

  public static AppModule_ProvideDbFactory create(Provider<Application> appProvider) {
    return new AppModule_ProvideDbFactory(appProvider);
  }

  public static AppDatabase provideDb(Application app) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDb(app));
  }
}
