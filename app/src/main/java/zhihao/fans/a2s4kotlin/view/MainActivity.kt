package zhihao.fans.a2s4kotlin.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import dev.utils.app.logger.DevLogger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import zhihao.fans.a2s4kotlin.R
import zhihao.fans.a2s4kotlin.kotlinEx.getAppIconDrawable
import zhihao.fans.a2s4kotlin.kotlinEx.getAppName
import zhihao.fans.a2s4kotlin.util.AppUtil
import zhihao.fans.a2s4kotlin.util.QMUIUtil


class MainActivity : AppCompatActivity() {

    private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        DevLogger.d("init")
        try {
            //init()
        } catch (e: Exception) {
            e.printStackTrace()
            DevLogger.e("init failed")
            QMUIUtil.alertOneButton(
                this, "错误", "初始化失败", "退出",
                QMUIDialogAction.ActionListener { dialog, _ ->
                    dialog.dismiss()
                    finish()
                })
        }
        fab_main.setOnClickListener { view ->
            startActivity<AppActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        val size = QMUIDisplayHelper.dp2px(this, 20)
        val mContext = this@MainActivity
        val tipDialog = QMUITipDialog.Builder(mContext)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord("正在加载")
            .create()
        tipDialog.show()
        doAsync {
            val appList = AppUtil.getAppList()
            if (appList == null) {
                uiThread {
                    QMUIUtil.alertOneButton(
                        mContext, "错误", "获取应用列表失败", "OK",
                        QMUIDialogAction.ActionListener { dialog, _ ->
                            dialog.dismiss()
                        })
                }
            } else {
                val section = QMUIGroupListView.newSection(mContext)
                section.apply {
                    setTitle("应用列表")
                    setDescription("下面没了")
                    setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                    appList.map { applicationInfo ->
                        addItemView(
                            getQmuiCommonListItemView(
                                groupListView_main,
                                applicationInfo.getAppName() ?: "(获取应用名称失败)",
                                applicationInfo.packageName,
                                applicationInfo.getAppIconDrawable()
                            )
                        ) {
                            QMUIUtil.alertOneButton(
                                mContext,
                                applicationInfo.packageName,
                                "应用名称：" + applicationInfo.getAppName(),
                                "OK",
                                QMUIDialogAction.ActionListener { dialog, _ ->
                                    dialog.dismiss()
                                })
                        }
                    }
                }
                uiThread {
                    section.addTo(groupListView_main)
                    tipDialog.dismiss()
                }
            }
        }
    }

    private fun getQmuiCommonListItemView(
        mGroupListView: QMUIGroupListView,
        title: String,
        detailText: String? = null,
        icon: Drawable?
    ): QMUICommonListItemView = mGroupListView.createItemView(
        icon ?: getDrawable(R.mipmap.ic_launcher),
        title,
        detailText,
        QMUICommonListItemView.HORIZONTAL,
        QMUICommonListItemView.ACCESSORY_TYPE_NONE
    )

}
