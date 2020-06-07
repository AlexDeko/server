create TABLE POSTS (
    id serial primary key,
    owner_id bigint,
    author varchar(100),
    created_date bigint,
    content_text varchar(100),
    is_approve boolean,
    count_approve bigint,
    is_not_approve boolean,
    count_not_approve bigint,
    count_repost bigint,
    post_type varchar(100),
    url_link varchar(100),
    count_views bigint,
    parent_id bigint,
    image_id varchar(100)
)