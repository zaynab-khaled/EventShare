package ajman.university.grad.project.eventshare.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import ajman.university.grad.project.eventshare.admin.helpers.Constants;
import ajman.university.grad.project.eventshare.common.contracts.IErrorService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class EventActivity extends SherlockActivity {

	private static String LOG_TAG = "EventActivity SetDate";
	private EditText etEventName;
	private EditText etEventLocation;
	private EditText etEventDesc;

	private Button btnD1;
	private Button btnT1;
	private Button btnD2;
	private Button btnT2;

	private Event event;
	private int mode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		setUpViews();
		getActionBar().setDisplayShowHomeEnabled(false);

		if (getIntent().getSerializableExtra(Constants.EDIT_EVENT) == null) {
			event = new Event();
			mode = 0;
		} else {
			event = (Event) getIntent().getSerializableExtra(Constants.EDIT_EVENT);
			mode = 1;
			populateFields();
		}
		
		// BEGIN_INCLUDE (inflate_set_custom_view)
				//Inflate a "Done/Cancel" custom action bar view.
				final LayoutInflater inflater = (LayoutInflater) getSupportActionBar()
			    .getThemedContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
				final View customActionBarView = inflater.inflate(R.layout.actionbar_custom_view_done_discard,
				    null);
				customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
				    new View.OnClickListener() {
				      @Override
				      public void onClick(View v) {
				    	  actionDone();
				      }
				    });
				customActionBarView.findViewById(R.id.actionbar_discard).setOnClickListener(
				    new View.OnClickListener() {
				      @Override
				      public void onClick(View v) {
				    	  actionCancel();
				      }
				    });

				// Show the custom action bar view and hide the normal Home icon and title.
				final ActionBar bar = getSupportActionBar();;
				bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM
				    | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
				bar.setCustomView(customActionBarView, new ActionBar.LayoutParams(
				    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
				// END_INCLUDE (inflate_set_custom_view)
	}


	public void showStartTimePickerDialog(View v) {
		DialogFragment newFragment = new EventStartTimePickerFragment(event, mode, btnT1);
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public void showEndTimePickerDialog(View v) {
		DialogFragment newFragment = new EventEndTimePickerFragment(event, mode, btnT2);
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public void showStartDatePickerDialog(View v) {
		DialogFragment newFragment = new EventStartDatePickerFragment(event, mode, btnD1);
		newFragment.show(getFragmentManager(), "StartDatePicker");
	}

	public void showEndDatePickerDialog(View v) {
		DialogFragment newFragment = new EventEndDatePickerFragment(event, mode, btnD2);
		newFragment.show(getFragmentManager(), "EndDatePicker");
	}

	private void setUpViews() {
		etEventName = (EditText) findViewById(R.id.editText_eventName);
		etEventLocation = (EditText) findViewById(R.id.editText_eventLoc);
		etEventDesc = (EditText) findViewById(R.id.editText_eventDesc);

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

	private void populateFields() {
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(Calendar.YEAR, event.getFromYear());
		fromCal.set(Calendar.MONTH, event.getFromMonth());
		fromCal.set(Calendar.DAY_OF_MONTH, event.getFromDay());
		fromCal.set(Calendar.HOUR_OF_DAY, event.getFromDayHour());
		fromCal.set(Calendar.MINUTE, event.getFromMinute());

		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.YEAR, event.getToYear());
		toCal.set(Calendar.MONTH, event.getToMonth());
		toCal.set(Calendar.DAY_OF_MONTH, event.getToDay());
		toCal.set(Calendar.HOUR_OF_DAY, event.getToDayHour());
		toCal.set(Calendar.MINUTE, event.getToMinute());

		etEventName.setText(event.getTitle());
		etEventLocation.setText(event.getLocation());
		etEventDesc.setText(event.getDescription());
		btnD1.setText(new SimpleDateFormat("yyyy-MM-dd").format(fromCal.getTime()));
		btnD2.setText(new SimpleDateFormat("yyyy-MM-dd").format(toCal.getTime()));
		btnT1.setText(new SimpleDateFormat("HH:mm").format(fromCal.getTime()));
		btnT2.setText(new SimpleDateFormat("HH:mm").format(toCal.getTime()));
	}

	private void actionCancel() {
		finish();
	}

	private void actionDone() {
		IErrorService errorService = ServicesFactory.getErrorService();

		// TODO: Some validation to make sure that the event data is actually
		// provided:
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(Calendar.YEAR, event.getFromYear());
		fromCal.set(Calendar.MONTH, event.getFromMonth());
		fromCal.set(Calendar.DAY_OF_MONTH, event.getFromDay());
		fromCal.set(Calendar.HOUR_OF_DAY, event.getFromDayHour());
		fromCal.set(Calendar.MINUTE, event.getFromMinute());
		
		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.YEAR, event.getToYear());
		toCal.set(Calendar.MONTH, event.getToMonth());
		toCal.set(Calendar.DAY_OF_MONTH, event.getToDay());
		toCal.set(Calendar.HOUR_OF_DAY, event.getToDayHour());
		toCal.set(Calendar.MINUTE, event.getToMinute());
		
		if (toCal.before(fromCal)) {	
			Toast.makeText(this, "TO date should be later that " + new SimpleDateFormat("yyyy-MM-dd").format(fromCal.getTime()) + " " + new SimpleDateFormat("HH:mm").format(fromCal.getTime()), Toast.LENGTH_LONG).show();
		}
		else if (etEventName.getText().length() > 0 &&
				etEventDesc.getText().length() > 0 &&
				etEventLocation.getText().length() > 0 &&
				event.getFromDay() != -1 &&
				event.getFromDayHour() != -1 &&
				event.getFromMinute() != -1 &&
				event.getFromMonth() != -1 &&
				event.getFromYear() != -1 &&
				event.getToDay() != -1 &&
				event.getToDayHour() != -1 &&
				event.getToMinute() != -1 &&
				event.getToMonth() != -1 &&
				event.getToYear() != -1) {

			// Transition to the main activity and remove this activity from the
			// stack
			// Get the events from the events repository
			ILocalStorageService service = ServicesFactory.getLocalStorageService();
			event.setTitle(etEventName.getText().toString());
			event.setDescription(etEventDesc.getText().toString());
			event.setLocation(etEventLocation.getText().toString());

			Log.d(LOG_TAG, "DateSet ToYear: " + event.getToYear() + " DateSet ToMonth: " + event.getToMonth());

			try {
				if (mode == 0){
					service.addEvent(event);
					Toast.makeText(getApplicationContext(), "Event Created",
						   Toast.LENGTH_SHORT).show(); }
				else {
					service.updateEvent(event);
				Toast.makeText(getApplicationContext(), "Event Saved",
						   Toast.LENGTH_SHORT).show(); }

				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				
				finish();
			} catch (Exception e) {
				errorService.log(e);
			}
		} else {
			errorService.log("You cannot keep some fields empty, please fill them out!");
			Toast.makeText(this, "Some fields cannot be empty!", Toast.LENGTH_LONG).show();
		}
	}

}

// Start code for TimePickerDialog
class EventStartTimePickerFragment extends DialogFragment
		implements TimePickerDialog.OnTimeSetListener {

	private Event _innerEvent;
	private int mode;
	private Button btnT1;
	private int hour;
	private int minute;

	public EventStartTimePickerFragment(Event e, int m, Button b) {
		_innerEvent = e;
		mode = m;
		btnT1 = b;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		if (mode == 1) {
			hour = _innerEvent.getFromDayHour();
			minute = _innerEvent.getFromMinute();
		}

		else {
			final Calendar c = Calendar.getInstance();
			hour = (_innerEvent.getFromDayHour() != -1 ? _innerEvent.getFromDayHour() : c.get(Calendar.HOUR_OF_DAY));
			minute = (_innerEvent.getFromMinute() != -1 ? _innerEvent.getFromMinute() : c.get(Calendar.MINUTE));
		}

		// Create a new instance of TimePickerDialog and return it
		TimePickerDialog dialogTime = new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
		dialogTime.setTitle("Set Time");

		return dialogTime;
	}

	public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {

		//btnT1.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

		_innerEvent.setFromDayHour(hourOfDay);
		_innerEvent.setFromMinute(minute);
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(Calendar.HOUR_OF_DAY, _innerEvent.getFromDayHour());
		fromCal.set(Calendar.MINUTE, _innerEvent.getFromMinute());
		btnT1.setText(new SimpleDateFormat("HH:mm").format(fromCal.getTime()));
		
		System.out.println("hour:" + hourOfDay + " minute: " + minute);
	}

}

class EventEndTimePickerFragment extends DialogFragment
		implements TimePickerDialog.OnTimeSetListener {

	private Event _innerEvent;
	private int mode;
	private Button btnT2;
	private int hour;
	private int minute;

	public EventEndTimePickerFragment(Event e, int m, Button b) {
		_innerEvent = e;
		mode = m;
		btnT2 = b;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (mode == 1) {
			hour = _innerEvent.getToDayHour();
			minute = _innerEvent.getToMinute();
		}
		else {
			final Calendar c = Calendar.getInstance();
			hour = (_innerEvent.getToDayHour() != -1 ? _innerEvent.getToDayHour() : c.get(Calendar.HOUR_OF_DAY));
			minute = (_innerEvent.getToMinute() != -1 ? _innerEvent.getToMinute() : c.get(Calendar.MINUTE));
		}

		// Create a new instance of TimePickerDialog and return it
		TimePickerDialog dialogTime = new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
		dialogTime.setTitle("Set Time");

		return dialogTime;
	}

	public void onTimeSet(TimePicker view, final int hourOfDay, final int minute) {

		//btnT2.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
		
		_innerEvent.setToDayHour(hourOfDay);
		_innerEvent.setToMinute(minute);
		
		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.HOUR_OF_DAY, _innerEvent.getToDayHour());
		toCal.set(Calendar.MINUTE, _innerEvent.getToMinute());
		btnT2.setText(new SimpleDateFormat("HH:mm").format(toCal.getTime()));
		
		System.out.println("hour:" + hourOfDay + " minute: " + minute);
	}

}

class EventStartDatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

	private Event _innerEvent;
	private int mode;
	private Button btnD1;
	private int year;
	private int month;
	private int day;

	public EventStartDatePickerFragment(Event e, int m, Button b) {
		_innerEvent = e;
		mode = m;
		btnD1 = b;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker

		if (mode == 1) {
			year = _innerEvent.getFromYear();
			month = _innerEvent.getFromMonth();
			day = _innerEvent.getFromDay();
		}
		else {
			final Calendar c = Calendar.getInstance();
			year = (_innerEvent.getFromYear() != -1 ? _innerEvent.getFromYear() : c.get(Calendar.YEAR));
			month = (_innerEvent.getFromMonth() != -1 ? _innerEvent.getFromMonth() : c.get(Calendar.MONTH));
			day = (_innerEvent.getFromDay() != -1 ? _innerEvent.getFromDay() : c.get(Calendar.DAY_OF_MONTH));
		}

		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog dialogDate = new DatePickerDialog(getActivity(), this, year, month, day);
		dialogDate.setTitle("Set Date");
		return dialogDate;

	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		//btnD1.setText(String.valueOf(day) + " " + String.valueOf(month + 1) + " " + String.valueOf(year));

		_innerEvent.setFromDay(day);
		_innerEvent.setFromMonth(month);
		_innerEvent.setFromYear(year);
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(Calendar.YEAR, _innerEvent.getFromYear());
		fromCal.set(Calendar.MONTH, _innerEvent.getFromMonth());
		fromCal.set(Calendar.DAY_OF_MONTH, _innerEvent.getFromDay());
		
		btnD1.setText(new SimpleDateFormat("yyyy-MM-dd").format(fromCal.getTime()));
		
		System.out.println("day:" + day + " month: " + month + " year:" + year);
	}
}

class EventEndDatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

	private static String LOG_TAG = "EventActivity SetDate";

	private Event _innerEvent;
	private int mode;
	private Button btnD2;
	private int year;
	private int month;
	private int day;

	public EventEndDatePickerFragment(Event e, int m, Button b) {
		_innerEvent = e;
		mode = m;
		btnD2 = b;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker

		if (mode == 1) {
			year = _innerEvent.getToYear();
			month = _innerEvent.getToMonth();
			day = _innerEvent.getToDay();
		}
		else {
			final Calendar c = Calendar.getInstance();
			year = (_innerEvent.getToYear() != -1 ? _innerEvent.getToYear() : c.get(Calendar.YEAR));
			month = (_innerEvent.getToMonth() != -1 ? _innerEvent.getToMonth() : c.get(Calendar.MONTH));
			day = (_innerEvent.getToDay() != -1 ? _innerEvent.getToDay() : c.get(Calendar.DAY_OF_MONTH));
		}

		// Create a new instance of DatePickerDialog and return it
		DatePickerDialog dialogDate = new DatePickerDialog(getActivity(), this, year, month, day);
		dialogDate.setTitle("Set Date");
		return dialogDate;

	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		//btnD2.setText(String.valueOf(day) + " " + String.valueOf(month + 1) + " " + String.valueOf(year));
		// btnD2.setText(new SimpleDateFormat("EEE,dd MMM yyyy HH:mm").format();
		_innerEvent.setToDay(day);
		_innerEvent.setToMonth(month);
		_innerEvent.setToYear(year);
		
		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.YEAR, _innerEvent.getToYear());
		toCal.set(Calendar.MONTH, _innerEvent.getToMonth());
		toCal.set(Calendar.DAY_OF_MONTH, _innerEvent.getToDay());
		
		btnD2.setText(new SimpleDateFormat("yyyy-MM-dd").format(toCal.getTime()));
		
		Log.d(LOG_TAG, "DateSet ToYear: " + _innerEvent.getToYear() + " DateSet ToMonth: " + _innerEvent.getToMonth());
	}
}
