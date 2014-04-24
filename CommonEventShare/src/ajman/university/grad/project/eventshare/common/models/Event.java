package ajman.university.grad.project.eventshare.common.models;

import java.io.Serializable;

public class Event implements Serializable {
	private static final long serialVersionUID = 4861948165540136263L;

	private int id;
	private String title;
	private String description;
	private String location;
	private int fromDayHour;
	private int fromMinute;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private int toDayHour;
	private int toMinute;
	private int toYear;
	private int toMonth;
	private int toDay;
	
	public Event () {
		this.setId(-1);
		this.setTitle("");
		this.setDescription("");
		this.setLocation("");
		this.setFromDayHour(-1);
		this.setFromDay(-1);
		this.setFromMinute(-1);
		this.setFromMonth(-1);
		this.setFromYear(-1);
		this.setToDayHour(-1);
		this.setToDay(-1);
		this.setToMinute(-1);
		this.setToMonth(-1);
		this.setToYear(-1);
	}

	public Event(int id, String title, String description, String location,
			int fromDayHour, int fromMinute, int fromYear, int fromMonth,
			int fromDay, int toDayHour, int toMinute, int toYear, int toMonth,
			int toDay) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.fromDayHour = fromDayHour;
		this.fromMinute = fromMinute;
		this.fromYear = fromYear;
		this.fromMonth = fromMonth;
		this.fromDay = fromDay;
		this.toDayHour = toDayHour;
		this.toMinute = toMinute;
		this.toYear = toYear;
		this.toMonth = toMonth;
		this.toDay = toDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getFromDayHour() {
		return fromDayHour;
	}

	public void setFromDayHour(int fromDayHour) {
		this.fromDayHour = fromDayHour;
	}

	public int getFromMinute() {
		return fromMinute;
	}

	public void setFromMinute(int fromMinute) {
		this.fromMinute = fromMinute;
	}

	public int getFromYear() {
		return fromYear;
	}

	public void setFromYear(int fromYear) {
		this.fromYear = fromYear;
	}

	public int getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(int fromMonth) {
		this.fromMonth = fromMonth;
	}

	public int getFromDay() {
		return fromDay;
	}

	public void setFromDay(int fromDay) {
		this.fromDay = fromDay;
	}

	public int getToDayHour() {
		return toDayHour;
	}

	public void setToDayHour(int toDayHour) {
		this.toDayHour = toDayHour;
	}

	public int getToMinute() {
		return toMinute;
	}

	public void setToMinute(int toMinute) {
		this.toMinute = toMinute;
	}

	public int getToYear() {
		return toYear;
	}

	public void setToYear(int toYear) {
		this.toYear = toYear;
	}

	public int getToMonth() {
		return toMonth;
	}

	public void setToMonth(int toMonth) {
		this.toMonth = toMonth;
	}

	public int getToDay() {
		return toDay;
	}

	public void setToDay(int toDay) {
		this.toDay = toDay;
	}
}
