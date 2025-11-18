package com.novella.app.viewmodel;

import com.novella.app.data.remote.FirestoreService;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<FirestoreService> remoteProvider;

  public SearchViewModel_Factory(Provider<FirestoreService> remoteProvider) {
    this.remoteProvider = remoteProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(remoteProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<FirestoreService> remoteProvider) {
    return new SearchViewModel_Factory(remoteProvider);
  }

  public static SearchViewModel newInstance(FirestoreService remote) {
    return new SearchViewModel(remote);
  }
}
