package com.example.locationupdates

import android.content.Intent
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.grumpyshoe.module.locationmanager.LocationManager
import com.grumpyshoe.module.locationmanager.impl.LocationManagerImpl
import com.grumpyshoe.module.locationmanager.models.LocationTrackerConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val locationManager : LocationManager = LocationManagerImpl()
    lateinit var mLocation:Location
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        locationManager.getLastKnownPosition(
            activity = this,
            onLastLocationFound = { location ->
                // handle location data
                this.mLocation=location
                tv_magdy.text=mLocation.latitude.toString()
         //       tv_location.text=location.latitude.toString()
            },
            onNoLocationFound = {
                // handle no location data
          //      tv_location.text="no location"

            })
        locationManager.startLocationTracker(
            activity = this ,
            onLocationChange = { location ->
                // handle location data
                tv_magdy.text=location.latitude.toString()

            },
            config = LocationTrackerConfig()
        )
     btn_next.setOnClickListener {
         var intent=Intent(this,MapsActivity::class.java)
         intent.putExtra("location",mLocation)
         startActivity(intent)}
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.stopLocationTracker()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
            ?: super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}
