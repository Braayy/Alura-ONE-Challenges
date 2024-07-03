create table users(
    id serial primary key,

    name varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(100) not null
);

create table courses(
    id serial primary key,

    name varchar(100) not null,
    category varchar(100) not null
);

create table topics(
    id serial primary key,

    title varchar(200) not null,
    message text not null,
    createdAt date not null,
    status varchar(20) not null,

    author_id serial references users(id),
    course_id serial references courses(id)
);

create table replies(
    id serial primary key,

    message text not null,
    solution boolean not null,

    topic_id serial references topics(id),
    author_id serial references users(id)
);