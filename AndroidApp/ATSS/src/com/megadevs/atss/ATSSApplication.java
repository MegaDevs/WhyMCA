package com.megadevs.atss;

import android.app.Application;
import android.content.Intent;
import android.view.ViewDebug.FlagToString;

public class ATSSApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		PrefMan.init(getSharedPreferences("ATSS", MODE_PRIVATE));

		PrefMan.setPref(PrefMan.PREF_NFC_ID, "041737824A2080");
	}

}
