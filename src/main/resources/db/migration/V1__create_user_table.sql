create TABLE USERS (
    id serial primary key,
    username varchar(100) unique,
    password varchar(100),
    token_firebase varchar,
    image_id bigint,
    badge varchar (100),
    not_approve bigint,
    approve bigint,
    only_reads boolean
)