package com.garagze.event.service;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.garagze.event.R;
import com.garagze.event.database.GaragzeDbAdapter;
import com.garagze.event.domain.SaleEvent;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

public class SaleEventManager implements SaleEventManagerInterface {

	private static List<SaleEvent> saleEvents = null;

	private static final String TAG = "EventService";

	private static com.garagze.event.feed.EventXMLProcessor parser =
            new com.garagze.event.feed.EventXMLProcessorAndroidSAX();

	public static List<SaleEvent> getAllEvents(Context context) {
        Log.d(TAG, "Running EventService.getAllEvents");

        try {
            Log.v("EventService", "Thread sleep");
            Thread.sleep(2000);
            Log.v("EventService", "Thread awake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (saleEvents == null) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.naper_events);
            saleEvents = parser.processEventFeed(inputStream);
        }

        // insert events into the database

        GaragzeDbAdapter adapter = new GaragzeDbAdapter(context);
        for (SaleEvent event : saleEvents) {
            adapter.open().insertSaleEvent(event);
        }

		return saleEvents;
	}

    public static void updateEvent(Context context, SaleEvent event) {

        for (SaleEvent eachEvent: getAllEvents(context)) {
            if (eachEvent.getId().equals(event.getId())) {
                eachEvent.setStreet(event.getStreet());

            }
        }

        for (SaleEvent eachEvent: saleEvents) {
            if (eachEvent.getId().equals(event.getId())) {
                eachEvent.setStreet(event.getStreet());
            }
        }


    }

    public static void addEvent(Context context, SaleEvent event) {
        saleEvents.add(0, event);

        event.setId(java.util.UUID.randomUUID().toString());
        event.setDate(new java.util.Date());

        writeFile(context, event.getId() + ".txt", writeXml(event));
    }

    public static void writeFile(Context context, String fileName, String outputString) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(outputString.getBytes());
            Log.v("EventService", "File written: " + fileName);
        } catch (FileNotFoundException e) {
            Log.e("EventService", "File not found", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("EventService", "IO problem", e);
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e("EventService", "IO problem", e);
                e.printStackTrace();
            }
        }

        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            fis.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String writeXml(SaleEvent event) {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "events");

            serializer.startTag("", "event");

            serializeTag(serializer, "id", event.getId());
            serializeTag(serializer, "date", event.getDate().toLocaleString());
            serializeTag(serializer, "title", event.getTitle());
            serializeTag(serializer, "street", event.getStreet());
            serializeTag(serializer, "city", event.getCity());
            serializeTag(serializer, "state", event.getState());
            serializeTag(serializer, "zip", event.getZip());
            serializeTag(serializer, "latitude", Double.toString(event.getLatitude()));
            serializeTag(serializer, "longitude", Double.toString(event.getLongitude()));
            serializeTag(serializer, "rating", Float.toString(event.getRating()));
            serializeTag(serializer, "description", event.getDescription());

            serializer.endTag("", "event");

            serializer.endTag("", "events");
            serializer.endDocument();
            Log.d(TAG, "Event XML: " + writer.toString());
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void serializeTag(XmlSerializer serializer, String tagName, String tagValue) throws IOException {
        serializer.startTag("", tagName);
        if (tagValue != null) {
            serializer.text(tagValue);
        }
        serializer.endTag("", tagName);
    }

}
