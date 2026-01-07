INSERT INTO users (id, created_at, email, password, user_status, updated_at, user_score, username) VALUES
(1, '2025-01-02 10:30:00', 'ev@mail.ru', 'zxc', 'ACTIVE', '2025-01-03 10:30:00', 0, 'Evgeny'),
(2, '2025-01-03 10:30:00', 'AQ@mail.ru', 'zxc', 'ACTIVE', '2025-01-04 10:30:00', 0, 'Alex'),
(3, '2025-01-01 10:30:00', 'qqqq@mail.ru', 'zxc', 'ACTIVE', '2025-01-04 10:30:00', 0, 'GLoria'),
(4, '2024-12-29 10:30:00', 'c1_c1@mail.ru', 'zxc', 'ACTIVE', '2025-01-01 10:30:00', 0, 'Timur'),
(5, '2024-12-30 10:30:00', 'well_9@mail.ru', 'zxc', 'ACTIVE', '2025-01-02 10:30:00', 0, 'Sarah');

INSERT INTO news_source(id, articles_count, country, created_at, description, is_active, language, last_parsed_at, logo_url, name, primary_category, rss_feed_url, slug, trust_score, update_frequency, updated_at, website_url) VALUES
    (1, 4, 'Russia', '2024-12-25 14:33:00', 'description 1', true, 'ru', null, 'logo_url_1', 'BBC news',
     'POLITICS',null, '/source/bbc-news', 50, null, '2024-12-25 14:33:00', 'https://bbc-news.com');

INSERT INTO news_articles(id, added_at, author, category, content, external_id, favorites_count, is_popular, is_processed, language, likes_count, original_url, published_at, summary, title, views_count, source_id) VALUES
(1, '2025-01-02 14:34:00', 'R.G.Red','SPORT', 'content 1', 'bbc_1',2, false,
 true, 'ru', 2, 'https://bbc.com', '2025-01-01 12:51:00', 'summary 1', 'title 1', 2, 1),
(2, '2024-12-25 14:34:00', 'A.W.Blue','POLITICS', 'content 2', 'bbc_2',1, false,
    true, 'ru', 0, 'https://bbc.com', '2024-12-22 12:51:00', 'summary 2', 'title 2', 4, 1),
(3, '2024-12-25 14:34:00', 'Y.H.Black','POLITICS', 'content 3', 'bbc_3',3, false,
 true, 'ru', 3, 'https://bbc.com', '2024-12-22 12:51:00', 'summary 3', 'title 3', 4, 1),
(4, '2024-12-25 14:34:00', 'A.K.Orange','SPORT', 'content 4', 'bbc_4',1, false,
 true, 'ru', 1, 'https://bbc.com', '2024-12-22 12:51:00', 'summary 4', 'title 4', 2, 1);

INSERT INTO article_likes(id, liked, article_id, created_at, updated_at, user_id) VALUES
(1, true, 1, '2025-01-03 12:11:03', '2025-01-03 12:11:03', 1),
(2, true, 2, '2025-01-03 13:42:33', '2025-01-03 13:42:33', 1),
(3, true, 3, '2025-01-03 13:42:33', '2025-01-03 13:42:33', 2);

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('news_articles_id_seq', (SELECT MAX(id) FROM news_articles));
SELECT setval('news_source_id_seq', (SELECT MAX(id) FROM news_source));
SELECT setval('article_likes_id_seq', (SELECT MAX(id) FROM article_likes));

