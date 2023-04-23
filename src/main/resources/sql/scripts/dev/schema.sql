CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tags
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id                  SERIAL PRIMARY KEY,
    total_price         NUMERIC(19, 2) NOT NULL,
    purchase_time       TIMESTAMP      NOT NULL DEFAULT NOW(),
    gift_certificate_id BIGINT         NOT NULL,
    user_id             BIGINT         NOT NULL,
    CONSTRAINT fk_orders_gift_certificate_id FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id),
    CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS gift_certificates
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255)   NOT NULL,
    description      TEXT           NOT NULL,
    price            NUMERIC(19, 2) NOT NULL,
    duration         INTEGER        NOT NULL,
    create_date      TIMESTAMP      NOT NULL DEFAULT NOW(),
    last_update_date TIMESTAMP      NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_gift_certificates_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS gift_certificates_tags
(
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,
    CONSTRAINT pk_gift_certificates_tags PRIMARY KEY (gift_certificate_id, tag_id),
    CONSTRAINT fk_gift_certificates_tags_gift_certificate_id FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id),
    CONSTRAINT fk_gift_certificates_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id)
);