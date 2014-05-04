package ajman.university.grad.project.eventshare.common.contracts;

import java.util.List;

import ajman.university.grad.project.eventshare.common.models.Event;

public interface ILocalStorageService {
	public void addEvent(Event event) throws Exception;
	public void updateEvent(Event event) throws Exception;
	public void removeEvent(Event event) throws Exception;
	public List<Event> getAllEvents() throws Exception;
	public void deleteDeclinedEvents() throws Exception;
}
