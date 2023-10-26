alter table books
    ADD current_rating double precision;

create table ratings
(
    rating_id   bigserial not null,
    user_email varchar(255),
    rating_score integer not null,
    primary key (rating_id)
);

alter table if exists ratings
    add constraint FK_rating_bookid
        foreign key (rating_id)
            references books;

