package com.dave.getdirapk

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_app_privacy.*

class AppPrivacyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_privacy)
        var intent = getIntent().extras
        //Toast.makeText(this, intent!!.getString("package_name"), Toast.LENGTH_LONG).show()
        var package_name = intent!!.getString("package_name")
        var app = packageManager.getApplicationInfo(package_name, 0)
        icon_app_privacy.setImageDrawable(packageManager.getApplicationIcon(package_name))
        idPackageNamePrivacy.text = package_name
        textViewprivacyLevel.text = textViewprivacyLevel.text.toString() + "\nActual: LOW"
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when(seekBar!!.progress){
                    0 -> textViewprivacyLevel.text = "Select Privacy Level\nActual: NONE"
                    1 -> textViewprivacyLevel.text = "Select Privacy Level\nActual: LOW"
                    2 -> textViewprivacyLevel.text = "Select Privacy Level\nActual: MEDIUM"
                    3 -> textViewprivacyLevel.text = "Select Privacy Level\nActual: HIGH"

                }
            }

        })

    }
}