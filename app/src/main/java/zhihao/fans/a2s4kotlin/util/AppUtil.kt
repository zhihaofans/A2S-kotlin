package zhihao.fans.a2s4kotlin.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import dev.utils.app.AppUtils
import dev.utils.app.AppUtils.getPackageManager


/**

 * @author: zhihaofans

 * @date: 2018-12-17 09:05

 */
class AppUtil {
    companion object {
        //val APP_SORT_TYPE_PACKAGE_NAME = "APP_SORT_TYPE_PACKAGE_NAME"
        const val APP_SORT_TYPE_APP_NAME = "APP_SORT_TYPE_APP_NAME"
        const val APP_SORT_TYPE_INSTALL_TIME = "APP_SORT_TYPE_INSTALL_TIME"
        const val APP_SORT_TYPE_UPDATE_TIME = "APP_SORT_TYPE_UPDATE_TIME"
        fun getAppList(
            mContext: Context,
            sortType: String = APP_SORT_TYPE_APP_NAME
        ): List<ApplicationInfo>? {
            val packages: List<ApplicationInfo> =
                getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA)
            return packages.sortedBy {
                when (sortType) {
                    APP_SORT_TYPE_APP_NAME -> AppUtils.getAppName(it.packageName)
                    APP_SORT_TYPE_INSTALL_TIME -> it.packageName
                    APP_SORT_TYPE_UPDATE_TIME -> it.packageName
                    else -> it.packageName
                }
            }
        }

        fun getAppListWithoutNotEnter(
            mContext: Context,
            sortType: String = APP_SORT_TYPE_APP_NAME
        ): List<ApplicationInfo>? {
            val packages: List<ApplicationInfo> =
                getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA)
            val newList = mutableListOf<ApplicationInfo>()
            packages.sortedBy {
                when (sortType) {
                    APP_SORT_TYPE_APP_NAME -> AppUtils.getAppName(it.packageName)
                    APP_SORT_TYPE_INSTALL_TIME -> it.packageName
                    APP_SORT_TYPE_UPDATE_TIME -> it.packageName
                    else -> it.packageName
                }
            }.map {
                if (!AppUtil.getAppActivity(mContext, it.packageName).isNullOrEmpty()) newList.add(it)
            }
            return newList
        }
        fun getAppActivity(context: Context, packageName: String): Array<out ActivityInfo>? {
            val pm = context.packageManager
            val info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            val list = info.activities
            return list
        }

        fun getActivityIcon(mContext: Context, packageName: String, className: String): Drawable {
            return mContext.packageManager.getActivityIcon(ComponentName(packageName, className))
        }
    }
}