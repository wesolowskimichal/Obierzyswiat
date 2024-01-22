package com.example.obierzyswiat

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.obierzyswiat.controllers.GPSController
import com.example.obierzyswiat.controllers.MonstersController
import com.example.obierzyswiat.database.MonstersDatabase
import com.example.obierzyswiat.repository.MonstersRepository
import com.example.obierzyswiat.viewmodels.FightViewModel
import com.example.obierzyswiat.viewmodels.GameViewModel
import com.example.obierzyswiat.viewmodels.GameViewModelProvider
import com.example.obierzyswiat.viewmodels.MonstersViewModel
import com.example.obierzyswiat.viewmodels.MonstersViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    lateinit var bnv: BottomNavigationView
    private lateinit var nhf: NavHostFragment
    lateinit var gameViewModel: GameViewModel
    lateinit var monstersViewModel: MonstersViewModel
    val fightViewModel: FightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gpsController = GPSController(this)
        val monstersController = MonstersController(this)
        val gameViewModelProvider = GameViewModelProvider(gpsController, monstersController)
        gameViewModel = ViewModelProvider(this, gameViewModelProvider)[GameViewModel::class.java]

        val monstersRepository = MonstersRepository(MonstersDatabase(this))
        val monstersViewModelProvider = MonstersViewModelProvider(monstersRepository)
        monstersViewModel = ViewModelProvider(this, monstersViewModelProvider)[MonstersViewModel::class.java]

        setContentView(R.layout.activity_main)

        bnv = findViewById(R.id.bottomNavigationView)
        nhf = supportFragmentManager.findFragmentById(R.id.contentNavHostFragment) as NavHostFragment
        bnv.setupWithNavController(nhf.findNavController())
    }

}