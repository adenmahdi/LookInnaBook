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
values (?,?,?,?,?,?);

//If there is a publisher for said bood, add that into the pub_book relation
insert into pub_book values (?,?);

//Add employee or customer to the user relation
insert into users values (?,?,?,?);

//Add employee to the employee relation
insert into employee values(?);

//Add customer to the customer relation
insert into customer values(?);

//Add an address to the address relation
insert into address values (?,?,?,?,?,?,?);

//Add a customer paired with an address into the address relation
insert into user_addr values (?,?,?,?,?,?,?);

//Add books to the basket
//Remove basket items if there are more than 1 items in the basket
//Remove basket items if there is 1 item in the basket
//Add basket items to the order, return order_no
//Return sales vs. expenditures, sales by genre, sales by author etc.
//Contact email of publisher of book running low on supplies
