package com.fyregroup.openpos.enums;

public enum POSStatus {
	
	POS_NOT_INIT, POS_CLOSED, POS_NO_CONNECTION, POS_OPEN, POS_USER_LOGGED;
	
	public POSStatus status;
	
	public POSStatus getCurrentStatus() {
		return status;
	}
	
	public void setCurrentStatus(POSStatus status) {
		this.status = status;
	}
	
	
}
