package edu.wpi.easyrent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RequestsActivity extends Activity implements OnClickListener {
	
	private Button openRequestsBtn, paidRequestsBtn, createRequestBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.requests_activity);
		
		openRequestsBtn = (Button) this.findViewById(R.id.openRequestsBtn);
		openRequestsBtn.setOnClickListener(this);
		paidRequestsBtn = (Button) this.findViewById(R.id.paidRequestsBtn);
		paidRequestsBtn.setOnClickListener(this);
		createRequestBtn = (Button) this.findViewById(R.id.createRequestBtn);
		createRequestBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.openRequestsBtn) {
			Intent intent = new Intent(this, OpenRequestsActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.paidRequestsBtn) {
			Intent intent = new Intent(this, PaidRequestsActivity.class);
			this.startActivity(intent);
		} else if (id == R.id.createRequestBtn) {
			Intent intent = new Intent(this, FindApartmentActivity.class);
			this.startActivity(intent);
		}
	}
}
