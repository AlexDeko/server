create TABLE POST (
    id serial primary key,
    ownerId bigint,
    author varchar(100),
    createdDate bigint,
    contentText varchar(100),
    countLike bigint,
    isLike boolean,
    countRepost bigint,
    postType varchar(100),
    adsUrl varchar(100),
    countViews bigint,
    parentId bigint,
    imageId bigint,
    videoUrl varchar(100),
    countComment bigint,
    isCanCommented boolean,
    selectedLocation varchar(100)
)