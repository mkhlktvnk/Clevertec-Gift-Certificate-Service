--liquibase formatted sql

INSERT INTO users (username, email)
VALUES
    ('Alice', 'alice@example.com'),
    ('Bob', 'bob@example.com'),
    ('Charlie', 'charlie@example.com'),
    ('Dave', 'dave@example.com'),
    ('Emma', 'emma@example.com'),
    ('Frank', 'frank@example.com'),
    ('Grace', 'grace@example.com'),
    ('Henry', 'henry@example.com'),
    ('Isabella', 'isabella@example.com'),
    ('John', 'john@example.com'),
    ('Kate', 'kate@example.com'),
    ('Luke', 'luke@example.com'),
    ('Mary', 'mary@example.com'),
    ('Nick', 'nick@example.com'),
    ('Olivia', 'olivia@example.com'),
    ('Peter', 'peter@example.com'),
    ('Quinn', 'quinn@example.com'),
    ('Rose', 'rose@example.com'),
    ('Sara', 'sara@example.com'),
    ('Tom', 'tom@example.com');

INSERT INTO tags (name)
VALUES
    ('food'),
    ('travel'),
    ('spa'),
    ('adventure'),
    ('nature'),
    ('fitness'),
    ('music'),
    ('books'),
    ('movies'),
    ('sports'),
    ('fashion'),
    ('technology'),
    ('art'),
    ('photography'),
    ('pets'),
    ('education'),
    ('history'),
    ('politics'),
    ('business'),
    ('finance');


INSERT INTO gift_certificates (name, description, price, duration)
VALUES
    ('Dinner for two', 'A romantic dinner for two', 100.00, 30),
    ('Weekend getaway', 'A weekend away in a luxurious hotel', 500.00, 48),
    ('Spa day', 'A relaxing day at the spa', 150.00, 8),
    ('Wine tasting tour', 'A tour of local wineries with tastings included', 200.00, 6),
    ('Cooking class', 'A hands-on cooking class with a professional chef', 250.00, 4),
    ('Adventure sports package', 'A package including bungee jumping, zip-lining, and rock climbing', 300.00, 12),
    ('Weekend spa retreat', 'A weekend retreat at a spa resort', 800.00, 48),
    ('Scuba diving package', 'A package including scuba diving lessons and diving excursions', 350.00, 10),
    ('Golf lessons', 'A series of golf lessons with a qualified instructor', 250.00, 6),
    ('Movie night package', 'A package including movie tickets and popcorn', 50.00, 2),
    ('Ski trip', 'A ski trip to a nearby resort', 400.00, 12),
    ('Wine and cheese tasting', 'A wine and cheese tasting with a sommelier', 75.00, 3),
    ('Hot air balloon ride', 'A hot air balloon ride over scenic landscapes', 300.00, 8),
    ('Dance lessons', 'A series of dance lessons with a qualified instructor', 200.00, 6),
    ('Painting class', 'A class on painting with a professional artist', 150.00, 4),
    ('Virtual reality experience', 'An immersive virtual reality experience', 100.00, 1),
    ('Escape room adventure', 'An adventure in an escape room', 80.00, 2),
    ('Sushi-making class', 'A hands-on class in making sushi', 120.00, 4),
    ('Luxury car rental', 'Rental of a luxury car for a day', 500.00, 24),
    ('Yoga retreat', 'A weekend retreat at a yoga center', 600.00, 48);

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
SELECT gc.id, t.id
FROM gift_certificates gc
         CROSS JOIN tags t
WHERE gc.id IN (1, 2, 3)
  AND t.id IN (1, 2, 3);

INSERT INTO orders (user_id, gift_certificate_id, purchase_time, total_price)
SELECT u.id, gc.id, NOW(), gc.price
FROM users u,
     gift_certificates gc
WHERE u.id IN (1, 2, 3)
  AND gc.id IN (1, 2, 3);

SELECT setval('tags_id_seq', (SELECT MAX(id) FROM tags));
SELECT setval('gift_certificates_id_seq', (SELECT MAX(id) FROM gift_certificates));
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
