--users
CREATE TABLE IF NOT EXISTS Users (
    userId INT NOT NULL,
    email varchar(250) NOT NULL,
    created_on timestamp NOT NULL,
    pass varchar(250) NOT NULL,
    PRIMARY KEY (userId)
);

--stocks
CREATE TABLE IF NOT EXISTS Stocks (
    stockId INT NOT NULL,
    symbol varchar(50) NOT NULL,
    name varchar(250) NOT NULL,
    type varchar(250) NOT NULL,
    region varchar(250) NOT NULL,
    currency varchar(250) NOT NULL,
    PRIMARY KEY (stockId)
);

--user-stocks
CREATE TABLE IF NOT EXISTS UsersStocks (
    userId INT NOT NULL,
    stockId INT NOT NULL,
    CONSTRAINT fk1 FOREIGN KEY (userId) REFERENCES Users(userId),
    CONSTRAINT fk2 FOREIGN KEY (stockId) REFERENCES Stocks(stockId),
    PRIMARY KEY (userId, stockId)
);