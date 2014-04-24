package ajman.university.grad.project.eventshare.common.services;

import java.util.List;

import ajman.university.grad.project.eventshare.common.contracts.ITagService;
import ajman.university.grad.project.eventshare.common.models.Event;

public class NfcTagService implements ITagService{

	@Override
	public List<Event> readEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeEvents(List<Event> events, boolean isSecure) {
		// TODO Auto-generated method stub
		if (isSecure)
			writeEventsWithSecurity(events);
		else 
			writeEventsWithoutSecurity(events);
		
	}
	
	/* PRIVATE METHODS */
	private void writeEventsWithoutSecurity(List<Event> events) {
		
	}
	
	private void writeEventsWithSecurity(List<Event> events) {
		
	}
}
