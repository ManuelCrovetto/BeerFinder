package com.macrosystems.beerfinder.ui.view.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.macrosystems.beerfinder.R
import com.macrosystems.beerfinder.core.connectionadvisor.ConnectionStatusLiveData
import com.macrosystems.beerfinder.core.ex.lostConnectionSnackBar
import com.macrosystems.beerfinder.core.ex.reconnectedSnackBar
import com.macrosystems.beerfinder.databinding.ActivityMainBinding
import com.macrosystems.beerfinder.ui.view.factory.BeerFinderFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentFactory: BeerFinderFragmentFactory

    @Inject
    lateinit var connectionStatusLiveData: ConnectionStatusLiveData
    private var isConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        setTheme(R.style.SplashScreen)
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BeerFinder)
        supportFragmentManager.fragmentFactory = fragmentFactory

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectionStatusLiveData.observe(this) { connectionStatus ->
            with(binding){
                if (!connectionStatus){
                    root.lostConnectionSnackBar()
                    isConnected = false
                } else {
                    if (!isConnected)
                    root.reconnectedSnackBar()
                }
            }
        }
    }
}