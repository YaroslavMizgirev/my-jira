-- ***********************************************************************************
--                    INSERT ISSUE_STATUSES DEFAULT VALUES
-- ***********************************************************************************

BEGIN;

INSERT INTO public.issue_statuses (name, description) VALUES
-- Основные статусы жизненного цикла задачи
('Conceived', "## Epic
Описание: Epic задуман и находится на этапе идеи, но не формализован.
Кто создает: Product Owner, Stakeholder.
Поля обязательные:
    - Название Epic;
    - Идея (1-2 предложения);
    - Business Value (предполагаемый).
Возможные действия:
    → Refine (Уточнить): Перейти к уточнению требований. Product Owner начинает исследование идеи.
    → Reject (Отклонить): Отменить идею.
    → Hold (Отложить): Вернуться позже для доработки идеи."),

('Refining', "## Epic
Описание: Детализация и исследование Epic. Задача находится на этапе уточнения требований и оценки.
Кто создает: Product Owner, Business Analyst.
Поля обязательные:
    - Business Case (обоснование)
    - Предварительная оценка (story points)
    - Риски и зависимости
    - Предполагаемые Stories
Проверки:
    - Есть ли MVP?
    - Соответствует ли стратегии?
    - Достаточно ли ресурсов?
Возможные действия:
    → Approve (Утвердить): Готово для планирования.
    → Reject (Отклонить): Не соответствует критериям.
    → Return to Conception (Вернуть): Нужна доп. информация."),

('Approved', "## Epic
Описание: Epic утвержден и готов к планированию и разбиению на Stories.
Кто создает: Product Owner, Scrum Master, Steering Committee, Product Council.
Поля обязательные:
    - Approved By (кто утвердил).
    - Approval Date (дата утверждения).
    - Budget (бюджет если есть).
    - Success Metrics (метрики успеха).
Триггеры:
    - Создание Stories под Epic.
    - Уведомление командам.
    - Добавление в Roadmap.
Возможные действия:
    → Plan (Запланировать): Назначить на квартал/релиз.
    → Reject (Отклонить): Отмена утверждения (редко).

## Improvement

Описание: Улучшение утверждено для реализации.
Поля обязательные:
  - Priority (приоритет);
  - Target Timeline (целевые сроки);
  - Success Metrics (метрики успеха);
  - Assigned Team (назначенная команда).
Критерии утверждения:
  - Positive ROI;
  - Aligns with strategy;
  - Resources available;
  - Acceptable risk level.
Возможные действия:
  → Plan (Запланировать): Включить в план.
  → Defer (Отложить): Утверждено, но не сейчас."),

('Planned', "## Epic

Описание: Epic запланирован на конкретный релиз или спринт и готов к выполнению.
Кто создает: Product Owner, Scrum Master, Development Team, Release Manager.
Поля обязательные:
    - Target Release (целевой релиз).
    - Priority (приоритет).
    - Dependencies (зависимости).
    - Team Assignment (назначенные команды).
Автоматические действия:
    - Создание связанных Stories.
    - Назначение Epic Owner.
    - Обновление Roadmap.
Возможные действия:
    → Start Progress (Начать): Начало работы команд.
    → Put on Hold (Приостановить): Изменение приоритетов.
    → Reject (Отклонить): Выведение из плана.

## Improvement

Описание: Улучшение включено в план работ.
Поля обязательные:
  - Sprint/Release (спринт/релиз);
  - Dependencies (зависимости);
  - Implementation Plan (план реализации).
Planning considerations:
  - Not blocking critical work;
  - Can be deprioritized if needed;
  - Clear success criteria.
Возможные действия:
  → Start Implementation (Начать реализацию).
  → Defer (Отложить): Изменение приоритетов."),

('In Progress', "## Epic

Описание: Epic находится в работе.
Кто работает: Все назначенные команды.
Поля обязательные:
    - Progress (% завершения).
    - Blockers (блокировки).
    - Last Updated (последнее обновление).
Мониторинг:
    - Еженедельные статус-встречи.
    - Отслеживание метрик.
    - Управление рисками.
Возможные действия:
    → Submit for Review (На проверку): Готово к Gate Review.
    → Put on Hold (Приостановить): Возникли проблемы.

## Task

Описание: Работа над задачей начата.
Кто работает: Назначенный исполнитель.
Поля обязательные:
  - Time Spent (затраченное время);
  - Progress Update (обновление прогресса);
  - Branch/PR если применимо.
Best Practices:
  - Регулярно обновлять прогресс;
  - Комментировать изменения;
  - Отмечать блокировки сразу.
Возможные действия:
  → Submit for Review (На ревью): Код готов к проверке.
  → Block (Заблокировать): Возникли проблемы.

## Bug

Описание: Разработчик исправляет баг.
Кто работает: Назначенный разработчик.
Поля обязательные:
  - Fix Description (описание исправления);
  - Code Changes (изменения кода);
  - Root Cause Analysis (анализ причины).
Best Practices:
  - Write regression tests;
  - Document the fix;
  - Consider similar issues.
Возможные действия:
  → Ready for Review (Готово к ревью): Исправление готово.
  → Cannot Fix (Невозможно исправить): Технические ограничения.

## Improvement

Описание: Реализация улучшения.
Отличия от Bug/Story implementation:
  - More experimental approach;
  - A/B testing;
  - Gradual rollout.
Поля обязательные:
  - Implementation Approach (подход к реализации);
  - Experiment Design (дизайн эксперимента);
  - Rollout Plan (план внедрения).
Возможные действия:
  → Ready for Review (Готово к ревью)."),

('In Review', "## Epic

Описание: Gate Review перед завершением.
Кто проверяет: Product Owner, Stakeholders.
Поля обязательные:
    - Review Notes (заметки по проверке).
    - Acceptance Criteria Met? (критерии выполнены?).
    - Business Value Delivered? (ценность доставлена?).
Проверки (Gate Criteria):
    - [ ] Все Stories завершены.
    - [ ] Метрики успеха достигнуты.
    - [ ] Пользовательские тесты пройдены.
    - [ ] Документация обновлена.
Возможные действия:
    → Accept (Принять): Epic соответствует критериям.
    → Return to Progress (Вернуть): Требуются доработки.
    → Put on Hold (Приостановить): Требуются доп. ресурсы.
    
## Story

Описание: Product Owner проверяет реализацию.
Кто проверяет: Product Owner, Stakeholders.
Поля обязательные:
  - PO Feedback (обратная связь PO);
  - Acceptance Verified? (критерии проверены?);
  - Business Value Confirmed (ценность подтверждена).
Checklist для PO:
  - [ ] All acceptance criteria met;
  - [ ] UI/UX matches expectations;
  - [ ] Performance acceptable;
  - [ ] No regression issues.
Возможные действия:
  → Accept (Принять): Story соответствует ожиданиям.
  → Request Changes (Запросить изменения): Требуются доработки.

## Bug

Описание: Исправление подтверждено.
Поля обязательные:
  - Verified By (кто подтвердил);
  - Verification Date (дата подтверждения);
  - Release Version (версия релиза).
Критерии верификации:
  - Original issue resolved;
  - No regression introduced;
  - Documentation updated;
  - Tests added to prevent recurrence.
Возможные действия:
  → Close (Закрыть): Финальное закрытие бага."),

('On Hold', "## Epic

Описание: Epic временно приостановлен.
Причины:
    - Изменение бизнес-приоритетов.
    - Технические блокировки.
    - Нехватка ресурсов.
    - Зависимости от других Epic.
Поля обязательные:
    - Hold Reason (причина приостановки).
    - Expected Resume Date (ожидаемая дата возобновления).
    - Impact Assessment (оценка влияния).
Возможные действия:
    → Resume Progress (Возобновить): Проблемы решены.
    → Cancel (Отменить): Epic больше не актуален."),

('Accepted', "## Epic

Описание: Epic прошел Gate Review и принят бизнесом.
Кто подтверждает: Product Owner, Stakeholders.
Поля обязательные:
    - Actual Business Value (фактическая ценность).
    - Lessons Learned (извлеченные уроки).
    - Retrospective Notes (заметки ретроспективы).
Автоматические действия:
    - Закрытие всех связанных Stories.
    - Отправка отчетов Stakeholders.
    - Архивирование документации.
Возможные действия:
    → Done (Завершить): Финальное закрытие Epic.

## Bug

Описание: Баг подтвержден и принят к исправлению.
Поля обязательные:
  - Assigned To (назначен кому);
  - Target Sprint (целевой спринт);
  - Estimated Fix Time (оценка времени исправления).
Критерии для перехода в работу:
  - Воспроизводимость подтверждена;
  - Приоритет установлен;
  - Влияние оценено;
  - Исполнитель назначен.
Возможные действия:
  → Start Fix (Начать исправление): Разработчик начинает работу.
  → Defer (Отложить): Низкий приоритет.

## Improvement

Описание: Эффект улучшения подтвержден.
Поля обязательные:
  - Actual ROI (фактический ROI);
  - Lessons Learned (извлеченные уроки);
  - Recommendations (рекомендации).
Критерии валидации:
  - Measurable improvement;
  - Positive user feedback;
  - Acceptable implementation cost.
Возможные действия:
  → Complete (Завершить): Финальное завершение."),

('Resolved', "## Epic

Описание: Epic полностью завершен и закрыт.
Кто закрывает: Product Owner, Scrum Master.
Критерии:
    - Все Stories закрыты.
    - Документация обновлена.
    - Метрики зафиксированы.
    - Знания переданы.
Поля обязательные:
    - Completion Date (дата завершения).
    - Post-Implementation Review (обзор после внедрения).
    - Next Steps (следующие шаги).
Финальные действия:
    - Epic архивируется.
    - Результаты добавляются в базу знаний.
    - Уведомление всех Stakeholders.

## Story

Описание: Story полностью завершена.
Definition of Done (критерии завершения):
  - [ ] Code completed and reviewed;
  - [ ] Tests written and passing;
  - [ ] QA verified;
  - [ ] Product Owner accepted;
  - [ ] Documentation updated;
  - [ ] Deployed to production (если применимо).
Поля обязательные:
  - Actual Story Points (фактические story points);
  - Completion Date (дата завершения);
  - Retrospective Notes (заметки ретроспективы).
Финальные действия:
  - Story архивируется;
  - Velocity пересчитывается;
  - Прогресс Epic обновляется.

## Task

Описание: Задача полностью завершена.
Definition of Done для Task:
  - [ ] Code implemented;
  - [ ] Code reviewed;
  - [ ] Tests passed (если применимо);
  - [ ] Code merged;
  - [ ] Documentation updated.
Поля обязательные:
  - Actual Time Spent (фактическое время);
  - Completion Date (дата завершения).
Финальные действия:
  - Обновление временных метрик;
  - Уведомление заинтересованных лиц.

## Bug

Описание: Баг полностью исправлен и закрыт.
Поля обязательные:
  - Resolution (резолюция: Fixed);
  - Fix Version (версия исправления);
  - Closing Comments (заключительные комментарии).
Финальные действия:
  - Update bug tracking metrics;
  - Notify stakeholders;
  - Archive related documents.

## Improvement

Описание: Улучшение успешно завершено.
Поля обязательные:
  - Final Report (финальный отчет);
  - Knowledge Transfer (передача знаний);
  - Future Recommendations (рекомендации на будущее).
Финальные действия:
  - Update best practices;
  - Share results with team;
  - Archive documentation."),

('Rejected', "## Epic
Описание: Epic отклонен на любом этапеи не будет реализован.
Причины:
    - Не соответствует стратегии.
    - ROI недостаточный.
    - Техническая невозможность.
    - Изменение рынка/конкурентов.
Поля обязательные:
    - Rejection Reason (причина отклонения).
    - Alternative Solutions (альтернативные решения).
    - Learnings (что узнали).
Возможные действия:
    → Archive (Архивировать): Сохранить для истории."),

('Backlog', "## Story
Описание: Идея для Story, но без деталей.
Кто создает: Product Owner, Business Analyst.
Поля обязательные:
    - User Story формулировка.
    - Предполагаемая ценность.
    - Приоритет (MoSCoW: Must, Should, Could, Won't).
Качество Backlog Item:
    - Независимая (Independent).
    - Обсуждаемая (Negotiable).
    - Ценная (Valuable).
    - Оцениваемая (Estimable).
    - Маленькая (Small).
    - Тестируемая (Testable) - INVEST критерии.
Возможные действия:
    → Refine (Уточнить): Перейти к детализации.
    → Reject (Отклонить): Не подходит для реализации."),

('Refined', "## Story
Описание: Story детализирована и готова для оценки.
Кто работает: Product Owner, Development Team.
Поля обязательные:
    - Детальное описание.
    - Acceptance Criteria (критерии приемки).
    - Предварительные Story Points.
    - Технические заметки.
Выход Refinement:
    - Clear Acceptance Criteria.
    - Story Points оценка.
    - Technical Spikes если нужно.
    - Dependencies identified.
Возможные действия:
    → Mark Ready (Пометить готовой): Готова для планирования спринта.
    → Return to Backlog (Вернуть): Требуется больше информации."),

('Ready for Development', "## Story
Описание: Story готова для взятия в спринт.
Критерии готовности (Definition of Ready):
    - [ ] Acceptance Criteria четко определены;
    - [ ] Дизайн готов (если требуется);
    - [ ] Зависимости разрешены;
    - [ ] Story Points оценены командой;
    - [ ] Тестовые данные подготовлены.
Поля обязательные:
    - Story Points (финальная оценка);
    - Assigned Sprint (назначенный спринт);
    - Dependencies Resolved (зависимости разрешены).
Возможные действия:
    → Start Development (Начать разработку): Разработчик берет в работу.
    → Return to Refined (Вернуть): Требуются изменения."),

('In Development', "## Story
Описание: Разработка в процессе.
Кто работает: Developer(s).
Поля обязательные:
    - Assignee (исполнитель);
    - Time Spent (затраченное время);
    - Branch Name (имя ветки Git);
    - PR Link (ссылка на Pull Request).
Стандарты разработки:
    - Code follows style guide;
    - Unit tests written;
    - Documentation updated;
    - Peer review planned.
Возможные действия:
    → Submit for Code Review (На ревью кода): Код готов для проверки.
    → Block (Заблокировать): Возникли проблемы."),

('Code Review', "## Story

Описание: Код проверяется другими разработчиками.
Кто проверяет: 1-2 других разработчика.
Поля обязательные:
    - Reviewer(s) (ревьюеры);
    - Review Comments (комментарии ревью);
    - Required Changes (требуемые изменения).
Критерии Code Review:
    - [ ] Code quality (качество кода);
    - [ ] Test coverage (покрытие тестами);
    - [ ] Security considerations (безопасность);
    - [ ] Performance implications (производительность).
Возможные действия:
    → Approve (Одобрить): Код прошел ревью.
    → Request Changes (Запросить изменения): Требуются правки.
    → Reject (Отклонить): Серьезные проблемы.

## Task

Описание: Техническая проверка кода.
Кто проверяет: Другой разработчик.
Поля обязательные:
  - Reviewer (ревьюер);
  - PR Link (ссылка на Pull Request);
  - Review Notes (заметки ревью).
Фокус ревью для Task:
  - Code quality standards;
  - No regression;
  - Performance impact;
  - Security considerations.
Возможные действия:
  → Approve (Одобрить): Ревью пройдено.
  → Request Changes (Запросить изменения): Требуются правки.

## Bug

Описание: Проверка исправления.
Фокус ревью для багов:
  - Fix addresses root cause (исправляет причину);
  - No new issues introduced (не вносит новых проблем);
  - Tests cover the scenario (тесты покрывают сценарий).
Поля обязательные:
  - Reviewer Feedback (обратная связь ревьюера);
  - Security Review (проверка безопасности).
Возможные действия:
  → Approve (Одобрить): Исправление корректно.
  → Request Changes (Запросить изменения): Требуются доработки.

## Improvement

Описание: Проверка реализации улучшения.
Фокус ревью:
  - Performance implications;
  - No negative impact on existing functionality;
  - Clean, maintainable code.
Возможные действия:
  → Approve (Одобрить);
  → Request Changes (Запросить изменения)."),

('Ready for QA', "## Story

Описание: Код прошел ревью и готов к тестированию.
Поля обязательные:
    - Build Version (версия сборки);
    - Deployment Environment (окружение);
    - Test Instructions (инструкции для тестирования).
Автоматические действия:
    - Запуск автоматических тестов;
    - Развертывание на тестовом окружении;
    - Уведомление QA команды.
Возможные действия:
    → Start QA (Начать тестирование): QA инженер берет в работу.

## Task

Описание: Код проверен и готов к тестированию.
Поля обязательные:
  - Test Environment (окружение для теста);
  - Test Instructions (инструкции).
Когда требуется тестирование:
  - Изменения затрагивают существующую функциональность;
  - Добавлена новая конфигурация;
  - Изменения в инфраструктуре.
Возможные действия:
  → Start Test (Начать тест): QA начинает тестирование.
  → Skip Test (Пропустить тест): Для чисто технических задач.

## Bug

Описание: Исправление готово для проверки.
Поля обязательные:
  - Build Version (версия сборки);
  - Test Environment (окружение для теста);
  - Special Instructions (особые инструкции).
Автоматические действия:
  - Развертывание на тестовом окружении;
  - Уведомление QA команды;
  - Запуск регрессионных тестов.
Возможные действия:
  → Start Retest (Начать повторное тестирование)."),

('In QA', "## Story

Описание: Функциональное тестирование.
Кто тестирует: QA Engineer.
Поля обязательные:
    - Tester (тестировщик);
    - Test Results (результаты тестов);
    - Defects Found (найденные дефекты).
Виды тестирования:
    - Functional testing (функциональное);
    - Regression testing (регрессионное);
    - Integration testing (интеграционное);
    - UX testing (пользовательское).
Возможные действия:
    → QA Pass (Тестирование пройдено): Все тесты пройдены.
    → QA Fail (Тестирование провалено): Найдены проблемы.

## Task

Описание: Проверка изменений.
Кто тестирует: QA Engineer или разработчик.
Поля обязательные:
  - Tester (тестировщик);
  - Test Results (результаты).
Виды тестирования для Task:
  - Smoke test (дымовое);
  - Regression test (регрессионное);
  - Integration test (интеграционное).
Возможные действия:
  → Test Pass (Тест пройден): Все проверки пройдены.
  → Test Fail (Тест провален): Найдены проблемы.

## Bug

Описание: Проверка исправления.
Кто тестирует: Изначальный репортер или QA.
Поля обязательные:
  - Retest Results (результаты повторного теста);
  - Regression Test Results (результаты регрессионных тестов).
Процесс проверки:
  1. Воспроизвести оригинальные шаги;
  2. Проверить что баг исправлен;
  3. Выполнить регрессионное тестирование;
  4. Проверить смежные функциональности.
Возможные действия:
  → Verify (Верифицировать): Баг исправлен.
  → Reopen (Переоткрыть): Баг не исправлен.

## Improvement

Описание: Проверка улучшения.
Особенности тестирования:
  - A/B testing setup;
  - Performance testing;
  - User acceptance testing.
Поля обязательные:
  - Test Results (результаты тестов);
  - User Feedback (обратная связь пользователей).
Возможные действия:
  → Ready for Measurement (Готово к измерению эффекта)."),

('QA Passed', "## Story

Описание: Все тесты успешно пройдены.
Поля обязательные:
  - Test Coverage (покрытие тестами);
  - Defect Count (количество дефектов);
  - Test Environment (окружение тестирования).
Автоматические действия:
  - Создание тестовой документации;
  - Обновление тестовых сценариев;
  - Отметка связанных багов как исправленных.
Возможные действия:
  → Submit for PO Review (На проверку PO): Готово для приемки.

## Task

Описание: Все проверки пройдены, готово к слиянию.
Поля обязательные:
  - Merge Target (ветка для мержа);
  - Deployment Plan (план развертывания).
Автоматические проверки:
  - Все тесты пройдены;
  - Ревью одобрено;
  - Конфликтов нет;
  - CI/CD pipeline green.
Возможные действия:
  → Merge (Смержить): Выполнить слияние кода."),

('Ready for PO Review', "## Story
Описание: Готово для приемки Product Owner.
Поля обязательные:
  - Demo Prepared? (демо подготовлено?);
  - Documentation Updated (документация обновлена);
  - Release Notes (заметки о релизе).
Подготовка к Review:
  - Demo environment ready;
  - Acceptance criteria checklist;
  - User documentation ready.
Возможные действия:
  → Start PO Review (Начать проверку): PO начинает проверку."),

('TODO', "## Task

Описание: Задача создана и готова к работе.
Кто создает: Разработчик, Тестировщик, DevOps.
Поля обязательные:
  - Заголовок задачи;
  - Описание (что нужно сделать);
  - Оценка времени (часы);
  - Приоритет.
Критерии хорошей Task:
  - Четкая техническая спецификация;
  - Измеримый результат;
  - Реалистичная оценка времени;
  - Минимум зависимостей.
Возможные действия:
  → Start Work (Начать работу): Исполнитель берет задачу.
  → Cancel (Отменить): Задача больше не актуальна."),

('Merged', "## Task

Описание: Код успешно смержен.
Поля обязательные:
  - Merge Date (дата мержа);
  - Merge Commit (коммит мержа);
  - Deployment Status (статус развертывания).
Post-merge действия:
  - Закрытие Pull Request;
  - Удаление feature branch;
  - Запуск deployment pipeline.
Возможные действия:
  → Mark Done (Пометить завершенной): Переход в финальный статус."),

('Reported', "##Bug

Описание: Задача полностью завершена.
Definition of Done для Task:
  - [ ] Code implemented;
  - [ ] Code reviewed;
  - [ ] Tests passed (если применимо);
  - [ ] Code merged;
  - [ ] Documentation updated.
Поля обязательные:
  - Actual Time Spent (фактическое время);
  - Completion Date (дата завершения).
Финальные действия:
  - Обновление временных метрик.
  - Уведомление заинтересованных лиц."),

('Triage', "## Bug

Описание: Анализ и классификация бага.
Кто делает: QA Lead, Product Owner, Tech Lead.
Цели триажа:
  - Validate the bug (подтвердить баг);
  - Assess impact (оценить влияние);
  - Assign priority (назначить приоритет);
  - Identify root cause (определить корневую причину).
Поля обязательные:
  - Triage Notes (заметки триажа);
  - Root Cause (корневая причина);
  - Impact Assessment (оценка влияния).
Возможные действия:
  → Accept (Принять): Баг валиден и требует исправления.
  → Reject (Отклонить): Не баг или не воспроизводится.
  → Mark Duplicate (Пометить дубликатом): Уже зарегистрирован.
  → Defer (Отложить): Исправить позже."),

("Won't Fix", "## Bug

Описание: Решение не исправлять баг.
Причины:
  - Low impact (низкое влияние);
  - Too expensive to fix (слишком дорого исправлять);
  - By design (так и задумано);
  - Obsolete feature (устаревшая функциональность).
Поля обязательные:
  - Business Justification (бизнес-обоснование).
  - Alternative Solution (альтернативное решение)."),

('Cannot Reproduce', "## Bug

Описание: Не удается воспроизвести баг.
Поля обязательные:
  - Attempts Made (попытки воспроизведения);
  - Environments Tested (проверенные окружения);
  - Additional Information Requested (запрошенная доп. информация)."),

('Proposed', "## Improvement

Описание: Предложение по улучшению.
Кто предлагает: Любой член команды, пользователь.
Поля обязательные:
  - Current State (текущее состояние);
  - Proposed Improvement (предлагаемое улучшение);
  - Expected Benefits (ожидаемые выгоды);
  - Metrics for Measurement (метрики для измерения).
Качество предложения:
  - Clear problem statement;
  - Data-driven proposal;
  - Measurable outcomes;
  - Estimated effort.
Возможные действия:
  → Evaluate (Оценить): Передать на оценку.
  → Reject (Отклонить): Не подходящее предложение."),

('Evaluating', "## Improvement

Описание: Оценка предложения.
Кто оценивает: Product Owner, Tech Lead, Business Analyst.
Поля обязательные:
  - ROI Analysis (анализ ROI);
  - Technical Feasibility (техническая осуществимость);
  - Resource Requirements (требования к ресурсам);
  - Risk Assessment (оценка рисков).
Метрики для оценки:
  - Cost vs Benefit (стоимость vs выгода);
  - Implementation Complexity (сложность реализации);
  - User Impact (влияние на пользователей);
  - Strategic Alignment (соответствие стратегии).
Возможные действия:
  → Approve (Утвердить): Улучшение одобрено.
  → Reject (Отклонить): Недостаточный ROI.
  → Request More Info (Запросить доп. информацию)."),

('Measuring Impact', "## Improvement

Описание: Измерение эффекта от улучшения.
Поля обязательные:
  - Baseline Metrics (базовые метрики);
  - Post-Implementation Metrics (метрики после внедрения);
  - Statistical Significance (статистическая значимость).
Метрики для измерения:
  - Performance metrics;
  - User engagement;
  - Business metrics;
  - System metrics.
Возможные действия:
  → Validate (Подтвердить): Эффект подтвержден.
  → No Impact (Нет эффекта): Улучшение не дало результата."),

('Open', 'Задача открыта и готова к выполнению'),

('Closed', 'Задача закрыта'),

('Reopened', 'Задача переоткрыта после закрытия'),

('Blocked', 'Задача заблокирована и не может быть выполнена'),

('Cancelled', 'Задача отменена и не будет выполнена');

COMMIT;
