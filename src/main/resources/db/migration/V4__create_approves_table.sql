create TABLE APPROVES (
    id serial primary key,
    user_id bigint,
    post_id bigint,
    user_id bigint,
    is_approve boolean,
    is_not_approve boolean
)