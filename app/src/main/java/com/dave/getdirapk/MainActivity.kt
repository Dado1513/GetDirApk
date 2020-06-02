package com.dave.getdirapk

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var applist: MutableList<ApplicationInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val linearLayoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        reciclerView.layoutManager = linearLayoutManager
        loadApp()
    }


    fun loadApp(){
        var apps = mutableListOf<App>()
        applist = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for(app in applist as MutableList<ApplicationInfo>){
            var app = App(app.packageName, app.publicSourceDir, packageManager.getApplicationIcon(app.packageName))
            apps.add(app)
        }

        val adapter = FeedAdapter(ListApps(apps), baseContext)
        reciclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}