package ajman.university.grad.project.eventshare.admin;

import ajman.university.grad.project.eventshare.adapters.EventsAdapter;
import ajman.university.grad.project.eventshare.admin.helpers.Constants;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	ListView list;
	EventsAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Code for list
        list = (ListView) findViewById(android.R.id.list);
        adapter = new EventsAdapter(this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        
        System.out.println("count: " + adapter.getCount());
        
    }
    
    @Override
    public void onBackPressed() {
    }
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(MainActivity.this, DetailEventActivity.class);
		Event event = (Event) adapter.getItem(arg2);
		
		System.out.println("arg2: " + arg2);
		System.out.println("toDay: " + ((Event) adapter.getItem(arg2)).getToDay());
		System.out.println("toYear: " + ((Event) adapter.getItem(arg2)).getToYear());
		System.out.println("toMonth: " + ((Event) adapter.getItem(arg2)).getToMonth());
		
		intent.putExtra(Constants.CLICKED_EVENT, event);
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
			
		case R.id.action_deleteExpired:
			actionDeleteExpired();
			return true;
			
		case R.id.action_erase:
			actionErase();
			return true;
			
		case R.id.action_about:
			actionAbout();
			return true;

		default:
			return super.onOptionsItemSelected(item);
    	}
    	
    }

	private void actionAbout() {
		Intent intent = new Intent(MainActivity.this, AboutActivity.class);
		startActivity(intent);
	}


	private void actionErase() {
		Intent intent = new Intent(MainActivity.this, EraseTagActivity.class);
		startActivity(intent);
	}


	private void actionDeleteExpired() {
		new AlertDialog.Builder(this)
		.setTitle("Delete Event")
		.setMessage("Are you sure you want to delete all expired events?")
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
	               
	           }

		})
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				ILocalStorageService service = ServicesFactory.getLocalStorageService();
				try {
					service.deleteDeclinedEvents();
					//Refreshes the activity
					onCreate(null);
					// TODO Need to check if there are any expired events first .. if yes delete them
					//and show this message. If not show message "No expired events"
					Toast.makeText(getApplicationContext(), "Declined events have been deleted!",
							   Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
		})
		.show();
	}


	private void addNewEvent() {
		Intent intent = new Intent(MainActivity.this, EventActivity.class);
		startActivity(intent);
	}

	private void writeToTag() {
		Intent intent = new Intent(MainActivity.this, WriteToTagActivity.class);
		intent.putExtra(Constants.ICALENDAR, adapter.toString());
		intent.putExtra(Constants.EVENTCOUNT, adapter.getValidCount());
		startActivity(intent);
	}
}
