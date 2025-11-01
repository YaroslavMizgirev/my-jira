FROM postgres:18-alpine

# Устанавливаем русские локали
RUN apk add --no-cache locales && \
    echo "ru_RU.UTF-8 UTF-8" >> /etc/locale.gen && \
    locale-gen

# Копируем схему БД
COPY myJIRA.sql /docker-entrypoint-initdb.d/
COPY init.sql /docker-entrypoint-initdb.d/

# Настройка прав
RUN chmod 644 /docker-entrypoint-initdb.d/*.sql

# SCRAM-SHA-256 (самый безопасный - рекомендуется)
#ENV POSTGRES_HOST_AUTH_METHOD=scram-sha-256

# MD5 (совместимый, достаточно безопасный)
#ENV POSTGRES_HOST_AUTH_METHOD=md5

# Trust (без пароля - ТОЛЬКО для разработки)
ENV POSTGRES_HOST_AUTH_METHOD=trust

# Переменные по умолчанию
ENV PGDATA=/var/lib/postgresql/data/pgdata

EXPOSE 5432