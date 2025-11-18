package com.novella.app.di;

import com.google.firebase.firestore.FirebaseFirestore;
import com.novella.app.data.remote.FirestoreService;
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
public final class AppModule_ProvideFirestoreServiceFactory implements Factory<FirestoreService> {
  private final Provider<FirebaseFirestore> fsProvider;

  public AppModule_ProvideFirestoreServiceFactory(Provider<FirebaseFirestore> fsProvider) {
    this.fsProvider = fsProvider;
  }

  @Override
  public FirestoreService get() {
    return provideFirestoreService(fsProvider.get());
  }

  public static AppModule_ProvideFirestoreServiceFactory create(
      Provider<FirebaseFirestore> fsProvider) {
    return new AppModule_ProvideFirestoreServiceFactory(fsProvider);
  }

  public static FirestoreService provideFirestoreService(FirebaseFirestore fs) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFirestoreService(fs));
  }
}
