package zhihao.fans.a2s4kotlin.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
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
        fun getAppList(sortType: String = APP_SORT_TYPE_APP_NAME): List<ApplicationInfo>? {
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
    }
}