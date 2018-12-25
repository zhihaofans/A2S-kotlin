package zhihao.fans.a2s4kotlin.kotlinEx

import android.text.Editable

/**

 * @author: zhihaofans

 * @date: 2018-12-20 16:40

 */
fun Editable?.string(): String? = if (this == null) null else this.toString()