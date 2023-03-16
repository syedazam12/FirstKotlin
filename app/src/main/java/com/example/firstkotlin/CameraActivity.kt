@file:Suppress("DEPRECATION")

package com.example.firstkotlin

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
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
//    private var mediaRecorder: MediaRecorder? = null
//    private var filePath: String? = null

    private lateinit var recordVideoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)

        recordVideoButton = findViewById(R.id.RecordVideo)
        recordVideoButton.setOnClickListener { launchCamera() }

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

//    companion object {
//        private const val CAMERA_REQUEST_CODE = 100
//    }

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
            {
                val videoUri = data?.data

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    val values = ContentValues().apply {
                        put(MediaStore.Video.Media.LATITUDE, location?.latitude)
                        put(MediaStore.Video.Media.LONGITUDE, location?.longitude)
                    }

                    contentResolver.update(videoUri!!, values, null, null)

                    Toast.makeText(this, "Video saved to $videoUri", Toast.LENGTH_LONG).show()
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == videoRequestCode && resultCode == RESULT_OK )
            }
            else
            {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
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