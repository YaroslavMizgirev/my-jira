# Ускоренный Workflow для Critical Bugs

```mermaid
graph TD
    A[Reported<br/>Зарегистрирован] --> B[Critical Triage<br/>Критический триаж]
    B --> C[Hotfix in Progress<br/>Хотфикс в работе]
    C --> D[Emergency Review<br/>Экстренное ревью]
    D --> E[Emergency Deploy<br/>Экстренное развертывание]
    E --> F[Verified<br/>Верифицирован]
    F --> G[Closed<br/>Закрыт]
    
    style A fill:#ff0000,stroke:#000,color:#000
    style B fill:#ff3333,stroke:#000,color:#000
    style C fill:#ff6666,stroke:#000,color:#000
```

## Особенности

- Максимальное время на каждом этапе: 2-4 часа;
- Автоматическое оповещение on-call инженеров;
- Обход стандартных проверок;
- Прямое развертывание в production.
