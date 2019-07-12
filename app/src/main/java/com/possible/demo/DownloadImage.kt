//package com.possible.demo
//
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.os.AsyncTask
//import android.util.Log
//import android.widget.ImageView
//import java.net.URL
//
//internal class DownloadImage(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
//    override fun doInBackground(vararg urls: String): Bitmap? {
//        val imageUrl = urls[0]
//        return try {
//            val inputStream = URL(imageUrl).openStream()
//            BitmapFactory.decodeStream(inputStream)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    override fun onPostExecute(result: Bitmap?) {
//        if (result != null) {
//            //set image to imageView
//            val scaledImage = Bitmap.createScaledBitmap(result, 100, 100, true)
//            imageView.setImageBitmap(scaledImage)
//        } else {
//            Log.e("Error downloading image", "error")
//        }
//    }
//}