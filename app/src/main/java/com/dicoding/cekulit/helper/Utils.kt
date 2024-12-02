package com.dicoding.cekulit.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    private const val MAXIMAL_SIZE = 1000000

    private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


    //Berbeda dengan cara lain yang biasanya membutuhkan Uri untuk menampung gambar,
    //untuk CameraX yang dibutuhkan adalah berupa File.
    fun createCustomTempFile(context: Context): File {
        // file bersifat sementara (temporary) dan disimpan di dalam cache
        //context.externalCacheDir digunakan untuk mendapatkan lokasi file cache,
        //yakni di /sdcard/Android/data/package/cache/
        val filesDir = context.externalCacheDir

        //Kemudian untuk membuat File yang sifatnya sementara,
        //gunakan fungsi File.createTempFile()
        //dengan argument berupa prefix, suffix, dan lokasi penyimpanan.
        return File.createTempFile(timeStamp, ".jpg", filesDir)
        //File ini akan hilang ketika terjadi proses clear cache pada aplikasi.
    }

    fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
    }


    //Proses kompresi ini memang akan sedikit membebani memory karena prosesnya dilakukan di main thread
    //ada baiknya untuk menambahkan indikator selama mengompres dan mengirim file ke server.
    @RequiresApi(Build.VERSION_CODES.Q)
    fun File.reduceFileImage(): File {
        val file = this
        val bitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)
        var compressQuality = 100
        var streamLength: Int

        //Melakukan perulangan untuk mengompres ketika ukurannya lebih dari 1MB.
        //Setiap iterasinya nilai compressQuality akan berkurang 5.
        //Sehingga, ukuran file akan menjadi lebih kecil.
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > MAXIMAL_SIZE)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun Bitmap.getRotatedBitmap(file: File): Bitmap? {
        val orientation = ExifInterface(file).getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90F)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180F)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270F)
            ExifInterface.ORIENTATION_NORMAL -> this
            else -> this
        }
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }
}