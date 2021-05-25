package sdk;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
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
import databox.importer.utils.DateFormatUtil;

public class DataboxWrapperTest {

	public String getToken() {
		String envToken = System.getenv("DATABOX_PUSH_TOKEN");
		return envToken == null ? MainConstants.databoxParcelPushToken : envToken;
	}

	public List<KPI> getValues(BigDecimal totalVal, BigDecimal area) throws ParseException {
		List<KPI> data = new ArrayList<>();
		Date dateFrom = DateFormatUtil.SDF.parse("2021-05-1 00:00:00");
		Date dateTo = DateFormatUtil.SDF.parse("2021-05-20 00:00:00");

		Calendar c = Calendar.getInstance();
		c.setTime(dateFrom);
		while (c.getTime().compareTo(dateTo) <= 0) {
			data.add(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_SIZE).setValue(getFactor(0.2) * area.doubleValue()).setDate(c.getTime()).setUnit("m2"));
			data.add(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_VALUE).setValue(getFactor(0.2) * totalVal.doubleValue()).setDate(c.getTime()).setUnit("€"));
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		return data;
	}

	private static double getFactor(Double varialePart) {
		return (ThreadLocalRandom.current().nextDouble() * varialePart) + (1 - varialePart);
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
	 * Test insertion of individual records
	 */

	@Test
	public void test2() {
		try {
			DataboxWrapper wrapper = new DataboxWrapper(getToken());
			wrapper.clearReport();
			Date date = DateFormatUtil.SDF.parse("2021-05-15 00:00:00");
			wrapper.pushDataAndLog(Arrays.asList(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_SIZE).setValue(12500.0).setDate(date).setUnit("m2")));
			wrapper.pushDataAndLog(Arrays.asList(new KPI().setKey(DataboxKeys.PARCEL.PARCEL_VALUE).setValue(1000.0).setDate(date).setUnit("€")));
			assertTrue("File should contain 2 records.", wrapper.getReportRowLines() == 2);
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
			List<KPI> values = getValues(new BigDecimal(22000), new BigDecimal(1500));
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