package com.example.studentportalapp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class PortalAdapter(private val context: Context, private val portals: List<Portal>) : RecyclerView.Adapter<PortalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.portal_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return portals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(portals[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvUrl: TextView = itemView.findViewById(R.id.tvUrl)
        private val clBackground: ConstraintLayout = itemView.findViewById(R.id.clBackground)

        fun bind(portal: Portal) {
            tvTitle.text = portal.title
            tvUrl.text = portal.uri.toString()
            clBackground.setOnClickListener { clickItem(portal) }
        }

        private fun clickItem(portal: Portal) {
            var url = portal.uri.toString()
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@PortalAdapter.context, Uri.parse(url))
        }
    }
}