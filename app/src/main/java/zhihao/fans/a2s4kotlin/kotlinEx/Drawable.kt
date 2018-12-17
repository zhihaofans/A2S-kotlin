package zhihao.fans.a2s4kotlin.kotlinEx

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**

 * @author: zhihaofans

 * @date: 2018-12-17 10:31

 */
fun Drawable.toBitmap(): Bitmap? {
    return (this as BitmapDrawable).bitmap
}