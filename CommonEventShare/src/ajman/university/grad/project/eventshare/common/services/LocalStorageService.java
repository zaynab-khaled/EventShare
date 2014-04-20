package ajman.university.grad.project.eventshare.common.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.db.*;
import ajman.university.grad.project.eventshare.common.helpers.ApplicationContextProvider;

public class LocalStorageService implements ILocalStorageService {

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
	
	private Context getApplicationContext() {
		return ApplicationContextProvider.getContext();		
	}
}
