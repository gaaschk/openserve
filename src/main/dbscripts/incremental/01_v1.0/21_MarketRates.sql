create table Rate(
	RateID BIGINT PRIMARY KEY AUTO_INCREMENT,
	RateName VARCHAR(50),
	TickerSymbol VARCHAR(20),
	AutoUpdate SMALLINT
);

create table RateValue(
	RateValueID BIGINT PRIMARY KEY AUTO_INCREMENT,
	RateID BIGINT,
	RateValueDate DATE,
	RateValue DECIMAL(20,6),
	IsValid SMALLINT
);

alter table RateValue
add foreign key RateValue_Rate_FK (RateID) references Rate(RateID);

insert into Rate (TickerSymbol, RateName) values ('LIBOR.USD1M', '1 Month LIBOR US Dollars');
insert into Rate (TickerSymbol, RateName) values ('LIBOR.USD6M', '6 Month LIBOR US Dollars');
insert into Rate (TickerSymbol, RateName) values ('LIBOR.USD12M', '12 Month LIBOR US Dollars');

alter table LoanTypeProfile 
add column variableRate SMALLINT;

create table FrequencyType(
	FrequencyTypeID BIGINT PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(50)
);

insert into FrequencyType (FrequencyTypeID, Name) value (10, "MONTHLY");
insert into FrequencyType (FrequencyTypeID, Name) value (20, "QUARTERLY");
insert into FrequencyType (FrequencyTypeID, Name) value (30, "SEMI_ANNUALLY");
insert into FrequencyType (FrequencyTypeID, Name) value (40, "ANNUALLY");

alter table LoanTypeProfile 
add column BaseRateUpdateFrequencyID BIGINT;

alter table LoanTypeProfile 
add foreign key LoanTypeProfile_BaseRate_FrequencyType (BaseRateUpdateFrequencyID) references FrequencyType(FrequencyTypeID);

update LoanTypeProfile set BaseRateUpdateFrequencyID = 10;

alter table LoanTypeProfile 
add column BaseRateID BIGINT;

alter table LoanTypeProfile 
add foreign key LoanTypeProfile_Rate (BaseRateID) references Rate(RateID);

update LoanTypeProfile set BaseRateID = (select RateID from Rate where TickerSymbol = 'LIBOR.USD1M');
