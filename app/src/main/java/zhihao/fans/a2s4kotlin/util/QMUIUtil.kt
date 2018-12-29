package zhihao.fans.a2s4kotlin.util

import android.content.Context
import android.graphics.drawable.Drawable
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import zhihao.fans.a2s4kotlin.R


/**

 * @author: zhihaofans

 * @date: 2018-12-17 10:06

 */
class QMUIUtil {
    companion object {
        const val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog

        fun getQmuiCommonListItemView(
            context: Context,
            mGroupListView: QMUIGroupListView,
            title: String,
            detailText: String? = null,
            icon: Drawable?
        ): QMUICommonListItemView = mGroupListView.createItemView(
            icon ?: context.getDrawable(R.mipmap.ic_launcher),
            title,
            detailText,
            QMUICommonListItemView.HORIZONTAL,
            QMUICommonListItemView.ACCESSORY_TYPE_NONE
        )
        fun alertOneButton(
            context: Context, title: String, message: String, buttonText: String,
            listener: QMUIDialogAction.ActionListener
        ) {
            // 一个按钮
            QMUIDialog.MessageDialogBuilder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .addAction(buttonText, listener)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(mCurrentDialogStyle).show()
        }

        fun alertTwoButton(
            context: Context, title: String, message: String, buttonOneText: String,
            listenerOne: QMUIDialogAction.ActionListener, buttonTwoText: String,
            listenerTwo: QMUIDialogAction.ActionListener
        ) {
            // 两个按钮
            QMUIDialog.MessageDialogBuilder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .addAction(buttonOneText, listenerOne)
                .addAction(buttonTwoText, listenerTwo)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(mCurrentDialogStyle).show()
        }

        private fun tipAlert(
            context: Context,
            iconType: Int?,
            message: String?,
            cancelable: Boolean = false
        ): QMUITipDialog {
            return QMUITipDialog.Builder(context).apply {
                if (iconType !== null) setIconType(iconType)
                if (message !== null) setTipWord(message)
            }.create(cancelable)


        }

        fun tipAlertLoading(context: Context, message: String, cancelable: Boolean = false): QMUITipDialog {
            return tipAlert(context, QMUITipDialog.Builder.ICON_TYPE_LOADING, message, cancelable)
        }

        fun tipAlertSuccess(context: Context, message: String, cancelable: Boolean = false): QMUITipDialog {
            return tipAlert(context, QMUITipDialog.Builder.ICON_TYPE_SUCCESS, message, cancelable)
        }

        fun tipAlertFail(context: Context, message: String, cancelable: Boolean = false): QMUITipDialog {
            return tipAlert(context, QMUITipDialog.Builder.ICON_TYPE_FAIL, message, cancelable)
        }

        fun tipAlertInfo(context: Context, message: String, cancelable: Boolean = false): QMUITipDialog {
            return tipAlert(context, QMUITipDialog.Builder.ICON_TYPE_INFO, message, cancelable)
        }

        fun tipAlertSuccessNoText(context: Context, cancelable: Boolean = false): QMUITipDialog {
            return tipAlert(context, QMUITipDialog.Builder.ICON_TYPE_SUCCESS, null, cancelable)
        }

        fun tipAlertOnlyText(context: Context, message: String, cancelable: Boolean = false): QMUITipDialog {
            return tipAlert(context, null, message, cancelable)
        }

    }
}