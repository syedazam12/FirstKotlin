@file:Suppress("DEPRECATION")

package com.example.firstkotlin

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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
    private fun launchCamera() {
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intentCamera, CAMERA_REQUEST_CODE)
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 100
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