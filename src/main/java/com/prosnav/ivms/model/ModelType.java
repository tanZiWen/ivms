/**
 * 
 */
package com.prosnav.ivms.model;

/**
 * @author wuzixiu
 *
 */
public enum ModelType {
	PERSON(1, "person"), FOF(2, "fof"), FOF_FIRM(3, "fof_firm"), GP(4, "gp"), GP_FIRM(
			5, "gp_firm"), LP(6, "lp"), COMPANY(7, "company"), OTHER(100,
			"other)");

	private int code;
	private String key;

	private ModelType(int value, String key) {
		this.code = value;
		this.key = key;
	}

	public int getCode() {
		return code;
	}

	public String getKey() {
		return key;
	}

	public static ModelType ofCode(int code) {
		switch (code) {
		case 1:
			return PERSON;
		case 2:
			return FOF;
		case 3:
			return FOF_FIRM;
		case 4:
			return GP;
		case 5:
			return GP_FIRM;
		case 6:
			return LP;
		case 7:
			return COMPANY;
		case 100:
			return OTHER;
		}

		return null;
	}
}
