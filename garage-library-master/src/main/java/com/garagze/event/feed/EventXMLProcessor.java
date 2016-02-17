package com.garagze.event.feed;

import com.garagze.event.domain.SaleEvent;

import java.io.InputStream;
import java.util.List;

public interface EventXMLProcessor {
	
	public static final String EVENT = "event";
	public static final String ID = "id";
	public static final String DATE = "date";
	public static final String TITLE = "title";
	public static final String STREET = "street";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String ZIP = "zip";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String DESCRIPTION = "description";
	public static final String RATING = "rating";
	public static final String DISTANCE = "distance";

	public List<SaleEvent> processEventFeed(InputStream in);

}
