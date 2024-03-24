create table users
(
    user_id bigserial not null,
    email varchar(255),
    password varchar(255),
    primary key (user_id)
);