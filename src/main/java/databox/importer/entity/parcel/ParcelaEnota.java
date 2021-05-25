package databox.importer.entity.parcel;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties
public class ParcelaEnota implements Serializable {
	String id;
	Long pcMid;
	Double delezPov;
	BigDecimal faktorPo;
	BigDecimal posplosenaVrednost;
	private String raven;
	private String conaIme;
	private String vpliv;
	protected String model;
	protected BigDecimal vrednost;

	public ParcelaEnota() {
	}

	@JsonGetter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonGetter
	public Long getPcMid() {
		return pcMid;
	}

	public void setPcMid(Long pcMid) {
		this.pcMid = pcMid;
	}

	@JsonGetter
	public Double getDelezPov() {
		return delezPov;
	}

	public void setDelezPov(Double delezPov) {
		this.delezPov = delezPov;
	}

	@JsonGetter
	public String getRaven() {
		return this.raven;
	}

	public void setRaven(String raven) {
		this.raven = raven;
	}

	@JsonGetter
	public String getVpliv() {
		return this.vpliv;
	}

	public void setVpliv(String vpliv) {
		this.vpliv = vpliv;
	}

	@JsonGetter
	public String getConaIme() {
		return conaIme;
	}

	public void setConaIme(String conaIme) {
		this.conaIme = conaIme;
	}

	@Override
	public String toString() {
		return "pcMid=" + pcMid + super.toString();
	}

	@JsonGetter
	public BigDecimal getPosplosenaVrednost() {
		return posplosenaVrednost;
	}

	public void setPosplosenaVrednost(BigDecimal posVred) {
		posplosenaVrednost = posVred;
	}

	@JsonGetter
	public BigDecimal getFaktorPo() {
		return faktorPo;
	}

	public void setFaktorPo(BigDecimal faktorPo) {
		this.faktorPo = faktorPo;
	}

}
