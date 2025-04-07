create sequence users_user_id_seq
    as integer;

alter sequence users_user_id_seq owner to postgres;

create sequence users_user_id_seq1;

alter sequence users_user_id_seq1 owner to postgres;

create table users
(
    user_id       integer generated always as identity
        primary key,
    username      varchar(50)  not null
        unique,
    email         varchar(255) not null
        unique,
    user_password varchar(255) not null,
    display_name  varchar(100),
    bio           text,
    avatar_url    varchar(255),
    created_at    timestamp with time zone default CURRENT_TIMESTAMP,
    last_login    timestamp with time zone,
    is_active     boolean                  default true
);

alter table users
    owner to postgres;

alter sequence users_user_id_seq1 owned by users.user_id;

create table content_types
(
    type_id   serial
        primary key,
    type_name varchar(50) not null
        unique,
    type_icon varchar(100)
);

alter table content_types
    owner to postgres;

create table media_items
(
    item_id       serial
        primary key,
    type_id       integer
        references content_types,
    title         varchar(255) not null,
    creator       varchar(255),
    description   text,
    thumbnail_url varchar(255),
    external_url  varchar(255),
    release_date  date,
    added_at      timestamp with time zone default CURRENT_TIMESTAMP
);

alter table media_items
    owner to postgres;

create index idx_media_items_type_id
    on media_items (type_id);

create table tags
(
    tag_id   serial
        primary key,
    tag_name varchar(50) not null
        unique
);

alter table tags
    owner to postgres;

create table media_tags
(
    media_id integer not null
        references media_items
            on delete cascade,
    tag_id   integer not null
        references tags
            on delete cascade,
    primary key (media_id, tag_id)
);

alter table media_tags
    owner to postgres;

create table collections
(
    collection_id   serial
        primary key,
    title           varchar(255) not null,
    description     text,
    cover_image_url varchar(255),
    user_id         integer
        references users
            on delete cascade,
    created_at      timestamp with time zone default CURRENT_TIMESTAMP,
    updated_at      timestamp with time zone default CURRENT_TIMESTAMP,
    is_public       boolean                  default false,
    view_count      integer                  default 0
);

alter table collections
    owner to postgres;

create index idx_collections_user_id
    on collections (user_id);

create table collection_collaborators
(
    collection_id integer                                                      not null
        references collections
            on delete cascade,
    user_id       integer                                                      not null
        references users
            on delete cascade,
    role          varchar(20)              default 'editor'::character varying not null,
    joined_at     timestamp with time zone default CURRENT_TIMESTAMP,
    primary key (collection_id, user_id)
);

alter table collection_collaborators
    owner to postgres;

create table collection_items
(
    collection_id    integer not null
        references collections
            on delete cascade,
    item_id          integer not null
        references media_items
            on delete cascade,
    added_by_user_id integer
        references users,
    added_at         timestamp with time zone default CURRENT_TIMESTAMP,
    position         integer,
    notes            text,
    primary key (collection_id, item_id)
);

alter table collection_items
    owner to postgres;

create index idx_collection_items_collection_id
    on collection_items (collection_id);

create index idx_collection_items_item_id
    on collection_items (item_id);

create table user_follows
(
    follower_id integer not null
        references users
            on delete cascade,
    followed_id integer not null
        references users
            on delete cascade,
    followed_at timestamp with time zone default CURRENT_TIMESTAMP,
    primary key (follower_id, followed_id)
);

alter table user_follows
    owner to postgres;

create table collection_likes
(
    user_id       integer not null
        references users
            on delete cascade,
    collection_id integer not null
        references collections
            on delete cascade,
    liked_at      timestamp with time zone default CURRENT_TIMESTAMP,
    primary key (user_id, collection_id)
);

alter table collection_likes
    owner to postgres;

create table collection_comments
(
    comment_id    serial
        primary key,
    collection_id integer
        references collections
            on delete cascade,
    user_id       integer
                       references users
                           on delete set null,
    comment_text  text not null,
    created_at    timestamp with time zone default CURRENT_TIMESTAMP
);

alter table collection_comments
    owner to postgres;

create index idx_collection_comments_collection_id
    on collection_comments (collection_id);

create table achievement_types
(
    achievement_id serial
        primary key,
    name           varchar(100) not null,
    description    text,
    icon_url       varchar(255),
    requirement    varchar(255)
);

alter table achievement_types
    owner to postgres;

