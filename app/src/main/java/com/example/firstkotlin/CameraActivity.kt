@file:Suppress("DEPRECATION")

package com.example.firstkotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat



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
//    private val cameraRequestCode = 200
    private val videoRequestCode = 300
    private fun launchCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), cameraPermissionCode)
        }
        
        else {
            val intentCamera = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intentCamera, videoRequestCode)
        }    
    }

//    companion object {
//        private const val CAMERA_REQUEST_CODE = 100
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
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

            Toast.makeText(this, "Video saved to ${videoUri.toString()}", Toast.LENGTH_SHORT).show()
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