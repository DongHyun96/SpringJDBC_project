create table login_table (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	username varchar(45) unique not null,
	email varchar(45) unique not null,
	password LONGTEXT not null,
	userversion varchar(45) not null
);

create table user_table (
	username varchar(45) unique not null,
    score BIGINT not null,
    coin BIGINT not null,
    knight varchar(45) not null
);