create table user_achievements
(
    user_id        integer not null
        references users
            on delete cascade,
    achievement_id integer not null
        references achievement_types
            on delete cascade,
    achieved_at    timestamp with time zone default CURRENT_TIMESTAMP,
    primary key (user_id, achievement_id)
);

alter table user_achievements
    owner to postgres;

create function update_collection_timestamp() returns trigger
    language plpgsql
as
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$;

alter function update_collection_timestamp() owner to postgres;

create trigger update_collection_timestamp
    before update
    on collections
    for each row
execute procedure update_collection_timestamp();

create function award_first_collection_achievement() returns trigger
    language plpgsql
as
$$
DECLARE
    achievement_type_id INTEGER;  -- Изменено имя переменной
    existing_achievement INTEGER;
BEGIN
    -- Проверяем, это первая коллекция пользователя
    IF (SELECT COUNT(*) FROM virtualcollections.collections
        WHERE user_id = NEW.user_id) = 1 THEN

        -- Получаем ID достижения "Collector Novice"
        SELECT achievement_id INTO achievement_type_id  -- Изменено имя переменной
        FROM virtualcollections.achievement_types
        WHERE requirement = 'create_1_collection';

        -- Проверяем, есть ли уже у пользователя это достижение
        SELECT 1 INTO existing_achievement
        FROM virtualcollections.user_achievements
        WHERE user_id = NEW.user_id AND achievement_id = achievement_type_id  -- Изменено имя переменной
        LIMIT 1;

        -- Если нет достижения и оно существует в таблице типов достижений
        IF existing_achievement IS NULL AND achievement_type_id IS NOT NULL THEN  -- Изменено имя переменной
            INSERT INTO virtualcollections.user_achievements (user_id, achievement_id)
            VALUES (NEW.user_id, achievement_type_id);  -- Изменено имя переменной
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

alter function award_first_collection_achievement() owner to postgres;

create trigger award_first_collection_achievement
    after insert
    on collections
    for each row
execute procedure award_first_collection_achievement();

create function award_five_collections_achievement() returns trigger
    language plpgsql
as
$$
DECLARE
    collections_count INTEGER;
    achievement_id INTEGER;
    existing_achievement INTEGER;
BEGIN
    -- Считаем количество коллекций пользователя
    SELECT COUNT(*) INTO collections_count
    FROM virtualcollections.collections
    WHERE user_id = NEW.user_id;

    -- Если это 5-я коллекция
    IF collections_count = 5 THEN
        -- Получаем ID достижения "Content Curator"
        SELECT achievement_id INTO achievement_id
        FROM virtualcollections.achievement_types
        WHERE requirement = 'create_5_collections';

        -- Проверяем, есть ли уже у пользователя это достижение
        SELECT 1 INTO existing_achievement
        FROM virtualcollections.user_achievements
        WHERE user_id = NEW.user_id AND achievement_id = achievement_id
        LIMIT 1;

        -- Если нет достижения и оно существует в таблице типов достижений, то выдаем
        IF existing_achievement IS NULL AND achievement_id IS NOT NULL THEN
            INSERT INTO virtualcollections.user_achievements (user_id, achievement_id)
            VALUES (NEW.user_id, achievement_id);
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

alter function award_five_collections_achievement() owner to postgres;

create trigger award_five_collections_achievement
    after insert
    on collections
    for each row
execute procedure award_five_collections_achievement();

create function award_ten_followers_achievement() returns trigger
    language plpgsql
as
$$
DECLARE
    followers_count INTEGER;
    achievement_id INTEGER;
    existing_achievement INTEGER;
BEGIN
    -- Считаем количество подписчиков у пользователя, на которого подписались
    SELECT COUNT(*) INTO followers_count
    FROM virtualcollections.user_follows
    WHERE followed_id = NEW.followed_id;

    -- Если это 10-й подписчик
    IF followers_count = 10 THEN
        -- Получаем ID достижения "Social Butterfly"
        SELECT achievement_id INTO achievement_id
        FROM virtualcollections.achievement_types
        WHERE requirement = 'followers_10';

        -- Проверяем, есть ли уже у пользователя это достижение
        SELECT 1 INTO existing_achievement
        FROM virtualcollections.user_achievements
        WHERE user_id = NEW.followed_id AND achievement_id = achievement_id
        LIMIT 1;

        -- Если нет достижения и оно существует в таблице типов достижений, то выдаем
        IF existing_achievement IS NULL AND achievement_id IS NOT NULL THEN
            INSERT INTO virtualcollections.user_achievements (user_id, achievement_id)
            VALUES (NEW.followed_id, achievement_id);
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

