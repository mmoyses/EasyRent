package edu.wpi.easyrent;

import edu.wpi.easyrent.util.DBOperator;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
				
		Button apartmentsBtn = (Button) this.findViewById(R.id.apartmentsBtn);
		apartmentsBtn.setOnClickListener(this);		
		Button btnmaintRequest = (Button) this.findViewById(R.id.btnMaintRequest);
		btnmaintRequest.setOnClickListener(this);		
		Button btnEmployeelogin = (Button) this.findViewById(R.id.btnEmployeelogin);
		btnEmployeelogin.setOnClickListener(this);		
		TextView txtAbout = (TextView) findViewById(R.id.txtAbout);
		txtAbout.append("EasyRent prevails as a leader in exclusive communities in different parts of United States. With an expert management team, our one hundred units are maintained inside and out in a highly professional manner. Our Staff takes great pride in providing the best rental experience available from the moment you arrive to tour through the leasing process until the time you choose to move on.");
		// copy database file
				try {
					DBOperator.copyDB(getBaseContext());
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.homemenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v) {
		int id = v.getId();
		
		if (id == R.id.apartmentsBtn) {
			Intent intent = new Intent(this, ApartmentsActivity.class);
			this.startActivity(intent);
		}  else if (id == R.id.btnMaintRequest) {
			Intent intent = new Intent(this, LoginActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.btnEmployeelogin) {
			Intent intent = new Intent(this, LoginActivity.class);
			this.startActivity(intent);
		}
	}

}
