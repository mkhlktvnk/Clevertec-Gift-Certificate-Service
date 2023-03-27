CREATE TABLE IF NOT EXISTS tags
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS gift_certificates
(
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    name             VARCHAR(255)   NOT NULL,
    description      VARCHAR(1000)  NOT NULL,
    price            NUMERIC(40, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      TIMESTAMP DEFAULT NOW(),
    last_update_date TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS gift_certificates_tags
(
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id)
        ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id)
        ON DELETE CASCADE
);
