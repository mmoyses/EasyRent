package edu.wpi.easyrent;

import java.text.DateFormat;
import java.util.Date;

import edu.wpi.easyrent.constant.Constants;
import edu.wpi.easyrent.util.DBOperator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CreateServiceRequestActivity extends Activity implements OnClickListener {
	
	private EditText issue;
	private CheckBox ispriority;
	private Button createBtn;
	
	private Integer aptid;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createservicerequest_activity);
		
		issue = (EditText) this.findViewById(R.id.issue_v);
		ispriority = (CheckBox) this.findViewById(R.id.ispriority_v);
		createBtn = (Button) this.findViewById(R.id.createBtn);
		createBtn.setOnClickListener(this);

		// get the sql string delivered from the FindApartmentActivity
		Intent intent = this.getIntent();
		aptid = intent.getIntExtra("aptid", 0);
	}
	
	@SuppressLint("NewApi")
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.createBtn) {
			String issue = this.issue.getText().toString();
			if (issue != null && !issue.isEmpty()) {
				DBOperator operator = DBOperator.getInstance(getApplicationContext());
				
				String table = "servicerequest";
				ContentValues values = new ContentValues();
				values.put("issue", issue);
				DateFormat formatter = Constants.DATE_FORMATTER;
				Date date = new Date();
				values.put("requestdate", formatter.format(date));
				values.put("ispriority", ispriority.isChecked() ? "1" : "0");
				values.put("apartmentid", aptid);
				operator.insert(table, values);
				Toast.makeText(getBaseContext(), "Service request created successfully", Toast.LENGTH_SHORT).show();
				NavUtils.navigateUpFromSameTask(this);
			}
		}
	}

}
