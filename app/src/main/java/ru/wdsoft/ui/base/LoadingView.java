package ru.wdsoft.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;

public class LoadingView {

	private static Activity mActivity;
	private static ProgressDialog mProgress;
	private static int mProgressCount = 0;

	public static void setActivity(Activity activity) {
		mActivity = activity;
	}

	public static void startProgress(final String mes){

		if (!mActivity.isFinishing()){
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					new Runnable() {

						@Override
						public void run() {
							resumeProgress(mes);
						}
					}.run();
				}
			});
		}
	}

	public static void resumeProgress(String mess) {

		if (mProgress != null) {
			pauseProgress();
		}

		if (mProgress == null && mProgressCount == 0) {

			mProgress = new ProgressDialog(mActivity);
			mProgress.setMessage(mess);
			mProgress.setIndeterminate(false);
			mProgress.setCancelable(true);

			mProgress.show();

			mProgressCount = 1;
		}

	}

	private static void pauseProgress() {
		try {
			if (mProgress != null && mProgress.isShowing()) {
				mProgress.dismiss();
			}
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			mProgress = null;
			mProgressCount = 0;
		}
	}

	public static void stopProgress() {

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {

				new Runnable() {

					@Override
					public void run() {
						pauseProgress();
					}
					}.run();

				}
			});


	}
}