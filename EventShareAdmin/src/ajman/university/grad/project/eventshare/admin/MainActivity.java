package ajman.university.grad.project.eventshare.admin;

import android.os.Bundle;
import ajman.university.grad.project.eventshare.common.contracts.IAlarmService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String LOG_TAG = "Main Activity";

	private ILocalStorageService service = ServicesFactory.getLocalStorageService();
	private IAlarmService alarmService = ServicesFactory.getAlarmService();

	private Button btnLogin;
	private Button btnDepartments;
	private EditText etPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();

		getActionBar().setBackgroundDrawable(new ColorDrawable(0xff33b5e5));
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);

		service.setAdminPassword("nfc123");

		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String password = service.getAdminPassword();

				if (btnDepartments.getText().equals("Select Department")) {
					Toast.makeText(getApplicationContext(), "Choose a department", Toast.LENGTH_SHORT).show();
				}
				else if (service.getAdminDepartment().equals("Neurology")) {
					Log.d(LOG_TAG, "Password: " + password);

					if (etPass.getText().length() == 0) {
						Log.d(LOG_TAG, "Password: " + password);
						Toast.makeText(getApplicationContext(), "No password has been entered!", Toast.LENGTH_SHORT).show();
					}
					else if (etPass.getText().toString().equals("neuro")) {
						alarmService.start();
						Intent intent = new Intent(MainActivity.this, ListActivity.class);
						startActivity(intent);
						finish();
					}
					else
						Toast.makeText(getApplicationContext(), "Sorry, invalid password", Toast.LENGTH_SHORT).show();
				}
				else if (service.getAdminDepartment().equals("Cardiology")) {
					Log.d(LOG_TAG, "Password: " + password);

					if (etPass.getText().length() == 0) {
						Log.d(LOG_TAG, "Password: " + password);
						Toast.makeText(getApplicationContext(), "No password has been entered!", Toast.LENGTH_SHORT).show();
					}
					else if (etPass.getText().toString().equals("card")) {
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
		final ArrayAdapter<?> adapterDepartment = ArrayAdapter.createFromResource(this, R.array.arrayDepartments,
				android.R.layout.simple_spinner_dropdown_item);

		etPass = (EditText) findViewById(R.id.editText_password);
		btnLogin = (Button) findViewById(R.id.btn_okay);
		btnDepartments = (Button) findViewById(R.id.btnDepartments);

		etPass.setGravity(Gravity.CENTER);
		btnLogin.setGravity(Gravity.CENTER);
		btnDepartments.setGravity(Gravity.CENTER);

		btnDepartments.setText(service.getAdminDepartment().equals("") ? "Select Department" : service.getAdminDepartment());
		btnDepartments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String[] departments = null;

				departments = getResources().getStringArray(
						R.array.arrayDepartments);
				final String[] items = departments;

				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Select Department")
						.setAdapter(adapterDepartment, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								String deptName = items[which];
								btnDepartments.setText(deptName);
								service.setAdminDepartment(btnDepartments.getText().toString());
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
