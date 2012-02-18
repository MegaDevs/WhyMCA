package com.megadevs.atss;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefMan {
	
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
}
