package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.DownloadsDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideDownloadsDaoFactory implements Factory<DownloadsDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideDownloadsDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DownloadsDao get() {
    return provideDownloadsDao(dbProvider.get());
  }

  public static AppModule_ProvideDownloadsDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideDownloadsDaoFactory(dbProvider);
  }

  public static DownloadsDao provideDownloadsDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDownloadsDao(db));
  }
}
