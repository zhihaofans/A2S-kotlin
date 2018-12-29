package zhihao.fans.a2s4kotlin.util

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon

/**

 * @author: zhihaofans

 * @date: 2018-12-20 11:01

 */
class AppShortcutsUtil(context: Context) {
    private val mContext = context
    fun getAppShortcuts(): List<AppShortcutItem>? {
        val shortcutManager = mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java)
        return try {
            val dynamicShortcuts = shortcutManager.dynamicShortcuts
            dynamicShortcuts.map { shortcutsItem ->
                AppShortcutItem(
                    shortcutsItem.id, shortcutsItem.shortLabel.toString(), shortcutsItem.longLabel.toString(),
                    getAppShortcutIcon(shortcutsItem), shortcutsItem.intent, shortcutsItem
                )
            }.toList()
        } catch (e: IllegalStateException) {
            null
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

    private fun addShortcuts(shortcuts: List<ShortcutInfo>): Boolean {
        return try {
            mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java)!!.addDynamicShortcuts(shortcuts)
        } catch (e: Exception) {
            false
        }
    }

    fun remove(shortcutId: String) {
        mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java)!!.removeDynamicShortcuts(
            listOf(
                shortcutId
            )
        )
    }
    fun removeAll() {
        mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java).removeAllDynamicShortcuts()
    }

    private fun getAppShortcutIcon(shortcutInfo: ShortcutInfo): Drawable? {
        return try {
            mContext.packageManager.getActivityIcon(shortcutInfo.intent.component)
        } catch (e: Exception) {
            null
        }
    }
}

data class AppShortcutItem(
    val id: String,
    val shortLabel: String,
    val longLabel: String,
    val icon: Drawable?,
    val intent: Intent,
    val shortcutInfo: ShortcutInfo
)