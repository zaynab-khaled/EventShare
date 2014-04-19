package ajman.university.grad.project.eventshare.admin;

import ajman.university.grad.project.eventshare.adapters.EventsAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	ListView list;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Code for list
        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(new EventsAdapter(this));
        list.setOnItemClickListener(this);
    }




	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
		startActivity(intent);
		
	}



	// From here on is the code for the action bar buttons and menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    	
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//return super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
		case R.id.addNewEvent:
			addNewEvent();
			return true;
			
		case R.id.writeToTag:
			writeToTag();
			return true;
			
		case R.id.action_deleteDeclined:
			actionDeleteDeclined();
			return true;
			
		case R.id.action_changePassword:
			actionChangePassword();
			return true;
			
		case R.id.action_erase:
			actionErase();
			return true;
			
		case R.id.action_settings:
			actionSettings();
			return true;
			
		case R.id.action_about:
			actionAbout();
			return true;

		default:
			return super.onOptionsItemSelected(item);
    	}
    	
    }

	private void actionAbout() {
		Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
		startActivity(intent);
	}


	private void actionErase() {
		Intent intent = new Intent(MainActivity.this, EraseTagActivity.class);
		startActivity(intent);
	}


	private void actionChangePassword() {
		Intent intent = new Intent(MainActivity.this, PasswordTagActivity.class);
		startActivity(intent);
	}


	private void actionDeleteDeclined() {
		new AlertDialog.Builder(this)
		.setTitle("Delete Event")
		.setMessage("Are you sure you want to delete declined events?")
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
	               //Already just closes the dialog
	           }

		})
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Remove all events having date less than present from list
	           }

		})
		.show();
	}


	private void actionSettings() {
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		startActivity(intent);
	}


	private void addNewEvent() {
		Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
		startActivity(intent);
	}

	private void writeToTag() {
		Intent intent = new Intent(MainActivity.this, WriteToTagActivity.class);
		startActivity(intent);
	}





    
}
