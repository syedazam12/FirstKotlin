@file:Suppress("DEPRECATION")

package com.example.firstkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class CameraActivity2 : AppCompatActivity(), SurfaceHolder.Callback, LocationListener
{

    private var camera: Camera? = null
    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder

    private lateinit var locationManager: LocationManager
    private lateinit var locationTextView: TextView

    private companion object { private const val locationRequestCode = 5000 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity2)

        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)

        surfaceView.setOnClickListener{
            camera?.autoFocus(null)
        }

        locationTextView = findViewById(R.id.liveLocationText)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED )
        {
            requestLocation()
        }
        else
        {
            requestPermissionLocation()
        }

        fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray )
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            if( requestCode == locationRequestCode )
            {
                if( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    requestLocation()
                }
                else
                {

                }
            }
        }
    }

    private fun requestPermissionLocation() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationRequestCode)
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this)
    }

    override fun onLocationChanged(location: Location) {
        runOnUiThread {
            locationTextView.text = "Latitude: ${location.latitude}\nLongitude: ${location.longitude}"
        }
    }

    override fun onProviderEnabled(provider: String) {
        //
    }

    override fun onProviderDisabled(provider: String) {
        //
    }

    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //
    }

    override fun surfaceCreated(holder: SurfaceHolder) { openCamera() }

    override fun surfaceChanged(holder: SurfaceHolder, format:Int, width:Int, height:Int) {
        if( surfaceHolder.surface == null )
        {
            return
        }

        try
        {
            camera?.stopPreview()
        }
        catch (e: Exception)
        {
            e.printStackTrace()
        }

        try
        {
            camera?.setPreviewDisplay(surfaceHolder)
            camera?.startPreview()
        }
        catch( e:Exception )
        {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        releaseCamera()
    }

    private fun openCamera() {
        try
        {
            camera = Camera.open()
            camera?.setDisplayOrientation(90)
        }
        catch( e:Exception )
        {
            e.printStackTrace()
        }
    }

    private fun releaseCamera() {
        camera?.apply {
            stopPreview()
            release()
        }

        camera = null
    }
}