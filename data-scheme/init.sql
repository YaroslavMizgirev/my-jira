-- =============================================
-- БАЗОВЫЕ НАСТРОЙКИ
-- =============================================

-- 1. НАСТРОЙКИ ВРЕМЕНИ И ДАТЫ
ALTER DATABASE myjira SET timezone TO 'Europe/Moscow';
ALTER DATABASE myjira SET datestyle TO 'ISO, DMY';

-- 2. НАСТРОЙКИ ТРАНЗАКЦИЙ И ПРОИЗВОДИТЕЛЬНОСТИ
-- Уровень изоляции транзакций:
--"Read Committed" - золотая середина для веб-приложений
-- Гарантирует: что транзакция видит только закоммиченные данные других транзакций
-- Предотвращает: "грязное чтение" (чтение незакоммиченных данных)
ALTER DATABASE myjira SET default_transaction_isolation TO 'read committed';
-- предотвращает вечную блокировку (deadlocks)
ALTER DATABASE myjira SET lock_timeout TO '10s';
-- statement_timeout - защита от "долгих" запросов
ALTER DATABASE myjira SET statement_timeout TO '30s';

-- 3. ПОДКЛЮЧАЕМ РАСШИРЕНИЯ
-- Расширение uuid-ossp:
-- Генерация UUID: uuid_generate_v4() - для создания уникальных идентификаторов
-- Если захотите использовать UUID вместо bigint
-- ALTER TABLE issues ADD COLUMN external_id UUID DEFAULT uuid_generate_v4();
-- Интеграции: Внешние системы часто используют UUID для ссылок
-- Безопасность: Сложнее угадать идентификаторы чем последовательные числа
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- Расширение pgcrypto:
-- Зачем это нужно:
-- Хеширование: crypt() - для безопасного хранения паролей
-- Шифрование: pgp_sym_encrypt() - если нужно зашифровать чувствительные данные
-- Генерация случайных данных: gen_random_bytes()
-- Пример использования:
-- Для улучшения безопасности паролей (дополнительно к хэшу в приложении)
-- UPDATE users SET password_hash = crypt('new_password', gen_salt('bf', 8)) WHERE id = 1;
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
-- btree_gin - для сложных индексов (если понадобится)
CREATE EXTENSION IF NOT EXISTS "btree_gin";

-- 4. НАСТРОЙКИ ПОИСКА И ТЕКСТА
-- lc_messages - сообщения СУБД
-- Влияет на:
-- * Сообщения об ошибках на русском
-- * Логи PostgreSQL
-- * Системные уведомления
ALTER DATABASE myjira SET lc_messages TO 'ru_RU.UTF-8';
-- lc_monetary - денежные форматы
-- Влияет на:
-- * Формат денежных сумм
-- * Символ валюты
-- * Разделители
ALTER DATABASE myjira SET lc_monetary TO 'ru_RU.UTF-8';
-- lc_numeric - числовые форматы
-- Влияет на:
-- * Разделитель тысяч и десятичных дробей
-- * Формат чисел
ALTER DATABASE myjira SET lc_numeric TO 'ru_RU.UTF-8';
-- lc_time - форматы даты/времени
-- Влияет на:
-- * Названия месяцев и дней недели
-- * Формат отображения дат
-- * AM/PM vs 24-часовой формат
ALTER DATABASE myjira SET lc_time TO 'ru_RU.UTF-8';

-- 5. СПЕЦИФИЧНЫЕ ДЛЯ JIRA НАСТРОЙКИ
-- Увеличиваем максимальное количество соединений (если нужно)
-- ALTER SYSTEM SET max_connections = '200';

-- Комментарий о назначении базы данных
COMMENT ON DATABASE myjira IS 'Issue tracking system database';