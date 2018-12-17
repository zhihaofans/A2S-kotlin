package zhihao.fans.a2s4kotlin.util

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction

/**

 * @author: zhihaofans

 * @date: 2018-12-17 10:06

 */
class QMUIUtil {
    companion object {
        private const val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
        fun alertOneButton(
            context: Context, title: String, message: String, buttonText: String,
            listener: QMUIDialogAction.ActionListener
        ) {
            // 一个按钮
            QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .addAction(buttonText, listener)
                .create(mCurrentDialogStyle).show()
        }

        fun alertTwoButton(
            context: Context, title: String, message: String, buttonOneText: String,
            listenerOne: QMUIDialogAction.ActionListener, buttonTwoText: String,
            listenerTwo: QMUIDialogAction.ActionListener
        ) {
            // 两个按钮
            QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .addAction(buttonOneText, listenerOne)
                .addAction(buttonTwoText, listenerTwo)
                .create(mCurrentDialogStyle).show()
        }


    }
}