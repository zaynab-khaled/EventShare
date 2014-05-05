package ajman.university.grad.project.eventshare.admin;

import java.util.List;

import android.os.Bundle;
import ajman.university.grad.project.eventshare.admin.helpers.Constants;
import ajman.university.grad.project.eventshare.adapters.EventsAdapter;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemSelectedListener{

	private Event event;
	private Spinner sp_dept;
	private Button btn;
	private String department;
	private TextView tv_schedule;
	private EditText et_pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)findViewById(R.id.btn_okay);
		sp_dept = (Spinner) findViewById(R.id.sp_department);
		tv_schedule = (TextView) findViewById(R.id.tv_schedule);
		et_pass = (EditText) findViewById(R.id.editText_password);
		
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.departments_array,android.R.layout.simple_spinner_item);
		sp_dept.setAdapter(adapter);
		sp_dept.setOnItemSelectedListener(this);
		
        tv_schedule.setText("Operation Schedule");
        
        tv_schedule.setGravity(Gravity.CENTER);
        sp_dept.setGravity(Gravity.CENTER);
        et_pass.setGravity(Gravity.CENTER);
        btn.setGravity(Gravity.CENTER);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ListActivity.class);
				intent.putExtra(Constants.DEPARTMENT, department);
				startActivity(intent);
				finish();
			}
		});
		
	}
	

	
    @Override
    public void onBackPressed() {
    	this.finish();
    	   return;
    }
	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		department = sp_dept.getSelectedItem().toString();	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

}
