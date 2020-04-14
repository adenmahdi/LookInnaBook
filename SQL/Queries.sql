//Used to get passwords from the database, to compare with user login
select passwords
from users
where email=?;

//Traversing through customer relation to see if the user email is in there
select *
from customer;
Ideally I would've used
select *
from customer natural left outer join user
where email = ?;
but I could not add it do to time constraints

//Search by ISBN
select *
from book
where ISBN=?;

//Search by first name (splits the search and does it by first name only)
select *
from book
where first_name=?;

//Search by book title
select *
from book
where title=?;

//Search by genre
select *
from book
where genre=?;

//Adding book values into book relation
insert
into book
values (ISBN, title, first_name, last_name, genre, price, percent);

//If there is a publisher for said bood, add that into the pub_book relation
insert into pub_book values (ISBN, name);

//Add employee or customer to the user relation
insert into users values (email, password, first_name, last_name);

//Add employee to the employee relation
insert into employee values(email);

//Add customer to the customer relation
insert into customer values(email);

//Add an address to the address relation
insert into address values (addr_no, street, apt_no, city, states, country, post_code);

//Add a customer paired with an address into the address relation
insert into user_addr values (email ,addr_no, street, city, states, country, post_code);

//Add books to the basket
insert into basket (ISBN, email, num);

//Remove basket items if there are more than 1 items in the basket
update basket
set num = num-1
where basket.ISBN=? and basket.email=?

//Remove basket items if there is 1 item in the basket
delete from basket
where basket.ISBN=? and basket.email=?

//Add basket items to the order, return order_no
insert into order
	select *
	from basket
	where basket.ISBN=? and basket.email=?;

select order_no
from order
where EXTRACT(day from date.ordered)=EXTRACT(day from current_date);
