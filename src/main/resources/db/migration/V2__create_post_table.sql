create TABLE POST (
    id serial primary key,
    author varchar(100),
    postType: PostType = PostType.POST,
    text varchar,
    date varchar,
    like LikeDto,
    comment CommentDto,
    reply RepostDto,
    address varchar,
    coordinates bigint,
    video VideoDto,
    adsUrl varchar,
    countViews bigint
)