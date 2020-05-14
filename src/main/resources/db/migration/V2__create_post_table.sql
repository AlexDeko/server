create TABLE POST (
    id serial primary key,
    ownerId bigint,
    author varchar(100),
    createdDate bigint,
    contentText varchar,
    countLike bigint,
    isLike boolean,
    countRepost bigint,
    postType varchar,
    adsUrl varchar,
    countViews bigint,
    parentId bigint,
    imageId bigint,
    videoUrl varchar,
    countComment bigint,
    isCanCommented boolean,
    selectedLocation varchar
)