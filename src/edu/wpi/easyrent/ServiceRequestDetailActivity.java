package edu.wpi.easyrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceRequestDetailActivity extends Activity implements OnClickListener {
	
	private TextView requestdate, apartmentid;
	private EditText issue, fixeddate, estimatedtime, actualtime, price;
	private CheckBox ispriority;
	private Spinner serviceperson;
	private Button updateBtn;
	
	private Integer srid;
	private Map<String, Integer> spMap = new HashMap<String, Integer>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicerequestdetail_activity);
		
		issue = (EditText) this.findViewById(R.id.issue_v);
		requestdate = (TextView) this.findViewById(R.id.requestdate_v);
		fixeddate = (EditText) this.findViewById(R.id.fixeddate_v);
		estimatedtime = (EditText) this.findViewById(R.id.estimatedtime_v);
		actualtime = (EditText) this.findViewById(R.id.actualtime_v);
		ispriority = (CheckBox) this.findViewById(R.id.ispriority_v);
		price = (EditText) this.findViewById(R.id.price_v);
		serviceperson = (Spinner) this.findViewById(R.id.serviceperson_v);
		apartmentid = (TextView) this.findViewById(R.id.apid_v);
		updateBtn = (Button) this.findViewById(R.id.updateBtn);
		updateBtn.setOnClickListener(this);

		// get the sql string delivered from the OpenRequestsActivity
		Intent intent = this.getIntent();
		srid = intent.getIntExtra("srid", 0);
		String[] args = new String[1];
		args[0] = srid.toString();
		// execute the sql
		String sql = SQLCommand.SERVICE_REQUEST_DETAILS;
		DBOperator operator = DBOperator.getInstance(getApplicationContext());
		Cursor cursor = operator.execQuery(sql, args);
		Integer serviceperson = null;
		while (cursor.moveToNext()) {
			String issue = cursor.getString(1);
			this.issue.setText(issue);
			String requestdate = cursor.getString(2);
			this.requestdate.setText(requestdate);
			String fixeddate = cursor.getString(3);
			this.fixeddate.setText(fixeddate);
			Integer estimatedtime = cursor.getInt(4);
			this.estimatedtime.setText(estimatedtime.toString());
			Integer actualtime = cursor.getInt(5);
			this.actualtime.setText(actualtime.toString());
			Integer ispriority = cursor.getInt(6);
			this.ispriority.setChecked(ispriority == 1);
			Float price = cursor.getFloat(7);
			this.price.setText(price.toString());
			serviceperson = cursor.getInt(8);
			Integer apartmentid = cursor.getInt(9);
			this.apartmentid.setText(apartmentid.toString());
		}
		cursor.close();
		sql = SQLCommand.SERVICE_PERSON;
		cursor = operator.execQuery(sql);
		List<String> list = new ArrayList<String>();
		list.add("Choose one");
		String selected = null;
		while (cursor.moveToNext()) {
			Integer id = cursor.getInt(0);
			String name = cursor.getString(1);
			if (serviceperson.equals(id)) {
				selected = name;
			}
			list.add(name);
			spMap.put(name, id);
		}
		cursor.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.serviceperson.setAdapter(adapter);
		if (serviceperson != 0) {
			this.serviceperson.setSelection(list.indexOf(selected));
		}
	}
	
	@SuppressLint("NewApi")
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.updateBtn) {
			DBOperator operator = DBOperator.getInstance(getApplicationContext());
			
			String table = "servicerequest";
			ContentValues values = new ContentValues();
			values.put("issue", issue.getText().toString());
			String estimatedtime = this.estimatedtime.getText().toString();
			if (estimatedtime.length() == 0 || estimatedtime.equals("0"))
				estimatedtime = null;
			values.put("estimatedtime", estimatedtime);
			String actualtime = this.actualtime.getText().toString();
			if (actualtime.length() == 0 || actualtime.equals("0"))
				actualtime = "0";
			values.put("actualtime", actualtime);
			values.put("ispriority", ispriority.isChecked() ? "1" : "0");
			String price = this.price.getText().toString();
			if (price.length() != 0) {
				Float p = Float.parseFloat(price);
				if (p == 0.0f)
					price = null;
				else
					price = p.toString();
			}
			values.put("price", price);
			Object temp = serviceperson.getSelectedItem();
			Integer spid = spMap.get(temp);
			values.put("servicepersonid", spid == null ? null : spid.toString());
			String whereClause = "servicerequestid = ?";
			String[] args = new String[] {srid.toString()};
			operator.updateTable(table, values, whereClause, args);
			Toast.makeText(getBaseContext(), "Service request updated successfully", Toast.LENGTH_SHORT).show();
			Intent upIntent = NavUtils.getParentActivityIntent(this);
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
		}
	}

}
