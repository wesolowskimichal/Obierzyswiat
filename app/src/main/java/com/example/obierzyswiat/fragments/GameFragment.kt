package com.example.obierzyswiat.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.obierzyswiat.engine.Engine
import com.example.obierzyswiat.MainActivity
import com.example.obierzyswiat.models.Player
import com.example.obierzyswiat.R
import com.example.obierzyswiat.controllers.MonstersController
import com.example.obierzyswiat.engine.AnimationManager
import com.example.obierzyswiat.engine.SubProcess
import com.example.obierzyswiat.models.serializable.MonsterSer
import com.example.obierzyswiat.models.serializable.PlayerSer
import com.example.obierzyswiat.viewmodels.GameViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GameFragment: Fragment(R.layout.game_fragment), OnMapReadyCallback, SubProcess {
    private val _engine = Engine(this)
    lateinit var viewModel: GameViewModel
    lateinit var _player: Player
//    private val monsters: MutableList<Monster> = mutableListOf()
    private var  mapFragment: SupportMapFragment? = null
    lateinit var monstersController: MonstersController


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =(activity as MainActivity).gameViewModel
        monstersController = MonstersController(requireContext())
        viewModel.start()

        mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        _player = Player("MAIN", LatLng(20.0, 20.0), 600, 600, requireContext())

        viewModel.getPosition().observe(viewLifecycleOwner, Observer {pos ->
            _player.pos = pos
        })
        _engine.startLoop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stop()
        _engine.stopLoop()
    }

    override fun onMapReady(p0: GoogleMap) {
        val success: Boolean = p0.setMapStyle(
            MapStyleOptions(
                resources
                    .getString(R.string.style_json)
            )
        )

        if (!success) {
            Log.e("TAG", "Style parsing failed.")
        }
        val mountainView = LatLng(20.0, 20.0)
        val cameraPosition = CameraPosition.Builder()
            .target(mountainView) // Sets the center of the map to Mountain View
            .zoom(35f)            // Sets the zoom
            .bearing(90f)         // Sets the orientation of the camera to east
            .tilt(60f)            // Sets the tilt of the camera to 30 degrees
            .build()              // Creates a CameraPosition from the builder
        p0.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        p0.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener {marker ->
            Log.d("ASDASD", "${marker.position}: ${marker.title}")
            val monster = monstersController.monsters.filter {monster->
                monster.name == marker.title
            }
            if(monster.isEmpty()) {
                return@OnMarkerClickListener false
            }
            val monsterSer = MonsterSer(
                name = marker.title!!,
                maxHealth = monster[0].maxHealth,
                attackDamage = monster[0].attackDamage,
                attackSpeed = monster[0].attackSpeed
            )
            val serialized = Json.encodeToString(monsterSer)
            val playerSer = Json.encodeToString(PlayerSer(_player.healt, _player.maxHealth, 1))
            val bundle = Bundle().apply {
                putString("monsterSerialized", serialized)
                putString("playerSerialized", playerSer)
            }
            monstersController.remove(monster[0])
            findNavController().navigate(
                R.id.action_gameFragment_to_fightFragment,
                bundle
            )

           /* when(it.title) {
                "Zmij" -> {
                    val monster = monstersController.monsters.filter {
                        it.name == "Zmij"
                    }
                    val monsterSer = MonsterSer(
                        name = "Zmij",
                        maxHealth = monster[0].maxHealth,
                        attackDamage = monster[0].attackDamage,
                        attackSpeed = monster[0].attackSpeed
                    )
                    val serialized = Json.encodeToString(monsterSer)
                    val playerSer = Json.encodeToString(PlayerSer(_player.healt, _player.maxHealth, 1))
                    val bundle = Bundle().apply {
                        putString("monsterSerialized", serialized)
                        putString("playerSerialized", playerSer)
                    }
                    findNavController().navigate(
                        R.id.action_gameFragment_to_fightFragment,
                        bundle
                    )
                }
            }*/
            return@OnMarkerClickListener false
        })
    }

    override fun update(deltaTime: Float) {
        view?.post {
            mapFragment?.getMapAsync { gMap ->
                gMap.moveCamera(CameraUpdateFactory.newLatLng(_player.pos))
                if(viewModel.isMoving()) {
                    _player.update(AnimationManager.POSITION.FRONT, AnimationManager.STATE.MOVE, deltaTime)
                } else {
                    _player.update(AnimationManager.POSITION.FRONT, AnimationManager.STATE.STAY, deltaTime)
                }
                monstersController.update(_player.pos)
                for(monster in monstersController.monsters) {
                    monster.update(deltaTime)
                    monster.draw(gMap)
                }
                _player.draw(gMap)
            }
        }
    }
}