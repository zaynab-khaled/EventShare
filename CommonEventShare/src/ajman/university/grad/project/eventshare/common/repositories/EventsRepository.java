package ajman.university.grad.project.eventshare.common.repositories;

import java.util.ArrayList;
import java.util.List;

import ajman.university.grad.project.eventshare.common.shared.models.Event;

public class EventsRepository {
	private List<Event> _events;
	
	public EventsRepository() {
		_events = new ArrayList<Event> ();

		// TODO: Load from a local storage
		String testTitle = "Title";
		String testDesc = "Description";
		String testLocation = "Location";
		//String fromDate = "fromDate";
		//String toDate = "toDate";
		//int rowID = "rowID";
		
		
	}
	
	public void addEvent(Event event) {
		_events.add(event);
	}
	
	// You can add filtering or what have you!!
	public List<Event> getAllEvents() {
		return _events;
	}
}
