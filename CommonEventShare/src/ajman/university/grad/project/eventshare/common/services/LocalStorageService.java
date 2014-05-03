package ajman.university.grad.project.eventshare.common.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.db.*;
import ajman.university.grad.project.eventshare.common.helpers.ApplicationContextProvider;

public class LocalStorageService implements ILocalStorageService {
	private static String LOG_TAG = "LocalStorageService";
	
	@Override
	public void addEvent(Event event) throws Exception {
		EventsDataSource ds = null;
				
    	try {
	    	ds = new EventsDataSource(getApplicationContext());
	    	ds.open();
	    	ds.insert(event);
    	} catch (Exception e) {
	    	throw new Exception (e.getMessage());
    	} finally {
    		if (ds != null)
    			ds.close();
    	}
	}

	@Override
	public void updateEvent(Event event) throws Exception {
		EventsDataSource ds = null;
		
    	try {
	    	ds = new EventsDataSource(getApplicationContext());
	    	ds.open();
	    	ds.delete(event);
	    	ds.insert(event);
    	} catch (Exception e) {
	    	throw new Exception (e.getMessage());
    	} finally {
    		if (ds != null)
    			ds.close();
    	}
	}

	@Override
	public void removeEvent(Event event) throws Exception {
		EventsDataSource ds = null;
		
    	try {
	    	ds = new EventsDataSource(getApplicationContext());
	    	ds.open();
	    	ds.delete(event);
    	} catch (Exception e) {
	    	throw new Exception (e.getMessage());
    	} finally {
    		if (ds != null)
    			ds.close();
    	}
	}

	@Override
	public List<Event> getAllEvents() throws Exception {
		List<Event> events = new ArrayList <Event>();
	    
    	EventsDataSource ds = null;
    	
    	try {
	    	ds = new EventsDataSource(getApplicationContext());
	    	ds.open();
	    	events = ds.retrieveAll(1000);
    	} catch (Exception e) {
	    	throw new Exception (e.getMessage());
    	} finally {
    		if (ds != null)
    			ds.close();
    	}
	    
	    return events;
	}
	
	public void deleteExpiredEvents() throws Exception {
		List<Event> events = getAllEvents();

		Calendar currentCal = Calendar.getInstance(); 
		
		//Compare each event, compare the event's from date to current 
		for (Event event : events) {
			Calendar eventCal = Calendar.getInstance(); 
			eventCal.set(Calendar.YEAR, event.getToYear());
			eventCal.set(Calendar.MONTH, event.getToMonth());
			eventCal.set(Calendar.DAY_OF_MONTH, event.getToDay());
			eventCal.set(Calendar.HOUR_OF_DAY, event.getToDayHour());
			eventCal.set(Calendar.MINUTE, event.getToMinute());
			Log.d(LOG_TAG, "Event year: " + event.getToYear() + " - month: " + event.getToMonth() + " - day: " + event.getToDay());
			if (eventCal.compareTo(currentCal) == -1) {
				Log.d(LOG_TAG, "Event name: " + event.getTitle() + " will be deleted!");
				removeEvent(event);
			}
		}
	}

	/*** PRIVATE METHODS */
	private Context getApplicationContext() {
		return ApplicationContextProvider.getContext();		
	}
}
