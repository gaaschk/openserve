package org.gsoft.openserv.domain.loan;

import org.gsoft.openserv.util.jpa.OpenServEnum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum LoanPhaseEvent implements OpenServEnum<LoanPhaseEvent>{
		FIRST_DISB_DATE(10L),
		LAST_DISB_DATE(20L),
		GRAD_SEP_DATE(30L);

		private long id;
		
		LoanPhaseEvent(long id){
			this.id = id;
		}
		
		@Override
		@JsonFormat(shape=JsonFormat.Shape.STRING)
		public Long getID() {
			return this.id;
		}

		public String getLabel(){
			return this.name();
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
