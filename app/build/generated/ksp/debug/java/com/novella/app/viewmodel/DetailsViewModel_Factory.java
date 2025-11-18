package com.novella.app.viewmodel;

import com.novella.app.data.repo.NovelsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DetailsViewModel_Factory implements Factory<DetailsViewModel> {
  private final Provider<NovelsRepository> repoProvider;

  public DetailsViewModel_Factory(Provider<NovelsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public DetailsViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static DetailsViewModel_Factory create(Provider<NovelsRepository> repoProvider) {
    return new DetailsViewModel_Factory(repoProvider);
  }

  public static DetailsViewModel newInstance(NovelsRepository repo) {
    return new DetailsViewModel(repo);
  }
}
