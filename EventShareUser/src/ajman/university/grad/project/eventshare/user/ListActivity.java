package ajman.university.grad.project.eventshare.user;

import ajman.university.grad.project.eventshare.adapters.EventsAdapter;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.helpers.Constants;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListActivity extends Activity implements OnItemClickListener {
	private static final String LOG_TAG = "List Activity User";
	
	private ILocalStorageService service = ServicesFactory.getLocalStorageService();
	
	private ListView list;
	private EventsAdapter adapter;
	private TextView tvDepartment;
	private TextView tvSchedule;
	private ImageView startingImage;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setUpViews();
        
        getActionBar().setBackgroundDrawable(new ColorDrawable(0xff33b5e5));
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		
        startingImage = (ImageView) findViewById(R.id.start_image);
		
		if(list.getCount() != 0) {
        	startingImage.setVisibility(View.GONE);
        } else
        	startingImage.setVisibility(View.VISIBLE);
		
    }
    
	private void setUpViews() {
		String dept = service.getUserDepartment();
		
		tvSchedule = (TextView) findViewById(R.id.tv_schedule);
		tvDepartment = (TextView) findViewById(R.id.tv_department);
		
        tvSchedule.setGravity(Gravity.CENTER);
        tvDepartment.setGravity(Gravity.CENTER);
        
        tvSchedule.setText("Operating Schedule");
        tvDepartment.setText(dept == null ? "Unknown" : dept + " Department");

        //Code for list
        list = (ListView) findViewById(android.R.id.list);
        adapter = new EventsAdapter(this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(ListActivity.this, DetailEventActivity.class);
		Event event = (Event) adapter.getItem(arg2);

		intent.putExtra(Constants.CLICKED_EVENT, event);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//return super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {			
			case R.id.readTag:
				readTag();
				return true;
			case R.id.sortByDoc:
				filter();
				return true;
	
			default:
				return super.onOptionsItemSelected(item);
    	}
    }

	private void filter() {
		ArrayAdapter<?> adapterFilter = null;
    	String [] doctors = null;
    	
		if(service.getUserDepartment().equals("Neurology")) {
			adapterFilter = ArrayAdapter.createFromResource(this,R.array.arrayFilterN, android.R.layout.simple_spinner_dropdown_item);
			doctors = getResources().getStringArray(R.array.arrayFilterN);
		} else {
			adapterFilter = ArrayAdapter.createFromResource(this,R.array.arrayFilterC, android.R.layout.simple_spinner_dropdown_item);
			doctors = getResources().getStringArray(R.array.arrayFilterC);
		}
		
		showDialog(adapterFilter, doctors);
	}

	private void readTag() {
		Intent intent = new Intent(ListActivity.this, ReadTagActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.onCreate(null);
	}
	
    @Override
    public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);	  
    }
    
	private void showDialog(ArrayAdapter<?> adapterData, String [] doctors) {
		final String [] items = doctors;
		new AlertDialog.Builder(ListActivity.this)
		.setTitle("Filter by .. ")
		.setAdapter(adapterData, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	String docName = items[which];
		    	if (items != null && !docName.toString().equals("Filter All")) {   	 
			    	Log.d(LOG_TAG, "Doc name: " + docName);
			        setupListAdapter(docName);
		    	} else {
			        setupListAdapter(null);
		    	}
		        dialog.dismiss();
		    }
		  }).create().show();
	}

	private void setupListAdapter(String docName) {
		if (docName != null)
			adapter = new EventsAdapter(this, docName);
		else
			adapter = new EventsAdapter(this);
		
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		list.invalidate();
	}
}
