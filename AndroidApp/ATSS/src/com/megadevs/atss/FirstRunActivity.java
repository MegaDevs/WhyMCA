package com.megadevs.atss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FirstRunActivity extends CommonActivity {
	
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
		        	   Intent i = new Intent(FirstRunActivity.this, ATSSActivity.class);
		        	   startActivity(i);
		        	   FirstRunActivity.this.finish();
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
	
}
