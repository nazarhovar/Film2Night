create table film
(
    kinopoiskId              int          not null
        primary key,
    nameOriginal             varchar(45)  not null,
    posterUrl                varchar(200) null,
    ratingKinopoisk          float        null,
    ratingKinopoiskVoteCount int          null,
    webUrl                   varchar(45)  null,
    year                     int          null,
    filmLength               int          null,
    lastSync                 datetime     null,
    isBlocked                varchar(50)  null
);

create table country
(
    id   int auto_increment
        primary key,
    name varchar(45) null
);

create table film_country
(
    film_id    int not null,
    country_id int not null,
    constraint film_country_film_kinopoiskId_fk
        foreign key (film_id) references film (kinopoiskId),
    constraint film_country_country_id_fk
        foreign key (country_id) references country (id)
);

create table genre
(
    id   int auto_increment
        primary key,
    name varchar(45) null
);

create table film_genre
(
    film_id  int null,
    genre_id int null,
    constraint film_genre_film_kinopoiskId_fk
        foreign key (film_id) references film (kinopoiskId),
    constraint film_genre_genre_id_fk
        foreign key (genre_id) references genre (id)
);

create table Top250
(
    id   int auto_increment
        primary key,
    name varchar(100) null
);


