package com.example.rocketreserver.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo.coroutines.toFlow
import com.example.rocketreserver.R
import com.example.rocketreserver.TripsBookedSubscription
import com.example.rocketreserver.data.api.apolloClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retryWhen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenResumed {
            apolloClient(this@MainActivity).subscribe(TripsBookedSubscription()).toFlow()
                .retryWhen { _, attempt ->
                    delay(attempt * 1000)
                    true
                }
                .collect {
                    val trips = it.data?.tripsBooked
                    val text = when {
                        trips == null -> getString(R.string.subscriptionError)
                        trips == -1 -> getString(R.string.tripCancelled)
                        else -> getString(R.string.tripBooked, trips)
                    }
                    Snackbar.make(
                        findViewById(R.id.main_frame_layout),
                        text,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
        }
    }
}
