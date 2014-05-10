package ajman.university.grad.project.eventshare.user;

import ajman.university.grad.project.eventshare.adapters.EventsAdapter;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.user.helpers.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {

	ListView list;
	EventsAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Code for list
        list = (ListView) findViewById(android.R.id.list);
        adapter = new EventsAdapter(this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        
        System.out.println("count: " + adapter.getCount());
        
    }
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(MainActivity.this, DetailEventActivity.class);
		Event event = (Event) adapter.getItem(arg2);
		
//		System.out.println("arg2: " + arg2);
//		System.out.println("toDay: " + ((Event) adapter.getItem(arg2)).getToDay());
//		System.out.println("toYear: " + ((Event) adapter.getItem(arg2)).getToYear());
//		System.out.println("toMonth: " + ((Event) adapter.getItem(arg2)).getToMonth());
//		
		intent.putExtra(Constants.CLICKED_EVENT, event);
		startActivity(intent);
	}

	// From here on is the code for the action bar buttons and menu
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

		default:
			return super.onOptionsItemSelected(item);
    	}
    	
    }

	private void readTag() {
		Intent intent = new Intent(MainActivity.this, ReadTagActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.onCreate(null);
	}
}
