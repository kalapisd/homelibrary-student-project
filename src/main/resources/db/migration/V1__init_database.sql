create sequence hibernate_sequence start 1 increment 1;

create table authors
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);

create table books
(
    id             bigserial not null,
    title          TEXT      not null,
    sub_title      varchar(255),
    published_year integer   not null,
    num_of_pages   integer   not null,
    language       varchar(255),
    genre_id       bigint,
    primary key (id)
);

create table books_authors
(
    book_id   bigint not null,
    author_id bigint not null,
    primary key (book_id, author_id)
);

create table genre
(
    id    bigserial not null,
    genre_type varchar(255),
    primary key (id)
);

alter table if exists books
    add constraint FK_book_genreid
        foreign key (genre_id)
            references genre;

alter table if exists books_authors
    add constraint FK_authors_book
        foreign key (author_id)
            references authors;

alter table if exists books_authors
    add constraint FK_books_author
        foreign key (book_id)
            references books;
