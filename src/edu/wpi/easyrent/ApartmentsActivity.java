package edu.wpi.easyrent;

import java.text.NumberFormat;

import edu.wpi.easyrent.constant.Constants;
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

public class ApartmentsActivity extends Activity {
	
	private ListView listView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showapartments_activity);

		listView = (ListView) this.findViewById(R.id.showapartments_listview);
		listView.setOnItemClickListener(new ItemClickListener());

		// get the sql string delivered from the HomeActivity
		Intent intent = this.getIntent();
		String sql = intent.getStringExtra("sql");
		// execute the sql
		if (sql != null) {
			Cursor cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql);
			// bind the data to list
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.listapartment, cursor, new String[] { "price", "city", "state", "zip" }, new int[] { R.id.price, R.id.city, R.id.state, R.id.zip }, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
			adapter.setViewBinder(new ListViewer());
			listView.setAdapter(adapter);
		}
	}

	private class ItemClickListener implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			Cursor cursor = (Cursor) listView.getItemAtPosition(position);
			Integer aptid = cursor.getInt(4);
			Intent intent = new Intent(view.getContext(), ApartmentDetailActivity.class);
			intent.putExtra("aptid", aptid);
			startActivityForResult(intent, 0);
		}
		
	}
	
	private class ListViewer implements ViewBinder {
		
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			if (columnIndex == 0) {
				Float price = cursor.getFloat(columnIndex);
				NumberFormat formatter = Constants.PRICE_FORMATTER;
				TextView textView = (TextView) view;
				textView.setText(formatter.format(price));
				return true;
			}

			return false;
		}
	}
	
}
