package com.megadevs.atss;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ATSSActivity extends CommonActivity {

	private SurfaceView previewSurface;
	private Camera theCamera;

	private boolean surfaceReady = false;
	private boolean isActive = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		previewSurface = (SurfaceView)findViewById(R.id.preview);
		previewSurface.getHolder().addCallback(new Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {}

			public void surfaceCreated(final SurfaceHolder holder) {
				surfaceReady = true;
				if (theCamera == null) {
					initCamera(holder);
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
		});

		initNfc(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		initNfc(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (theCamera != null) {
			theCamera.stopPreview();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (theCamera != null) {
			theCamera.startPreview();
		} else if (surfaceReady) {
			initCamera(previewSurface.getHolder());
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (theCamera != null) {
			theCamera.release();
		}
	}
	
	@Override
	public void pinpadNumber(View v) {
		super.pinpadNumber(v);
		String text = ((TextView)findViewById(R.id.pinpad_text)).getText().toString();
		if (text.length() == 4) {
			if (text.equals(PrefMan.getPref(PREF_PIN))) {
				activate();
			} else {
				((TextView)findViewById(R.id.pinpad_text)).setText("");
				Toast.makeText(this, R.string.wrong_pin, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void activate() {
		isActive = true;
		
	}
	
	public void deactivate() {
		isActive = false;
	}

	private void initNfc(Intent intent) {
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			String id = byteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));	
		}
	}

	private void initCamera(final SurfaceHolder holder) {
		new Thread(new Runnable() {
			public void run() {
				try {
					theCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
					setCameraDisplayOrientation(ATSSActivity.this, CameraInfo.CAMERA_FACING_BACK, theCamera);
					theCamera.setPreviewDisplay(holder);
					theCamera.setPreviewCallback(new PreviewCallback() {
						public void onPreviewFrame(byte[] data, Camera camera) {
							System.out.println("preview callback");
						}
					});
					theCamera.startPreview();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0: degrees = 0; break;
		case Surface.ROTATION_90: degrees = 90; break;
		case Surface.ROTATION_180: degrees = 180; break;
		case Surface.ROTATION_270: degrees = 270; break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	private String byteArrayToHexString(byte[] inarray) {
		int i, j, in;
		String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		String out= "";

		for(j = 0 ; j < inarray.length ; ++j)  {
			in = (int) inarray[j] & 0xff;
			i = (in >> 4) & 0x0f;
			out += hex[i];
			i = in & 0x0f;
			out += hex[i];
		}
		return out;
	}
}