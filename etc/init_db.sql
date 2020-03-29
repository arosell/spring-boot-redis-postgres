create schema postservice;

create table postservice.users (
    username varchar(20),
    fullname varchar(100),
    constraint pk_users primary key (username)
);

create table postservice.posts (
    id varchar(16),
    message text,
    created_user varchar(20),
    created_date timestamp,
    constraint pk_posts primary key (id),
    constraint fk_posts_users foreign key (created_user) references postservice.users (username)
);

insert into postservice.users (username, fullname)
values
    ('asterix', 'Asterix'),
    ('obelix', 'Obelix'),
    ('timandahaf', 'Timandahaf The Conqueror');
    
insert into postservice.posts (id, message, created_user, created_date)
values
    ('MSG001', 'Who are you?', 'timandahaf', NOW()),
    ('MSG002', 'More to the point, who are you?', 'asterix', NOW() + interval '1 second'),
    ('MSG003', 'I am Timandahaf The Conqueror. Chief of the Normans!', 'timandahaf', NOW() + interval '2 second'),
    ('MSG004', 'Such funny names! MMMMEEHEEHO!', 'obelix', NOW() + interval '3 second'),
    ('MSG005', 'Obelix, control yourself! You''ll hurt his feelings!', 'asterix', NOW() + interval '4 second');