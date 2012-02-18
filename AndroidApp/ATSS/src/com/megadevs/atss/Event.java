package com.megadevs.atss;

public class Event {
	
	public static final String MOTION_DETECTED = "motion_detected";
	public static final String MOTION_ENDED = "motion_ended";
	public static final String UNKNOWN_EVENT = "unknown_event";
	
	public static final long MIN_FRAME_DELAY = 1000;
	
	static private int ID = PrefMan.getPrefInt(PrefMan.PREF_ID, 0);

	private int myId;
	private long startTime;
	private long endTime;
	
	private long lastFrameSent;
	
	private boolean isActive = false;
	
	public Event(long t) {
		startTime = t;
		myId = ID;
		ID++;
		PrefMan.setPrefInt(PrefMan.PREF_ID, ID);
		isActive = true;
	}
	
	public int getID() {
		return myId;
	}
	
	public long getLastFrameSent() {
		return lastFrameSent;
	}
	
	public void setLastFrameSent(long time) {
		lastFrameSent = time;
	}
	
	public void finish(long t) {
		endTime = t;
		isActive = false;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
}
