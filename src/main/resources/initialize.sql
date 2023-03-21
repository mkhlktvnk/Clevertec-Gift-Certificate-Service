CREATE TABLE IF NOT EXISTS tags
(
    id   BIGSERIAL    NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS gift_certificates
(
    id               BIGSERIAL      NOT NULL,
    name             VARCHAR(255)   NOT NULL,
    description      VARCHAR(1000)  NOT NULL,
    price            NUMERIC(40, 2) NOT NULL,
    create_date      TIMESTAMP      NOT NULL,
    last_update_date TIMESTAMP      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS gift_certificates_tags
(
    id                  BIGSERIAL NOT NULL,
    gift_certificate_id BIGSERIAL NOT NULL,
    tag_id              BIGSERIAL NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id),
    FOREIGN KEY (tag_id) REFERENCES tags (id)
);