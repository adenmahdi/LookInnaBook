//create trigger that removes books from the warehouse once it is ordered
create trigger books_release 
after insert of order_no ON order_books
referencing NEW as N
for each row
when (N.order_no>0)
DECLARE
	tot_book_ordered number;
BEGIN ATOMIC
	//Counts the number of books to be ordered
	select sum(book_no) into tot_book_ordered
	from order_book
	where N.ISBN=order_book.ISBN
	//Removes said books from the stock
	update book_no
	set book_no=book_no-tot_book_ordered
	where N.ISBN=book_no.ISBN
END;

//create trigger to order from publisher if 10 or less books are in stock
create trigger order_new_books 
after update of book_no ON book_no
referencing NEW as N
for each row
when (N=.book_no<10)
BEGIN
	//Get the book's publisher's email and print it
	select email
	from publisher natural join pub_book
	where N.ISBN=pub_book.ISBN
END;

//create trigger to remove books from basket once they are ordered
create trigger books_release 
after insert of order_no ON order_books
referencing NEW as N
for each row
when (N.order_no>0)
BEGIN
	//Deletes all entries of orders of the book in the basket once they have been transferred.
	delete from basket
	where (select ISBN
		from basket natural right outer join customer_or
		where N.ISBN=basket.ISBN and N.order_no=customer_or.order_no)
END;

//create trigger to check if addresses reference an object (and if not, to remove them)
create trigger address
after delete of name ON pub_addr
referencing OLD as O
for each row
when ()
BEGIN
	delete from address
	where (addr_no=O.addr_no and street=O.street and 
	apt_no=O.apt_no and city=O.city and states=O.states 
	and country=O.country)
END;

create trigger address
after delete of name ON war_addr
referencing OLD as O
for each row
when ()
BEGIN
	delete from address
	where (addr_no=O.addr_no and street=O.street and 
	apt_no=O.apt_no and city=O.city and states=O.states 
	and country=O.country)
END;

create trigger address
after delete of email ON user_addr
referencing OLD as O
for each row
when ()
BEGIN
	delete from address
	where (addr_no=O.addr_no and street=O.street and 
	apt_no=O.apt_no and city=O.city and states=O.states 
	and country=O.country)
END;
