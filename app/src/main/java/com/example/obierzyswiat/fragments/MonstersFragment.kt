package com.example.obierzyswiat.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obierzyswiat.MainActivity
import com.example.obierzyswiat.R
import com.example.obierzyswiat.adapters.MonstersAdapter
import com.example.obierzyswiat.viewmodels.MonstersViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MonstersFragment: Fragment(R.layout.monsters_fragment) {
    lateinit var viewModel: MonstersViewModel
    lateinit var monstersAdapter: MonstersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).monstersViewModel
        monstersAdapter = MonstersAdapter()
        initRecyclerView()

        viewModel.getAllMonsters().observe(viewLifecycleOwner, Observer { monsters ->
            monstersAdapter.differ.submitList(monsters)
        })


    }

    private fun initRecyclerView() {
        val rvSavedContacts = view?.findViewById<RecyclerView>(R.id.rvMonsters)
        rvSavedContacts?.apply {
            adapter = monstersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}