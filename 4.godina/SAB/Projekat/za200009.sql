
CREATE TABLE [Article]
( 
	[idA]                integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL ,
	[Price]              decimal(10,3)  NOT NULL ,
	[Count]              integer  NOT NULL ,
	[idS]                integer  NOT NULL 
)
go

CREATE TABLE [Buyer]
( 
	[idB]                integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL ,
	[Money]              decimal(10,3)  NOT NULL ,
	[idC]                integer  NOT NULL 
)
go

CREATE TABLE [City]
( 
	[idC]                integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL 
)
go

CREATE TABLE [City_connection]
( 
	[Days]               integer  NOT NULL ,
	[idC1]               integer  NOT NULL ,
	[idC2]               integer  NOT NULL ,
	[idCC]               integer  IDENTITY  NOT NULL 
)
go

CREATE TABLE [In_Order]
( 
	[Count]              integer  NOT NULL ,
	[idO]                integer  NOT NULL ,
	[idA]                integer  NOT NULL ,
	[idIO]               integer  IDENTITY  NOT NULL 
)
go

CREATE TABLE [Order_]
( 
	[idO]                integer  IDENTITY  NOT NULL ,
	[State]              varchar(100)  NOT NULL ,
	[Time_start]         datetime  NULL ,
	[Time_arrive]        datetime  NULL ,
	[idB]                integer  NOT NULL ,
	[idC]                integer  NOT NULL 
)
go

CREATE TABLE [Payment]
( 
	[idT]                integer  NOT NULL ,
	[idB]                integer  NOT NULL 
)
go

CREATE TABLE [Payout]
( 
	[idT]                integer  NOT NULL ,
	[idS]                integer  NOT NULL 
)
go

CREATE TABLE [Shop]
( 
	[idS]                integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL ,
	[Money]              decimal(10,3)  NOT NULL ,
	[Discount]           integer  NOT NULL ,
	[idC]                integer  NOT NULL 
)
go

CREATE TABLE [Transaction_]
( 
	[idT]                integer  IDENTITY  NOT NULL ,
	[Price]              decimal(10,3)  NOT NULL ,
	[Time_t]               datetime  NOT NULL ,
	[idO]                integer  NOT NULL 
)
go

ALTER TABLE [Article]
	ADD CONSTRAINT [XPKArticle] PRIMARY KEY  CLUSTERED ([idA] ASC)
go

ALTER TABLE [Buyer]
	ADD CONSTRAINT [XPKBuyer] PRIMARY KEY  CLUSTERED ([idB] ASC)
go

ALTER TABLE [City]
	ADD CONSTRAINT [XPKCity] PRIMARY KEY  CLUSTERED ([idC] ASC)
go

ALTER TABLE [City_connection]
	ADD CONSTRAINT [XPKCity_connection] PRIMARY KEY  CLUSTERED ([idCC] ASC)
go

ALTER TABLE [In_Order]
	ADD CONSTRAINT [XPKIn_Order] PRIMARY KEY  CLUSTERED ([idIO] ASC)
go

ALTER TABLE [Order_]
	ADD CONSTRAINT [XPKOrder] PRIMARY KEY  CLUSTERED ([idO] ASC)
go

ALTER TABLE [Payment]
	ADD CONSTRAINT [XPKPayment] PRIMARY KEY  CLUSTERED ([idT] ASC)
go

ALTER TABLE [Payout]
	ADD CONSTRAINT [XPKPayout] PRIMARY KEY  CLUSTERED ([idT] ASC)
go

ALTER TABLE [Shop]
	ADD CONSTRAINT [XPKShop] PRIMARY KEY  CLUSTERED ([idS] ASC)
go

ALTER TABLE [Transaction_]
	ADD CONSTRAINT [XPKTransaction] PRIMARY KEY  CLUSTERED ([idT] ASC)
go


ALTER TABLE [Article]
	ADD CONSTRAINT [R_8] FOREIGN KEY ([idS]) REFERENCES [Shop]([idS])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Buyer]
	ADD CONSTRAINT [R_6] FOREIGN KEY ([idC]) REFERENCES [City]([idC])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [City_connection]
	ADD CONSTRAINT [R_4] FOREIGN KEY ([idC1]) REFERENCES [City]([idC])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [City_connection]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([idC2]) REFERENCES [City]([idC])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [In_Order]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([idO]) REFERENCES [Order_]([idO])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [In_Order]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([idA]) REFERENCES [Article]([idA])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Order_]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([idB]) REFERENCES [Buyer]([idB])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Order_]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([idC]) REFERENCES [City]([idC])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Payment]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([idT]) REFERENCES [Transaction_]([idT])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go

ALTER TABLE [Payment]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([idB]) REFERENCES [Buyer]([idB])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Payout]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([idT]) REFERENCES [Transaction_]([idT])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go

ALTER TABLE [Payout]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([idS]) REFERENCES [Shop]([idS])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Shop]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([idC]) REFERENCES [City]([idC])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Transaction_]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([idO]) REFERENCES [Order_]([idO])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go
