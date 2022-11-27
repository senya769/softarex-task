create table users
(
    id         integer generated by default as identity primary key,
    email      varchar(255) unique not null,
    first_name varchar(255),
    last_name  varchar(255),
    number     varchar(255),
    password   varchar(255)
);

create table answers
(
    id      integer generated by default as identity primary key,
    answer  varchar(255),
    user_id integer,
    foreign key (user_id) references users
);

create table questions
(
    id          integer generated by default as identity primary key ,
    question    varchar(255),
    type_answer varchar(255),
    answer_id   integer,
    user_id     integer,
    foreign key (answer_id) references answers,
    foreign key (user_id) references users
);
create table user_role
(
    user_id integer not null,
    roles   varchar(255),
    foreign key (user_id) references users
);

