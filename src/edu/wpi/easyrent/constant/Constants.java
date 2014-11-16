package edu.wpi.easyrent.constant;

import java.text.NumberFormat;
import java.util.Locale;

public abstract class Constants {
	
	public static final NumberFormat PRICE_FORMATTER = NumberFormat.getCurrencyInstance(Locale.US);
	public static final NumberFormat AREA_FORMATTER = NumberFormat.getNumberInstance(Locale.US);

}
