create table tokens
(
    value       text            not null constraint tokens_value primary key,
    expired_at  timestamptz     default date('2050-01-01 00:00:00.0000000+00'),
    created_at  timestamptz     not null default now(),
    updated_at  timestamptz     not null default now()
);

comment on table tokens is 'Токены, соли, хэши';
comment on column tokens.value is 'Уникальное значение токена';
comment on column tokens.expired_at is 'Время действия токена';

create table users
(
    id          bigint          not null constraint users_id primary key,
    name        varchar(255)    not null,
    login       varchar(255)    not null,
    email       varchar(255)    not null,
    password    text            not null,
    token       text            default null,
    avatar      varchar(255)    default null,
    created_at  timestamptz     not null default now(),
    updated_at  timestamptz     not null default now(),
    deleted_at  timestamptz     default null
);

create unique index on users(login) where deleted_at is null;
create unique index on users(email);

ALTER TABLE users ADD FOREIGN KEY ("token") REFERENCES "tokens" ("value");

create table posts
(
    id              bigint          not null constraint posts_id primary key,
    title           varchar(255)    not null,
    description     varchar(255)    default null,
    content         text            not null,
    image           varchar(255)    default null,
    date            timestamptz     default now(),
    author_id       bigint          not null,
    created_at      timestamptz     not null default now(),
    updated_at      timestamptz     not null default now(),
    deleted_at      timestamptz     default null
);

ALTER TABLE posts ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

create table comments
(
    id              bigint          not null constraint comments_id primary key,
    title           varchar(255)    default null,
    content         text            not null,
    author_id       bigint          not null,
    post_id         bigint          not null,
    created_at      timestamptz     not null default now(),
    updated_at      timestamptz     not null default now()
);

ALTER TABLE comments ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");
ALTER TABLE comments ADD FOREIGN KEY ("author_id") REFERENCES "users" ("id");

create table tags
(
    id              bigint          not null constraint tags_id primary key,
    title           varchar(255)    not null
);

create table post_tag
(
    post_id         bigint      not null,
    tag_id          bigint      not null,
    PRIMARY KEY (post_id, tag_id)
);

ALTER TABLE post_tag ADD FOREIGN KEY ("post_id") REFERENCES "posts" ("id");
ALTER TABLE post_tag ADD FOREIGN KEY ("tag_id") REFERENCES "tags" ("id");