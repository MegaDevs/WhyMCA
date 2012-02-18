package com.megadevs.atss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FirstRunActivity extends CommonActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_run);
	}
	
	public void confirmPin(View v) {
		final String pin = ((TextView)findViewById(R.id.pinpad_text)).getText().toString();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(String.format(getResources().getString(R.string.confirm_pin), pin))
		       .setCancelable(false)
		       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   setPref(PREF_PIN, pin);
		        	   setResult(RESULT_OK);
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
