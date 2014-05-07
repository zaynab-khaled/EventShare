package ajman.university.grad.project.eventshare.admin;


import android.os.Bundle;
import ajman.university.grad.project.eventshare.admin.helpers.Constants;
import ajman.university.grad.project.eventshare.admin.helpers.SharedPref;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener{

	private static String LOG_TAG = "Main Activity";
	
	private Button btnOkay;
	private Button btnDepartments;
	private TextView tvSchedule;
	private EditText etPass;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();
		
		//Set password in SharedPref
		SharedPref.setDefaults(Constants.PASSWORD, "Neurology101" , getApplicationContext());

		btnOkay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String password = SharedPref.getDefaults(Constants.PASSWORD, getApplicationContext());
				String deptName = SharedPref.getDefaults(Constants.DEPARTMENT, getApplicationContext());
				
				if(etPass.getText().length() == 0){
					Toast.makeText(getApplicationContext(), "No password has been entered!", Toast.LENGTH_SHORT).show();
				} 
				else if (etPass.getText().toString().equals(password) && !deptName.equals("Neurology"))
					Toast.makeText(getApplicationContext(), "Are you sure you've chosen the correct department?", Toast.LENGTH_SHORT).show();
				
				else if (etPass.getText().toString().equals(password) && deptName.equals("Neurology")){
					Intent intent = new Intent(MainActivity.this, ListActivity.class);
					startActivity(intent);
					finish();
				}
				else 
					Toast.makeText(getApplicationContext(), "Sorry, invalid password", Toast.LENGTH_SHORT).show();
			}
		});
	}

    private void setUpViews() {
    	final ArrayAdapter adapterDepartment = ArrayAdapter.createFromResource(this,R.array.arrayDepartments, android.R.layout.simple_spinner_dropdown_item);
    	
    	tvSchedule = (TextView) findViewById(R.id.tv_schedule);
		etPass = (EditText) findViewById(R.id.editText_password);
		btnOkay = (Button) findViewById(R.id.btn_okay);
		btnDepartments = (Button) findViewById(R.id.btnDepartments);
		
        tvSchedule.setGravity(Gravity.CENTER);
        etPass.setGravity(Gravity.CENTER);
        btnOkay.setGravity(Gravity.CENTER);
        
        tvSchedule.setText("Operation Schedule");
        
        btnDepartments.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("Select Department")
				.setAdapter(adapterDepartment, new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog, int which) {

				    	switch(which) {
				    	case 0:
				    		btnDepartments.setText("Cardiology");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 1:
				    		btnDepartments.setText("Cancer Care Unit");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 2:
				    		btnDepartments.setText("Dermatology");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 3:
				    		btnDepartments.setText("Diabetology");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 4:
				    		btnDepartments.setText("Digestive Diseases");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 5:
				    		btnDepartments.setText("Medical ICU");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 6:
				    		btnDepartments.setText("Surgical ICU");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 7:
				    		btnDepartments.setText("Neurology");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 8:
				    		btnDepartments.setText("Orthopedics");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 9:
				    		btnDepartments.setText("Pediatric");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	case 10:
				    		btnDepartments.setText("Plastic Surgery");
				    		SharedPref.setDefaults(Constants.DEPARTMENT, btnDepartments.getText().toString(), getApplicationContext());
				    		break;
				    	}

				      dialog.dismiss();
				    }
				  }).create().show();
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
		TextView tvDepartment = (TextView) view;
		SharedPref.setDefaults(Constants.DEPARTMENT, tvDepartment.getText().toString(), getApplicationContext());
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
