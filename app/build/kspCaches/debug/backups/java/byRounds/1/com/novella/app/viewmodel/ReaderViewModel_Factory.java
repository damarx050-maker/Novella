package com.novella.app.viewmodel;

import com.novella.app.data.local.dao.DownloadsDao;
import com.novella.app.data.remote.StorageService;
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
public final class ReaderViewModel_Factory implements Factory<ReaderViewModel> {
  private final Provider<NovelsRepository> repoProvider;

  private final Provider<DownloadsDao> downloadsDaoProvider;

  private final Provider<StorageService> storageServiceProvider;

  public ReaderViewModel_Factory(Provider<NovelsRepository> repoProvider,
      Provider<DownloadsDao> downloadsDaoProvider,
      Provider<StorageService> storageServiceProvider) {
    this.repoProvider = repoProvider;
    this.downloadsDaoProvider = downloadsDaoProvider;
    this.storageServiceProvider = storageServiceProvider;
  }

  @Override
  public ReaderViewModel get() {
    return newInstance(repoProvider.get(), downloadsDaoProvider.get(), storageServiceProvider.get());
  }

  public static ReaderViewModel_Factory create(Provider<NovelsRepository> repoProvider,
      Provider<DownloadsDao> downloadsDaoProvider,
      Provider<StorageService> storageServiceProvider) {
    return new ReaderViewModel_Factory(repoProvider, downloadsDaoProvider, storageServiceProvider);
  }

  public static ReaderViewModel newInstance(NovelsRepository repo, DownloadsDao downloadsDao,
      StorageService storageService) {
    return new ReaderViewModel(repo, downloadsDao, storageService);
  }
}
