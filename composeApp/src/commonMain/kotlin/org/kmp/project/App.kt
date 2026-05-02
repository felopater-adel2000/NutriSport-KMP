package org.kmp.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.nutrisport.data.domin.CustomerRepository
import com.nutrisport.shared.navigation.Screen
import com.nutrisport.navigation.SetupNavGraph
import com.nutrisport.shared.Constant.WEB_CLIENT_ID
import org.jetbrains.compose.resources.painterResource

import nutrisport.composeapp.generated.resources.Res
import nutrisport.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        var appReady by remember { mutableStateOf(false) }
        val customerRepo: CustomerRepository = koinInject()
        val isUserLoggedIn = remember { customerRepo.getCurrentUserId() != null }
        val startDestination = remember {
            if (isUserLoggedIn) Screen.HomeGraph
            else Screen.Auth
        }

        LaunchedEffect(Unit) {
            GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = WEB_CLIENT_ID))
            appReady = true
        }


        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = appReady
        ) {

            SetupNavGraph(startDestination = startDestination)
        }
    }
}