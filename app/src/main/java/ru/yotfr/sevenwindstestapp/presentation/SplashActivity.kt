package ru.yotfr.sevenwindstestapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isAuthorized.collectLatest { isAuthorized ->
                    val startActivityIntent = Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )
                    startActivityIntent.putExtra(
                        MainActivity.IS_AUTHORIZED_INTENT_EXTRA_KEY,
                        isAuthorized
                    )
                    startActivity(startActivityIntent)
                    finish()
                }
            }
        }
    }

}