package com.example.tiktokandroid.core.utils

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build


/**
 * Created by Puskal Khadka on 3/16/2023.
 */
object FileUtils {

    fun extractThumbnailFromAsset(context: Context, assetPath: String, atTime: Int): Bitmap? {
        return try {
            context.assets.openFd(assetPath).use { afd ->
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                val bitmap = getFrame(retriever, atTime)
                retriever.release()
                bitmap
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun extractThumbnailFromUrl(url: String, atTime: Int): Bitmap? {
        return try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(url, HashMap()) // For remote URLs
            val bitmap = getFrame(retriever, atTime)
            retriever.release()
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFrame(retriever: MediaMetadataRetriever, atTime: Int): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            retriever.getScaledFrameAtTime(
                atTime.toLong(),
                MediaMetadataRetriever.OPTION_CLOSEST,
                1280,
                720
            )
        } else {
            retriever.getFrameAtTime(atTime.toLong())
        }
    }}