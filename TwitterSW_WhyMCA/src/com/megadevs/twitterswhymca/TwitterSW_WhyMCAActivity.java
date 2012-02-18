package com.megadevs.twitterswhymca;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.megadevs.socialwrapper.whymca.SocialWrapper;
import com.megadevs.socialwrapper.whymca.exceptions.SocialNetworkNotFoundException;
import com.megadevs.socialwrapper.whymca.thetwitter.TheTwitter;

public class TwitterSW_WhyMCAActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try {
			_init();
		} catch (SocialNetworkNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public void _init() throws SocialNetworkNotFoundException {
    	SocialWrapper wrapper = SocialWrapper.getInstance();
    	wrapper.setActivity(this);
  
    	final TheTwitter twitter = (TheTwitter) wrapper.getSocialNetwork(SocialWrapper.THETWITTER);
    	twitter.setParameters("eRDkGhPEdxx68WtMpjgnw", "4kHvD6PqPqm6T028h96Fa64p4E3iMneNQIeqm8iNg", "atss://oauth");
    	
    	twitter.authenticate(new TheTwitter.TheTwitterLoginCallback() {
			
			@Override
			public void onLoginCallback(String result) {
				Toast.makeText(TwitterSW_WhyMCAActivity.this, "logged in", Toast.LENGTH_SHORT).show();
				
				System.out.println(twitter.isAuthenticated());
			}
			
			@Override
			public void onErrorCallback(String error, Exception e) {
				Toast.makeText(TwitterSW_WhyMCAActivity.this, "failed to log in", Toast.LENGTH_LONG).show();
			}
		});
    }
}