package com.dave.getdirapk

import android.app.SearchManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.ApplicationInfo.FLAG_SYSTEM
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var applist: MutableList<ApplicationInfo>? = null
    var searchView: SearchView? = null
    var adapter: AppAdapter? = null

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
            if ((FLAG_SYSTEM and app.flags)==0){
                var app = App(
                    app.packageName,
                    app.publicSourceDir,
                    packageManager.getApplicationIcon(app.packageName),
                    app.name
                )
                apps.add(app)
            }
        }

        adapter = AppAdapter(ListApps(apps), ListApps(apps), baseContext)
        reciclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search)
            .actionView as SearchView
        searchView!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView!!.setMaxWidth(Int.MAX_VALUE)

        // listening to search query text change

        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                adapter!!.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                // filter recycler view when text is changed
                adapter!!.getFilter().filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var  id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
