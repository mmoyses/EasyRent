package edu.wpi.easyrent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.easyrent.constant.Constants;
import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ApartmentsActivity extends Activity {
	
	private ListView listView;
	private Spinner states;
	private List<String> list;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showapartments_activity);

		listView = (ListView) this.findViewById(R.id.showapartments_listview);
		listView.setOnItemClickListener(new ItemClickListener());
		states = (Spinner) this.findViewById(R.id.spinnerstates);

		Cursor cursor = DBOperator.getInstance(getApplicationContext()).execQuery(SQLCommand.STATES);
		list = new ArrayList<String>();
		list.add("All");
		while (cursor.moveToNext()) {
			String state = cursor.getString(0);
			list.add(state);
		}
		cursor.close();
		ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		states.setAdapter(arrayadapter);
		states.setOnItemSelectedListener(new SpinnerClickListener());
		
		// get the sql string delivered from the HomeActivity
		String sql = SQLCommand.AVAILABLE_APARTMENTS;
		// execute the sql
		populateListView(sql, null);
		View empty = (View) this.findViewById(R.id.emptyapartments);
		listView.setEmptyView(empty);
	}
	
	@SuppressLint("NewApi")
	private void populateListView(String sql, String[] args) {
		Cursor cursor = null;
		if (args == null)
			cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql);
		else
			cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql, args);
		// bind the data to list
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.listapartment, cursor, new String[] { "price", "city", "state", "zip" }, new int[] { R.id.price, R.id.city, R.id.state, R.id.zip }, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
		adapter.setViewBinder(new ListViewer());
		listView.setAdapter(adapter);
	}

	private class ItemClickListener implements OnItemClickListener {
		
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Cursor cursor = (Cursor) listView.getItemAtPosition(position);
			Integer aptid = cursor.getInt(4);
			Intent intent = new Intent(view.getContext(), ApartmentDetailActivity.class);
			intent.putExtra("aptid", aptid);
			startActivityForResult(intent, 0);
		}
		
	}
	
	private class SpinnerClickListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String sql = null;
			String[] args = null;
			if (position != 0) {
				sql = SQLCommand.AVAILABLE_APARTMENTS_BY_STATE;
				args = new String[] { list.get(position) };
				
			} else {
				sql = SQLCommand.AVAILABLE_APARTMENTS;
			}
			populateListView(sql, args);
		}

		public void onNothingSelected(AdapterView<?> parent) {
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
