package org.kmp.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.nutrisport.data.domin.CustomerRepository
import com.nutrisport.navigation.SetupNavGraph
import com.nutrisport.shared.Constant.WEB_CLIENT_ID
import com.nutrisport.shared.navigation.Screen
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