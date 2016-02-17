package com.garagze.event.feed;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Xml;
import android.util.Xml.Encoding;

import com.garagze.event.domain.SaleEvent;


public class EventXMLProcessorAndroidSAX implements EventXMLProcessor {

	public List<SaleEvent> processEventFeed(InputStream inputStream) {
		EventHandler handler = null;
		try {
			handler = new EventHandler();
			Xml.parse(inputStream, Encoding.UTF_8, handler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getSaleEvents();
	}

	class EventHandler extends DefaultHandler {

		private List<SaleEvent> saleEvents;
		private SaleEvent currentSaleEvent;
		private StringBuilder builder;

		public List<SaleEvent> getSaleEvents() {
			return this.saleEvents;
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);
			builder.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			super.endElement(uri, localName, name);
			if (this.currentSaleEvent != null) {
				if (localName.equalsIgnoreCase(ID)) {
					currentSaleEvent.setId(builder.toString());
				} else if (localName.equalsIgnoreCase(DATE)) {
					Date date = null;
					try {
						String dateString = builder.toString().trim();
						//dateString = "Mon 2010-10-18 10:45 AM CST";
						System.out.println("Parsing date string: " + dateString);
						date = new SimpleDateFormat("EEE yyyy-MM-dd hh:mm aa zzz").parse(dateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					currentSaleEvent.setDate(date);
				} else if (localName.equalsIgnoreCase(TITLE)) {
					currentSaleEvent.setTitle(builder.toString().trim());
				} else if (localName.equalsIgnoreCase(STREET)) {
					currentSaleEvent.setStreet(builder.toString().trim());
				} else if (localName.equalsIgnoreCase(CITY)) {
					currentSaleEvent.setCity(builder.toString().trim());
				} else if (localName.equalsIgnoreCase(STATE)) {
					currentSaleEvent.setState(builder.toString().trim());
				} else if (localName.equalsIgnoreCase(ZIP)) {
					currentSaleEvent.setZip(builder.toString().trim());
				} else if (localName.equalsIgnoreCase(LATITUDE)) {
					currentSaleEvent.setLatitude(Double.parseDouble(builder.toString().trim()));
				} else if (localName.equalsIgnoreCase(LONGITUDE)) {
					currentSaleEvent.setLongitude(Double.parseDouble(builder.toString().trim()));
				} else if (localName.equalsIgnoreCase(DESCRIPTION)) {
					currentSaleEvent.setDescription(builder.toString().trim());
				} else if (localName.equalsIgnoreCase(RATING)) {
					currentSaleEvent.setRating(Float.parseFloat(builder.toString().trim()));
				} else if (localName.equalsIgnoreCase(DISTANCE)) {
					currentSaleEvent.setDistance(Double.parseDouble(builder.toString().trim()));
				}else if (localName.equalsIgnoreCase(EVENT)) {
					saleEvents.add(currentSaleEvent);
				}
				builder.setLength(0);
			}
		}

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			saleEvents = new ArrayList<SaleEvent>();
			builder = new StringBuilder();
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, name, attributes);
			if (localName.equalsIgnoreCase(EVENT)) {
				this.currentSaleEvent = new SaleEvent();
			}
		}
	}

}
