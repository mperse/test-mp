package databox.importer.services.parcel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import databox.importer.entity.parcel.Parcela;

public class ParcelQueryUtilTest {

	@Test
	public void test1() {
		ParcelQueryUtil util = new ParcelQueryUtil();

		Parcela parc = util.getParcel("166", "2196");
		assertNotNull("Parcel should not be null", parc);

		BigDecimal value = util.getParcelValue(parc.getPcMid());
		assertEquals(BigDecimal.valueOf(4920), value);
	}

}
