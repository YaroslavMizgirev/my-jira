FROM postgres:18

# Активируем русскую локаль
RUN sed -i 's/# ru_RU.UTF-8 UTF-8/ru_RU.UTF-8 UTF-8/' /etc/locale.gen && \
    locale-gen

# Устанавливаем русскую локаль по умолчанию
ENV LANG=ru_RU.UTF-8
ENV LC_ALL=ru_RU.UTF-8

# Копируем схему БД
COPY ../myjira.sql /docker-entrypoint-initdb.d/
COPY ../init.sql /docker-entrypoint-initdb.d/

# Настройка прав
RUN chmod 644 /docker-entrypoint-initdb.d/*.sql

# SCRAM-SHA-256 (самый безопасный - рекомендуется)
ENV POSTGRES_HOST_AUTH_METHOD=scram-sha-256

# Trust (без пароля - ТОЛЬКО для разработки)
# ENV POSTGRES_HOST_AUTH_METHOD=trust

# Переменные по умолчанию
ENV PGDATA=/var/lib/postgresql/data/pgdata

EXPOSE 5432