-- ***********************************************************************************
--                    INSERT WORKFLOW_TRANSITIONS DEFAULT VALUES
-- ***********************************************************************************
-- Определяет допустимые переходы между статусами в рамках каждого рабочего процесса

BEGIN;

INSERT INTO public.workflow_transitions (workflow_id, name, from_status_id, to_status_id) VALUES
-- Эпик
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Уточнение идеи', (SELECT id FROM public.issue_statuses WHERE name = 'Conceived'), (SELECT id FROM public.issue_statuses WHERE name = 'Research')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Идея не актуальна', (SELECT id FROM public.issue_statuses WHERE name = 'Conceived'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Утвердить идею для планирования', (SELECT id FROM public.issue_statuses WHERE name = 'Research'), (SELECT id FROM public.issue_statuses WHERE name = 'Approved')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Идея не соответствует критериям', (SELECT id FROM public.issue_statuses WHERE name = 'Research'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Вернуть идею на доработку', (SELECT id FROM public.issue_statuses WHERE name = 'Research'), (SELECT id FROM public.issue_statuses WHERE name = 'Conceived')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Запланировано на квартал/релиз', (SELECT id FROM public.issue_statuses WHERE name = 'Approved'), (SELECT id FROM public.issue_statuses WHERE name = 'Planned')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Отмена утверждения', (SELECT id FROM public.issue_statuses WHERE name = 'Approved'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Начало работы команды', (SELECT id FROM public.issue_statuses WHERE name = 'Planned'), (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Выведение из плана', (SELECT id FROM public.issue_statuses WHERE name = 'Planned'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Приостановлено, всвязи с изменением приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'Planned'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Передано на проверку', (SELECT id FROM public.issue_statuses WHERE name = 'In Progress'), (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Приостановлено, всвязи с возникшими проблемами', (SELECT id FROM public.issue_statuses WHERE name = 'In Progress'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Соответствует критериям', (SELECT id FROM public.issue_statuses WHERE name = 'In Review'), (SELECT id FROM public.issue_statuses WHERE name = 'Accepted')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Возврат на доработку', (SELECT id FROM public.issue_statuses WHERE name = 'In Review'), (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Требуются дополнительные ресурсы', (SELECT id FROM public.issue_statuses WHERE name = 'In Review'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Не соответствует критериям', (SELECT id FROM public.issue_statuses WHERE name = 'In Review'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Восстановление в планах на квартал/релиз', (SELECT id FROM public.issue_statuses WHERE name = 'On Hold'), (SELECT id FROM public.issue_statuses WHERE name = 'Planned')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Отменено', (SELECT id FROM public.issue_statuses WHERE name = 'On Hold'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), 'Финализирование эпик процесса', (SELECT id FROM public.issue_statuses WHERE name = 'Accepted'), (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),

-- История
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Перейти к детализации', (SELECT id FROM public.issue_statuses WHERE name = 'Backlog'), (SELECT id FROM public.issue_statuses WHERE name = 'Research')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Пользовательская история не актуальна', (SELECT id FROM public.issue_statuses WHERE name = 'Backlog'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Утвердить пользовательскую историю для планирования', (SELECT id FROM public.issue_statuses WHERE name = 'Research'), (SELECT id FROM public.issue_statuses WHERE name = 'Approved')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Пользовательская история не соответствует критериям', (SELECT id FROM public.issue_statuses WHERE name = 'Research'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Запланировано на спринт', (SELECT id FROM public.issue_statuses WHERE name = 'Approved'), (SELECT id FROM public.issue_statuses WHERE name = 'Planned')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Отмена утверждения пользовательской истории', (SELECT id FROM public.issue_statuses WHERE name = 'Approved'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Подготовлено к разработке', (SELECT id FROM public.issue_statuses WHERE name = 'Planned'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Development')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Выведение пользовательской истории из плана', (SELECT id FROM public.issue_statuses WHERE name = 'Planned'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Изменение приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'Planned'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Начало разработки', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Development'), (SELECT id FROM public.issue_statuses WHERE name = 'In Development')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с изменением приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Development'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Код готов для проверки', (SELECT id FROM public.issue_statuses WHERE name = 'In Development'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Code Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с возникшими проблемами', (SELECT id FROM public.issue_statuses WHERE name = 'In Development'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Успешно пройден code review', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Code Review'), (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Passed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Не пройден code review', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Code Review'), (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Failed')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Передача кода в тестирование', (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Passed'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for QA')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Возврат на доработку', (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Failed'), (SELECT id FROM public.issue_statuses WHERE name = 'In Development')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с изменением приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Failed'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Начало тестирования', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for QA'), (SELECT id FROM public.issue_statuses WHERE name = 'In QA')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с изменением приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for QA'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Тесты успешно выполнены', (SELECT id FROM public.issue_statuses WHERE name = 'In QA'), (SELECT id FROM public.issue_statuses WHERE name = 'QA Passed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Тестирование не пройдены', (SELECT id FROM public.issue_statuses WHERE name = 'In QA'), (SELECT id FROM public.issue_statuses WHERE name = 'QA Failed')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Передача разработанного и протестированного функционала в проверку', (SELECT id FROM public.issue_statuses WHERE name = 'QA Passed'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Review')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Возврат на доработку', (SELECT id FROM public.issue_statuses WHERE name = 'QA Failed'), (SELECT id FROM public.issue_statuses WHERE name = 'In Development')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с изменением приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'QA Failed'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Начало проверки', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Review'), (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с изменением приоритетов', (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Review'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Проверка успешно пройдена', (SELECT id FROM public.issue_statuses WHERE name = 'In Review'), (SELECT id FROM public.issue_statuses WHERE name = 'Review Passed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Проверка не пройдена', (SELECT id FROM public.issue_statuses WHERE name = 'In Review'), (SELECT id FROM public.issue_statuses WHERE name = 'Review Failed')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Пользовательская история завершена', (SELECT id FROM public.issue_statuses WHERE name = 'Review Passed'), (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Приостановлено, всвязи с возникновением проблем', (SELECT id FROM public.issue_statuses WHERE name = 'Review Failed'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Отменено, всвязи с возникновением проблем', (SELECT id FROM public.issue_statuses WHERE name = 'Review Failed'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Восстановление в планах на спринт', (SELECT id FROM public.issue_statuses WHERE name = 'On Hold'), (SELECT id FROM public.issue_statuses WHERE name = 'Planned')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), 'Отменено', (SELECT id FROM public.issue_statuses WHERE name = 'On Hold'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected'));

COMMIT;
