drop database if exists thru_hike_logistics_test;
create database thru_hike_logistics_test;
use thru_hike_logistics_test;

create table trail(
	trail_id int primary key auto_increment,
    app_user_id int,
    trail_name varchar(300) not null unique,
    trail_abbreviation varchar(20) null
);

create table trail_section(
trail_section_id int primary key auto_increment,
app_user_id int,
trail_id int not null,
section_start varchar(200) not null,
section_end varchar(200) not null,
start_latitude decimal(9,6) null,
start_longitude decimal(9,6) null,
end_latitude decimal(9,6) null,
end_longitude decimal(9,6) null,
section_length int not null,
section_days int not null,
upcoming bit not null,
active bit not null,
constraint fk_trail_id
	foreign key (trail_id)
    references trail(trail_id)
);

create table note_category(
category_id int primary key auto_increment,
app_user_id int,
category_name varchar(100) not null,
color varchar(100) null
);

create table hiker_note(
note_id int primary key auto_increment,
app_user_id int,
category_id int not null,
content varchar(2000) not null,
trail_section_id int null,
constraint fk_hiker_note_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_note_category_id
	foreign key (category_id)
    references note_category(category_id)
);

create table future_download(
download_id int primary key auto_increment,
app_user_id int,
category varchar(100) not null,
download_name varchar(250) not null,
trail_section_id int not null,
constraint fk_future_download_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id)
);

create table food_idea(
food_id int primary key auto_increment,
app_user_id int,
food_name varchar(500) not null,
calories int null,
price decimal(13,2) null,
weight decimal(4,2) null,
requires_stove bit null,
food_notes varchar(500) null
);

create table resupply_item(
item_id int primary key auto_increment,
app_user_id int,
item_name varchar(100) not null,
item_quantity int null,
trail_section_id int not null,
store_name varchar(200) null,
food_id int null,
mark_as_recurring bit not null,
constraint fk_resupply_item_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_resupply_item_food_id
	foreign key (food_id)
    references food_idea(food_id)
);

create table alert_category(
alert_category_id int primary key auto_increment,
alert_category_name varchar(200)not null
);

create table section_alert(
alert_id int primary key auto_increment,
app_user_id int,
alert_category_id int not null,
alert_content varchar(1000) not null,
trail_section_id int not null,
constraint fk_section_alert_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_section_alert_category_id
	foreign key (alert_category_id)
    references alert_category(alert_category_id)
);

create table highlight(
highlight_id int primary key auto_increment,
app_user_id int,
highlight_content varchar(1000) not null,
in_town bit not null,
trail_id int not null,
trail_section_id int,
constraint fk_highlight_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_highlight_trail_id
	foreign key (trail_id)
    references trail(trail_id)
);


create table calendar_item(
calendar_item_id int primary key auto_increment,
app_user_id int,
calendar_item_date date not null,
calendar_item_name varchar(50) not null,
calendar_item_content varchar(1000) null
);

create table exit_item(
exit_item_id int primary key auto_increment,
app_user_id int,
exit_item_name varchar(50) not null,
good_to_go bit not null
);

create table town_contact(
town_contact_id int primary key auto_increment,
app_user_id int,
contact_category varchar(200) not null,
town_contact_content varchar(250) not null,
town_contact_other_notes varchar(500) null,
trail_section_id int not null,
constraint fk_town_contact_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id)
);

create table gear(
gear_id int primary key auto_increment,
app_user_id int,
gear_name varchar(100) not null,
gear_weight int null,
gear_notes varchar(500) null,
where_to_buy varchar(250),
trail_section_id int null,
trail_id int not null,
constraint fk_gear_trail_id
	foreign key (trail_id)
    references trail(trail_id),
constraint fk_gear_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id)
);

create table emergency_info(
emergency_info_id int primary key auto_increment,
app_user_id int,
emergency_info_note varchar(400) not null
);

insert into alert_category (alert_category_id,  alert_category_name)
values
(1,"OTHER"),
(2,"CLOSURE"),
(3,"WATER"),
(4,"WEATHER"),
(5,"FIRE"),
(6,"WEEKEND"),
(7,"CALENDAR"),
(8,"GEAR");

delimiter //
create procedure set_known_good_state()
begin
delete from section_alert;
alter table section_alert auto_increment=1;
delete from town_contact;
alter table town_contact auto_increment=1;
delete from trail_section;
alter table trail_section auto_increment = 1;
delete from trail;
alter table trail auto_increment = 1;
delete from exit_item;
alter table exit_item auto_increment = 1;

insert into trail (trail_id, app_user_id, trail_name, trail_abbreviation) 
values
(1,1,'Pacific Crest Trail', 'PCT'),
(2,1,'Continental Divide Trail', 'CDT');

insert into trail_section (trail_section_id, app_user_id, trail_id, section_start, section_end, start_latitude, start_longitude, end_latitude, end_longitude,section_length, section_days, upcoming, active)
values
(1,1,2,'Doc Campbells', 'Pie Town', null, null, null, null,100, 5, 1,1),
(2,1,1,'Canadian Border', 'Hart\'s Pass',70,-90, null, null,30,3,0,0),
(3,1,1,'Mexican Border', 'First Town', null, null, null, null,80, 6, 1,0);

insert into exit_item (exit_item_id, app_user_id, exit_item_name, good_to_go)
values
(1,1,'Charge electronics',1),
(2,1,'resupply',0),
(3,1,'call the fam',0),
(4,1,'download episodes',0);

insert into section_alert (alert_id, app_user_id, alert_category_id, alert_content, trail_section_id)
values
(1,1,1,'Test Alert',1),
(2,1,8,'Pick up bear can',2),
(3,1,2,'Trail closed',2),
(4,1,2,'Bridge out and trail closed',2),
(5,1,2,'Trail closed for trail work',2);

insert into town_contact (town_contact_id, app_user_id, contact_category, town_contact_content, town_contact_other_notes, trail_section_id)
values
(1,1,'Rides','123-456-7890', 'other hikers say to tip $10',2),
(2,1,'PO general delivery address', 'General Delivery, town, WA, 12345', null,2),
(3,1,'Rides','Bob TrailAngel call 123-321-1234', null, 1);

end //
delimiter ;
