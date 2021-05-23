package databox.importer.services.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import databox.importer.constants.DataboxKeys;
import databox.importer.constants.MainConstants;
import databox.importer.services.core.sdk.DataboxWrapper;
import databox.importer.services.core.sdk.KPI;

public class DataboxWrapperTest {

	public String getToken() {
		String envToken = System.getenv("DATABOX_PUSH_TOKEN");
		return envToken == null ? MainConstants.databoxPushToken : envToken;
	}

	public List<KPI> getValues() throws ParseException {
		List<KPI> data = new ArrayList<>();
		Date dateFrom = DataboxWrapper.SDF.parse("2021-05-1 00:00:00");
		Date dateTo = DataboxWrapper.SDF.parse("2021-05-20 00:00:00");

		Calendar c = Calendar.getInstance();
		c.setTime(dateFrom);
		while (c.getTime().compareTo(dateTo) <= 0) {
			data.add(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_SIZE).setValue(ThreadLocalRandom.current().nextDouble() * 12500.0).setDate(c.getTime()).setUnit("m2"));
			data.add(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_VALUE).setValue(ThreadLocalRandom.current().nextDouble() * 8500).setDate(c.getTime()).setUnit("€"));
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return data;
	}

	/**
	 * Test that clearing of the report file
	 */
	@Test
	public void test1() {
		try {
			DataboxWrapper wrapper = new DataboxWrapper(getToken());
			wrapper.clearReport();
			assertTrue("File should be empty", wrapper.getReportRowLines() == 0);
		} catch (Exception e) {
			fail("Should insert data normally.");
		}
	}

	/**
	 * Test insertion of a single record
	 */

	@Test
	public void test2() {
		try {
			DataboxWrapper wrapper = new DataboxWrapper(getToken());
			wrapper.clearReport();
			Date date = DataboxWrapper.SDF.parse("2021-05-15 00:00:00");
			wrapper.pushDataAndLog(Arrays.asList(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_SIZE).setValue(12500.0).setDate(date).setUnit("m2")));
			wrapper.pushDataAndLog(Arrays.asList(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_VALUE).setValue(1000.0).setDate(date).setUnit("€")));
			assertTrue("File should not be empty", wrapper.getReportRowLines() > 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should insert data normally.");
		}
	}

	/**
	 * Test insertion to databox service for multiple records in single
	 */

	@Test
	public void test3() {
		try {
			DataboxWrapper wrapper = new DataboxWrapper(getToken());
			wrapper.clearReport();
			List<KPI> values = getValues();
			wrapper.pushDataAndLog(values);
			assertTrue("File should not be empty", wrapper.getReportRowLines() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Should insert data normally.");
		}
	}

	/*
	 * not working !!
	 * 
	 * @Test public void test3() throws IOException { StringBuffer lastPush = new Databox(getToken()).lastPush(); System.out.println("Last push value: " + lastPush); String matchExpression = "(.*)" + ParcelKeys.PARCEL_VALUE + "(.*)"; assertTrue("Last pushed value should be of type " + ParcelKeys.PARCEL_VALUE, lastPush.toString().matches(matchExpression)); }
	 */

}