package ajman.university.grad.project.eventshare.common.repositories;

public class RepositoriesFactory {
	private static EventsRepository _eventsRepository;
	
	public static EventsRepository getEventsRepository() {
		if (_eventsRepository == null) {
			_eventsRepository = new EventsRepository();
		}
		
		return _eventsRepository;
	}
}
