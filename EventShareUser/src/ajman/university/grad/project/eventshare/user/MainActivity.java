package ajman.university.grad.project.eventshare.user;

import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private ILocalStorageService service = ServicesFactory.getLocalStorageService();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		service.setRegistered(false);
		Boolean registered = service.isRegistered();
		
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
