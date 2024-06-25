SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO letter (id, user_id, pet_id, summary, content, share_link,
                    image, status, created_at, updated_at)
VALUES (1, 1, 1, '콩아 형아야', '콩아 형아야 잘 지내고 있지? 형아가 많이 보고싶어', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'objectKey', 'RESPONSE', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO letter (id, user_id, pet_id, summary, content, share_link,
                    image, status, created_at, updated_at)
VALUES (2, 1, 1, '콩이 안녕', '콩이 안녕 밥은 먹었어?', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'objectKey', 'REQUEST', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO letter (id, user_id, pet_id, summary, content,
                    share_link, image, status,
                    created_at, updated_at)
VALUES (3, 1, 2, '미키야 엄마가 보고싶다.', '미키야 엄마가 보고싶다. 엄마는 오늘 미키 생각하면서 그림을 그렸어.',
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'objectKey', 'RESPONSE',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO letter (id, user_id, pet_id, summary, content, share_link,
                    image, status, created_at, updated_at)
VALUES (4, 1, 2, '미키야 엄마야', '미키야 엄마야 엄마가 오늘 미키 생각이 많이 났어', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        null, 'REQUEST', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

SET FOREIGN_KEY_CHECKS = 1;