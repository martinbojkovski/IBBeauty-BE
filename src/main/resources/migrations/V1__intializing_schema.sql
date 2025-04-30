CREATE TABLE admin_user
(
    id       UUID PRIMARY KEY,
    user_name TEXT NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR NOT NULL default USER
);

CREATE TABLE post
(
    id    UUID PRIMARY KEY,
    photo BYTEA NOT NULL,
    text  TEXT NOT NULL,
    date_published TIMESTAMP NOT NULL default CURRENT_TIMESTAMP
);

CREATE TABLE reservation
(
    id                   UUID PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    reservation_start    TIMESTAMP    NOT NULL,
    reservation_end      TIMESTAMP    NOT NULL,
    description          TEXT
);

CREATE TABLE reservation_services
(
    reservation_id UUID REFERENCES reservation (id) ON DELETE CASCADE,
    service_type   VARCHAR(50) NOT NULL,
    PRIMARY KEY (reservation_id, service_type)
);
