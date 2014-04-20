package ajman.university.grad.project.eventshare.admin;



import java.util.Calendar;
import java.util.List;

import ajman.university.grad.project.eventshare.common.contracts.IErrorService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class AddEventActivity extends Activity {
	
	private EditText _etEventName;
	private EditText _eventLocation;
	private EditText _eventDesc;
	 
	static Button btnD1;
	static Button btnT1;
	static Button btnD2;
	static Button btnT2;

	//Start code for TimePickerDialog
	public static class StartTimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {


		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
		 	TimePickerDialog dialogTime = new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
			dialogTime.setTitle("Set Time");
			
			return dialogTime;
		}

		public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {

			btnT1.setText(String.valueOf(hourOfDay) + ":" +   String.valueOf(minute));
		}
		
	}

	public void showStartTimePickerDialog(View v) {
	    DialogFragment newFragment = new StartTimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public static class EndTimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {


		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			TimePickerDialog dialogTime = new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
			dialogTime.setTitle("Set Time");
			
			return dialogTime;
		}

		public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {

			btnT2.setText(String.valueOf(hourOfDay) + ":" +   String.valueOf(minute));
		}
		
	}
	
	public void showEndTimePickerDialog(View v) {
	    DialogFragment newFragment = new EndTimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	//End code for TimePickerDialog

	

	//Start code for DatePickerDialog
	public static class StartDatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {


		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog dialogDate = new DatePickerDialog(getActivity(), this, year, month, day);
		dialogDate.setTitle("Set Date");
		return dialogDate;
				
		}
		

		public void onDateSet(DatePicker view, int year, int month, int day) {
			btnD1.setText(String.valueOf(month + 1 ) + "/" +   String.valueOf(day) + "/" + String.valueOf(year));

		}
	}
	
	public void showStartDatePickerDialog(View v) {
	    DialogFragment newFragment = new StartDatePickerFragment();
	    newFragment.show(getFragmentManager(), "StartDatePicker");
	}
	
	public static class EndDatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog dialogDate = new DatePickerDialog(getActivity(), this, year, month, day);
		dialogDate.setTitle("Set Date");
		return dialogDate;
		
		}
		

		public void onDateSet(DatePicker view, int year, int month, int day) {
			btnD2.setText(String.valueOf(month + 1 ) + "/" +   String.valueOf(day) + "/" + String.valueOf(year));

		}
	}
	
	public void showEndDatePickerDialog(View v) {
	    DialogFragment newFragment = new EndDatePickerFragment();
	    newFragment.show(getFragmentManager(), "EndDatePicker");
	}
	// End Code for DatePickerDialog

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		_etEventName = (EditText) findViewById(R.id.editText_eventName);
		_eventLocation = (EditText) findViewById(R.id.editText_eventLoc);
		_eventDesc = (EditText) findViewById(R.id.editText_eventDesc);
		
		setUpViews();
		getActionBar().setDisplayShowHomeEnabled(false);
	}
	

	private void setUpViews() {
		btnD1 = (Button) findViewById(R.id.btnDate1);
		btnT1 = (Button) findViewById(R.id.btnTime1);
		btnD2 = (Button) findViewById(R.id.btnDate2);
		btnT2 = (Button) findViewById(R.id.btnTime2);

		btnD1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showStartDatePickerDialog(v);
			}
		});
		
		btnD2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showEndDatePickerDialog(v);
			}
		});
		
		btnT1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showStartTimePickerDialog(v);
			}
		});
		
		btnT2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showEndTimePickerDialog(v);
			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
		IErrorService errorService = ServicesFactory.getErrorService();

		//TODO: Some validation to make sure that the event data is actually provided:
		if (_etEventName.getText().length() > 0 && 
			_eventDesc.getText().length() > 0 &&
			_eventLocation.getText().length() > 0) {

			// Transition to the main activity and remove this activity from the stack
			// Get the events from the events repository
			ILocalStorageService service = ServicesFactory.getLocalStorageService();
			Event event = new Event();
			event.setTitle(_etEventName.getText().toString());
			event.setDescription( _eventDesc.getText().toString());
			event.setLocation(_eventLocation.getText().toString());
			//TODO: Add from and to date and time
			try {
				service.addEvent(event);
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			} catch (Exception e) {
				errorService.log(e);
			}
		} else {
			errorService.log("You cannot keep some fields empty, please fill them out!");
		}
	}

}
