package com.megadevs.atss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CommonActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	
}
