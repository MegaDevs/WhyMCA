package com.megadevs.atss;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CommonActivity extends Activity {
	
	public static final String PREF_PIN = "pin";
	public static final String PREF_PIN_SETTED = "pin_setted";
	public static final String PREF_FIRST_RUN = "first_run";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void pinpadNumber(View v) {
		TextView pinText = (TextView)findViewById(R.id.pinpad_text);
		if (v.getId() == R.id.pinpad_btn_backspace) {
			if (pinText.getText() != null && pinText.getText().length() > 0) {
				pinText.setText(pinText.getText().toString().substring(0, pinText.getText().toString().length()-1));
			}
		} else {
			String value = ((Button)v).getText().toString();
			pinText.setText(pinText.getText()+value);
		}
	}
	
}
