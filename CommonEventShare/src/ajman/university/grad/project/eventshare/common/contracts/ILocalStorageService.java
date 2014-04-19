package ajman.university.grad.project.eventshare.common.contracts;

import java.util.List;

import ajman.university.grad.project.eventshare.common.shared.models.Event;

public interface ILocalStorageService {
	public void addEvent(Event event);
	public void removeEvent(Event event);
	public List<Event> getAllEvents();
}
