package constant;

import android.app.ProgressDialog;
import android.content.Context;


public class AppUtils {
	static ProgressDialog progressDialog;

	public static  void generateWaitingDialog(String message, Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	
	
	public static void stopWaitingDialog() {
		if (progressDialog != null
				&& progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
}