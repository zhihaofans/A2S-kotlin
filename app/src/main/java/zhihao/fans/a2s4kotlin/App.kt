package zhihao.fans.a2s4kotlin

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dev.DevUtils


/**

 * @author: zhihaofans

 * @date: 2018-12-17 00:13

 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DevUtils.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}