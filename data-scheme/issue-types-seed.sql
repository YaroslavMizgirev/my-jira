-- ***********************************************************************************
--                    INSERT ISSUE_TYPES DEFAULT VALUES
-- ***********************************************************************************

INSERT INTO public.issue_types (name, icon_url, color_hex_code) VALUES
-- Основные типы задач
('Bug', 'https://cdn-icons-png.flaticon.com/128/1828/1828270.png', '#E74C3C'),             -- Красный для багов
('Task', 'https://cdn-icons-png.flaticon.com/128/2920/2920222.png', '#343fdbff'),          -- Синий для задач
('Story', 'https://cdn-icons-png.flaticon.com/128/3369/3369154.png', '#15a14fff'),         -- Зелёный для историй
('Epic', 'https://cdn-icons-png.flaticon.com/128/3050/3050159.png', '#842ba7ff'),          -- Фиолетовый для эпиков
('Sub-task', 'https://cdn-icons-png.flaticon.com/128/1995/1995467.png', '#ff9d00ff'),      -- Оранжевый для подзадач
('Improvement', 'https://cdn-icons-png.flaticon.com/128/3588/3588592.png', '#18be9dff'),   -- Бирюза для улучшений
('Documentation', 'https://cdn-icons-png.flaticon.com/128/4034/4034920.png', '#34495E');   -- Тёмно-серый для документации

COMMIT;
