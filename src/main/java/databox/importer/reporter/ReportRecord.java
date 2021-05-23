package databox.importer.reporter;

import java.io.Serializable;
import java.util.Date;

public class ReportRecord implements Serializable {

	private String service;
	private Date sendTime;
	private int numKpi;
	private boolean wasSucessfull;
	private String errorMsg;

	public ReportRecord() {
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getNumKpi() {
		return numKpi;
	}

	public void setNumKpi(int numKpi) {
		this.numKpi = numKpi;
	}

	public boolean isWasSucessfull() {
		return wasSucessfull;
	}

	public void setWasSucessfull(boolean wasSucessfull) {
		this.wasSucessfull = wasSucessfull;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "service=" + service + ", sendTime=" + sendTime + ", numKpi=" + numKpi + ", wasSucessfull=" + wasSucessfull + (errorMsg != null ? (", errorMsg=" + errorMsg) : "");
	}

}
