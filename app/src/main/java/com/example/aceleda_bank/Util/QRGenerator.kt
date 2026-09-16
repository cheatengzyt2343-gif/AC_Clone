package com.example.aceleda_bank.Util

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

object QRGenerator {

    fun generate(text: String): Bitmap {

        val matrix = QRCodeWriter().encode(
            text,
            BarcodeFormat.QR_CODE,
            512,
            512
        )

        val width = matrix.width
        val height = matrix.height

        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.RGB_565
        )

        for (x in 0 until width) {
            for (y in 0 until height) {

                bitmap.setPixel(
                    x,
                    y,
                    if (matrix[x, y])
                        Color.BLACK
                    else
                        Color.WHITE
                )
            }
        }

        return bitmap
    }
}