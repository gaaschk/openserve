alter table Loan add column BaseRate Decimal(20,6) after Margin;

update loan set baserate = .0425;