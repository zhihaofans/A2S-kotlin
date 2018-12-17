package zhihao.fans.a2s4kotlin.kotlinEx

import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import dev.utils.app.AppUtils
import dev.utils.app.AppUtils.getPackageManager


/**

 * @author: zhihaofans

 * @date: 2018-12-17 10:27

 */
fun ApplicationInfo.getAppName(): String? {
    return AppUtils.getAppName(this.packageName)
}


fun ApplicationInfo.getAppIconDrawable(): Drawable? {
    return this.loadIcon(getPackageManager())
}

fun ApplicationInfo.getAppIconBitmap(): Bitmap? {
    return this.loadIcon(getPackageManager()).toBitmap()
}