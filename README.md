# my-jira

Создаю приложение похожее на Jira с функциональностью:

- Ведение проектной деятельности;
- "Архитектура как код";
- Подключение ИИ помощника.

## Этап 1: Выбор СУБД, создание схемы данных

За основную СУБД берем PostgreSQL 18:

1. см. [Схема данных](./data-scheme/postgres-18-debian/myjira.sql)
2. см. [Базовые настройки](./data-scheme/postgres-18-debian/init.sql)

ОС Debian Linux 13:

1. см. [Переменные окружения](./data-scheme/postgres-18-debian/myjira.env)
2. см. [Dockerfile](./data-scheme/postgres-18-debian/Dockerfile)
3. см. раздел 'postgres' в [docker-compose.yml](docker-compose.yml)

ОС Alpine Linux 3.2.2 (легковеснее):

1. см. [Переменные окружения](./data-scheme/postgres-18-alpine/postgres-18-alpine.env)
2. см. [Dockerfile](./data-scheme/postgres-18-alpine/postgres-18-alpine.dockerfile)
3. см. [docker-ensure-initdb.sh](./data-scheme/postgres-18-alpine/docker-ensure-initdb.sh)
4. см. [docker-entrypoint.sh](./data-scheme/postgres-18-alpine/docker-entrypoint.sh)
5. см. раздел 'postgres' в [docker-compose.yml](docker-compose.yml)

```shell
# запуск контейнера
docker-compose --env-file data-scheme/postgres-18-debian/myjira.env up -d
# или (? проверить на запускаемость, особенно переменные окружения ?)
docker-compose --env-file data-scheme/postgres-18-alpine/postgres-18-alpine.env up -d

docker-compose down # Остановить контейнер
```

## Этап 2: Разработка backend

Проект будем реализовывать с применением Spring Boot, на платформе Java 17 используя [Zulu JDK FX](https://www.azul.com/core-post-download/?endpoint=zulu&uuid=ba2dc6eb-1dae-44af-a4f5-760bb2c23553).

### Используемые зависимости

- Spring Web;
- PostgreSQL Driver;
- SpringData JPA;
- Spring Security;
- Validation;
- Spring Boot DevTools;
- Liquibase Migration;
- Lombok.

### Справочная документация

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.0/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.0/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.0/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/3.5.0/reference/web/spring-security.html)
* [Validation](https://docs.spring.io/spring-boot/3.5.0/reference/io/validation.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.0/reference/using/devtools.html)
* [Liquibase Migration](https://docs.spring.io/spring-boot/3.5.0/how-to/data-initialization.html#howto.data-initialization.migration-tool.liquibase)

### Справочные руководства

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Справочное переопределение Maven Parents

Из-за дизайна Maven элементы наследуются от родительского POM к POM проекта.
Хотя большая часть наследования в порядке, оно также наследует нежелательные элементы, такие как `<license>` и `<developers>` от родителя.
Чтобы предотвратить это, POM проекта содержит пустые переопределения для этих элементов.
Если вы вручную переключаетесь на другого родителя и на самом деле хотите наследование, вам нужно удалить эти переопределения.
