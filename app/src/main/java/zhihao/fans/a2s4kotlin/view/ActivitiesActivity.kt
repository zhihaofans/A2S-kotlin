package zhihao.fans.a2s4kotlin.view

import android.content.ComponentName
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Icon
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.KeyEvent
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import dev.utils.app.ActivityUtils
import dev.utils.app.AppUtils
import dev.utils.app.IntentUtils
import kotlinx.android.synthetic.main.activity_activities.*
import kotlinx.android.synthetic.main.content_activities.*
import org.jetbrains.anko.toast
import zhihao.fans.a2s4kotlin.R
import zhihao.fans.a2s4kotlin.kotlinEx.string
import zhihao.fans.a2s4kotlin.kotlinEx.toBitmap
import zhihao.fans.a2s4kotlin.util.AppShortcutsUtil
import zhihao.fans.a2s4kotlin.util.AppUtil
import zhihao.fans.a2s4kotlin.util.QMUIUtil


class ActivitiesActivity : AppCompatActivity() {
    private val mContext = this@ActivitiesActivity
    private val appShortcutsUtil = AppShortcutsUtil(mContext)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)
        setSupportActionBar(toolbar_activities)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        try {
            init()
        } catch (e: Exception) {
            e.printStackTrace()
            toast("初始化失败")
            finish()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return false
    }

    private fun init() {
        if (intent.extras !== null) {
            val mApplicationInfo = intent.extras!!.get("applicationInfo") as ApplicationInfo?
            if (mApplicationInfo == null) {
                    QMUIUtil.alertOneButton(this@ActivitiesActivity,
                        "初始化错误",
                        "接收到null数据",
                        "返回",
                        QMUIDialogAction.ActionListener { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        })
            } else {
                //Logger.d("mApplicationInfo:" + mApplicationInfo.packageName)
                initList(mApplicationInfo)
            }
        } else {
            toast("intent.extras == null")
            finish()
        }
    }

    private fun initList(mApplicationInfo: ApplicationInfo) {
        val tipDialog = QMUIUtil.tipAlertLoading(mContext, "正在加载")
        val activities = AppUtil.getAppActivity(mContext, mApplicationInfo.packageName)
        if (activities == null) {
            tipDialog.dismiss()
            QMUIUtil.alertOneButton(this@ActivitiesActivity,
                "初始化错误",
                "接收到null数据",
                "返回",
                QMUIDialogAction.ActionListener { dialog, _ ->
                    dialog.dismiss()
                    finish()
                })
        } else {
            val size = QMUIDisplayHelper.dp2px(this, 20)
            val section = QMUIGroupListView.newSection(mContext)
            section.apply {
                setTitle("应用列表")
                setDescription("下面没了")
                setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                activities.map { mActivity ->
                    mActivity.isEnabled
                    val mClassName = mActivity.name
                    if (ActivityUtils.isActivityExists(
                            mContext,
                            mApplicationInfo.packageName,
                            mClassName
                        ) && mActivity.isEnabled && mActivity.exported
                    ) {
                        val activityIcon =
                            mContext.packageManager.getActivityIcon(
                                ComponentName(
                                    mApplicationInfo.packageName,
                                    mClassName
                                )
                            )
                        Logger.d(activityIcon)
                        val activityTitle = mActivity.loadLabel(packageManager).toString()
                        val addText = if (activityTitle.isEmpty()) "" else " ($activityTitle)"
                        addItemView(
                            QMUIUtil.getQmuiCommonListItemView(
                                mContext,
                                groupListView_activities,
                                mClassName + addText,
                                null,
                                AppUtils.getAppIcon(mApplicationInfo.packageName)
                                /*
                                if (activityIcon == 0) {
                                    AppUtils.getAppIcon(mApplicationInfo.packageName)
                                } else {
                                    mContext.getDrawable(activityIcon)
                                }*/
                            )
                        ) {
                            val intent =
                                IntentUtils.getComponentIntent(mApplicationInfo.packageName, mClassName, true)
                            if (IntentUtils.isIntentAvailable(intent)) {
                                val builder = QMUIDialog.EditTextDialogBuilder(mContext)
                                builder.setTitle(mClassName)
                                    .setPlaceholder("在此输入快捷方式名称")
                                    .setInputType(InputType.TYPE_CLASS_TEXT)
                                    .setDefaultText(activityTitle)
                                    .addAction("确定") { mDialog, _ ->
                                        val text = builder.editText.text.string()
                                        when {
                                            text.isNullOrEmpty() -> toast("必须输入内容")
                                            text.length > 10 -> toast("快捷方式名称最长为10个字")
                                            else -> {
                                                mDialog.dismiss()
                                                val icon =
                                                    Icon.createWithBitmap(activityIcon.toBitmap())
                                                try {
                                                    val addResult = appShortcutsUtil.addShortcut(
                                                        mClassName,
                                                        text,
                                                        text,
                                                        icon,
                                                        intent
                                                    )
                                                    if (addResult) {
                                                        val successTipDialog =
                                                            QMUIUtil.tipAlertSuccess(
                                                                mContext,
                                                                "添加成功",
                                                                true
                                                            )
                                                        successTipDialog.show()
                                                        groupListView_activities.postDelayed(
                                                            { successTipDialog.dismiss() },
                                                            1000
                                                        )
                                                    } else {
                                                        val failedTipDialog =
                                                            QMUIUtil.tipAlertFail(
                                                                mContext,
                                                                "添加失败",
                                                                true
                                                            )
                                                        failedTipDialog.show()
                                                        groupListView_activities.postDelayed(
                                                            { failedTipDialog.dismiss() },
                                                            1000
                                                        )
                                                    }
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                    val failedTipDialog =
                                                        QMUIUtil.tipAlertFail(
                                                            mContext,
                                                            "添加失败",
                                                            true
                                                        )
                                                    failedTipDialog.show()
                                                    groupListView_activities.postDelayed(
                                                        { failedTipDialog.dismiss() },
                                                        1000
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    .create(QMUIUtil.mCurrentDialogStyle).show()

                            } else {
                                QMUIUtil.alertOneButton(this@ActivitiesActivity,
                                    "添加失败",
                                    "该活动不允许启动",
                                    "好的",
                                    QMUIDialogAction.ActionListener { dialog, _ ->
                                        dialog.dismiss()
                                    })
                            }
                        }
                    }
                    mActivity
                }
            }
            section.addTo(groupListView_activities)
            tipDialog.dismiss()

        }
    }
}
