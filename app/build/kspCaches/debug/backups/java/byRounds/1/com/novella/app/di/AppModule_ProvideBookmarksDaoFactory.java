package com.novella.app.di;

import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.BookmarksDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideBookmarksDaoFactory implements Factory<BookmarksDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideBookmarksDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public BookmarksDao get() {
    return provideBookmarksDao(dbProvider.get());
  }

  public static AppModule_ProvideBookmarksDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideBookmarksDaoFactory(dbProvider);
  }

  public static BookmarksDao provideBookmarksDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBookmarksDao(db));
  }
}
