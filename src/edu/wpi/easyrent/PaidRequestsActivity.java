package edu.wpi.easyrent;

import edu.wpi.easyrent.constant.Constants;
import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class PaidRequestsActivity extends Activity {

	private ListView listView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paidrequests_activity);

		listView = (ListView) this.findViewById(R.id.paidrequests_listview);

		String sql = SQLCommand.PAID_REQUESTS;
		// execute the sql
		if (sql != null) {
			Cursor cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql);
			// bind the data to list
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.paidrequests, cursor, new String[] { "issue", "requestdate", "price", "serviceperson", "apartmentid" }, new int[] { R.id.issue, R.id.requestdate, R.id.price, R.id.serviceperson, R.id.apartmentid }, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
			adapter.setViewBinder(new ListViewer());
			listView.setAdapter(adapter);
		}
	}

	private class ListViewer implements ViewBinder {

		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

			boolean bound = false;
			TextView textView = (TextView) view;

			switch (columnIndex) {
			case 2:
				Float price = cursor.getFloat(columnIndex);
				textView.setText(Constants.PRICE_FORMATTER.format(price));
				bound = true;
				break;
			default:
				break;
			}

			return bound;
		}
	}

}
