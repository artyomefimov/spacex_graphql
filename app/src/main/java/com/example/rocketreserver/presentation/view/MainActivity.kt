package com.example.rocketreserver.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toFlow
import com.example.rocketreserver.R
import com.example.rocketreserver.TripsBookedSubscription
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retryWhen
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    private val apolloClient by inject<ApolloClient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()

        lifecycleScope.launchWhenResumed {
            apolloClient.subscribe(TripsBookedSubscription()).toFlow()
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

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    private fun setupToolbar() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        appBarConfiguration = AppBarConfiguration(
            navController.graph
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
