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
	public static final String PREF_FIRST_RUN = "first_run";
	
	public static final int ACTIVITY_SET_PIN = 1;

	protected SharedPreferences preferences;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		preferences = getPreferences(MODE_PRIVATE);
	}
	
	public void pinpadNumber(View v) {
		TextView pinText = (TextView)findViewById(R.id.pinpad_text);
		if (v.getId() == R.id.pinpad_btn_backspace) {
			if (pinText.getText().length() > 0) {
				pinText.setText(pinText.getText().toString().substring(0, pinText.getText().toString().length()-2));
			}
		} else {
			String value = ((Button)v).getText().toString();
			pinText.setText(pinText.getText()+value);
		}
	}
	
	public String getPref(String name) {
		return preferences.getString(name, null);
	}
	
	public boolean getPrefBool(String name) {
		return preferences.getBoolean(name, false);
	}
	
	public void setPref(String name, String val) {
		Editor e = preferences.edit();
		e.putString(name, val);
		e.commit();
	}
	
	public void setPrefBool(String name, boolean val) {
		Editor e = preferences.edit();
		e.putBoolean(name, val);
		e.commit();
	}
	
}
