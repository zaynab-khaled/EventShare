package ajman.university.grad.project.eventshare.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.user.helpers.Constants;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class DetailEventActivity extends Activity {
	
	private static String LOG_TAG = "detailEventActivity";
	private Event _event;
	
	private TextView tvEventName;
	private TextView tvEventLoc;
	private TextView tvEventDesc;
	private TextView tvEventFromDate;
	private TextView tvEventToDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_details);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		tvEventName = (TextView) findViewById(R.id.edit_tvName);
		tvEventLoc = (TextView) findViewById(R.id.edit_tvLoc);
		tvEventDesc = (TextView) findViewById(R.id.edit_tvDescription);
		tvEventFromDate = (TextView) findViewById(R.id.edit_tvSdate);
		tvEventToDate = (TextView) findViewById(R.id.edit_tvEdate);
		
		// Get bundled data
		_event = (Event) getIntent().getSerializableExtra(Constants.CLICKED_EVENT);
		
		// Bind the UI
		populateFields();
	}

	private void populateFields() {
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(Calendar.YEAR, _event.getFromYear());
		fromCal.set(Calendar.MONTH, _event.getFromMonth());
		fromCal.set(Calendar.DAY_OF_MONTH, _event.getFromDay());
		fromCal.set(Calendar.HOUR_OF_DAY, _event.getFromDayHour());
		fromCal.set(Calendar.MINUTE, _event.getFromMinute());
		
		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.YEAR, _event.getToYear());
		toCal.set(Calendar.MONTH, _event.getToMonth());
		toCal.set(Calendar.DAY_OF_MONTH, _event.getToDay());
		toCal.set(Calendar.HOUR_OF_DAY, _event.getToDayHour());
		toCal.set(Calendar.MINUTE, _event.getToMinute());
		
		
		tvEventName.setText(_event.getTitle());
		tvEventLoc.setText(_event.getLocation());
		tvEventDesc.setText(_event.getDescription());
		tvEventFromDate.setText(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm").format(fromCal.getTime()));
		tvEventToDate.setText(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm").format(toCal.getTime()));
		// Setting them wrong here
		Log.d(LOG_TAG, "from cal: " + fromCal.getTime() + " to cal: " +  toCal.getTime());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
