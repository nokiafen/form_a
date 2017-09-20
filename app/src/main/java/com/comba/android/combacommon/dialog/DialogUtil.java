package com.comba.android.combacommon.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;

import com.comba.android.combacommon.R;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

/**
 * Created by chenhailin on 2017/6/2.
 */

public class DialogUtil {
    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public static void showMessageDialog(CharSequence title, int message , Context ctx) {
        showMessageDialog(title, message,ctx);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public  static  void showMessageDialog(CharSequence title, CharSequence message,Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.know, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 显示图片dialog。
     *
     * @param title  标题。
     * @param bitmap 图片。
     */
    public static ImageDialog showImageDialog(CharSequence title, Bitmap bitmap , Context ctx) {
        ImageDialog imageDialog = new ImageDialog(ctx);
        imageDialog.setTitle(title);
        imageDialog.setImage(bitmap);
        imageDialog.show();
        return  imageDialog;
    }

    public  static  WaitDialog showWaitingDialog(Context context,String tip){
        WaitDialog waitDialog=new WaitDialog(context,tip);
        waitDialog.show();
        return waitDialog;
    }

    public  static void iosAlertDialog(Context context,String title, String tip,MyDialogListener Listener){
        StyledDialog.buildIosAlert(title, tip,Listener).show();
    }
}
