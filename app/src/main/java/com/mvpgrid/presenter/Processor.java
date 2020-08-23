package com.mvpgrid.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.mvpgrid.R;
import com.mvpgrid.listener.LoaderListener;


/**
 * For showing processor
 */
public class Processor implements LoaderListener {
    private final Context _context;
    private ProgressDialog progressDialog;

    public Processor(Context context) {
        this._context = context;
    }

    @Override
    public void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(_context, R.style.AppCompatAlertDialogStyle);
            progressDialog.show();
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            if (progressDialog.getWindow() != null)
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.setContentView(R.layout.progressdialog);
        } else {
            progressDialog.show();
            progressDialog.setIndeterminate(true);
        }
    }

    @Override
    public void hideDialog() {
        try {
            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (final Exception e) {
            // Handle or log or ignore
            Log.e("Exception : ", Log.getStackTraceString(e));
        } finally {
            progressDialog = null;
        }
    }
}
