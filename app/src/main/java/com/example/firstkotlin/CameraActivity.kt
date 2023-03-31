@file:Suppress("DEPRECATION")

package com.example.firstkotlin

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class CameraActivity  : AppCompatActivity()
{
    private lateinit var recordVideoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        recordVideoButton = findViewById(R.id.RecordVideo)
        recordVideoButton.setOnClickListener { launchCamera() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), cameraPermissionCode)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), locationPermissionCode)
        }

//        recordVideoButton.setOnClickListener{
//            if (mediaRecorder == null)
//            {
//                startRecording()
//            }
//            else
//            {
//                stopRecording()
//            }
//        }
    }

    private val cameraPermissionCode = 100
    private val videoRequestCode = 300
    private val locationPermissionCode = 500
    private fun launchCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), cameraPermissionCode)
        }
        else
        {
            val intentCamera = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intentCamera, videoRequestCode)
        }    
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraPermissionCode)
        {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                launchCamera()
            }

            else {
                Toast.makeText(this, "Camera and microphone permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == videoRequestCode && resultCode == RESULT_OK ) {
//            val imageBitmap = data?.extras?.get("data") as Bitmap

//            val better = BitmapFactory.Options()
//            better.inSampleSize = 0.5f.toInt()

//            val pictureDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//            val pictureFile = File.createTempFile( "photo", ".jpg", pictureDirectory )

//            val fos = FileOutputStream(pictureFile)
//            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//            fos.close()

            val videoUri = data?.data

            if (videoUri != null) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    if (location != null) {
                        saveVideoLocation(this, videoUri, location)
                        Toast.makeText(this, "Video saved to $videoUri", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), locationPermissionCode)
                }
            }
            else {
                Toast.makeText(this, "Failed to save video", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveVideoLocation(context: Context, videoUri: Uri, location: Location) {
        val values =  ContentValues().apply {
            put(MediaStore.Video.Media.LATITUDE, location.latitude)
            put(MediaStore.Video.Media.LONGITUDE, location.longitude)
        }

        context.contentResolver.update(videoUri, values, null, null)
    }
}

//private fun startRecording() {
//    var mediaRecorder = MediaRecorder.apply {
//        setAudioSource(MediaRecorder.AudioSource.MIC)
//        setVideoSource(MediaRecorder.VideoSource.CAMERA)
//        setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//        setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//        setOutputFile(getOutputFile().absolutePath)
//
//        try {
//            prepare()
//            start()
//
//            Toast.makeText(this@CameraActivity, "Recording started", Toast.LENGTH_SHORT).show()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//}
//
//private fun stopRecording() {
//    mediaRecorder?.apply {
//        stop()
//        reset()
//        release()
//        mediaRecorder = null
//
//        Toast.makeTest(this@CameraActivity, "Recording stopped", Toast.LENGTH_SHORT).show()
//    }
//}
//
//private fun getOutputFile(): File {
//    val mediaStorageDir = File(
//        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "VideoRecordingApp"
//    )
//
//    if (!mediaStorageDir.exists() ) {
//        mediaStorageDir.mkdirs()
//    }
//
//    filePath = "${mediaStorageDir.path}/${System.currentTimeMillis()}.mp4"
//
//    return File(filePath!!)
//}