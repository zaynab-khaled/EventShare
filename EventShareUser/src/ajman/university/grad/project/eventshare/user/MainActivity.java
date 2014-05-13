package ajman.university.grad.project.eventshare.user;

import ajman.university.grad.project.eventshare.common.contracts.IAlarmService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private static String LOG_TAG = "Main Activity";
	
	private ILocalStorageService localStorageService = ServicesFactory.getLocalStorageService();
	private IAlarmService alarmService = ServicesFactory.getAlarmService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(0xff33b5e5));
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		
		alarmService.start();
		
		localStorageService.setRegistered(false);
		Boolean registered = localStorageService.isRegistered();
		
		Log.d(LOG_TAG, "Registered? " + localStorageService.isRegistered());
		if(registered.equals("true")){
			Intent intent = new Intent(MainActivity.this, ListActivity.class);
			startActivity(intent);
		} 
		else {
			Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
			startActivity(intent);
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
