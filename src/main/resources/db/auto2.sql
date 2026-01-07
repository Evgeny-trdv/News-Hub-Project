INSERT INTO user_interests(users_id, interests) VALUES
(1, 'Sport'),
(2, 'Sport'),
(3, 'Sport'),
(1, 'Politics'),
(4, 'Politics');

INSERT INTO user_favorites(article_id, user_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(1, 4),
(1, 3),
(2, 3);

INSERT INTO read_history(article_id, user_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(1, 4),
(1, 3),
(1, 2),
(3, 1),
(3, 3),
(2, 3);

INSERT INTO article_tag(article_id, tags) VALUES
(1, 'kitty'),
(1, 'doge'),
(1, 'horse'),
(2, 'USA');

INSERT INTO source_categories(source_id, categories) VALUES
(1, 'Sport'),
(1, 'Politics');