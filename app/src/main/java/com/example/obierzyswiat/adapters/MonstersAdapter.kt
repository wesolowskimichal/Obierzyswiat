package com.example.obierzyswiat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.obierzyswiat.R
import com.example.obierzyswiat.engine.MonsterCreator
import com.example.obierzyswiat.models.MonsterRecord
import kotlin.coroutines.coroutineContext

class MonstersAdapter: RecyclerView.Adapter<MonstersAdapter.MonsterViewHolder>() {
    inner class MonsterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<MonsterRecord>() {
        override fun areItemsTheSame(oldItem: MonsterRecord, newItem: MonsterRecord): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MonsterRecord, newItem: MonsterRecord): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        return MonsterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.monster_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        val monster = differ.currentList[position]
        val head = holder.itemView.findViewById<ImageView>(R.id.ivMonsterHead)
        val name = holder.itemView.findViewById<TextView>(R.id.tvName)
        val number = holder.itemView.findViewById<TextView>(R.id.tvNumber)
        holder.apply {
            head.setImageBitmap(MonsterCreator.getHead(itemView.context, monster.name))
            name.text = monster.name
            number.text = monster.number.toString()
        }
    }
}