package ajman.university.grad.project.eventshare.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class eventsharehelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "eventdatabase";
	private static final String TABLE_NAME = "EVENTTABLE";
	private static final int DATABASE_VERSION = 1;
	private static final String UID = "_id";
	private static final String NAME = "Name";
	private static final String LOCATION = "Location";
	private static final String DESCRIPTION = "Description";
	private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+LOCATION+" VARCHAR(255), "+DESCRIPTION+" VARCHAR(255)";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
	
	//private Context context;
	
	
	public eventsharehelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//this.context = context;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {

		try {
			db.execSQL(CREATE_TABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Message.message(context, ""+e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		try {
			db.execSQL(DROP_TABLE);
			onCreate(db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//
		}
		

	}

}
