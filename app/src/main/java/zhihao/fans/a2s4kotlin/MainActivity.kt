package zhihao.fans.a2s4kotlin

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action") {
                    QMUIDialog.MessageDialogBuilder(this)
                        .setTitle("标题")
                        .setMessage("确定要发送吗？")
                        .addAction("取消") { dialog, index -> dialog.dismiss() }
                        .addAction("你好") { dialog, index ->
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
}
