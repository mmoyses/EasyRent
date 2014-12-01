package edu.wpi.easyrent;

import edu.wpi.easyrent.security.SecurityContext;
import edu.wpi.easyrent.util.DBOperator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener {
	
	private Button apartmentsBtn, requestsBtn, createRequestBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		apartmentsBtn = (Button) this.findViewById(R.id.apartmentsBtn);
		apartmentsBtn.setOnClickListener(this);
		requestsBtn = (Button) this.findViewById(R.id.requestsBtn);
		requestsBtn.setOnClickListener(this);
		createRequestBtn = (Button) this.findViewById(R.id.createRequestBtn);
		createRequestBtn.setOnClickListener(this);
		
		SecurityContext ctx = SecurityContext.getInstance();
		if (ctx.isLandlord()) {
			apartmentsBtn.setVisibility(View.INVISIBLE);
			createRequestBtn.setVisibility(View.INVISIBLE);
		} else {
			apartmentsBtn.setVisibility(View.INVISIBLE);
			requestsBtn.setVisibility(View.INVISIBLE);
		}
		
		// copy database file
		try {
			DBOperator.copyDB(getBaseContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.apartmentsBtn) {
			Intent intent = new Intent(this, ApartmentsActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.requestsBtn) {
			Intent intent = new Intent(this, RequestsActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.createRequestBtn) {
			Intent intent = new Intent(this, FindApartmentActivity.class);
			this.startActivity(intent);
		}
	}
}
