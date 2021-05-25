package databox.importer.entity.parcel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties
public class Parcela implements Serializable {

	private Long pcMid;
	private Long obMid;
	private Short koSifko;
	private String parcela;
	private BigDecimal povrsina;
	private BigDecimal cenx;
	private BigDecimal ceny;
	private BigDecimal boniteta = null;
	private BigDecimal rk = null;
	private BigDecimal odprtost = null;

	public Parcela() {
	}

	public Parcela(Long pcMid) {
		this.pcMid = pcMid;
	}

	@JsonGetter
	public Long getPcMid() {
		return this.pcMid;
	}

	public void setPcMid(Long pcMid) {
		this.pcMid = pcMid;
	}

	public Long getId() {
		return pcMid;
	}

	public Long getObMid() {
		return this.obMid;
	}

	public void setObMid(Long obMid) {
		this.obMid = obMid;
	}

	@JsonGetter
	public Short getKoSifko() {
		return this.koSifko;
	}

	public void setKoSifko(Short koSifko) {
		this.koSifko = koSifko;
	}

	@JsonGetter
	public String getParcela() {
		return this.parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	@JsonGetter
	public BigDecimal getPovrsina() {
		return this.povrsina;
	}

	public void setPovrsina(BigDecimal povrsina) {
		this.povrsina = povrsina.setScale(2);
	}

	@JsonGetter
	public BigDecimal getRk() {
		return rk != null ? rk.setScale(0) : null;
	}

	public void setRk(BigDecimal rk) {
		this.rk = rk != null ? rk.setScale(2) : null;
	}

	@JsonGetter
	public BigDecimal getOdprtost() {
		return odprtost != null ? odprtost.setScale(0, RoundingMode.HALF_UP) : null;
	}

	public void setOdprtost(BigDecimal odprtost) {
		this.odprtost = odprtost != null ? odprtost.setScale(0) : null;
	}

	@JsonGetter
	public BigDecimal getBoniteta() {
		return boniteta != null ? boniteta.setScale(0) : null;
	}

	public void setBoniteta(BigDecimal boniteta) {
		this.boniteta = boniteta != null ? boniteta.setScale(0) : null;
	}

	@JsonGetter
	public BigDecimal getCenx() {
		return this.cenx != null ? cenx.setScale(0, RoundingMode.HALF_UP) : null;
	}

	public void setCenx(BigDecimal cenx) {
		this.cenx = cenx;
	}

	@JsonGetter
	public BigDecimal getCeny() {
		return this.ceny != null ? ceny.setScale(0, RoundingMode.HALF_UP) : null;
	}

	public void setCeny(BigDecimal ceny) {
		this.ceny = ceny;
	}

	@Override
	public String toString() {
		return "Parcela [pcMid=" + pcMid + ", koSifko=" + koSifko + ", parcela=" + parcela + ", povrsina=" + povrsina + "]";
	}

}
