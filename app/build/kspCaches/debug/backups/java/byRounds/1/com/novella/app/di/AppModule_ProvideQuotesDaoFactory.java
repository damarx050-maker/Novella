package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.QuotesDao;
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
public final class AppModule_ProvideQuotesDaoFactory implements Factory<QuotesDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideQuotesDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QuotesDao get() {
    return provideQuotesDao(dbProvider.get());
  }

  public static AppModule_ProvideQuotesDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideQuotesDaoFactory(dbProvider);
  }

  public static QuotesDao provideQuotesDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQuotesDao(db));
  }
}
