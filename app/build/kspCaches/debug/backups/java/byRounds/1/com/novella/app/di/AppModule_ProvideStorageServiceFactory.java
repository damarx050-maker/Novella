package com.novella.app.di;

import com.google.firebase.storage.FirebaseStorage;
import com.novella.app.data.remote.StorageService;
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
public final class AppModule_ProvideStorageServiceFactory implements Factory<StorageService> {
  private final Provider<FirebaseStorage> storageProvider;

  public AppModule_ProvideStorageServiceFactory(Provider<FirebaseStorage> storageProvider) {
    this.storageProvider = storageProvider;
  }

  @Override
  public StorageService get() {
    return provideStorageService(storageProvider.get());
  }

  public static AppModule_ProvideStorageServiceFactory create(
      Provider<FirebaseStorage> storageProvider) {
    return new AppModule_ProvideStorageServiceFactory(storageProvider);
  }

  public static StorageService provideStorageService(FirebaseStorage storage) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideStorageService(storage));
  }
}
