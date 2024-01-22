package com.example.obierzyswiat.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.obierzyswiat.MainActivity
import com.example.obierzyswiat.R
import com.example.obierzyswiat.engine.AnimationManager
import com.example.obierzyswiat.engine.Engine
import com.example.obierzyswiat.engine.MonsterCreator
import com.example.obierzyswiat.engine.SubProcess
import com.example.obierzyswiat.models.monsters.Monster
import com.example.obierzyswiat.models.serializable.MonsterSer
import com.example.obierzyswiat.models.serializable.PlayerSer
import com.example.obierzyswiat.viewmodels.FightViewModel
import com.example.obierzyswiat.viewmodels.MonstersViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.w3c.dom.Text
import kotlin.math.log

class FightFragment: Fragment(R.layout.fight_fragment), SubProcess {
    private val _engine = Engine(this)
    lateinit var viewModel: FightViewModel
    lateinit var monstersViewModel: MonstersViewModel
    val args: FightFragmentArgs by navArgs()
    lateinit var monsterImg: ImageView
    lateinit var healthBar: ProgressBar
    lateinit var playerHealthBar: ProgressBar
    lateinit var tvMonsterHealth: TextView
    lateinit var tvPlayerHealth: TextView
    lateinit var monster: Monster
    lateinit var player: PlayerSer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).fightViewModel
        monstersViewModel = (activity as MainActivity).monstersViewModel
        (activity as MainActivity).bnv.visibility = View.GONE
        view.findViewById<ImageView>(R.id.imgMonster)

        val monsterSer = Json.decodeFromString<MonsterSer>(args.monsterSerialized)
        monster = MonsterCreator.createMonster(requireContext(), monsterSer)!!

        player = Json.decodeFromString(args.playerSerialized)

        monsterImg = view.findViewById(R.id.imgMonster)

        healthBar = view.findViewById(R.id.pbHealth)
        healthBar.progress = 100

        playerHealthBar = view.findViewById(R.id.pbPlayerHealth)
        playerHealthBar.progress = 100

        tvMonsterHealth = view.findViewById(R.id.tvMonsterHealth)
        tvPlayerHealth = view.findViewById(R.id.tvPlayerHealth)


        view.setOnClickListener{
            viewModel.onClick()
        }

        viewModel.clicks.observe(viewLifecycleOwner, Observer {clicks ->
            Log.d("FIGHT_CLICKS", clicks.toString())
            monster.healt = monster.maxHealth - (clicks * player.attackDamage)
            val hpPercentage = (monster.healt.toFloat() / monster.maxHealth * 100).toInt()
            healthBar.progress = hpPercentage
            tvMonsterHealth.text = monster.healt.toString() + " / ${monster.maxHealth}"
            if(monster.healt <= 0) {
                tvMonsterHealth.text = "0 / ${monster.maxHealth}"
                move(true)
            }
        })

        _engine.startLoop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _engine.stopLoop()
        (activity as MainActivity).bnv.visibility = View.VISIBLE
    }

    override fun update(deltaTime: Float) {
        requireActivity().runOnUiThread{
            val updateAnim = monster.animationManager.updateRBM(AnimationManager.POSITION.FRONT, AnimationManager.STATE.MOVE, deltaTime)
            val switchTime = if (monster.state == AnimationManager.STATE.MOVE) monster.attackSpeed*0.25f else monster.attackSpeed*0.75f
            updateAnim.first?.let { nonNullBitmap ->
                monsterImg.setImageBitmap(nonNullBitmap)
            }
            if (updateAnim.second) {
                monster.state = if (monster.state == AnimationManager.STATE.MOVE) AnimationManager.STATE.STAY else AnimationManager.STATE.MOVE
                monster.animationManager.switchTime = switchTime
                // if
                    player.health -= monster.attackDamage
                    val hpPercentage = (player.health.toFloat() / player.maxHealth * 100).toInt()
                    playerHealthBar.progress = hpPercentage
                    tvPlayerHealth.text = player.health.toString() + " / ${player.maxHealth}"
                    if(player.health <= 0) {
                        tvPlayerHealth.text = "0 / ${player.maxHealth}"
                        move(false)
                    }
            }
        }
    }

    private fun move(playerWon: Boolean) {
        if(playerWon) {
            lifecycleScope.launch {
                val monsterRec = monstersViewModel.getMonsterByName(monster.name)
                if(monsterRec == null) {
                    monstersViewModel.upsert(MonsterCreator.createMonsterRecord(monster))
                } else {
                    monstersViewModel.updateMonsterNumberByName(monsterRec.name, monsterRec.number+1)
                }
            }
        }
        findNavController().navigate(
            R.id.action_fightFragment_to_gameFragment
        )
    }
}