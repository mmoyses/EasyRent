package edu.wpi.easyrent;

import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener {
	
	private Button apartmentsBtn, requestsBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		apartmentsBtn = (Button) this.findViewById(R.id.apartmentsBtn);
		apartmentsBtn.setOnClickListener(this);
		requestsBtn = (Button) this.findViewById(R.id.requestsBtn);
		requestsBtn.setOnClickListener(this);
		
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
			String sql = SQLCommand.AVAILABLE_APARTMENTS;
			Intent intent = new Intent(this, ApartmentsActivity.class);
			intent.putExtra("sql", sql);
			this.startActivity(intent);
		} else if (id == R.id.requestsBtn) {
			Intent intent = new Intent(this, RequestsActivity.class);
			this.startActivity(intent);
		}
	}
}
