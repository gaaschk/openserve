create table Stock(
	StockID BIGINT PRIMARY KEY AUTO_INCREMENT,
	Symbol VARCHAR(20),
	Name VARCHAR(50),
	AutoUpdate SMALLINT
);

create table DailyStockQuote(
	DailyStockQuoteID BIGINT PRIMARY KEY AUTO_INCREMENT,
	StockID BIGINT,
	QuoteDate DATE,
	OpenValue DECIMAL(20,6),
	LastValue DECIMAL(20,6),
	LowValue DECIMAL(20,6),
	HighValue DECIMAL(20,6)
);

alter table DailyStockQuote
add foreign key DailyStockQuote_Stock_FK (StockID) references Stock(StockID);

insert into stock (Symbol, name, AutoUpdate) values ('GOOG', 'Google', 1);
insert into stock (Symbol, name, AutoUpdate) values ('VMW', 'VMWare', 1);
insert into stock (Symbol, name, AutoUpdate) values ('%5EGSPC', 'S&P 500 Index', 1);

create table Rate(
	RateID BIGINT PRIMARY KEY AUTO_INCREMENT,
	Symbol VARCHAR(20),
	Name VARCHAR(50)
);

create table DailyRateQuote(
	DailyRateQuoteID BIGINT PRIMARY KEY AUTO_INCREMENT,
	RateID BIGINT,
	QuoteDate DATE,
	Value DECIMAL(20,6)
);

alter table DailyRateQuote
add foreign key DailyRateQuote_Rate_FK (RateID) references Rate(RateID);

insert into Rate (Symbol, name) values ('LIBOR.USD1M', '1 Month LIBOR US Dollars');
insert into Rate (Symbol, name) values ('LIBOR.USD6M', '6 Month LIBOR US Dollars');
insert into Rate (Symbol, name) values ('LIBOR.USD12M', '12 Month LIBOR US Dollars');

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

update LoanTypeProfile set BaseRateID = (select RateID from Rate where Symbol = 'LIBOR.USD1M');
