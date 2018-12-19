package zhihao.fans.a2s4kotlin

import android.app.Application
import dev.DevUtils
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig
import dev.utils.app.logger.LogLevel


/**

 * @author: zhihaofans

 * @date: 2018-12-17 00:13

 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DevUtils.init(this)
        val logConfig = LogConfig()
        logConfig.logLevel = LogLevel.DEBUG
        logConfig.tag = "zhihao.fans.a2s4kotlin.log"
        DevLogger.init(logConfig)
        DevUtils.openLog()
        DevUtils.openDebug()
    }
}