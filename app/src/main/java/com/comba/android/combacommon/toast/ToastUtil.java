package com.comba.android.combacommon.toast;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.comba.android.combacommon.R;
import com.comba.android.combacommon.utils.ScreenUtil;


public class ToastUtil {

	private static Toast toast;
	private static String showingText;

	/**
	 * 显示toast，时长为Toast.LENGTH_SHORT
	 * 
	 * @param context
	 *            The context to use. Usually your Application or Activity
	 *            object.
	 * @param text
	 *            The text to show. Can be formatted text.
	 */
	public static void showToastShort(Context context, String text) {
		showToast(context, text, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示toast，时长为Toast.LENGTH_SHORT
	 * 
	 * @param context
	 *            context The context to use. Usually your Application or
	 *            Activity object.
	 * @param resId
	 *            The resource id of the string resource to use. Can be
	 *            formatted text.
	 */
	public static void showToastShort(Context context, int resId) {
		showToast(context, context.getResources().getString(resId),
				Toast.LENGTH_SHORT);
	}

	/**
	 * 显示toast，时长为Toast.LENGTH_LONG
	 * 
	 * @param context
	 *            The context to use. Usually your Application or Activity
	 *            object.
	 * @param text
	 *            The text to show. Can be formatted text.
	 */
	public static void showToastLong(Context context, String text) {
		showToast(context, text, Toast.LENGTH_LONG);
	}

	/**
	 * 显示toast，时长为Toast.LENGTH_LONG
	 * 
	 * @param context
	 *            context The context to use. Usually your Application or
	 *            Activity object.
	 * @param resId
	 *            The resource id of the string resource to use. Can be
	 *            formatted text.
	 */
	public static void showToastLong(Context context, int resId) {
		showToast(context, context.getResources().getString(resId),
				Toast.LENGTH_LONG);
	}

	/**
	 * 显示一个toast，在这个toast没有完全消失之前，不会再显示同样的toast
	 * 
	 * @param context
	 *            context The context to use. Usually your Application or
	 *            Activity object.
	 * @param text
	 *            text The text to show. Can be formatted text.
	 * @param duration
	 *            {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
	 */
	private static void showToast(Context context, String text, int duration) {
		if (text != null && !text.equals(showingText)) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			toast.show();
			toast.getView().setAlpha(0.5F);
			showingText = text;
			// 启动计时器，当toast消失后，将showingText置为null
			if (duration == Toast.LENGTH_SHORT) {
				newCountDownTimer(2000);
			} else if (duration == Toast.LENGTH_LONG) {
				newCountDownTimer(3500);
			}
		}
	}

	private static void newCountDownTimer(int time) {
		new CountDownTimer(time, time) {

			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				showingText = null;
			}
		}.start();
	}

	/**
	 * cancel the toast </br> you can use in Activity's onDestory method
	 * 
	 */
	public static void cancelToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

	/**
	 * A Toast with an alert picture.
	 * 
	 * @param msg
	 *            a string to show in Toast.
	 */
	static int dimen = ScreenUtil.dip2px(160);

	public static void showToastWithAlertPic(Context context,String msg) {
		if (!TextUtils.isEmpty(msg) && !msg.equals(showingText)) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			View toastView = View.inflate(context,R.layout.toast_custom_view, null);
			LinearLayout llToastView = (LinearLayout) toastView.findViewById(R.id.llToastView);
			if (msg.length() <= 6) {
				LayoutParams params = (LayoutParams) llToastView.getLayoutParams();
				params.width = dimen;
				params.height = dimen;
				llToastView.setLayoutParams(params);
			} else {
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, dimen);
				llToastView.setLayoutParams(params);
			}
			((TextView) toastView.findViewById(R.id.tvToastText)).setText(msg);
			((ImageView) toastView.findViewById(R.id.ivToastIcon)).setImageResource(R.drawable.ic_tip_alert);
			toast.setView(toastView);
			toast.show();
			showingText = msg;
			newCountDownTimer(2000);
		}
	}

	/**
	 * A Toast with an alert picture.
	 *
	 * @param resId
	 *            the resource id of a string to show.
	 */
	public static void showToastWithAlertPic(Context context,int resId) {
		showToastWithAlertPic(context, context.getResources().getString(resId));
	}

	/**
	 * A Toast with an OK picture.
	 *
	 * @param msg
	 *            the string to show in Toast.
	 */
	public static void showToastWithOkPic(Context context,String msg) {
		if (!TextUtils.isEmpty(msg) && !msg.equals(showingText)) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			View toastView = View.inflate(context,R.layout.toast_custom_view, null);
			LinearLayout llToastView = (LinearLayout) toastView.findViewById(R.id.llToastView);
			if (msg.length() <= 6) {
				LayoutParams params = (LayoutParams) llToastView.getLayoutParams();
				params.width = dimen;
				params.height = dimen;
				llToastView.setLayoutParams(params);
			} else {
				LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, dimen);
				llToastView.setLayoutParams(params);
			}
			((TextView) toastView.findViewById(R.id.tvToastText)).setText(msg);
			((ImageView) toastView.findViewById(R.id.ivToastIcon)).setImageResource(R.drawable.ic_tip_ok);
			toast.setView(toastView);
			toast.show();
			showingText = msg;
			newCountDownTimer(2000);
		}
	}

	/**
	 * A Toast with an OK picture.
	 *
	 * @param resId
	 *            the resource id of a string to show.
	 */
	public static void showToastWithOkPic(Context context,int resId) {
		showToastWithOkPic(context, context.getResources().getString(resId));
	}
}
