package zhihao.fans.a2s4kotlin

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        this.init()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action") {
                    QMUIDialog.MessageDialogBuilder(this)
                        .setTitle("标题")
                        .setMessage("确定要发送吗？")
                        .addAction("取消") { dialog, index -> dialog.dismiss() }
                        .addAction(0, "你好", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, index ->
                            Toast.makeText(this, "你好", Toast.LENGTH_SHORT).show()
                        }
                        .addAction("确定") { dialog, index ->
                            dialog.dismiss()
                            Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show()
                        }
                        .create(mCurrentDialogStyle).show()
                }.show()

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
        QMUIGroupListView.newSection(this)
            .setTitle("Section 1: 默认提供的样式")
            .setDescription("Section 1 的描述")
            .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
            .addItemView(getQmuiCommonListItemView(this, groupListView, "1", "5", R.mipmap.ic_launcher)) { toast("hi") }
            .addItemView(getQmuiCommonListItemView(this, groupListView, "2", "4", R.mipmap.ic_launcher)) { toast("hi") }
            .addItemView(getQmuiCommonListItemView(this, groupListView, "3", "3", R.mipmap.ic_launcher)) { toast("hi") }
            .addItemView(getQmuiCommonListItemView(this, groupListView, "4", "2", R.mipmap.ic_launcher)) { toast("hi") }
            .addItemView(getQmuiCommonListItemView(this, groupListView, "5", "1", R.mipmap.ic_launcher)) { toast("hi") }
            .addTo(groupListView)
    }

    private fun getQmuiCommonListItemView(
        context: Context,
        mGroupListView: QMUIGroupListView,
        title: String,
        detailText: String? = null,
        icon: Int
    ): QMUICommonListItemView = mGroupListView.createItemView(
        ContextCompat.getDrawable(context, icon),
        title,
        detailText,
        QMUICommonListItemView.HORIZONTAL,
        QMUICommonListItemView.ACCESSORY_TYPE_NONE
    )
}
