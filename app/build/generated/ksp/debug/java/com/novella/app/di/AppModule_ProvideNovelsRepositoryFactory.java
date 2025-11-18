package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.remote.FirestoreService;
import com.novella.app.data.repo.BillingRepository;
import com.novella.app.data.repo.NovelsRepository;
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
public final class AppModule_ProvideNovelsRepositoryFactory implements Factory<NovelsRepository> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<FirestoreService> remoteProvider;

  private final Provider<BillingRepository> billingProvider;

  public AppModule_ProvideNovelsRepositoryFactory(Provider<AppDatabase> dbProvider,
      Provider<FirestoreService> remoteProvider, Provider<BillingRepository> billingProvider) {
    this.dbProvider = dbProvider;
    this.remoteProvider = remoteProvider;
    this.billingProvider = billingProvider;
  }

  @Override
  public NovelsRepository get() {
    return provideNovelsRepository(dbProvider.get(), remoteProvider.get(), billingProvider.get());
  }

  public static AppModule_ProvideNovelsRepositoryFactory create(Provider<AppDatabase> dbProvider,
      Provider<FirestoreService> remoteProvider, Provider<BillingRepository> billingProvider) {
    return new AppModule_ProvideNovelsRepositoryFactory(dbProvider, remoteProvider, billingProvider);
  }

  public static NovelsRepository provideNovelsRepository(AppDatabase db, FirestoreService remote,
      BillingRepository billing) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNovelsRepository(db, remote, billing));
  }
}
