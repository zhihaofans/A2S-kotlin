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
            val mR = dynamicShortcuts.map { shortcutsItem ->
                AppShortcutItem(
                    shortcutsItem.id, shortcutsItem.shortLabel.toString(), shortcutsItem.longLabel.toString(),
                    getAppShortcutIcon(shortcutsItem), shortcutsItem.intent, shortcutsItem
                )
            }.toList()
            mR
        } catch (e: Exception) {
            null
        }

    }
    fun addShortcut(id: String, shortLabel: String, longLabel: String, icon: Icon, intent: Intent): Boolean {
        return try {
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
            addShortcuts(listOf(shortcut))
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun addShortcuts(shortcuts: List<ShortcutInfo>): Boolean {
        val shortcutManager = mContext.getSystemService<ShortcutManager>(ShortcutManager::class.java)
        val shortcutsMax = shortcutManager.maxShortcutCountPerActivity
        return try {
            val shortcutsList = getAppShortcuts()
            if (shortcutsList.isNullOrEmpty()) {
                shortcutManager.addDynamicShortcuts(shortcuts)
            } else {
                if (shortcutsList.size == shortcutsMax || (shortcutsList.size + shortcuts.size > shortcutsMax)) return false
                val newList = mutableListOf<ShortcutInfo>()
                shortcutsList.map {
                    newList.add(it.shortcutInfo)
                }
                shortcuts.map {
                    newList.add(it)
                }
                if (newList.size > shortcutsMax) return false
                shortcutManager.addDynamicShortcuts(shortcuts)
            }
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