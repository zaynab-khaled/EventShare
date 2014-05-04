package ajman.university.grad.project.eventshare.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ajman.university.grad.project.eventshare.admin.R;
import ajman.university.grad.project.eventshare.common.contracts.IErrorService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsAdapter extends BaseAdapter {
	private List<Event> events = new ArrayList<Event>();
	private Context context;
	private int nrOfValidEvents;
	private final static int tagSize = (256 - 40) * 16;
	//(nr of block - trailer blocks) * block size = 3456 bytes;

	public EventsAdapter(Context c) {
		context = c;
		events = new ArrayList<Event>();

		// Get the events from the events repository
		ILocalStorageService service = ServicesFactory.getLocalStorageService();
		try {
			List<Event> storedEvents = service.getAllEvents();
			for (Event event : storedEvents) {
				events.add(event);
			}
		} catch (Exception e) {
			// TODO: Error service
			IErrorService eService = ServicesFactory.getErrorService();
			eService.log(e);
		}
	}

	@Override
	public int getCount() {
		return events.size();
	}

	@Override
	public Object getItem(int i) {
		return events.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		
		String LOG_TAG = "getView List";
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Event event = events.get(i);
		Calendar currentCal = Calendar.getInstance();
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
		View row;
		
		if (toCal.compareTo(currentCal) == -1) {
			// contains a reference to the Linear layout
			Log.d(LOG_TAG, "Event: " + event);
			row = inflater.inflate(R.layout.expired_row_list, viewGroup, false);
			event.setExpired(true); }
		else {
			row = inflater.inflate(R.layout.single_row_list, viewGroup, false);
			event.setExpired(false); }
		
		TextView title = (TextView) row.findViewById(R.id.textView1);
		TextView location = (TextView) row.findViewById(R.id.textView2);
		TextView fromDate = (TextView) row.findViewById(R.id.textView3);
		
		Log.d(LOG_TAG, "Title: " + title + " Event: " + event);
		title.setText(event.getTitle());
		location.setText(event.getLocation());
		fromDate.setText(new SimpleDateFormat("EEE, dd MMM yyyy").format(fromCal.getTime()) + " - " + new SimpleDateFormat("EEE, dd MMM yyyy").format(toCal.getTime()));
		

		return row; // return the rootView of the single_row_list.xml
	}

	// Final format should be 20140924T185545Z
	private String formatToDate(Event event) {
		String date = "";
		date += event.getToYear();
		date += (event.getToMonth() < 10 ? "0" + (event.getToMonth() + 1) : event.getToMonth() + 1);
		date += (event.getToDay() < 10 ? "0" + event.getToDay() : event.getToDay());
		date += "T";
		date += (event.getToDayHour() < 10 ? "0" + event.getToDayHour() : event.getToDayHour());
		date += (event.getToMinute() < 10 ? "0" + event.getToMinute() : event.getToMinute());
		date += "00Z";
		return date;
	}

	private String formatFromDate(Event event) {
		String date = "";
		date += event.getFromYear();
		date += (event.getFromMonth() < 10 ? "0" + (event.getFromMonth() + 1) : event.getFromMonth() + 1);
		date += (event.getFromDay() < 10 ? "0" + event.getFromDay() : event.getFromDay());
		date += "T";
		date += (event.getFromDayHour() < 10 ? "0" + event.getFromDayHour() : event.getFromDayHour());
		date += (event.getFromMinute() < 10 ? "0" + event.getFromMinute() : event.getFromMinute());
		date += "00Z";
		return date;
	}

	@Override
	public String toString() {
		String sep = System.getProperty("line.separator");
		String vCal = "";
		String vCalPrev = "";
		String vEvent = "";
		nrOfValidEvents = 0;

		vCal += "BEGIN:VCALENDAR" + sep +
				"VERSION:2.0" + sep +
				"PRODID:-//yusra/cal//TEST //EN" + sep;

		for (int i = 0; i < events.size(); i++) {
			if (!isExpired(events.get(i))) {
				vEvent = "BEGIN:VEVENT" + sep;
				vEvent += "DTSTART:" + formatFromDate(events.get(i)) + sep;
				vEvent += "DTEND:" + formatToDate(events.get(i)) + sep;
				vEvent += "SUMMARY:" + events.get(i).getTitle() + sep;
				vEvent += "DESCRIPTION:" + events.get(i).getDescription() + sep;
				vEvent += "UID:" + events.get(i).getId() + sep;
				vEvent += "LOCATION:" + events.get(i).getLocation() + sep;
				vEvent += "END:VEVENT" + sep;

				vCal += vEvent;

				if (vCal.getBytes().length < (tagSize - "END:VCALENDAR".getBytes().length)) {
					vCalPrev = vCal;
					nrOfValidEvents++;
					System.out.println("nrofevents = " + nrOfValidEvents);
					System.out.println("size = " + vCal.getBytes().length + " bytes");
				}
				else {
					vCal = vCalPrev;
					break;
				}
			}
		}

		vCal += "END:VCALENDAR";
		return vCal;
	}

	private boolean isExpired(Event event) {
		
		Calendar toCal = Calendar.getInstance();
		toCal.set(Calendar.YEAR, event.getToYear());
		toCal.set(Calendar.MONTH, event.getToMonth());
		toCal.set(Calendar.DAY_OF_MONTH, event.getToDay());
		toCal.set(Calendar.HOUR_OF_DAY, event.getToDayHour());
		toCal.set(Calendar.MINUTE, event.getToMinute());
	
		Calendar c = Calendar.getInstance();

		if (toCal.getTimeInMillis() < c.getTimeInMillis()) { 
			return true;
		}
		return false;
	}
	
	public int getValidCount() {
		return nrOfValidEvents;
	}
}
