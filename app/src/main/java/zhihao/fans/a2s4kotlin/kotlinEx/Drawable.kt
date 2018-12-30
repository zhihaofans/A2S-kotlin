package zhihao.fans.a2s4kotlin.kotlinEx

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**

 * @author: zhihaofans

 * @date: 2018-12-17 10:31

 */
fun Drawable.toBitmap(): Bitmap? {
    val mBitmap = try {
        (this as BitmapDrawable).bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        this.toBitmap2()
    }
    return mBitmap
}

fun Drawable.toBitmap2(): Bitmap? {
    return try {
        val w = this.intrinsicWidth
        val h = this.intrinsicHeight
        Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}