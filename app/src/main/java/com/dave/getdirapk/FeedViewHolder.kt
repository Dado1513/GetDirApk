package com.dave.getdirapk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView

class FeedViewHolder(itemView:View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    var iconApp: ImageView
    var packageName: TextView
    var pathApp: TextView
    private var itemClickListener: ItemClickListener?=null

    init {
        iconApp = itemView.findViewById(R.id.icon_app) as ImageView
        packageName = itemView.findViewById(R.id.idPackageName) as TextView
        pathApp = itemView.findViewById(R.id.idPathApp) as TextView
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)

    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        // !! will return NON-NULL value of this variable
        itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }

}

class FeedAdapter(private val listAppObject: ListApps, private val mContext: Context): RecyclerView.Adapter<FeedViewHolder>(){
    private val inflater:LayoutInflater
    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listAppObject.apps.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.packageName.text = listAppObject.apps[position].packageName
        holder.pathApp.text = listAppObject.apps[position].pathApp
        holder.iconApp.setImageDrawable(listAppObject.apps[position].icon)
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                if (isLongClick){
                    var clipboard = mContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    var clip = ClipData.newPlainText("path", listAppObject.apps[position].pathApp)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(mContext, "Path ${listAppObject.apps[position].pathApp} copied into clipboard", Toast.LENGTH_LONG).show()
                }
            }
        })
    }



}