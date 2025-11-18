package com.novella.app.ui.subscription

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.novella.app.billing.BillingProductIds
import com.novella.app.theme.NovellaPrimary
import com.novella.app.theme.NovellaSurface
import com.novella.app.theme.NovellaSurfaceVariant
import com.novella.app.utils.UiStrings
import com.novella.app.viewmodel.BillingViewModel

@Composable
fun SubscriptionScreen(vm: BillingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PlanCard(
            title = UiStrings.monthlyPlanTitle(),
            subtitle = UiStrings.monthlyPlanSubtitle(),
            price = UiStrings.monthlyPlanPrice(),
            onClick = {
                val act = context as? Activity ?: return@PlanCard
                vm.subscribeMonthly(act, BillingProductIds.MONTHLY_SUB)
            }
        )
        PlanCard(
            title = UiStrings.yearlyPlanTitle(),
            subtitle = UiStrings.yearlyPlanSubtitle(),
            price = UiStrings.yearlyPlanPrice(),
            onClick = {
                val act = context as? Activity ?: return@PlanCard
                vm.subscribeMonthly(act, BillingProductIds.YEARLY_SUB)
            }
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { /* TODO: deep link to manage subscriptions */ },
            colors = ButtonDefaults.buttonColors(containerColor = NovellaSurfaceVariant),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(UiStrings.manageSubscription())
        }
    }
}

@Composable
private fun PlanCard(title: String, subtitle: String, price: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = NovellaSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(price, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NovellaPrimary)
                ) { Text(UiStrings.subscribeNow(), color = Color.White) }
            }
        }
    }
}
