SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO reply (id, pet_id, letter_id, summary, content, status,
                   inspection, inspection_time, read_status, gpt_content, prompt_type,
                   submit_time, created_at, updated_at)
VALUES (1, 1, 1, '형아 콩이 여기서 잘 지내!', '콩이는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'REPLY',
        true, '2024-01-01 12:00:00.000000', 'UNREAD', '콩이는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'A',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO reply (id, pet_id, letter_id, summary, content, status,
                   inspection, inspection_time, read_status, gpt_content, prompt_type,
                   submit_time, created_at, updated_at)
VALUES (2, 1, 2, '형아 콩이 여기서 잘 지내!', '콩이는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'CHAT_GPT',
        false, null, 'UNREAD', '콩이는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'A',
        null, '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO reply (id, pet_id, letter_id, summary, content, status,
                   inspection, inspection_time, read_status, gpt_content, prompt_type,
                   submit_time, created_at, updated_at)
VALUES (3, 1, 3, '엄마 미키 여기서 잘 지내!', '미키도 엄마가 그린 그림 보고싶어.', 'REPLY',
        true, '2024-01-01 12:00:00.000000', 'READ', '미키도 엄마가 그린 그림 보고싶어.', 'A',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO reply (id, pet_id, letter_id, summary, content, status,
                   inspection, inspection_time, read_status, gpt_content, prompt_type,
                   submit_time, created_at, updated_at)
VALUES (4, 1, 4, '엄마 미키 여기서 잘 지내!', '미키는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'CHAT_GPT',
        true, '2024-01-01 12:00:00.000000', 'READ', '미키는 무지개마을에서 따듯한 햇살을 느끼고있어. 사랑해!', 'A',
        null, '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

SET FOREIGN_KEY_CHECKS = 1;