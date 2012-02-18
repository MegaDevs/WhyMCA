package com.megadevs.atss;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefMan {
	
	public static final String PREF_PIN = "pin";
	public static final String PREF_PIN_SETTED = "pin_setted";
	public static final String PREF_FIRST_RUN = "first_run";
	public static final String PREF_ID = "id";
	
	private static SharedPreferences preferences;
	
	public static void init(SharedPreferences pref) {
		preferences = pref;
	}
	
	public static String getPref(String name) {
		return preferences.getString(name, null);
	}
	
	public static boolean getPrefBool(String name) {
		return preferences.getBoolean(name, false);
	}
	
	public static boolean getPrefBool(String name, boolean def) {
		return preferences.getBoolean(name, def);
	}
	
	public static int getPrefInt(String name) {
		return preferences.getInt(name, 0);
	}
	
	public static int getPrefInt(String name, int def) {
		return preferences.getInt(name, def);
	}
	
	public static void setPref(String name, String val) {
		Editor e = preferences.edit();
		e.putString(name, val);
		e.commit();
	}
	
	public static void setPrefBool(String name, boolean val) {
		Editor e = preferences.edit();
		e.putBoolean(name, val);
		e.commit();
	}
	
	public static void setPrefInt(String name, int val) {
		Editor e = preferences.edit();
		e.putInt(name, val);
		e.commit();
	}
}
