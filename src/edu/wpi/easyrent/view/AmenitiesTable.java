package edu.wpi.easyrent.view;

import java.util.Set;

import edu.wpi.easyrent.R;
import android.content.Context;
import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AmenitiesTable extends TableLayout {

	public AmenitiesTable(Context context, Set<String> amenities) {
		super(context);
		setData(context, amenities);
	}
	
	private void setData(Context context, Set<String> amenities) {
		TextView textView = new TextView(context);
		textView.setText(R.string.amenities_d);
		textView.setTextSize(22);
		textView.setTextColor(Color.DKGRAY);
		LayoutParams params = new LayoutParams();
		params.topMargin = 15;
		textView.setLayoutParams(params);
		addView(textView);
		for (String amenity : amenities) {
			TableRow row = new TableRow(context);
			textView = new TextView(context);
			textView.setText(amenity);
			textView.setTextSize(22);
			textView.setTextColor(Color.DKGRAY);
			row.addView(textView);
			params = new LayoutParams();
			params.topMargin = 15;
			params.leftMargin = 20;
			row.setLayoutParams(params);
			addView(row);
		}
	}

}
