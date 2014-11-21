package edu.wpi.easyrent;

import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class OpenRequestsActivity extends Activity {
	
	private ListView listView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openrequests_activity);

		listView = (ListView) this.findViewById(R.id.openrequests_listview);
		listView.setOnItemClickListener(new ItemClickListener());

		String sql = SQLCommand.OPEN_REQUESTS;
		// execute the sql
		if (sql != null) {
			Cursor cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql);
			// bind the data to list
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.openrequests, cursor, new String[] { "issue", "requestdate", "estimatedtime", "ispriority", "apartmentid" }, new int[] { R.id.issue, R.id.requestdate, R.id.estimatedtime, R.id.ispriority, R.id.apartmentid }, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
			adapter.setViewBinder(new ListViewer());
			listView.setAdapter(adapter);
		}
	}

	private class ItemClickListener implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			Cursor cursor = (Cursor) listView.getItemAtPosition(position);
			Integer srid = cursor.getInt(5);
			Intent intent = new Intent(view.getContext(), ServiceRequestDetailActivity.class);
			intent.putExtra("srid", srid);
			startActivityForResult(intent, 0);
		}
		
	}
	
	private class ListViewer implements ViewBinder {
		
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			boolean bound = false;
			TextView textView = (TextView) view;
			
			switch (columnIndex) {
				case 2:
					Integer estimatedtime = cursor.getInt(columnIndex);
					textView.setText(estimatedtime == 0 ? "?" : estimatedtime.toString());
					bound = true;
					break;
				case 3:
					Integer priority = cursor.getInt(columnIndex);
					textView.setText(priority == 1 ? "Yes" : "No");
					bound = true;
					break;
				default:
					break;
			}

			return bound;
		}
	}
	
}
