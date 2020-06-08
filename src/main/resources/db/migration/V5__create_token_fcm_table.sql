create TABLE TOKEN_FCM (
    id serial primary key,
    user_id bigint,
    token varchar(1000)
)