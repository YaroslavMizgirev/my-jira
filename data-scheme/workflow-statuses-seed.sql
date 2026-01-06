-- ***********************************************************************************
--                    INSERT WORKFLOW_STATUSES DEFAULT VALUES
-- ***********************************************************************************
-- Связь рабочих процессов (workflows) со статусами задач (issue_statuses)
-- Каждая строка указывает, какие статусы доступны в конкретном рабочем процессе

BEGIN;

INSERT INTO public.workflow_statuses (workflow_id, status_id) VALUES
-- Статусы рабочего процесса для эпиков
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Conceived')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Research')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Approved')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Planned')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'In Progress')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Accepted')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для эпиков'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected')),

-- Статусы рабочего процесса для историй
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Backlog')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Research')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Approved')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Planned')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Development')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'In Development')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Code Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Passed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Code Review Failed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for QA')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'In QA')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'QA Passed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'QA Failed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Ready for Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'In Review')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Review Passed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Review Failed')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Resolved')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'On Hold')),
((SELECT id FROM public.workflows WHERE name = 'Рабочий процесс для пользовательских историй'), (SELECT id FROM public.issue_statuses WHERE name = 'Rejected'));

-- Статусы рабочего процесса для задач

-- Статусы рабочего процесса для багов

-- Статусы рабочего процесса для улучшений

-- Статусы рабочего процесса для документации

COMMIT;
