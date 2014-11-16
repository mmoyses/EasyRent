package edu.wpi.easyrent.constant;

/**
 * SQL commands Including select/delete/update/insert
 */
public abstract class SQLCommand {
	
	// check available apartments
	public static final String AVAILABLE_APARTMENTS = "select price, city, state, zip, apartment.apartmentid as _id from apartment, floorplan, location where apartmentid not in (select apartmentid from lease where enddate > date('now')) and apartment.floorplanid = floorplan.floorplanid and apartment.locationid=location.locationid order by price asc";
	
	// apartment details
	public static final String APARTMENT_DETAILS = "select price, city, state, zip, bedroom, bathroom, area from apartment, floorplan, location where apartment.apartmentid = ? and apartment.floorplanid = floorplan.floorplanid and apartment.locationid=location.locationid;";
	
}
