delete
from item_category;
delete
from item;
delete
from category;
delete
from manufacturer;

alter sequence manufacturer_id_seq restart with 1;
alter sequence category_id_seq restart with 1;
alter sequence item_id_seq restart with 1;
alter sequence item_category_id_seq restart with 1;

insert into manufacturer(id, building_number, city, country, street, zip, email, foundation_year, name)
values (nextval('manufacturer_id_seq'), 1, 'Saint-Petersburg', 'Russia', 'Esenina', 194356, 'man1@mail.ru', 2015,
        'man1');

insert into manufacturer(id, building_number, city, country, street, zip, email, foundation_year, name)
values (nextval('manufacturer_id_seq'), 2, 'Milan', 'Italy', 'Duomo', 123456, 'man2@mail.ru', 2014, 'man2');

insert into manufacturer(id, building_number, city, country, street, zip, email, foundation_year, name)
values (nextval('manufacturer_id_seq'), 3, 'Moscow', 'Russia', 'Esenina', 671231, 'man3@mail.ru', 2012, 'man3');

insert into category(id, name)
values (nextval('category_id_seq'), 'handtool');

insert into category(id, name)
values (nextval('category_id_seq'), 'electric');

insert into category(id, name)
values (nextval('category_id_seq'), 'home');

insert into category(id, name)
values (nextval('category_id_seq'), 'phone');

insert into item(id, description, name, price, manufacturer_id,quantity)
values (nextval('item_id_seq'), 'good tool', 'screwdriver', 50.00, 1,100);

insert into item(id, description, name, price, manufacturer_id,quantity)
values (nextval('item_id_seq'), 'norm tool', 'phone', 40.00, 2,200);

insert into item(id, description, name, price, manufacturer_id,quantity)
values (nextval('item_id_seq'), 'bad tool', 'chair', 30.00, 1,100);

insert into item(id, description, name, price, manufacturer_id,quantity)
values (nextval('item_id_seq'), 'good tool', 'fitting', 20.00, 3,100);

insert into item_category(id, category_id, item_id)
values (nextval('item_category_id_seq'), 1, 1);
insert into item_category(id, category_id, item_id)
values (nextval('item_category_id_seq'), 2, 1);
insert into item_category(id, category_id, item_id)
values (nextval('item_category_id_seq'), 3, 2);
insert into item_category(id, category_id, item_id)
values (nextval('item_category_id_seq'), 4, 3);
insert into item_category(id, category_id, item_id)
values (nextval('item_category_id_seq'), 1, 4);

