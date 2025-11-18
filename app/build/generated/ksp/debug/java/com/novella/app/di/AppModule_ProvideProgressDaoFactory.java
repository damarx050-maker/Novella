package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.ProgressDao;
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
public final class AppModule_ProvideProgressDaoFactory implements Factory<ProgressDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideProgressDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ProgressDao get() {
    return provideProgressDao(dbProvider.get());
  }

  public static AppModule_ProvideProgressDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideProgressDaoFactory(dbProvider);
  }

  public static ProgressDao provideProgressDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideProgressDao(db));
  }
}
