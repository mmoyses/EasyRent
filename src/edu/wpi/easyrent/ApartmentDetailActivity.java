package edu.wpi.easyrent;

import edu.wpi.easyrent.constant.Constants;
import edu.wpi.easyrent.constant.SQLCommand;
import edu.wpi.easyrent.util.DBOperator;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class ApartmentDetailActivity extends Activity {
	
	private TextView price, city, state, zip, area, bedrooms, bathrooms;
	
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

		// get the sql string delivered from the HomeActivity
		Intent intent = this.getIntent();
		Integer aptid = intent.getIntExtra("aptid", 0);
		String[] args = new String[1];
		args[0] = aptid.toString();
		// execute the sql
		String sql = SQLCommand.APARTMENT_DETAILS;
		Cursor cursor = DBOperator.getInstance(getApplicationContext()).execQuery(sql, args);
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
	}

}
