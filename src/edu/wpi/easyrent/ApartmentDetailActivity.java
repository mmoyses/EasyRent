package edu.wpi.easyrent;

import java.util.HashSet;
import java.util.Set;

import edu.wpi.easyrent.constant.Constants;
import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import edu.wpi.easyrent.view.AmenitiesTable;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class ApartmentDetailActivity extends Activity {
	
	private TextView price, city, state, zip, area, bedrooms, bathrooms;
	private ScrollView scrollView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apartmentdetail_activity);
		
		price = (TextView) this.findViewById(R.id.price_v);
		city = (TextView) this.findViewById(R.id.city_v);
		state = (TextView) this.findViewById(R.id.state_v);
		zip = (TextView) this.findViewById(R.id.zip_v);
		area = (TextView) this.findViewById(R.id.area_v);
		bedrooms = (TextView) this.findViewById(R.id.bedrooms_v);
		bathrooms = (TextView) this.findViewById(R.id.bathrooms_v);
		scrollView = (ScrollView) this.findViewById(R.id.scrollView1);

		// get the sql string delivered from the HomeActivity
		Intent intent = this.getIntent();
		Integer aptid = intent.getIntExtra("aptid", 0);
		String[] args = new String[1];
		args[0] = aptid.toString();
		// execute the sql
		String sql = SQLCommand.APARTMENT_DETAILS;
		DBOperator operator = DBOperator.getInstance(getApplicationContext());
		Cursor cursor = operator.execQuery(sql, args);
		while (cursor.moveToNext()) {
			Float price = cursor.getFloat(0);
			this.price.setText(Constants.PRICE_FORMATTER.format(price));
			String city = cursor.getString(1);
			this.city.setText(city);
			String state = cursor.getString(2);
			this.state.setText(state);
			String zip = cursor.getString(3);
			this.zip.setText(zip);
			Integer bedrooms = cursor.getInt(4);
			this.bedrooms.setText(bedrooms.toString());
			Integer bathrooms = cursor.getInt(5);
			this.bathrooms.setText(bathrooms.toString());
			Float area = cursor.getFloat(6);
			this.area.setText(Constants.AREA_FORMATTER.format(area));
		}
		cursor.close();
		sql = SQLCommand.APARTMENT_AMENITIES;
		Set<String> amenities = new HashSet<String>();
		cursor = operator.execQuery(sql, args);
		while (cursor.moveToNext()) {
			String amenity = cursor.getString(0);
			amenities.add(amenity);
		}
		if (!amenities.isEmpty()) {
			scrollView.addView(new AmenitiesTable(getBaseContext(), amenities));
		}
		cursor.close();
	}

}
