package com.megadevs.atss;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;
import com.android.future.usb.UsbAccessory;
import com.android.future.usb.UsbManager;

public class ATSSActivity extends CommonActivity implements Runnable{

	/*Arduino Stuffs --------------------------------------------------*/
	private static final String TAG = "TestArduino";
	private static final String ACTION_USB_PERMISSION = "test.arduino.action.USB_PERMISSION";

	private UsbManager mUsbManager;
	private PendingIntent mPermissionIntent;
	private boolean mPermissionRequestPending;
	private TextView log;

	UsbAccessory mAccessory;
	ParcelFileDescriptor mFileDescriptor;
	FileInputStream mInputStream;
	FileOutputStream mOutputStream;
	/*-----------------------------------------------------------------*/
	
	private SurfaceView previewSurface;
	private Camera theCamera;
	private boolean surfaceReady = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if (true) {
			Intent i = new Intent(this, FirstRunActivity.class);
			startActivity(i);
		} else {
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
		
		//ARDUINO
		mUsbManager = UsbManager.getInstance(this);
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				ACTION_USB_PERMISSION), 0);
		IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
		filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
		registerReceiver(mUsbReceiver, filter);

		if (getLastNonConfigurationInstance() != null) {
			mAccessory = (UsbAccessory) getLastNonConfigurationInstance();
			openAccessory(mAccessory);
		}

		log = (TextView)findViewById(R.id.log);

		enableControls(false);
		
	}

	//OK
	@Override
	public Object onRetainNonConfigurationInstance() {
		if (mAccessory != null) {
			return mAccessory;
		} else {
			return super.onRetainNonConfigurationInstance();
		}
	}


	//ARDUINO
	//TODO bradcast
	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbAccessory accessory = UsbManager.getAccessory(intent);
					if (intent.getBooleanExtra(
							UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						openAccessory(accessory);
					} else {
						Log.d(TAG, "permission denied for accessory "
								+ accessory);
					}
					mPermissionRequestPending = false;
				}
			} else if (UsbManager.ACTION_USB_ACCESSORY_DETACHED.equals(action)) {
				UsbAccessory accessory = UsbManager.getAccessory(intent);
				if (accessory != null && accessory.equals(mAccessory)) {
					closeAccessory();
				}
			}
		}
	};

	
	
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
		
		//Arduino
		closeAccessory();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (theCamera != null) {
			theCamera.startPreview();
		} else if (surfaceReady) {
			initCamera(previewSurface.getHolder());
		}
		
		
		//Arduino
		Intent intent = getIntent();
		if (mInputStream != null && mOutputStream != null) {
			return;
		}

		UsbAccessory[] accessories = mUsbManager.getAccessoryList();
		UsbAccessory accessory = (accessories == null ? null : accessories[0]);
		if (accessory != null) {
			if (mUsbManager.hasPermission(accessory)) {
				openAccessory(accessory);
			} else {
				synchronized (mUsbReceiver) {
					if (!mPermissionRequestPending) {
						mUsbManager.requestPermission(accessory,
								mPermissionIntent);
						mPermissionRequestPending = true;
					}
				}
			}
		} else {
			Log.d(TAG, "mAccessory is null");
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
	public void onDestroy() {
		unregisterReceiver(mUsbReceiver);
		super.onDestroy();
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
	
	//ARDUINO
	private void openAccessory(UsbAccessory accessory) {
		mFileDescriptor = mUsbManager.openAccessory(accessory);
		if (mFileDescriptor != null) {
			mAccessory = accessory;
			FileDescriptor fd = mFileDescriptor.getFileDescriptor();
			mInputStream = new FileInputStream(fd);
			mOutputStream = new FileOutputStream(fd);
			Thread thread = new Thread(null, this, "TestArduino");
			thread.start();
			Log.d(TAG, "accessory opened");
			enableControls(true);
		} else {
			Log.d(TAG, "accessory open fail");
		}
	}

	//ARDUINO
	private void closeAccessory() {
		enableControls(false);

		try {
			if (mFileDescriptor != null) {
				mFileDescriptor.close();
			}
		} catch (IOException e) {
		} finally {
			mFileDescriptor = null;
			mAccessory = null;
		}
	}
	
	//ARDUINO
	protected void enableControls(boolean enable) {
	}
	
	private void notifyTheftEvent(byte e){
		final Intent i = new Intent();
		final Date d = Calendar.getInstance().getTime();
		i.putExtra("timestamp", d.getTime());
		
		if(e == 0x1)
			i.putExtra("event", "motion_detected");
		else if(e == 0x2)
			i.putExtra("event", "motion_ended");
		else 	
			i.putExtra("event", "unknown_event");
		
		sendOrderedBroadcast(i, null);
		
		runOnUiThread(new Runnable() {
			public void run() {
				if(log==null)log = (TextView)findViewById(R.id.log);
				log.setText("# " + d.getHours() + ":" +d.getMinutes()+":"+d.getSeconds()+"  -> "+i.getStringExtra("event")
						      +"\n" +log.getText());
			}
		});
		
	}

	public void run() {
		int ret = 0;
		byte[] buffer = new byte[64];
		int i;

		while (ret >= 0) {
			try {
				ret = mInputStream.read(buffer);
			} catch (IOException e) {
				break;
			}

			i = 0;
			while (i < ret) {
				int len = ret - i;

				switch (buffer[i]) {
				case 0x1:
					/*if (len >= 3) {
						Message m = Message.obtain(mHandler, MESSAGE_SWITCH);
						m.obj = new SwitchMsg(buffer[i + 1], buffer[i + 2]);
						mHandler.sendMessage(m);
					}
					i += 3;*/

				case 0x4:
				case 0x5:
				case 0x6:
				default:
					Log.d(TAG, "unknown msg: " + buffer[i]);
					final byte a = buffer[2];
					//final String a = ""+ (char)buffer[0] + (char)buffer[1] + (char)buffer[2];/*ByteArrayToHexString(buffer);*/
					i = len;
					
					//SEND NOTIFY!!
					notifyTheftEvent(a);
					
					break;
				}
			}

		}
	}

	/* può servire se gestiamo Messaggi come classi 
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_SWITCH:
				SwitchMsg o = (SwitchMsg) msg.obj;
				handleSwitchMessage(o);
				break;

			case MESSAGE_TEMPERATURE:
				TemperatureMsg t = (TemperatureMsg) msg.obj;
				handleTemperatureMessage(t);
				break;

			case MESSAGE_LIGHT:
				LightMsg l = (LightMsg) msg.obj;
				handleLightMessage(l);
				break;

			case MESSAGE_JOY:
				JoyMsg j = (JoyMsg) msg.obj;
				handleJoyMessage(j);
				break;

			}
		}
	};*/

	public void sendCommand(byte command, byte target, int value) {
		byte[] buffer = new byte[3];
		if (value > 255)
			value = 255;

		buffer[0] = command;
		buffer[1] = target;
		buffer[2] = (byte) value;
		if (mOutputStream != null && buffer[1] != -1) {
			try {
				mOutputStream.write(buffer);
			} catch (IOException e) {
				Log.e(TAG, "write failed", e);
			}
		}
	}
}