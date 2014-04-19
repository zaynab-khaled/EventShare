package ajman.university.grad.project.eventshare.admin;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.cancel_done, menu);
		//return true;
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.cancel_done, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//return super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
		case R.id.action_cancel:
			actonCancel();
			return true;
			
		case R.id.action_done:
			actionDone();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
    	}
    	
    }

	private void actonCancel() {
		finish();
	}

	private void actionDone() {
		Toast.makeText(getBaseContext(), "Clicked on the done, need to save it", Toast.LENGTH_SHORT).show();
		Toast.makeText(getBaseContext(), "Your password has been changed successfully!", Toast.LENGTH_LONG).show();
	}


}