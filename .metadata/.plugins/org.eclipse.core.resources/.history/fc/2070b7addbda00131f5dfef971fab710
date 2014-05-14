package ajman.university.grad.project.eventshare.common.services;

import java.util.ArrayList;
import java.util.List;

import ajman.university.grad.project.eventshare.common.contracts.ITagService;
import ajman.university.grad.project.eventshare.common.models.Event;

public class FakeNfcTagService implements ITagService{

	@Override
	public List<Event> readEvents() {
		List<Event> readEvents = new ArrayList<Event>();
		Event event = new Event();
		event.setId(2);
		event.setTitle("Heart Transplant");
		event.setDepartment("Neurology");
		event.setDescription("Patient has low blood count");
		event.setNameDoc("Dr. Suhail Al Rukn");
		event.setNamePat("John Carter");
		event.setLocation("OR 3");
		event.setFromDay(13);
		event.setFromMonth(4);
		event.setFromYear(2014);
		event.setFromDayHour(20);
		event.setFromMinute(07);
		event.setAlarmable(false);
		event.setExpired(false);
		readEvents.add(event);
		
		event = new Event();
		event.setId(3);
		event.setTitle("Biopsy");
		event.setDepartment("Neurology");
		event.setDescription("Patient has requested anesthesia");
		event.setNameDoc("Dr. Suhail Al Rukn");
		event.setNamePat("Kelse Milllers");
		event.setLocation("OR 4");
		event.setFromDay(13);
		event.setFromMonth(4);
		event.setFromYear(2014);
		event.setFromDayHour(21);
		event.setFromMinute(05);
		event.setAlarmable(false);
		event.setExpired(false);
		readEvents.add(event);
		
		event = new Event();
		event.setId(4);
		event.setTitle("Brain Tumor Removal");
		event.setDepartment("Neurology");
		event.setDescription("No info");
		event.setNameDoc("Dr. Mohammed Saadah");
		event.setNamePat("Tim Parker");
		event.setLocation("OR 1");
		event.setFromDay(13);
		event.setFromMonth(4);
		event.setFromYear(2014);
		event.setFromDayHour(21);
		event.setFromMinute(03);
		event.setAlarmable(false);
		event.setExpired(false);
		readEvents.add(event);
		
		return readEvents;
	}

	@Override
	public void writeEvents(List<Event> events) {
	}
}
