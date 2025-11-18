package com.novella.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;

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
public final class AppModule_ProvideIoScopeFactory implements Factory<CoroutineScope> {
  @Override
  public CoroutineScope get() {
    return provideIoScope();
  }

  public static AppModule_ProvideIoScopeFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineScope provideIoScope() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideIoScope());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideIoScopeFactory INSTANCE = new AppModule_ProvideIoScopeFactory();
  }
}
