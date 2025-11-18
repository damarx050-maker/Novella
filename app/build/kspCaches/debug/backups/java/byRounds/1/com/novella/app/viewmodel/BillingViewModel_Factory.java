package com.novella.app.viewmodel;

import com.novella.app.billing.BillingManager;
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
public final class BillingViewModel_Factory implements Factory<BillingViewModel> {
  private final Provider<BillingManager> billingProvider;

  public BillingViewModel_Factory(Provider<BillingManager> billingProvider) {
    this.billingProvider = billingProvider;
  }

  @Override
  public BillingViewModel get() {
    return newInstance(billingProvider.get());
  }

  public static BillingViewModel_Factory create(Provider<BillingManager> billingProvider) {
    return new BillingViewModel_Factory(billingProvider);
  }

  public static BillingViewModel newInstance(BillingManager billing) {
    return new BillingViewModel(billing);
  }
}
