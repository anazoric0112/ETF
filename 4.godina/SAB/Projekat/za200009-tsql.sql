drop procedure SP_FINAL_PRICE
go

CREATE PROCEDURE SP_FINAL_PRICE
	@idO int,
	@sum decimal(10,3) output
AS
	select @sum = sum(a.price*i.count*(1-s.discount*1./100))
    from article a, in_order i, shop s
    where i.idO=@idO and i.idA=a.idA and a.idS=s.idS

RETURN @sum
go



drop trigger TR_TRANSFER_MONEY_TO_SHOPS
go

create TRIGGER TR_TRANSFER_MONEY_TO_SHOPS
    ON Order_
    FOR UPDATE
    AS
    BEGIN
		declare @state_before varchar(100)
		declare @state_after varchar(100)
		declare @idO int, @idS int, @idB int
		declare @sum decimal(10,3)
		declare @time DATETIME

		declare @cursor_order cursor 
		set @cursor_order = cursor for 
		select o1.idB, o1.idO, o2.State, o1.State, o2.time_arrive
		from deleted o1, Order_ o2
		where o1.idO=o2.idO

		open @cursor_order
		fetch from @cursor_order into @idB,@idO,@state_after,@state_before,@time

		while @@FETCH_STATUS=0
		begin
			if (@state_after='arrived' and @state_before='sent')
			begin		
				declare @cursor_shops cursor

				set @cursor_shops = cursor for
				select a.idS, sum(a.Price*i.Count*(1-s.discount*1./100))
				from article a, in_order i, shop s
				where @idO=i.idO and a.idA=i.idA and a.idS=s.idS
				group by a.idS
				
				open @cursor_shops
				fetch from @cursor_shops into @idS, @sum

				declare @discount decimal(10,3)
				set @discount = 0.05

				declare @exists int
				select @exists = count(*) from payment p, Transaction_ t
				where idB=@idB and p.idT=t.idT and t.Price>10000 and datediff(day, t.time_t, getdate())<=30

				if (@exists>0) 
				begin
					set @discount=0.03
				end

				while @@FETCH_STATUS=0
				begin
					declare @money int
					select @money=money from shop where ids=@idS
					set @money=@money+@sum*(1-@discount)

					update Shop
					set money=@money
					where idS=@idS
					print @time
					insert into Transaction_(price, Time_t, idO)
					values (@money,@time, @idO)

					declare @idT int
					select @idT = IDENT_CURRENT('Transaction_')
					insert into Payout(idT, idS)
					values (@idT, @idS)

					fetch from @cursor_shops into @idS, @sum
				end
				close @cursor_shops
				deallocate @cursor_shops
			end
			fetch from @cursor_order into @idB, @idO,@state_after,@state_before,@time
		end
		close @cursor_order
		deallocate @cursor_order
    END
go




select * from article

select * from City_connection

select * from city

select * from shop

select * from buyer

select * from order_

select * from In_Order

select * from payment

select * from payout

select * from Transaction_
