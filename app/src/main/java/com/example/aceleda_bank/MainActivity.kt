package com.example.aceleda_bank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.aceleda_bank.ui.theme.Aceleda_BankTheme
import com.example.aceleda_bank.Navigation.AppNavigation
import android.content.Intent
import com.example.aceleda_bank.Screen.QRScannerScreen
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Aceleda_BankTheme {
                AppNavigation()
            }
        }
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        val result = IntentIntegrator.parseActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (result != null) {

            if (result.contents != null) {

                println(result.contents)

                val json = JSONObject(result.contents)

                val receiverId =
                    json.getLong("receiverId")

                val receiverName =
                    json.getString("receiverName")

                println(receiverId)

                println(receiverName)
            }
        }

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }
}