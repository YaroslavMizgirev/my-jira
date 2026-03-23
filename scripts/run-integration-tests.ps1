# Запуск интеграционных тестов с PostgreSQL в Docker

Write-Host "🐳 Starting PostgreSQL for integration tests..." -ForegroundColor Green

# Запуск Docker Compose для тестов
docker-compose -f docker-compose.test.yml up -d

# Ожидание готовности базы
Write-Host "⏳ Waiting for PostgreSQL to be ready..." -ForegroundColor Yellow
$timeout = 60
$elapsed = 0

do {
    try {
        docker exec myjira-postgres-test pg_isready -U test_user -d myjira_test
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ PostgreSQL is ready!" -ForegroundColor Green
            break
        }
    } catch {
        # Continue waiting
    }
    
    Start-Sleep -Seconds 2
    $elapsed += 2
    
    if ($elapsed -ge $timeout) {
        Write-Host "❌ PostgreSQL startup timeout!" -ForegroundColor Red
        docker-compose -f docker-compose.test.yml down
        exit 1
    }
    
    Write-Host "Waiting... ($elapsed/$timeout seconds)" -ForegroundColor Yellow
} while ($elapsed -lt $timeout)

# Запуск интеграционных тестов
Write-Host "🧪 Running integration tests..." -ForegroundColor Blue
try {
    ./mvnw test -P test-postgres -Dtest=**/*IntegrationTest
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Integration tests passed!" -ForegroundColor Green
    } else {
        Write-Host "❌ Integration tests failed!" -ForegroundColor Red
    }
} finally {
    # Очистка
    Write-Host "🧹 Cleaning up..." -ForegroundColor Yellow
    docker-compose -f docker-compose.test.yml down
}

Write-Host "🏁 Integration testing completed!" -ForegroundColor Cyan
