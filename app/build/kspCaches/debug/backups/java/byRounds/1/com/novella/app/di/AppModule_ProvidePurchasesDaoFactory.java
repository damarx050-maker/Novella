package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.PurchasesDao;
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
public final class AppModule_ProvidePurchasesDaoFactory implements Factory<PurchasesDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvidePurchasesDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PurchasesDao get() {
    return providePurchasesDao(dbProvider.get());
  }

  public static AppModule_ProvidePurchasesDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvidePurchasesDaoFactory(dbProvider);
  }

  public static PurchasesDao providePurchasesDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePurchasesDao(db));
  }
}
