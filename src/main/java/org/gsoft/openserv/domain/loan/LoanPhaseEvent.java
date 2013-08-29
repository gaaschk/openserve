package org.gsoft.openserv.domain.loan;

import org.gsoft.openserv.util.jpa.OpenServEnum;


public enum LoanPhaseEvent implements OpenServEnum<LoanPhaseEvent>{
		FIRST_DISB_DATE(10L),
		LAST_DISB_DATE(20L),
		GRAD_SEP_DATE(30L);

		private long id;
		
		LoanPhaseEvent(long id){
			this.id = id;
		}
		
		@Override
		public Long getID() {
			return this.id;
		}

		public static LoanPhaseEvent forID(Long id) {
			for (final LoanPhaseEvent loanPhaseEvent : values()) {
				if (loanPhaseEvent.getID().equals(id)) {
					return loanPhaseEvent;
				}
			}
			return null;
		}
}
