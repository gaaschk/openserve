create table Stock(
	StockID BIGINT PRIMARY KEY AUTO_INCREMENT,
	Symbol VARCHAR(10),
	Name VARCHAR(50),
	AutoUpdate SMALLINT
);

create table DailyStockRate(
	DailyStockRateID BIGINT PRIMARY KEY AUTO_INCREMENT,
	StockID BIGINT,
	QuoteDate DATE,
	OpenValue DECIMAL(20,6),
	LastValue DECIMAL(20,6),
	LowValue DECIMAL(20,6),
	HighValue DECIMAL(20,6)
);

insert into stock (Symbol, name) values ('GOOG', 'Google');
insert into stock (Symbol, name, AutoUpdate) values ('VMW', 'VMWare', 1);
insert into stock (Symbol, name, AutoUpdate) values ('%5EGSPC', 'S&P 500 Index', 1);
