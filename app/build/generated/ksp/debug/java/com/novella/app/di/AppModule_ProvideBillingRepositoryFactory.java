package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.repo.BillingRepository;
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
public final class AppModule_ProvideBillingRepositoryFactory implements Factory<BillingRepository> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideBillingRepositoryFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BillingRepository get() {
    return provideBillingRepository(dbProvider.get());
  }

  public static AppModule_ProvideBillingRepositoryFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideBillingRepositoryFactory(dbProvider);
  }

  public static BillingRepository provideBillingRepository(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBillingRepository(db));
  }
}
