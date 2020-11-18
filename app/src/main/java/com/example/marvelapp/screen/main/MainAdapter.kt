package com.example.marvelapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.model.Character

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*

class MainAdapter (
    var characterList: MutableList<Character>?,
    var itemClicked: OnItemClicked
    ) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    interface OnItemClicked {
        fun itemClicked(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(characterList.isNullOrEmpty()){
            return 0
        }
        return characterList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(!characterList.isNullOrEmpty()) {
            holder.bindView(characterList!![position])
        }
    }

    fun updateTransactionList(characterList: MutableList<Character>?){
        if (this.characterList.isNullOrEmpty()) {
            this.characterList = characterList
            notifyDataSetChanged()
        } else {
            if (characterList != null && characterList.last() != this.characterList!!.last()) {
                for (character in characterList) {
                    this.characterList!!.add(character)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun clearMainAdapter() {
        characterList?.run {
            characterList!!.clear()
            characterList = null
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener   {

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            characterList?.get(adapterPosition)?.let { itemClicked.itemClicked(it) }
        }

        fun bindView(character: Character) {
            view.apply {
                character.image?.run {
                    Picasso.with(context)
                        .load(character.image.getImageUrl())
                        .error(R.drawable.ic_baseline_error_24)
                        .placeholder(R.drawable.ic_marvel)
                        .into(characterImageView)

                }
                nameTextView.text = character.name
            }
        }
    }
}