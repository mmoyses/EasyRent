package edu.wpi.easyrent.constant;

/**
 * SQL commands Including select/delete/update/insert
 */
public abstract class SQLCommand {
	
	// check available apartments
	public static final String AVAILABLE_APARTMENTS = "select price, city, state, zip, apartment.apartmentid as _id from apartment, floorplan, location where apartmentid not in (select apartmentid from lease where enddate > date('now')) and apartment.floorplanid = floorplan.floorplanid and apartment.locationid=location.locationid order by price asc";
	
	// check available apartments by state
	public static final String AVAILABLE_APARTMENTS_BY_STATE = "select price, city, state, zip, apartment.apartmentid as _id from apartment, floorplan, location where apartmentid not in (select apartmentid from lease where enddate > date('now')) and apartment.floorplanid = floorplan.floorplanid and apartment.locationid=location.locationid and state = ? order by price asc";
	
	// apartment details
	public static final String APARTMENT_DETAILS = "select price, address, apartmentnumber, city, state, zip, bedroom, bathroom, area from apartment, floorplan, location where apartment.apartmentid = ? and apartment.floorplanid = floorplan.floorplanid and apartment.locationid=location.locationid";
	
	// apartment amenities
	public static final String APARTMENT_AMENITIES = "select amenity from apartmentamenity, amenity where apartmentid = ? and apartmentamenity.amenityid=amenity.amenityid";
	
	// open requests
	public static final String OPEN_REQUESTS = "select issue, strftime('%m/%d/%Y',requestdate) as requestdate, estimatedtime, ispriority, apartmentid, servicerequestid as _id from servicerequest where fixeddate is null order by ispriority desc, requestdate";

	// paid requests
	public static final String PAID_REQUESTS = "select issue, strftime('%m/%d/%Y',requestdate) as requestdate, price, firstname || ' ' || lastname as 'serviceperson', apartmentid, servicerequestid as _id from servicerequest sr, serviceperson sp where price is not null and sr.servicepersonid=sp.servicepersonid order by requestdate desc";
	
	// service request details
	public static final String SERVICE_REQUEST_DETAILS = "select servicerequestid, issue, strftime('%m/%d/%Y',requestdate) as requestdate, strftime('%m/%d/%Y',fixeddate) as fixeddate, estimatedtime, actualtime, ispriority, price, servicepersonid, apartmentid from servicerequest where servicerequestid = ?";
	
	// service person
	public static final String SERVICE_PERSON = "select servicepersonid, firstname || ' ' || lastname as 'serviceperson' from serviceperson";
	
	// states
	public static final String STATES = "select distinct state from location order by state asc";
	
	// check available apartments by zip
	public static final String FIND_APARTMENTS_BY_ZIP = "select address, apartmentnumber, city, state, apartment.apartmentid as _id from apartment, location where apartment.locationid=location.locationid and zip = ? order by address,apartmentnumber asc";
	
	
}