alter function award_ten_followers_achievement() owner to postgres;

create trigger award_ten_followers_achievement
    after insert
    on user_follows
    for each row
execute procedure award_ten_followers_achievement();

create function award_hundred_likes_achievement() returns trigger
    language plpgsql
as
$$
DECLARE
    total_likes INTEGER;
    achievement_id INTEGER;
    collection_owner_id INTEGER;
    existing_achievement INTEGER;
BEGIN
    -- Получаем ID владельца коллекции
    SELECT user_id INTO collection_owner_id
    FROM virtualcollections.collections
    WHERE collection_id = NEW.collection_id;

    -- Считаем общее количество лайков у коллекций этого пользователя
    SELECT COUNT(*) INTO total_likes
    FROM virtualcollections.collection_likes cl
             JOIN virtualcollections.collections c ON cl.collection_id = c.collection_id
    WHERE c.user_id = collection_owner_id;

    -- Если достигнуто ровно 100 лайков
    IF total_likes = 100 THEN
        -- Получаем ID достижения "Popular Creator"
        SELECT achievement_id INTO achievement_id
        FROM virtualcollections.achievement_types
        WHERE requirement = 'get_100_likes';

        -- Проверяем, есть ли уже у пользователя это достижение
        SELECT 1 INTO existing_achievement
        FROM virtualcollections.user_achievements
        WHERE user_id = collection_owner_id AND achievement_id = achievement_id
        LIMIT 1;

        -- Если нет достижения и оно существует в таблице типов достижений, то выдаем
        IF existing_achievement IS NULL AND achievement_id IS NOT NULL THEN
            INSERT INTO virtualcollections.user_achievements (user_id, achievement_id)
            VALUES (collection_owner_id, achievement_id);
        END IF;
    END IF;

    RETURN NEW;
END;
$$;

alter function award_hundred_likes_achievement() owner to postgres;

create trigger award_hundred_likes_achievement
    after insert
    on collection_likes
    for each row
execute procedure award_hundred_likes_achievement();

create function increment_view_count(collection_id_param integer) returns void
    language plpgsql
as
$$
BEGIN
    UPDATE virtualcollections.collections
    SET view_count = view_count + 1
    WHERE collection_id = collection_id_param;
END;
$$;

alter function increment_view_count(integer) owner to postgres;

create function prevent_self_follow() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW.follower_id = NEW.followed_id THEN
        RAISE EXCEPTION 'Users cannot follow themselves';
    END IF;
    RETURN NEW;
END;
$$;

alter function prevent_self_follow() owner to postgres;

create trigger prevent_self_follow
    before insert
    on user_follows
    for each row
execute procedure prevent_self_follow();

create function update_last_login(user_id_param integer) returns void
    language plpgsql
as
$$
BEGIN
    UPDATE virtualcollections.users
    SET last_login = CURRENT_TIMESTAMP
    WHERE user_id = user_id_param;
END;
$$;

alter function update_last_login(integer) owner to postgres;

create function prevent_owner_as_collaborator() returns trigger
    language plpgsql
as
$$
DECLARE
    owner_id INTEGER;
BEGIN
    -- Получаем ID владельца коллекции
    SELECT user_id INTO owner_id
    FROM virtualcollections.collections
    WHERE collection_id = NEW.collection_id;

    -- Если пытаемся добавить владельца как соавтора
    IF NEW.user_id = owner_id THEN
        RAISE EXCEPTION 'Collection owner cannot be added as a collaborator';
    END IF;
    RETURN NEW;
END;
$$;

alter function prevent_owner_as_collaborator() owner to postgres;

create trigger prevent_owner_as_collaborator
    before insert
    on collection_collaborators
    for each row
execute procedure prevent_owner_as_collaborator();

create function limit_collaborators_count() returns trigger
    language plpgsql
as
$$
DECLARE
    collaborators_count INTEGER;
BEGIN
    -- Считаем текущее количество соавторов
    SELECT COUNT(*) INTO collaborators_count
    FROM virtualcollections.collection_collaborators
    WHERE collection_id = NEW.collection_id;

    -- Если уже 10 соавторов, то отклоняем добавление еще одного
    IF collaborators_count >= 10 THEN
        RAISE EXCEPTION 'Cannot add more than 10 collaborators to a collection';
    END IF;
    RETURN NEW;
END;
$$;

