-- ***********************************************************************************
--                    INSERT PRIORITIES DEFAULT VALUES
-- ***********************************************************************************

BEGIN;

INSERT INTO public.priorities (level, name, color_hex_code, icon_url) VALUES
-- Приоритеты от самого высокого к самому низкому
(1, 'Highest', 'rgba(255, 0, 0, 1)', './icons/priority-highest-48.png'), -- Красный для наивысшего приоритета
(2, 'High', 'rgba(139, 0, 255, 1)', './icons/priority-high-48.png'),     -- Фиолетовый для высокого приоритета
(3, 'Medium', 'rgba(255, 157, 0, 1)', './icons/priority-medium-48.png'), -- Оранжевый для среднего приоритета
(4, 'Low', 'rgba(255, 255, 0, 1)', './icons/priority-low-48.png'),       -- Желтый для низкого приоритета
(5, 'Lowest', 'rgba(0, 0, 255, 1)', './icons/priority-lowest-48.png'); -- Синий для самого низкого приоритета

COMMIT;
