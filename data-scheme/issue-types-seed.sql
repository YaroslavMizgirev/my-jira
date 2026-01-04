-- ***********************************************************************************
--                    INSERT ISSUE_TYPES DEFAULT VALUES
-- ***********************************************************************************

BEGIN;

INSERT INTO public.issue_types (name, icon_url, color_hex_code) VALUES
-- Основные типы задач
('Bug', './icons/bug-48.png', 'rgba(255, 0, 0, 1)'),                       -- Красный для багов
('Task', './icons/task-48.png', 'rgba(0, 0, 255, 1)'),                     -- Синий для задач
('Story', './icons/story-50.png', 'rgba(0, 255, 0, 1)'),                   -- Зелёный для историй
('Epic', './icons/epic-48.png', 'rgba(139, 0, 255, 1)'),                   -- Фиолетовый для эпиков
('Sub-task', './icons/task-48.png', 'rgba(255, 157, 0, 1)'),               -- Оранжевый для подзадач
('Improvement', './icons/improvement-50.png', 'rgba(48, 213, 200, 1)'),    -- Бирюза для улучшений
('Documentation', './icons/documentation-48.png', 'rgba(73, 66, 61, 1)');  -- Тёмно-серый для документации

COMMIT;
