create TABLE REACTIONS (
    id serial primary key,
    post_id bigint,
    user_id bigint,
    created_date bigint,
    reaction_type varchar(100)
)