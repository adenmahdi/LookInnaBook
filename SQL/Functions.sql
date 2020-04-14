//functions to generate total sales of all books for a month
create function sales(date_ordered timestamp)
	return numeric
	begin
	declare sales_count integer;
		//Sums all the prices of books sold into a single number
		select sum(price*book_no) into sales_count
		from (book natural right outer join order_book) join order using (order_no)
		where EXTRACT(MONTH from order.date_ordered)=EXTRACT(MONTH from date_ordered) and 
			EXTRACT(YEAR from order.date_ordered)=EXTRACT(YEAR from date_ordered)
	return sales_count
	end

//function to calculate the commissions for a single publisher
create function commission(name varchar(75))
	return numeric
	begin
	declare sales_count integer;
		//Sums the commission of books ordered where the ISBN belongs to a specific publisher
		select sum(price*book_no*percent/100) into sales_count
		from (book natural right outer join order_book) join order using (order_no) as S
		where exists (select ISBN
				from pub_book
				where pub_book.name=name)
	return sales_count
	end
