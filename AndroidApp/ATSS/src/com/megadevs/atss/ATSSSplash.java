package com.megadevs.atss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ATSSSplash extends CommonActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new View(this));
		
		System.out.println((PrefMan.getPref(PrefMan.PREF_PIN) == null) ? "damn" : PrefMan.getPref(PrefMan.PREF_PIN));
		
		if (!PrefMan.getPrefBool(PrefMan.PREF_PIN_SETTED, false)) {
			Intent i = new Intent(this, FirstRunActivity.class);
			startActivity(i);
		} else {
			Intent i = new Intent(this, ATSSActivity.class);
			startActivity(i);
		}
		
		finish();
	}
}
