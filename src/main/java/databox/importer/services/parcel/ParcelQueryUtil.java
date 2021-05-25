package databox.importer.services.parcel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import databox.importer.constants.MainConstants;
import databox.importer.entity.parcel.Parcela;
import databox.importer.entity.parcel.ParcelaEnota;
import databox.importer.utils.ConnectionUtil;

public class ParcelQueryUtil {
	private ConnectionUtil util = new ConnectionUtil();

	public Parcela getParcel(String kosifko, String parcel) {
		HashMap<String, String> queryParams = new HashMap<>();
		queryParams.put("koSifko", kosifko);
		queryParams.put("parcela", parcel);
		List<Parcela> parcelaList = util.executeGetRequestList(new ParcelQueryParams(MainConstants.parcelServiceUrl + "parcela/search/", queryParams), Parcela.class);
		return parcelaList.isEmpty() ? null : parcelaList.get(0);
	}

	public BigDecimal getParcelValue(Long pcMid) {
		String valUrl = MainConstants.parcelServiceUrl + "parcela/" + pcMid + "/vrednost";
		List<ParcelaEnota> vrednosti = util.executeGetRequestList(new ParcelQueryParams(valUrl, null), ParcelaEnota.class);
		return vrednosti.stream().map(v -> v.getPosplosenaVrednost()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
