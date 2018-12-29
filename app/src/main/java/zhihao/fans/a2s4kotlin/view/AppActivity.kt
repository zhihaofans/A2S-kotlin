package zhihao.fans.a2s4kotlin.view

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.content_app.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import zhihao.fans.a2s4kotlin.R
import zhihao.fans.a2s4kotlin.kotlinEx.getAppIconDrawable
import zhihao.fans.a2s4kotlin.kotlinEx.getAppName
import zhihao.fans.a2s4kotlin.util.AppUtil
import zhihao.fans.a2s4kotlin.util.QMUIUtil


class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setSupportActionBar(toolbar_app)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        try {
            init()
        } catch (e: Exception) {
            e.printStackTrace()
            QMUIUtil.alertOneButton(this, "错误", "初始化失败", "OK",
                QMUIDialogAction.ActionListener { dialog, _ ->
                    dialog.dismiss()
                })
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return false
    }

    private fun init() {
        val size = QMUIDisplayHelper.dp2px(this, 20)
        val mContext = this@AppActivity
        val tipDialog = QMUIUtil.tipAlertLoading(mContext, "正在加载")
        tipDialog.show()
        doAsync {
            val appList = AppUtil.getAppListWithoutNotEnter(this@AppActivity)
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
                    appList.map { mApplicationInfo ->
                        addItemView(
                            getQmuiCommonListItemView(
                                groupListView_app,
                                mApplicationInfo.getAppName() ?: "(获取应用名称失败)",
                                mApplicationInfo.packageName,
                                mApplicationInfo.getAppIconDrawable()
                            )
                        ) {
                            Logger.d(mApplicationInfo.packageName)
                            val activities =
                                AppUtil.getAppActivity(this@AppActivity, mApplicationInfo.packageName)
                            if (activities.isNullOrEmpty()) {
                                val failTipDialog = QMUIUtil.tipAlertFail(mContext, "该应用没有活动(Activity)可以使用", true)
                                failTipDialog.show()
                                groupListView_app.postDelayed({ failTipDialog.dismiss() }, 2000)
                            } else {
                                addAppShortcuts(mApplicationInfo)

                            }
                        }
                    }
                }
                uiThread {
                    section.addTo(groupListView_app)
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

    private fun addAppShortcuts(mApplicationInfo: ApplicationInfo) {
        //appShortcutsUtil.addShortcut()
        startActivity<ActivitiesActivity>("applicationInfo" to mApplicationInfo)
    }
}
