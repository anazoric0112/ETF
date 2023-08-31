insert into City(name)
values ('Beograd'),('Pancevo'),('Novi Sad'),('Nis'),('Kragujevac'),('Valjevo')

insert into city_connection(idc1,idc2,days)
values (1,2,1), 
	   (1,3,2),
	   (1,5,4),
	   (2,3,2),
	   (2,6,3),
	   (3,4,3),
	   (5,6,1),
	   (4,5,2),
	   (4,6,3)

insert into Buyer(name, money, idc)
values ('Ana', 0, 1),
	   ('Marko',0,1),
	   ('Filip',0,6),
	   ('Nikola',0,2),
	   ('Milos',0,3),
	   ('Mina',0,3),
	   ('Sofija',0,4),
	   ('Nadja',0,5),
	   ('Stanko',0,5),
	   ('Stefan',0,5),
	   ('Anastasija',0,6),
	   ('Dragan',0,6)

insert into Shop(name,money,discount,idc)
values ('Market',0,0,1),
	   ('Maxi',0,0,1),
	   ('Aroma',0,0,2),
	   ('Beneton',0,0,2),
	   ('HM',0,0,3),
	   ('Orsay',0,0,4),
	   ('Sport vision',0,0,4),
	   ('Nike',0,0,4),
	   ('Gifts',0,0,5),
	   ('Ikea',0,0,5),
	   ('Newyorker',0,0,6),
	   ('Adidas',0,0,6)

insert into Article(name, price, count, idS)
values ('Jabuka',10, 4, 1),
	   ('Kruska',20, 3, 1),
	   ('Grozdje',30, 4, 1),
	   ('Mleko',100, 10, 1),
	   ('Cokolada',200, 3, 2),
	   ('Cips',150, 10, 2),
	   ('Kinder',100, 30, 3),
	   ('Vino',1100, 10, 3),
	   ('Majica',2000, 5, 4),
	   ('Dzemper',3000, 5, 4),
	   ('Pantalone',1500, 5, 5),
	   ('Haljina',10000, 1, 6),
	   ('Patike',10000, 4, 7),
	   ('Patike',20500, 6, 8),
	   ('Solja',500, 10, 9)