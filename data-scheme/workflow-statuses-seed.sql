-- ***********************************************************************************
--                    INSERT WORKFLOW_STATUSES DEFAULT VALUES
-- ***********************************************************************************
-- Связь рабочих процессов (workflows) со статусами задач (issue_statuses)
-- Каждая строка указывает, какие статусы доступны в конкретном рабочем процессе

BEGIN;

-- Базовый рабочий процесс - включает все статусы
INSERT INTO public.workflow_statuses (workflow_id, status_id) VALUES
-- Базовый рабочий процесс (id=1)
(1, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'Reopened')),
(1, (SELECT id FROM public.issue_statuses WHERE name = 'Blocked')),

-- Рабочий процесс для багов (id=2)
(2, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'Reopened')),
(2, (SELECT id FROM public.issue_statuses WHERE name = 'Blocked')),

-- Рабочий процесс для историй (id=3)
(3, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(3, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(3, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(3, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(3, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(3, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(3, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

-- Рабочий процесс для эпиков (id=4)
(4, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(4, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(4, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(4, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(4, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(4, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

-- Рабочий процесс для улучшений (id=5)
(5, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(5, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(5, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(5, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(5, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(5, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(5, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

-- Упрощенный рабочий процесс (id=6)
(6, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(6, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(6, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),

-- Рабочий процесс для документации (id=7)
(7, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(7, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(7, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(7, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),

-- Расширенный рабочий процесс (id=8)
(8, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'Reopened')),
(8, (SELECT id FROM public.issue_statuses WHERE name = 'Blocked')),

-- Рабочий процесс с этапом проверки (id=9)
(9, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(9, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(9, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(9, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(9, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(9, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(9, (SELECT id FROM public.issue_statuses WHERE name = 'Reopened')),

-- Рабочий процесс для Scrum (id=10)
(10, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(10, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(10, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(10, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(10, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(10, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),

-- Рабочий процесс для Kanban (id=11)
(11, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(11, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(11, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(11, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(11, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(11, (SELECT id FROM public.issue_statuses WHERE name = 'Closed')),
(11, (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),

-- Рабочий процесс для Waterfall (id=12)
(12, (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
(12, (SELECT id FROM public.issue_statuses WHERE name = 'Open')),
(12, (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
(12, (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
(12, (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
(12, (SELECT id FROM public.issue_statuses WHERE name = 'Closed'));

COMMIT;
