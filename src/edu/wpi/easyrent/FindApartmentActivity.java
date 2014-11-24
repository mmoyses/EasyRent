package edu.wpi.easyrent;

import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class FindApartmentActivity extends Activity implements OnClickListener {
	
	private ListView listView;
	private EditText zip;
	private Button findBtn;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findapartment_activity);

		listView = (ListView) this.findViewById(R.id.findapartment_listview);
		listView.setOnItemClickListener(new ItemClickListener());
		zip = (EditText) this.findViewById(R.id.zip);
		findBtn = (Button) this.findViewById(R.id.findBtn);
		findBtn.setOnClickListener(this);
		View empty = (View) this.findViewById(R.id.emptyapartments);
		listView.setEmptyView(empty);
	}
	
	@SuppressLint("NewApi")
	private void populateListView(String sql, String[] args) {
		Cursor cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql, args);
		// bind the data to list
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.findapartment, cursor, new String[] { "address", "apartmentnumber", "city", "state" }, new int[] { R.id.address, R.id.apartmentnumber, R.id.city, R.id.state }, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
		listView.setAdapter(adapter);
	}
	
	@SuppressLint("NewApi")
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.findBtn) {
			String zipcode = zip.getText().toString();
			if (zipcode != null && !zipcode.isEmpty()) {
				String sql = SQLCommand.FIND_APARTMENTS_BY_ZIP;
				String[] args = new String[] { zipcode };
				populateListView(sql, args);
			}
		}
	}

	private class ItemClickListener implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Cursor cursor = (Cursor) listView.getItemAtPosition(position);
			Integer aptid = cursor.getInt(4);
			Intent intent = new Intent(view.getContext(), CreateServiceRequestActivity.class);
			intent.putExtra("aptid", aptid);
			startActivityForResult(intent, 0);
		}
		
	}
	
}
