DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS url_mappings;

create table Member
(
    id integer not null,
    name varchar(255) not null,
    primary key (id)
);

create table url_mappings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ori_url VARCHAR(2083) NOT NULL UNIQUE,
    short_url VARCHAR(8) NOT NULL UNIQUE,
    request_count INT DEFAULT 0 NOT NULL
);