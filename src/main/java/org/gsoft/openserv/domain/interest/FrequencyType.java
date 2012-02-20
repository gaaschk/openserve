package org.gsoft.openserv.domain.interest;

import org.gsoft.openserv.util.jpa.OpenServEnum;

public enum FrequencyType implements OpenServEnum<FrequencyType>{
	MONTHLY(10L),
	QUARTERLY(20L),
	SEMI_ANNUALLY(30L),
	ANNUALLY(40L);

	private Long frequencyTypeID;
	
	FrequencyType(Long frequencyTypeID) {
		this.setFrequencyTypeID(frequencyTypeID);
	}

	public Long getFrequencyTypeID() {
		return frequencyTypeID;
	}

	public void setFrequencyTypeID(Long frequencyTypeID) {
		this.frequencyTypeID = frequencyTypeID;
	}

	@Override
	public Long getID() {
		return this.getFrequencyTypeID();
	}

	public static FrequencyType forID(Long id) {
		for (final FrequencyType frequencyType : values()) {
			if (frequencyType.getID().equals(id)) {
				return frequencyType;
			}
		}
		return null;
	}
}
