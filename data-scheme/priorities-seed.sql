-- ***********************************************************************************
--                    INSERT PRIORITIES DEFAULT VALUES
-- ***********************************************************************************

BEGIN;

INSERT INTO public.priorities (level, name, icon_url, color_hex_code) VALUES
-- Приоритеты от самого высокого к самому низкому
(1, 'Highest', './icons/priority-highest-48.png', 'rgba(255, 0, 0, 1)'),     -- Красный для наивысшего приоритета
(2, 'High', './icons/priority-high-48.png', 'rgba(139, 0, 255, 1)'),         -- Фиолетовый для высокого приоритета
(3, 'Medium', './icons/priority-medium-48.png', 'rgba(255, 157, 0, 1)'),     -- Оранжевый для среднего приоритета
(4, 'Low', './icons/priority-low-48.png', 'rgba(255, 255, 0, 1)'),           -- Желтый для низкого приоритета
(5, 'Lowest', './icons/priority-lowest-48.png', 'rgba(0, 0, 255, 1)');       -- Синий для самого низкого приоритета

COMMIT;