alter function limit_collaborators_count() owner to postgres;

create trigger limit_collaborators_count
    before insert
    on collection_collaborators
    for each row
execute procedure limit_collaborators_count();

create function create_default_user_data() returns trigger
    language plpgsql
as
$$
DECLARE
    -- Переменные для хранения ID созданных записей
    collection_id1 INTEGER;
    collection_id2 INTEGER;
    collection_id3 INTEGER;

    media_id1 INTEGER;
    media_id2 INTEGER;
    media_id3 INTEGER;
    media_id4 INTEGER;
    media_id5 INTEGER;

    tag_id1 INTEGER;
    tag_id2 INTEGER;
    tag_id3 INTEGER;

    -- Даты для использования в тестовых данных
    today_date TIMESTAMP = NOW();
    past_date TIMESTAMP = NOW() - INTERVAL '10 days';
    future_date TIMESTAMP = NOW() + INTERVAL '5 days';

    -- ID типов контента
    movie_type_id INTEGER;
    book_type_id INTEGER;
    song_type_id INTEGER;

    -- ID достижений
    first_collection_achievement_id INTEGER;  -- Изменено имя переменной

BEGIN
    -- Получаем ID типов контента
    SELECT type_id INTO movie_type_id FROM virtualcollections.content_types WHERE type_name = 'movie' LIMIT 1;
    SELECT type_id INTO book_type_id FROM virtualcollections.content_types WHERE type_name = 'book' LIMIT 1;
    SELECT type_id INTO song_type_id FROM virtualcollections.content_types WHERE type_name = 'song' LIMIT 1;

    -- Если типы контента не найдены, используем значения по умолчанию
    IF movie_type_id IS NULL THEN
        movie_type_id := 1;
    END IF;

    IF book_type_id IS NULL THEN
        book_type_id := 2;
    END IF;

    IF song_type_id IS NULL THEN
        song_type_id := 3;
    END IF;

    -- Получаем ID достижения за первую коллекцию
    SELECT achievement_id INTO first_collection_achievement_id  -- Изменено имя переменной
    FROM virtualcollections.achievement_types
    WHERE requirement = 'create_1_collection' LIMIT 1;

    -- Создаем тестовые теги для пользователя
    INSERT INTO virtualcollections.tags (tag_name)
    VALUES ('Favorite_' || NEW.username)
    ON CONFLICT DO NOTHING
    RETURNING tag_id INTO tag_id1;

    INSERT INTO virtualcollections.tags (tag_name)
    VALUES ('Recommended_' || NEW.username)
    ON CONFLICT DO NOTHING
    RETURNING tag_id INTO tag_id2;

    INSERT INTO virtualcollections.tags (tag_name)
    VALUES ('MustSee_' || NEW.username)
    ON CONFLICT DO NOTHING
    RETURNING tag_id INTO tag_id3;

    -- Создаем тестовые медиа-элементы
    INSERT INTO virtualcollections.media_items (
        type_id, title, creator, description,
        thumbnail_url, external_url, release_date, added_at
    )
    VALUES (
               movie_type_id,
               'Favorite Movie of ' || NEW.username,
               'Famous Director',
               'This is a must-watch movie for everyone',
               'https://picsum.photos/id/10/200/300',
               'https://example.com/movie/1',
               today_date - INTERVAL '1 year',
               past_date
           )
    RETURNING item_id INTO media_id1;

    INSERT INTO virtualcollections.media_items (
        type_id, title, creator, description,
        thumbnail_url, external_url, release_date, added_at
    )
    VALUES (
               book_type_id,
               'Must-Read Book for ' || NEW.username,
               'Great Author',
               'This book will change your perspective on life',
               'https://picsum.photos/id/20/200/300',
               'https://example.com/book/1',
               today_date - INTERVAL '5 years',
               past_date
           )
    RETURNING item_id INTO media_id2;

    INSERT INTO virtualcollections.media_items (
        type_id, title, creator, description,
        thumbnail_url, external_url, release_date, added_at
    )
    VALUES (
               song_type_id,
               'Favorite Song of ' || NEW.username,
               'Amazing Artist',
               'This song will lift your spirits',
               'https://picsum.photos/id/30/200/300',
               'https://example.com/song/1',
               today_date - INTERVAL '6 months',
               past_date
           )
    RETURNING item_id INTO media_id3;

    INSERT INTO virtualcollections.media_items (
        type_id, title, creator, description,
        thumbnail_url, external_url, release_date, added_at
    )
    VALUES (
               movie_type_id,
               'Classic Movie for ' || NEW.username,
               'Legendary Director',
               'A timeless classic everyone should watch',
               'https://picsum.photos/id/40/200/300',
               'https://example.com/movie/2',
               today_date - INTERVAL '20 years',
               past_date
           )
    RETURNING item_id INTO media_id4;

    INSERT INTO virtualcollections.media_items (
        type_id, title, creator, description,
        thumbnail_url, external_url, release_date, added_at
    )
    VALUES (
               book_type_id,
               'Recent Book for ' || NEW.username,
               'New Author',
               'A recent release that is getting great reviews',
               'https://picsum.photos/id/50/200/300',
               'https://example.com/book/2',
               today_date - INTERVAL '1 month',
               past_date
           )
    RETURNING item_id INTO media_id5;

    -- Связываем медиа с тегами
    IF tag_id1 IS NOT NULL THEN
        INSERT INTO virtualcollections.media_tags (media_id, tag_id)
        VALUES
            (media_id1, tag_id1),
            (media_id3, tag_id1)
        ON CONFLICT DO NOTHING;
    END IF;

    IF tag_id2 IS NOT NULL THEN
        INSERT INTO virtualcollections.media_tags (media_id, tag_id)
        VALUES
            (media_id2, tag_id2),
            (media_id5, tag_id2)
        ON CONFLICT DO NOTHING;
    END IF;

    IF tag_id3 IS NOT NULL THEN
        INSERT INTO virtualcollections.media_tags (media_id, tag_id)
        VALUES
            (media_id1, tag_id3),
            (media_id4, tag_id3)
        ON CONFLICT DO NOTHING;
    END IF;

    -- Создаем тестовые коллекции для пользователя
    INSERT INTO virtualcollections.collections (
        title, description, cover_image_url, user_id,
        created_at, updated_at, is_public, view_count
    )
    VALUES (
               'Favorites of ' || NEW.username,
               'My all-time favorite movies, books, and songs',
               'https://picsum.photos/id/100/800/400',
               NEW.user_id,
               past_date,
               past_date,
               TRUE,
               5
           )
    RETURNING collection_id INTO collection_id1;

    INSERT INTO virtualcollections.collections (
        title, description, cover_image_url, user_id,
        created_at, updated_at, is_public, view_count
    )
    VALUES (
               'Must Watch - ' || NEW.username,
               'Collection of must-watch movies and shows',
               'https://picsum.photos/id/200/800/400',
               NEW.user_id,
               past_date,
               past_date,
               FALSE,
               0
           )
    RETURNING collection_id INTO collection_id2;

    INSERT INTO virtualcollections.collections (
        title, description, cover_image_url, user_id,
        created_at, updated_at, is_public, view_count
    )
    VALUES (
               'Reading List - ' || NEW.username,
               'Books I want to read in the future',
               'https://picsum.photos/id/300/800/400',
               NEW.user_id,
               past_date,
               past_date,
               TRUE,
               3
           )
    RETURNING collection_id INTO collection_id3;

    -- Добавляем элементы в коллекции
    INSERT INTO virtualcollections.collection_items (
        collection_id, item_id, added_by_user_id,
        added_at, position, notes
    )
    VALUES
        (collection_id1, media_id1, NEW.user_id, past_date, 1, 'Absolute favorite!'),
        (collection_id1, media_id3, NEW.user_id, past_date, 2, NULL),
        (collection_id2, media_id4, NEW.user_id, past_date, 1, 'Must watch for cinema history'),
        (collection_id2, media_id1, NEW.user_id, past_date, 2, NULL),
        (collection_id3, media_id2, NEW.user_id, past_date, 1, 'Highly recommended by friends'),
        (collection_id3, media_id5, NEW.user_id, past_date, 2, 'New release I want to check out');

    -- Выдаем достижение за первую коллекцию (если оно определено)
    IF first_collection_achievement_id IS NOT NULL THEN  -- Изменено имя переменной
        INSERT INTO virtualcollections.user_achievements (user_id, achievement_id, achieved_at)
        VALUES (NEW.user_id, first_collection_achievement_id, past_date)  -- Изменено имя переменной
        ON CONFLICT DO NOTHING;
    END IF;

    RETURN NEW;
END;
$$;

alter function create_default_user_data() owner to postgres;

create trigger create_default_user_data
    after insert
    on users
    for each row
execute procedure create_default_user_data();

