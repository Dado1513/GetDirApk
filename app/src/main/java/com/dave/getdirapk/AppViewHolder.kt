package com.dave.getdirapk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class AppViewHolder(itemView:View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener{

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

class AppAdapter(private val listAppObject: ListApps, private var listAppObjectFiltered: ListApps, private val mContext: Context): RecyclerView.Adapter<AppViewHolder>(), Filterable{


    private val inflater:LayoutInflater
    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return AppViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listAppObjectFiltered.apps.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.packageName.text = listAppObjectFiltered.apps[position].packageName
        holder.pathApp.text = listAppObjectFiltered.apps[position].pathApp
        holder.iconApp.setImageDrawable(listAppObjectFiltered.apps[position].icon)
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                if (isLongClick){
                    var clipboard = mContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    var clip = ClipData.newPlainText("path", listAppObjectFiltered.apps[position].pathApp)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(mContext, "Path ${listAppObjectFiltered.apps[position].pathApp} copied into clipboard", Toast.LENGTH_LONG).show()
                } else{
                    var intent = Intent(mContext, AppPrivacyActivity::class.java)
                    intent.putExtra("package_name", listAppObjectFiltered.apps[position].packageName)
                    mContext.startActivity(intent)

                }
            }
        })
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var charString = p0.toString();
                if (charString.isEmpty() || charString.equals("")) {
                    listAppObjectFiltered.apps = listAppObject.apps;
                } else {
                    var listAppFiltered = mutableListOf<App>()
                    for (app in listAppObject.apps) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or package number match
                        if (app.packageName.toLowerCase().contains(charString.toLowerCase()) ||
                            (app?.name != null && app.name.toLowerCase().contains(charString.toLowerCase())) ) {
                            listAppFiltered.add(app);
                        }
                    }
                    listAppObjectFiltered.apps = listAppFiltered
                }

                var filterResults = FilterResults();
                filterResults.values = listAppObjectFiltered
                return filterResults;
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listAppObjectFiltered = p1!!.values as ListApps
                notifyDataSetChanged()
            }

            }
    }
}
