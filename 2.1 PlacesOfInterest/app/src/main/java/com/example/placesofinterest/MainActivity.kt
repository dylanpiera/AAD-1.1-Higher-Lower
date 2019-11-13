package com.example.placesofinterest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_place.view.*

class MainActivity : AppCompatActivity() {

    private val places = arrayListOf<Place>()
    private val placeAdapter = PlaceAdapter(places)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPlaces.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rvPlaces.adapter = placeAdapter

        for(i in Place.PLACE_NAMES.indices) {
            places.add(Place(Place.PLACE_NAMES[i], Place.PLACE_RES_DRAWABLE_IDS[i]))
        }
        placeAdapter.notifyDataSetChanged()
    }
}

data class Place (
    var name: String,
    @DrawableRes var ImageResId: Int
) {
    companion object {
        val PLACE_NAMES = arrayOf(
            "Amsterdam Dam",
            "Amsterdam Weesperplein",
            "Rotterdam Euromast",
            "Den Haag Binnenhof",
            "Utrecht Dom",
            "Groningen Martinitoren",
            "Maastricht Vrijthof",
            "New York Vrijheidsbeeld",
            "San Francisco Golden Gate",
            "Yellowstone Old Faithful",
            "Yosemite Half Dome",
            "Washington White House",
            "Ottawa Parliament Hill",
            "Londen Tower Bridge",
            "Brussel Manneken Pis",
            "Berlijn Reichstag",
            "Parijs Eiffeltoren",
            "Barcelona Sagrada Familia",
            "Rome Colosseum",
            "Pompeii",
            "Kopenhagen",
            "Oslo",
            "Stockholm",
            "Helsinki",
            "Moskou Rode Plein",
            "Beijing Verboden Stad",
            "Kaapstad Tafelberg",
            "Rio de Janeiro Copacabana",
            "Sydney Opera",
            "Hawaii Honolulu",
            "Alaska Denali"
            )

        val PLACE_RES_DRAWABLE_IDS = arrayOf(
            R.drawable.amsterdam_dam,
            R.drawable.amsterdam_weesperplein,
            R.drawable.rotterdam_euromast,
            R.drawable.den_haag_binnenhof,
            R.drawable.utrecht_dom,
            R.drawable.groningen_martinitoren,
            R.drawable.maastricht_vrijthof,
            R.drawable.new_york_vrijheidsbeeld,
            R.drawable.san_francisco_golden_gate,
            R.drawable.yellowstone_old_faithful,
            R.drawable.yosemite_half_dome,
            R.drawable.washington_white_house,
            R.drawable.ottawa_parliament_hill,
            R.drawable.london_tower_bridge,
            R.drawable.brussel_manneken_pis,
            R.drawable.berlijn_reichstag,
            R.drawable.parijs_eiffeltoren,
            R.drawable.barcelona_sagrada_familia,
            R.drawable.rome_colosseum,
            R.drawable.pompeii,
            R.drawable.kopenhagen,
            R.drawable.oslo,
            R.drawable.stockholm,
            R.drawable.helsinki,
            R.drawable.moskou_rode_plein,
            R.drawable.beijing_verboden_stad,
            R.drawable.kaapstad_tafelberg,
            R.drawable.rio_de_janeiro_copacabana,
            R.drawable.sydney_opera,
            R.drawable.hawaii,
            R.drawable.alaska_denali
        )
    }
}

class PlaceAdapter(private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_place, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(place : Place) {
            itemView.ivPlace.setImageDrawable(context.getDrawable(place.ImageResId))
            itemView.tvPlace.text = place.name
        }
    }
}
