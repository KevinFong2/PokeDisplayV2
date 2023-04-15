package com.example.pokedisplayv2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var pokemonList: MutableList<String>
    private lateinit var rvPokemon: RecyclerView
    var pokemon_name = ""
    var pokemon_weight = ""
    var pokemon_type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPokemon = findViewById(R.id.pokemon_list)
        pokemonList = mutableListOf()
        for(i in 0 until 60){
            getPokemonURL()
        }
    }
    private fun getPokemonURL(){
        val randomID = (1..1008).random()
        val client = AsyncHttpClient()
        client["https://pokeapi.co/api/v2/pokemon/$randomID", object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Pokemon Image", "response successful$json")
                val pokemonImageArray = json.jsonObject.getJSONObject("sprites").getString("front_default")
                pokemon_name = json.jsonObject.getString("name")
                pokemon_weight = json.jsonObject.getString("weight")
                pokemon_type = json.jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
                val pokemonItem = "$pokemon_name|$pokemonImageArray|$pokemon_weight|$pokemon_type"
                pokemonList.add(pokemonItem)
                val adapter = PokemonAdapter(pokemonList)
                rvPokemon.adapter = adapter
                rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
                rvPokemon.addItemDecoration((DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)))
            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]
    }
}