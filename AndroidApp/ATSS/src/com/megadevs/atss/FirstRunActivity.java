package com.megadevs.atss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.megadevs.socialwrapper.whymca.SocialWrapper;
import com.megadevs.socialwrapper.whymca.exceptions.SocialNetworkNotFoundException;
import com.megadevs.socialwrapper.whymca.thetwitter.TheTwitter;

public class FirstRunActivity extends CommonActivity {

	public static TheTwitter tw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_run);

		((TextView)findViewById(R.id.pinpad_text)).setTransformationMethod(null);
	}

	public void confirmPin(View v) {
		final String pin = ((TextView)findViewById(R.id.pinpad_text)).getText().toString();
		if (pin.length() != 4) {
			Toast.makeText(this, R.string.pin_size_wrong, Toast.LENGTH_LONG).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(String.format(getResources().getString(R.string.confirm_pin), pin))
		.setCancelable(false)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				PrefMan.setPref(PREF_PIN, pin);
				PrefMan.setPrefBool(PREF_PIN_SETTED, true);

				try {
					initTwitter();
				} catch (SocialNetworkNotFoundException e) {
					Log.e("ATSS", "Unable to find TheTwitter object");
				}
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void initTwitter() throws SocialNetworkNotFoundException {
		SocialWrapper wrapper = SocialWrapper.getInstance();
		wrapper.setActivity(this);

		tw = (TheTwitter) wrapper.getSocialNetwork(SocialWrapper.THETWITTER);
		tw.setParameters("eRDkGhPEdxx68WtMpjgnw", "4kHvD6PqPqm6T028h96Fa64p4E3iMneNQIeqm8iNg", "atss://oauth");

		tw.authenticate(new TheTwitter.TheTwitterLoginCallback() {

			@Override
			public void onLoginCallback(String result) {
				Log.i("ATSS", "Twitter successfully logged in");
				
				Intent i = new Intent(FirstRunActivity.this, ATSSActivity.class);
				startActivity(i);
				FirstRunActivity.this.finish();
			}

			@Override
			public void onErrorCallback(String error, Exception e) {
				Log.e("ATSS", "Twitter login error");
			}
		});
	}

}
