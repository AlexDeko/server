create TABLE POSTS (
    id serial primary key,
    owner_id bigint,
    author varchar(100),
    created_date bigint,
    content_text varchar(100),
    count_like bigint,
    is_like boolean,
    count_repost bigint,
    post_type varchar(100),
    ads_url varchar(100),
    count_views bigint,
    parent_id bigint,
    image_id bigint,
    video_url varchar(100),
    count_comment bigint,
    is_can_commented boolean,
    selected_location varchar(100)
)