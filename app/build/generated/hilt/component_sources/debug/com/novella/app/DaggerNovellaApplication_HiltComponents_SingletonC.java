package com.novella.app;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.novella.app.billing.BillingManager;
import com.novella.app.data.local.AppDatabase;
import com.novella.app.data.local.dao.DownloadsDao;
import com.novella.app.data.local.dao.ProgressDao;
import com.novella.app.data.remote.FirestoreService;
import com.novella.app.data.remote.StorageService;
import com.novella.app.data.repo.BillingRepository;
import com.novella.app.data.repo.NovelsRepository;
import com.novella.app.data.repo.SettingsRepository;
import com.novella.app.di.AppModule_ProvideBillingManagerFactory;
import com.novella.app.di.AppModule_ProvideBillingRepositoryFactory;
import com.novella.app.di.AppModule_ProvideContextFactory;
import com.novella.app.di.AppModule_ProvideDbFactory;
import com.novella.app.di.AppModule_ProvideDownloadsDaoFactory;
import com.novella.app.di.AppModule_ProvideFirestoreFactory;
import com.novella.app.di.AppModule_ProvideFirestoreServiceFactory;
import com.novella.app.di.AppModule_ProvideNovelsRepositoryFactory;
import com.novella.app.di.AppModule_ProvideProgressDaoFactory;
import com.novella.app.di.AppModule_ProvideSettingsRepositoryFactory;
import com.novella.app.di.AppModule_ProvideStorageFactory;
import com.novella.app.di.AppModule_ProvideStorageServiceFactory;
import com.novella.app.viewmodel.AuthViewModel;
import com.novella.app.viewmodel.AuthViewModel_HiltModules;
import com.novella.app.viewmodel.BillingViewModel;
import com.novella.app.viewmodel.BillingViewModel_HiltModules;
import com.novella.app.viewmodel.DetailsViewModel;
import com.novella.app.viewmodel.DetailsViewModel_HiltModules;
import com.novella.app.viewmodel.HomeViewModel;
import com.novella.app.viewmodel.HomeViewModel_HiltModules;
import com.novella.app.viewmodel.LibraryViewModel;
import com.novella.app.viewmodel.LibraryViewModel_HiltModules;
import com.novella.app.viewmodel.OnboardingViewModel;
import com.novella.app.viewmodel.OnboardingViewModel_HiltModules;
import com.novella.app.viewmodel.ReaderViewModel;
import com.novella.app.viewmodel.ReaderViewModel_HiltModules;
import com.novella.app.viewmodel.SearchViewModel;
import com.novella.app.viewmodel.SearchViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerNovellaApplication_HiltComponents_SingletonC {
  private DaggerNovellaApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public NovellaApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements NovellaApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements NovellaApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements NovellaApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements NovellaApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements NovellaApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements NovellaApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements NovellaApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public NovellaApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends NovellaApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends NovellaApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends NovellaApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends NovellaApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(8).put(LazyClassKeyProvider.com_novella_app_viewmodel_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_BillingViewModel, BillingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_DetailsViewModel, DetailsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_LibraryViewModel, LibraryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_ReaderViewModel, ReaderViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_novella_app_viewmodel_SearchViewModel, SearchViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_novella_app_viewmodel_AuthViewModel = "com.novella.app.viewmodel.AuthViewModel";

      static String com_novella_app_viewmodel_BillingViewModel = "com.novella.app.viewmodel.BillingViewModel";

      static String com_novella_app_viewmodel_ReaderViewModel = "com.novella.app.viewmodel.ReaderViewModel";

      static String com_novella_app_viewmodel_HomeViewModel = "com.novella.app.viewmodel.HomeViewModel";

      static String com_novella_app_viewmodel_SearchViewModel = "com.novella.app.viewmodel.SearchViewModel";

      static String com_novella_app_viewmodel_OnboardingViewModel = "com.novella.app.viewmodel.OnboardingViewModel";

      static String com_novella_app_viewmodel_DetailsViewModel = "com.novella.app.viewmodel.DetailsViewModel";

      static String com_novella_app_viewmodel_LibraryViewModel = "com.novella.app.viewmodel.LibraryViewModel";

      @KeepFieldType
      AuthViewModel com_novella_app_viewmodel_AuthViewModel2;

      @KeepFieldType
      BillingViewModel com_novella_app_viewmodel_BillingViewModel2;

      @KeepFieldType
      ReaderViewModel com_novella_app_viewmodel_ReaderViewModel2;

      @KeepFieldType
      HomeViewModel com_novella_app_viewmodel_HomeViewModel2;

      @KeepFieldType
      SearchViewModel com_novella_app_viewmodel_SearchViewModel2;

      @KeepFieldType
      OnboardingViewModel com_novella_app_viewmodel_OnboardingViewModel2;

      @KeepFieldType
      DetailsViewModel com_novella_app_viewmodel_DetailsViewModel2;

      @KeepFieldType
      LibraryViewModel com_novella_app_viewmodel_LibraryViewModel2;
    }
  }

  private static final class ViewModelCImpl extends NovellaApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<BillingViewModel> billingViewModelProvider;

    private Provider<DetailsViewModel> detailsViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LibraryViewModel> libraryViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<ReaderViewModel> readerViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.billingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.detailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.libraryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.readerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(8).put(LazyClassKeyProvider.com_novella_app_viewmodel_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_BillingViewModel, ((Provider) billingViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_DetailsViewModel, ((Provider) detailsViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_LibraryViewModel, ((Provider) libraryViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_ReaderViewModel, ((Provider) readerViewModelProvider)).put(LazyClassKeyProvider.com_novella_app_viewmodel_SearchViewModel, ((Provider) searchViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_novella_app_viewmodel_AuthViewModel = "com.novella.app.viewmodel.AuthViewModel";

      static String com_novella_app_viewmodel_SearchViewModel = "com.novella.app.viewmodel.SearchViewModel";

      static String com_novella_app_viewmodel_BillingViewModel = "com.novella.app.viewmodel.BillingViewModel";

      static String com_novella_app_viewmodel_OnboardingViewModel = "com.novella.app.viewmodel.OnboardingViewModel";

      static String com_novella_app_viewmodel_HomeViewModel = "com.novella.app.viewmodel.HomeViewModel";

      static String com_novella_app_viewmodel_ReaderViewModel = "com.novella.app.viewmodel.ReaderViewModel";

      static String com_novella_app_viewmodel_LibraryViewModel = "com.novella.app.viewmodel.LibraryViewModel";

      static String com_novella_app_viewmodel_DetailsViewModel = "com.novella.app.viewmodel.DetailsViewModel";

      @KeepFieldType
      AuthViewModel com_novella_app_viewmodel_AuthViewModel2;

      @KeepFieldType
      SearchViewModel com_novella_app_viewmodel_SearchViewModel2;

      @KeepFieldType
      BillingViewModel com_novella_app_viewmodel_BillingViewModel2;

      @KeepFieldType
      OnboardingViewModel com_novella_app_viewmodel_OnboardingViewModel2;

      @KeepFieldType
      HomeViewModel com_novella_app_viewmodel_HomeViewModel2;

      @KeepFieldType
      ReaderViewModel com_novella_app_viewmodel_ReaderViewModel2;

      @KeepFieldType
      LibraryViewModel com_novella_app_viewmodel_LibraryViewModel2;

      @KeepFieldType
      DetailsViewModel com_novella_app_viewmodel_DetailsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.novella.app.viewmodel.AuthViewModel 
          return (T) new AuthViewModel(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule), singletonCImpl.provideSettingsRepositoryProvider.get());

          case 1: // com.novella.app.viewmodel.BillingViewModel 
          return (T) new BillingViewModel(singletonCImpl.provideBillingManagerProvider.get());

          case 2: // com.novella.app.viewmodel.DetailsViewModel 
          return (T) new DetailsViewModel(singletonCImpl.provideNovelsRepositoryProvider.get());

          case 3: // com.novella.app.viewmodel.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.provideNovelsRepositoryProvider.get());

          case 4: // com.novella.app.viewmodel.LibraryViewModel 
          return (T) new LibraryViewModel(singletonCImpl.downloadsDao(), singletonCImpl.progressDao());

          case 5: // com.novella.app.viewmodel.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.provideSettingsRepositoryProvider.get());

          case 6: // com.novella.app.viewmodel.ReaderViewModel 
          return (T) new ReaderViewModel(singletonCImpl.provideNovelsRepositoryProvider.get(), singletonCImpl.downloadsDao(), singletonCImpl.provideStorageServiceProvider.get());

          case 7: // com.novella.app.viewmodel.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.provideFirestoreServiceProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends NovellaApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends NovellaApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends NovellaApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<Context> provideContextProvider;

    private Provider<SettingsRepository> provideSettingsRepositoryProvider;

    private Provider<AppDatabase> provideDbProvider;

    private Provider<BillingRepository> provideBillingRepositoryProvider;

    private Provider<BillingManager> provideBillingManagerProvider;

    private Provider<FirebaseFirestore> provideFirestoreProvider;

    private Provider<FirestoreService> provideFirestoreServiceProvider;

    private Provider<NovelsRepository> provideNovelsRepositoryProvider;

    private Provider<FirebaseStorage> provideStorageProvider;

    private Provider<StorageService> provideStorageServiceProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private DownloadsDao downloadsDao() {
      return AppModule_ProvideDownloadsDaoFactory.provideDownloadsDao(provideDbProvider.get());
    }

    private ProgressDao progressDao() {
      return AppModule_ProvideProgressDaoFactory.provideProgressDao(provideDbProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideContextProvider = DoubleCheck.provider(new SwitchingProvider<Context>(singletonCImpl, 1));
      this.provideSettingsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepository>(singletonCImpl, 0));
      this.provideDbProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 4));
      this.provideBillingRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BillingRepository>(singletonCImpl, 3));
      this.provideBillingManagerProvider = DoubleCheck.provider(new SwitchingProvider<BillingManager>(singletonCImpl, 2));
      this.provideFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 7));
      this.provideFirestoreServiceProvider = DoubleCheck.provider(new SwitchingProvider<FirestoreService>(singletonCImpl, 6));
      this.provideNovelsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<NovelsRepository>(singletonCImpl, 5));
      this.provideStorageProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseStorage>(singletonCImpl, 9));
      this.provideStorageServiceProvider = DoubleCheck.provider(new SwitchingProvider<StorageService>(singletonCImpl, 8));
    }

    @Override
    public void injectNovellaApplication(NovellaApplication novellaApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.novella.app.data.repo.SettingsRepository 
          return (T) AppModule_ProvideSettingsRepositoryFactory.provideSettingsRepository(singletonCImpl.provideContextProvider.get());

          case 1: // android.content.Context 
          return (T) AppModule_ProvideContextFactory.provideContext(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 2: // com.novella.app.billing.BillingManager 
          return (T) AppModule_ProvideBillingManagerFactory.provideBillingManager(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule), singletonCImpl.provideBillingRepositoryProvider.get());

          case 3: // com.novella.app.data.repo.BillingRepository 
          return (T) AppModule_ProvideBillingRepositoryFactory.provideBillingRepository(singletonCImpl.provideDbProvider.get());

          case 4: // com.novella.app.data.local.AppDatabase 
          return (T) AppModule_ProvideDbFactory.provideDb(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 5: // com.novella.app.data.repo.NovelsRepository 
          return (T) AppModule_ProvideNovelsRepositoryFactory.provideNovelsRepository(singletonCImpl.provideDbProvider.get(), singletonCImpl.provideFirestoreServiceProvider.get(), singletonCImpl.provideBillingRepositoryProvider.get());

          case 6: // com.novella.app.data.remote.FirestoreService 
          return (T) AppModule_ProvideFirestoreServiceFactory.provideFirestoreService(singletonCImpl.provideFirestoreProvider.get());

          case 7: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) AppModule_ProvideFirestoreFactory.provideFirestore();

          case 8: // com.novella.app.data.remote.StorageService 
          return (T) AppModule_ProvideStorageServiceFactory.provideStorageService(singletonCImpl.provideStorageProvider.get());

          case 9: // com.google.firebase.storage.FirebaseStorage 
          return (T) AppModule_ProvideStorageFactory.provideStorage();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
