package com.novella.app.viewmodel;

import com.novella.app.data.local.dao.DownloadsDao;
import com.novella.app.data.local.dao.ProgressDao;
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
public final class LibraryViewModel_Factory implements Factory<LibraryViewModel> {
  private final Provider<DownloadsDao> downloadsDaoProvider;

  private final Provider<ProgressDao> progressDaoProvider;

  public LibraryViewModel_Factory(Provider<DownloadsDao> downloadsDaoProvider,
      Provider<ProgressDao> progressDaoProvider) {
    this.downloadsDaoProvider = downloadsDaoProvider;
    this.progressDaoProvider = progressDaoProvider;
  }

  @Override
  public LibraryViewModel get() {
    return newInstance(downloadsDaoProvider.get(), progressDaoProvider.get());
  }

  public static LibraryViewModel_Factory create(Provider<DownloadsDao> downloadsDaoProvider,
      Provider<ProgressDao> progressDaoProvider) {
    return new LibraryViewModel_Factory(downloadsDaoProvider, progressDaoProvider);
  }

  public static LibraryViewModel newInstance(DownloadsDao downloadsDao, ProgressDao progressDao) {
    return new LibraryViewModel(downloadsDao, progressDao);
  }
}
