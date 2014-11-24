package edu.wpi.easyrent.constant;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class Constants {
	
	public static final NumberFormat PRICE_FORMATTER = NumberFormat.getCurrencyInstance(Locale.US);
	public static final NumberFormat AREA_FORMATTER = NumberFormat.getNumberInstance(Locale.US);
	public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

}
