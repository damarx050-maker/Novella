package com.novella.app.di;

import android.app.Application;
import com.novella.app.billing.BillingManager;
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
public final class AppModule_ProvideBillingManagerFactory implements Factory<BillingManager> {
  private final Provider<Application> appProvider;

  private final Provider<BillingRepository> billingRepositoryProvider;

  public AppModule_ProvideBillingManagerFactory(Provider<Application> appProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    this.appProvider = appProvider;
    this.billingRepositoryProvider = billingRepositoryProvider;
  }

  @Override
  public BillingManager get() {
    return provideBillingManager(appProvider.get(), billingRepositoryProvider.get());
  }

  public static AppModule_ProvideBillingManagerFactory create(Provider<Application> appProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    return new AppModule_ProvideBillingManagerFactory(appProvider, billingRepositoryProvider);
  }

  public static BillingManager provideBillingManager(Application app,
      BillingRepository billingRepository) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBillingManager(app, billingRepository));
  }
}
