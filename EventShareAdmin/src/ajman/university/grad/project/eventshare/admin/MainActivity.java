package ajman.university.grad.project.eventshare.admin;


import android.os.Bundle;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
	private static final String LOG_TAG = "Main Activity";
	
	private ILocalStorageService service = ServicesFactory.getLocalStorageService();
	
	private Button btnLogin;
	private Button btnDepartments;
	private TextView tvSchedule;
	private EditText etPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();
	
		service.setAdminPassword("nfc123");

		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String password = service.getAdminPassword();
//				Log.d(LOG_TAG, "password: " + password);
//				
//				if(etPass.getText().length() == 0){
//					Toast.makeText(getApplicationContext(), "No password has been entered!", Toast.LENGTH_SHORT).show();
//				} 
//				else if (etPass.getText().toString().equals(password)){
//					if (btnDepartments.getText().toString().equals("Neurology") || btnDepartments.getText().toString().equals("Cardiology")) {
//						Intent intent = new Intent(MainActivity.this, ListActivity.class);
//						startActivity(intent);
//						finish();
//					}
//					else
//						Toast.makeText(getApplicationContext(), "This department is coming soon!", Toast.LENGTH_SHORT).show();
//
//				}
//				else 
//					Toast.makeText(getApplicationContext(), "Sorry, invalid password", Toast.LENGTH_SHORT).show();
//			}
				String password = service.getAdminPassword();
				
				if(service.getAdminDepartment().equals("")) 
					Toast.makeText(getApplicationContext(), "Choose a department", Toast.LENGTH_SHORT).show();
				else if(service.getAdminDepartment().equals("Neurology")) {
					Log.d(LOG_TAG, "Password: " + password);
					
					if(etPass.getText().length() == 0){
						Log.d(LOG_TAG, "Password: " + password);
						Toast.makeText(getApplicationContext(), "No password has been entered!", Toast.LENGTH_SHORT).show();
					} 
					else if (etPass.getText().toString().equals("neuro")){
						Intent intent = new Intent(MainActivity.this, ListActivity.class);
						startActivity(intent);
						finish();
					}				
					else 
						Toast.makeText(getApplicationContext(), "Sorry, invalid password", Toast.LENGTH_SHORT).show();
				}
				else if(service.getAdminDepartment().equals("Cardiology")){
					Log.d(LOG_TAG, "Password: " + password);
					
					if(etPass.getText().length() == 0){
						Log.d(LOG_TAG, "Password: " + password);
						Toast.makeText(getApplicationContext(), "No password has been entered!", Toast.LENGTH_SHORT).show();
					} 
					else if (etPass.getText().toString().equals("card")){
						Intent intent = new Intent(MainActivity.this, ListActivity.class);
						startActivity(intent);
						finish();
					}
					else 
						Toast.makeText(getApplicationContext(), "Sorry, invalid password", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getApplicationContext(), "This department is coming soon!", Toast.LENGTH_SHORT).show();
			}
		});
	}

    private void setUpViews() { 	
    	final ArrayAdapter<?> adapterDepartment = ArrayAdapter.createFromResource(this,R.array.arrayDepartments, android.R.layout.simple_spinner_dropdown_item);
    	
    	tvSchedule = (TextView) findViewById(R.id.tv_schedule);
    	etPass = (EditText) findViewById(R.id.editText_password);
		btnLogin = (Button) findViewById(R.id.btn_okay);
		btnDepartments = (Button) findViewById(R.id.btnDepartments);
		
		tvSchedule.setGravity(Gravity.CENTER);
        etPass.setGravity(Gravity.CENTER);
        btnLogin.setGravity(Gravity.CENTER);
        btnDepartments.setGravity(Gravity.CENTER);
        
        tvSchedule.setText("Operation Schedule");
        
        btnDepartments.setText(service.getAdminDepartment().equals("") ? "Select Department" : service.getAdminDepartment());
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
				    		service.setAdminDepartment("Cardiology");
				    		break;
				    	case 1:
				    		btnDepartments.setText("Cancer Care Unit");
				    		service.setAdminDepartment("Cancer Care Unit");
				    		break;
				    	case 2:
				    		btnDepartments.setText("Dermatology");
				    		service.setAdminDepartment("Dermatology");
				    		break;
				    	case 3:
				    		btnDepartments.setText("Diabetology");
				    		service.setAdminDepartment("Diabetology");
				    		break;
				    	case 4:
				    		btnDepartments.setText("Digestive Diseases");
				    		service.setAdminDepartment("Digestive Diseases");
				    		break;
				    	case 5:
				    		btnDepartments.setText("Medical ICU");
				    		service.setAdminDepartment("Medical ICU");
				    		break;
				    	case 6:
				    		btnDepartments.setText("Surgical ICU");
				    		service.setAdminDepartment("Surgical ICU");
				    		break;
				    	case 7:
				    		btnDepartments.setText("Neurology");
				    		service.setAdminDepartment("Neurology");
				    		break;
				    	case 8:
				    		btnDepartments.setText("Orthopedics");
				    		service.setAdminDepartment("Orthopedics");
				    		break;
				    	case 9:
				    		btnDepartments.setText("Pediatric");
				    		service.setAdminDepartment("Pediatric");
				    		break;
				    	case 10:
				    		btnDepartments.setText("Plastic Surgery");
				    		service.setAdminDepartment("Plastic Surgery");
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
		service.setAdminDepartment(tvDepartment.getText().toString());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
