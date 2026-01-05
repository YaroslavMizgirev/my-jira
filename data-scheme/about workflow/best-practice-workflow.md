# Best Practices

## Общие рекомендации

### Keep it Simple

```yaml
# ХОРОШО: 4-8 статусов
# ПЛОХО: 15+ статусов

Рекомендации:
- Epic: 6-10 статусов
- Story: 8-12 статусов  
- Task: 4-6 статусов
- Bug: 6-8 статусов
- Improvement: 6-8 статусов
```

### Use Clear Status Names

```yaml
ХОРОШО:
  - In Development
  - Code Review
  - Ready for QA

ПЛОХО:
  - Dev (неясно)
  - CR (аббревиатура)
  - QA Ready (противоречиво)
```

### Define Clear Criteria

```yaml
Для каждого статуса определите:
  - Definition of Ready (когда можно перейти В статус)
  - Definition of Done (когда можно уйти ИЗ статуса)
  - Required Fields (обязательные поля)
  - Expected Duration (ожидаемая длительность)
```

### Automate Where Possible

```yaml
Автоматизируйте:
  - Назначение исполнителей
  - Отправку уведомлений
  - Проверку критериев
  - Обновление связанных задач
```

## Мониторинг и метрики

```sql
-- SQL запросы для анализа эффективности Workflow
-- Среднее время в каждом статусе
SELECT 
    issue_type,
    status,
    AVG(time_in_status_hours) as avg_hours,
    COUNT(*) as issue_count
FROM (
    SELECT 
        i.issue_type,
        h.status,
        EXTRACT(EPOCH FROM (h.status_end - h.status_start)) / 3600 as time_in_status_hours
    FROM issue_history h
    JOIN issues i ON h.issue_id = i.id
    WHERE h.status_start >= NOW() - INTERVAL '90 days'
) as status_times
GROUP BY issue_type, status
ORDER BY issue_type, avg_hours DESC;

-- Bottleneck анализ
WITH workflow_analysis AS (
    SELECT 
        issue_type,
        status,
        AVG(time_in_status) as avg_time,
        PERCENTILE_CONT(0.95) WITHIN GROUP (ORDER BY time_in_status) as p95_time,
        COUNT(*) as total_issues
    FROM issue_status_durations
    WHERE time_in_status IS NOT NULL
    GROUP BY issue_type, status
)
SELECT 
    issue_type,
    status,
    avg_time,
    p95_time,
    total_issues,
    CASE 
        WHEN avg_time > (SELECT AVG(avg_time) * 2 FROM workflow_analysis wa2 WHERE wa2.issue_type = wa.issue_type)
        THEN 'BOTTLENECK'
        ELSE 'NORMAL'
    END as bottleneck_flag
FROM workflow_analysis wa
ORDER BY issue_type, avg_time DESC;
```

## Непрерывное улучшение Workflow

### Регулярные ревью Workflow

```yaml
Частота: Каждые 3 месяца
Участники: Product Owners, Team Leads, Key Users
Анализ:
  - Какие статусы редко используются?
  - Где самые большие задержки?
  - Какие переходы вызывают проблемы?
```

### A/B тестирование Workflow

```yaml
Подход:
  - Запустить новый Workflow для 50% команд
  - Сравнить метрики через 1 месяц
  - Принять решение на основе данных

Метрики для сравнения:
  - Cycle Time (время цикла)
  - Throughput (пропускная способность)
  - Defect Rate (уровень дефектов)
  - Team Satisfaction (удовлетворенность команды)
```

### Обратная связь от пользователей

```yaml
Методы сбора обратной связи:
  - Ежеквартальные опросы
  - User interviews (интервью с пользователями)
  - Support ticket analysis (анализ тикетов поддержки)
  - Usage analytics (аналитика использования)
```

## Общие анти-паттерны

```yaml
Анти-паттерны Workflow:
  1. "Status Bloat" - слишком много статусов
  2. "Transition Spaghetti" - слишком сложные переходы
  3. "Bottleneck Creation" - один статус блокирует все
  4. "Shadow Statuses" - команды создают свои статусы вне Jira
  5. "Automation Overload" - слишком много автоматизации
  6. "Rigid Workflow" - негибкий, не адаптируется к изменениям
  7. "Missing Governance" - нет контроля за изменениями Workflow
```

## Ключевые метрики для каждого Workflow

|Workflow|Key Metrics|Target Values|
|---|---|---|
|Epic|Time to Market, Business Value Delivered, ROI|< 6 months, > 80% value delivered, ROI > 200%|
|Story|Cycle Time, Velocity, Defect Rate|< 2 weeks, Stable velocity, < 5% defects|
|Task|Completion Rate, Time Estimation Accuracy|> 95% completion, ±20% estimation accuracy|
|Bug|Time to Fix, Reopen Rate, Customer Impact|< 24h critical, < 5% reopen, Minimal impact|
|Improvement|ROI Realized, Adoption Rate, User Satisfaction|ROI > 150%, > 60% adoption, > 4/5 satisfaction|

## Итоговые рекомендации

Для каждого типа задач:

### Epic

- Фокус на бизнес-результатах
- Регулярные gate reviews
- Гибкость для изменений
- Прозрачность для стейкхолдеров

### Story

- User-centric подход
- Четкие критерии приемки
- Инкрементальная доставка
- Непрерывная обратная связь

### Task

- Минимальная бюрократия
- Быстрое выполнение
- Техническая фокусировка
- Прямое назначение

### Bug

- Приоритет на основе серьезности
- Быстрое исправление критических проблем
- Профилактика регрессии
- Прозрачность статуса

## Improvement

- Data-driven решения
- Измерение эффекта
- Гибкое планирование
- Обучение и адаптация

> Идеальный Workflow - это тот, который помогает команде доставлять ценность быстрее и качественнее, а не создает бюрократию. Настраивайте Workflow под потребности вашей команды, а не наоборот.
