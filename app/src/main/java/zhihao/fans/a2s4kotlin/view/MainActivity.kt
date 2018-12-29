package zhihao.fans.a2s4kotlin.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import dev.utils.app.logger.DevLogger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import zhihao.fans.a2s4kotlin.R
import zhihao.fans.a2s4kotlin.util.AppShortcutsUtil
import zhihao.fans.a2s4kotlin.util.QMUIUtil


class MainActivity : AppCompatActivity() {

    //private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
    private val mContext = this@MainActivity
    private var section = QMUIGroupListView.newSection(mContext)
    private val appShortcutsUtil = AppShortcutsUtil(this@MainActivity)
    private var listLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        Logger.d("init")
        tryInit()
        pull2refresh_main.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {

            }

            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                //pull2refresh_main.postDelayed({ pull2refresh_main.finishRefresh() }, 2000)
                init()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.0
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

    private fun tryInit() {
        try {
            init()
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
        fab_main.setOnClickListener {
            startActivity<AppActivity>()
        }
    }

    private fun init() {
        if (listLoaded) section.removeFrom(groupListView_main)
        section = QMUIGroupListView.newSection(mContext)
        val size = QMUIDisplayHelper.dp2px(this, 20)
        val tipDialog = QMUITipDialog.Builder(mContext)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord("正在加载")
            .create()
        tipDialog.show()
        doAsync {
            val appShortcutList = appShortcutsUtil.getAppShortcuts()
            when {
                appShortcutList == null -> uiThread {
                    QMUIUtil.alertOneButton(
                        mContext, "错误", "获取应用列表失败", "退出",
                        QMUIDialogAction.ActionListener { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        })
                }
                appShortcutList.isEmpty() -> {
                    section.apply {
                        setTitle("快捷方式")
                        setDescription("未添加快捷方式")
                        setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                    }
                    uiThread {
                        section.addTo(groupListView_main)
                        tipDialog.dismiss()
                        listLoaded = true
                    }
                }
                else -> {
                    section.apply {
                        setTitle("快捷方式")
                        setDescription("下面没了")
                        setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                        appShortcutList.map { appShortcutItem ->
                            addItemView(
                                QMUIUtil.getQmuiCommonListItemView(
                                    this@MainActivity, groupListView_main,
                                    appShortcutItem.longLabel, null, appShortcutItem.icon
                                )
                            ) {
                                QMUIUtil.alertOneButton(
                                    mContext,
                                    "是否删除",
                                    "长昵称：" + appShortcutItem.longLabel +
                                            "\n短昵称：" + appShortcutItem.shortLabel,
                                    "删除",
                                    QMUIDialogAction.ActionListener { dialog, _ ->
                                        dialog.dismiss()
                                        QMUIUtil.alertTwoButton(
                                            mContext,
                                            "确定删除？",
                                            "长昵称：" + appShortcutItem.longLabel +
                                                    "\n短昵称：" + appShortcutItem.shortLabel,
                                            "否", QMUIDialogAction.ActionListener { dialogA, _ ->
                                                dialogA.dismiss()
                                            },
                                            "是", QMUIDialogAction.ActionListener { dialogB, _ ->
                                                dialogB.dismiss()
                                                appShortcutsUtil.remove(appShortcutItem.id)
                                                init()
                                            })
                                    })
                            }
                        }
                    }
                    uiThread {
                        section.addTo(groupListView_main)
                        tipDialog.dismiss()
                        listLoaded = true
                        pull2refresh_main.finishRefresh()
                    }
                }
            }
        }
    }


}
