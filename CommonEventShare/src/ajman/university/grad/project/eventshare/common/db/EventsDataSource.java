package ajman.university.grad.project.eventshare.common.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ajman.university.grad.project.eventshare.common.models.Event;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class EventsDataSource {
	private final static String LOG_TAG = "Events Data Source";
	
	private SQLiteDatabase database;
	private EventsSqlLiteHelper dbHelper;
	private String [] allColumns = {
			EventsSqlLiteHelper.COLUMN_ID,
			EventsSqlLiteHelper.COLUMN_TITLE,
			EventsSqlLiteHelper.COLUMN_DESC,
			EventsSqlLiteHelper.COLUMN_LOCATION,
			EventsSqlLiteHelper.COLUMN_FROM_DAY_HOUR,
			EventsSqlLiteHelper.COLUMN_FROM_MINUTE,
			EventsSqlLiteHelper.COLUMN_FROM_YEAR,
			EventsSqlLiteHelper.COLUMN_FROM_MONTH,
			EventsSqlLiteHelper.COLUMN_FROM_DAY,
			EventsSqlLiteHelper.COLUMN_TO_DAY_HOUR,
			EventsSqlLiteHelper.COLUMN_TO_MINUTE,
			EventsSqlLiteHelper.COLUMN_TO_YEAR,
			EventsSqlLiteHelper.COLUMN_TO_MONTH,
			EventsSqlLiteHelper.COLUMN_TO_DAY
	};
	
	public EventsDataSource(Context context) {
		dbHelper = new EventsSqlLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	// Expects the database to be open!!
	public Event insert (Event event) {
		Log.d(LOG_TAG, "insert");
		Event newEvent = null;
		Cursor cursor = null;

		try {
			ContentValues  values = new ContentValues();
			values.put(EventsSqlLiteHelper.COLUMN_TITLE, event.getTitle());
			values.put(EventsSqlLiteHelper.COLUMN_DESC, event.getDescription());
			values.put(EventsSqlLiteHelper.COLUMN_LOCATION, event.getLocation());
			values.put(EventsSqlLiteHelper.COLUMN_FROM_DAY_HOUR, event.getFromDayHour());
			values.put(EventsSqlLiteHelper.COLUMN_FROM_MINUTE, event.getFromMinute());
			values.put(EventsSqlLiteHelper.COLUMN_FROM_YEAR, event.getFromYear());
			values.put(EventsSqlLiteHelper.COLUMN_FROM_MONTH, event.getFromMonth());
			values.put(EventsSqlLiteHelper.COLUMN_FROM_DAY, event.getFromDay());
			values.put(EventsSqlLiteHelper.COLUMN_TO_DAY_HOUR, event.getToDayHour());
			values.put(EventsSqlLiteHelper.COLUMN_TO_MINUTE, event.getToMinute());
			values.put(EventsSqlLiteHelper.COLUMN_TO_YEAR, event.getToYear());
			values.put(EventsSqlLiteHelper.COLUMN_TO_MONTH, event.getToMonth());
			values.put(EventsSqlLiteHelper.COLUMN_TO_DAY, event.getToDay());
			
			long insertId = database.insert(EventsSqlLiteHelper.TABLE_EVENTS, null, values);
			Log.d(LOG_TAG, "inserted id: " + insertId);
			cursor = database.query(EventsSqlLiteHelper.TABLE_EVENTS, allColumns, EventsSqlLiteHelper.COLUMN_ID + " = " + insertId, null, null, null , null);
			cursor.moveToFirst();
			newEvent = cursorToEvent(cursor);
			Log.d(LOG_TAG, "inserted name: " + newEvent.getTitle());
		} catch (Exception e) {
			Log.d(LOG_TAG, "insert exception: " + e.getMessage());
		} finally {
			try {if (cursor != null) cursor.close();} catch (Exception e) {/*Ignore */}
		}

		return newEvent;
	}
	 
	// Expects the database to be open!!
	public void delete(Event event) {
		try {
			long id = event.getId();
			database.delete(EventsSqlLiteHelper.TABLE_EVENTS, EventsSqlLiteHelper.COLUMN_ID + " = " + id, null);
		} catch (Exception e) {
			Log.d(LOG_TAG, "delete exception: " + e.getMessage());
		} finally {
			
		}
	}
	
	// Expects the database to be open!!
	public List<Event> retrieveAll(int records) {
		Log.d(LOG_TAG, "retrieveAll: " + Environment.getDataDirectory());
		List<Event> events = new ArrayList<Event> ();
		Cursor cursor = null;
		String orderBy = EventsSqlLiteHelper.COLUMN_FROM_YEAR + "," + EventsSqlLiteHelper.COLUMN_FROM_MONTH + "," + EventsSqlLiteHelper.COLUMN_FROM_DAY + "," + EventsSqlLiteHelper.COLUMN_FROM_DAY_HOUR + "," + EventsSqlLiteHelper.COLUMN_FROM_MINUTE + "," + EventsSqlLiteHelper.COLUMN_TO_YEAR + "," + EventsSqlLiteHelper.COLUMN_TO_MONTH + "," + EventsSqlLiteHelper.COLUMN_TO_DAY + "," + EventsSqlLiteHelper.COLUMN_TO_DAY_HOUR + "," + EventsSqlLiteHelper.COLUMN_TO_MINUTE;
		
		try {
			cursor = database.query(EventsSqlLiteHelper.TABLE_EVENTS, allColumns, null, null, orderBy, null, EventsSqlLiteHelper.COLUMN_TITLE, "" + records);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				events.add(cursorToEvent(cursor));
				cursor.moveToNext();
			} 
		} catch (Exception e) {
			Log.d(LOG_TAG, "retrieveAll exception: " + e.getMessage());
		} finally {
			try {if (cursor != null) cursor.close();} catch (Exception e) {/*Ignore */}
		}

		return events;
	}

	private Event cursorToEvent(Cursor cursor) {
		Log.d(LOG_TAG, "cursorToEvent id: " + cursor.getInt(0));
		Event event = new Event();
		event.setId(cursor.getInt(0));
		event.setTitle(cursor.getString(1));
		event.setDescription(cursor.getString(2));
		event.setLocation(cursor.getString(3));
		event.setFromDayHour(cursor.getInt(4));
		event.setFromMinute(cursor.getInt(5));
		event.setFromYear(cursor.getInt(6));
		event.setFromMonth(cursor.getInt(7));
		event.setFromDay(cursor.getInt(8));
		event.setToDayHour(cursor.getInt(9));
		event.setToMinute(cursor.getInt(10));
		event.setToYear(cursor.getInt(11));
		event.setToMonth(cursor.getInt(12));
		event.setToDay(cursor.getInt(13));
		
		String st = "";
		for (int i = 0; i < 14; i++) {
			st += cursor.getString(i) + " | ";
		}
		System.out.println(st);
		return event;
	}
}
