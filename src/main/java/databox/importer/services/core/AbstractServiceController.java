package databox.importer.services.core;

import databox.importer.MainControllerHolder;

public class AbstractServiceController extends Thread {

	private String type;
	private String id;

	public AbstractServiceController(String type, String id) {
		this.type = type;
	}

	/**
	 * 
	 * @param controlerID
	 *            - id in the form of 'type|id'. Use createId to generate unique identifier
	 */
	public boolean idMatheches(String controlerID) {
		return controlerID != null && controlerID.equals(createId(type, id));
	}

	public static String createId(String type, String id) {
		return type + "|" + id;
	}

	public String getFullId() {
		return createId(type, id);
	}

	public void disableAndRemove() {
		MainControllerHolder.getInstance().removeControler(this);
	}

}
