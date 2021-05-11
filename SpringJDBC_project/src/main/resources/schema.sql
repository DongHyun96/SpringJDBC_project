create table User (
	id identity,
	username varchar(20) not null,
	email varchar(20) not null,
	score INT(11) not null,
	companion varchar(30) unique  not null
);