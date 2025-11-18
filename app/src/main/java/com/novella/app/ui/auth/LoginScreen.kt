package com.novella.app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.novella.app.theme.NovellaBackground
import com.novella.app.theme.NovellaPrimary
import com.novella.app.theme.NovellaTextPrimary
import com.novella.app.utils.UiStrings
import com.novella.app.viewmodel.AuthViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(onLoggedIn: () -> Unit, authVm: AuthViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val firebaseAuth = remember { FirebaseAuth.getInstance() }
    val signInError = remember { mutableStateOf<String?>(null) }

    // Build GoogleSignInOptions. If default_web_client_id is absent, we do basic profile only (will fail to auth with Firebase), and show guidance.
    val gso = remember {
        val webClientIdRes = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
        if (webClientIdRes != 0) {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(webClientIdRes))
                .requestEmail()
                .build()
        } else {
            // Fallback: still create options to allow sign-in UI; we will show error to set SHA-1 and re-download google-services.json
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        }
    }

    val googleClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken.isNullOrEmpty()) {
                    signInError.value = "Missing idToken. Add SHA-1 in Firebase, re-download google-services.json (with default_web_client_id)."
                    return@rememberLauncherForActivityResult
                }
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        val uid = it.user?.uid ?: account.id ?: ""
                        authVm.setLoggedIn(uid)
                        onLoggedIn()
                    }
                    .addOnFailureListener { e -> signInError.value = e.message }
            } catch (e: ApiException) {
                signInError.value = e.message
            }
        } else {
            signInError.value = "Sign-in cancelled"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Novella", color = NovellaTextPrimary, fontSize = 36.sp)
            Spacer(Modifier.height(24.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = UiStrings.welcome(), color = NovellaTextPrimary)
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { launcher.launch(googleClient.signInIntent) },
                        colors = ButtonDefaults.buttonColors(containerColor = NovellaPrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text(text = UiStrings.loginWithGoogle(), color = Color.White)
                    }
                    if (signInError.value != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = signInError.value!!, color = Color.Red)
                    }
                }
            }
        }
    }
}
