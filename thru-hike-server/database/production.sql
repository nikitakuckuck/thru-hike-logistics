drop database if exists thru_hike_logistics;
create database thru_hike_logistics;
use thru_hike_logistics;

create table trail(
	trail_id int primary key auto_increment,
    app_user_id int,
    trail_name varchar(300) not null
);

create table trail_section(
trail_section_id int primary key auto_increment,
app_user_id int,
trail_section_nickname varchar(500) not null unique,
section_start varchar(200) not null,
section_end varchar(200) not null,
-- TODO: need whatever data necessary for weather api
section_length int not null,
section_days int not null,
upcoming bit not null,
trail_id int not null,
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
price int null,
weight int null,
requires_stove bit null,
food_notes varchar(500) null,
trail_id int not null,
constraint fk_food_idea_trail_id
	foreign key (trail_id)
    references trail(trail_id)
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
app_user_id int,
alert_category_name varchar(200)not null
);

create table alert(
alert_id int primary key auto_increment,
app_user_id int,
alert_category_id int not null,
alert_content varchar(1000) not null,
trail_section_id int not null,
future_sections bit not null,
constraint fk_alert_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_alert_category_id
	foreign key (alert_category_id)
    references alert_category(alert_category_id)
);

create table cool_thing(
cool_thing_id int primary key auto_increment,
app_user_id int,
cool_thing_content varchar(1000) not null,
in_town bit not null,
trail_id int not null,
trail_section_id int,
constraint fk_cool_thing_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_cool_thing_trail_id
	foreign key (trail_id)
    references trail(trail_id)
);

create table murphys_day(
reminder_id int primary key auto_increment,
app_user_id int,
reminder_name varchar(50) not null,
reminder_content varchar(1000) null
);

create table calendar_item(
calendar_item_id int primary key auto_increment,
app_user_id int,
calendar_item_date date not null,
calendar_item_name varchar(50) not null,
calendar_item_content varchar(1000) null
);

create table town_exit(
exit_item_id int primary key auto_increment,
app_user_id int,
exit_item_name varchar(50) not null,
item_okay bit not null
);

create table contact_category(
contact_category_id int primary key auto_increment,
app_user_id int,
contact_category_name varchar(50) not null
);

create table town_contact(
town_contact_id int primary key auto_increment,
app_user_id int,
contact_category_id int not null,
town_contact_content varchar(250) not null,
town_contact_other_notes varchar(500) null,
trail_section_id int not null,
constraint fk_town_contact_trail_section_id
	foreign key (trail_section_id)
    references trail_section(trail_section_id),
constraint fk_contact_category_id
	foreign key (contact_category_id)
    references contact_category(contact_category_id)
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



