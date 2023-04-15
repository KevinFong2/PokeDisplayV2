package com.example.pokedisplayv2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList : List<String>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView
        val pokemonName : TextView
        val pokemonWeight : TextView
        val pokemonType : TextView
        init {
            pokemonImage = view.findViewById(R.id.pokemon_image)
            pokemonName = view.findViewById(R.id.pokemon_name)
            pokemonWeight = view.findViewById(R.id.pokemon_weight)
            pokemonType = view.findViewById(R.id.pokemon_types)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonItem = pokemonList[position].split("|")
        holder.pokemonName.text = "Name: " + pokemonItem[0]
        holder.pokemonWeight.text = pokemonItem[2] + " Hectogram"
        holder.pokemonType.text = "Type: " + pokemonItem[3]
        holder.pokemonImage.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                pokemonItem[0]+ " at position $position clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        Glide.with(holder.itemView)
            .load(pokemonItem[1])
            .fitCenter()
            .into(holder.pokemonImage)
    }
}