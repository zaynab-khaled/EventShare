package ajman.university.grad.project.eventshare.admin;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EventDetailsActivity extends Activity {
	
	private TextView _eventName;
	private TextView _eventLoc;
	private TextView _eventDesc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		_eventName = (TextView) findViewById(R.id.edit_tvName);
		_eventLoc = (TextView) findViewById(R.id.edit_tvLoc);
		_eventDesc = (TextView) findViewById(R.id.edit_tvDescription);
		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.edit_delete, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//return super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
		case R.id.action_edit:
			actionEdit();
			return true;
			
		case R.id.action_delete:
			actionDelete();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
    	}
    	
    }


	private void actionEdit() {
		// Still need to add the event info to the elements, so they don't start from scratch
		//load the event details ?
		Intent intent = new Intent(EventDetailsActivity.this, AddEventActivity.class);
		startActivity(intent);
	}


	private void actionDelete() {
		new AlertDialog.Builder(this)
		.setTitle("Delete Event")
		.setMessage("Are you sure you want to delete this event?")
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
	               //Already just closes the dialog
	           }

		})
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(EventDetailsActivity.this, MainActivity.class);
				startActivity(intent);
	           }

		})
		.show();
	}


}
