package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.NovelsDao;
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
public final class AppModule_ProvideNovelsDaoFactory implements Factory<NovelsDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideNovelsDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NovelsDao get() {
    return provideNovelsDao(dbProvider.get());
  }

  public static AppModule_ProvideNovelsDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideNovelsDaoFactory(dbProvider);
  }

  public static NovelsDao provideNovelsDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNovelsDao(db));
  }
}
