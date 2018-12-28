package zhihao.fans.a2s4kotlin.util

import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.util.DisplayMetrics

/**

 * @author: zhihaofans

 * @date: 2018-12-20 11:01

 */
class AppShortcutsUtil(context: Context) {
    private val mContext = context
    fun getAppShortcuts() {
        val shortcutManager = mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java)
        shortcutManager.dynamicShortcuts.map {

        }
    }
    fun addShortcut(id: String, shortLabel: String, longLabel: String, icon: Icon, intent: Intent): Boolean {
        val shortcut = ShortcutInfo.Builder(mContext, id)
            .setShortLabel(shortLabel)
            .setLongLabel(longLabel)
            //.setIcon(Icon.createWithResource(mContext, R.drawable.icon_website))
            .setIcon(icon)
            /*.setIntent(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.mysite.example.com/")
                )
            )*/
            .setIntent(intent)
            .build()
        return addShortcuts(listOf(shortcut))
    }

    fun addShortcuts(shortcuts: List<ShortcutInfo>): Boolean {
        return mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java)!!.addDynamicShortcuts(shortcuts)
    }

    fun removeAll() {
        mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java).removeAllDynamicShortcuts()
    }

    fun getAppShortcutIcon(shortcutInfo: ShortcutInfo): Drawable? {
        val launcherApps = mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
        return try {
            launcherApps.getShortcutIconDrawable(shortcutInfo, DisplayMetrics.DENSITY_DEFAULT)
        } catch (e: IllegalStateException) {
            null
        }
    }
}

data class AppShortcutItem(
    val id: String,
    val shortLabel: String,
    val longLabel: String,
    val icon: Drawable,
    val intent: Intent
)