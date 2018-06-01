package nguyenhuu.hoang.powerstationidentifier

import android.support.v7.app.AppCompatActivity
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog

/**
 * Created by Hoang on 6/1/2018.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var loadingDialog: SweetAlertDialog? = null

    fun showLoadingDialog() {
        if (!isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                loadingDialog?.setTitleText(getString(R.string.loading_data))?.setCancelable(false)
            }

            if (!isShowingLoadingDialog()) {
                loadingDialog?.show()
            }
        }
    }

    fun hideLoadingDialog() {
        if (isShowingLoadingDialog()) {
            loadingDialog?.dismiss()
        }
    }

    fun isShowingLoadingDialog(): Boolean {
        return loadingDialog?.isShowing == true
    }
}