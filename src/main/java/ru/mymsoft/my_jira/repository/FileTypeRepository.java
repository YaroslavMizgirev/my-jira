package ru.mymsoft.my_jira.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.FileType;

@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long> {

  Optional<FileType> findByExtension(String extension);
  Optional<FileType> findByMimeType(String mimeType);

  // Проверка существования
  boolean existsByExtension(String extension);
  boolean existsByMimeType(String mimeType);

  // Поиск по части расширения (для подсказок)
  List<FileType> findByExtensionContainingIgnoreCase(String extensionPart);

  // Поиск по типу MIME (например, все image/*)
  List<FileType> findByMimeTypeStartingWith(String mimePrefix);

  // Получить все с сортировкой
  List<FileType> findAllByOrderByExtensionAsc();

  // Найти по группе MIME types (images, documents, etc)
  @Query("SELECT ft FROM FileType ft WHERE ft.mimeType LIKE :category%")
  List<FileType> findByMimeCategory(@Param("category") String category);
  // Использование: findByMimeCategory("image") → image/jpeg, image/png, etc.

  // Получить популярные типы файлов (если добавишь счетчик использования)
  // List<FileType> findTop10ByOrderByUsageCountDesc();

  // Проверка, разрешен ли тип файла для загрузки
  // @Query("SELECT COUNT(ft) > 0 FROM FileType ft WHERE ft.extension = :extension AND ft.isAllowed = true")
  // boolean isExtensionAllowed(@Param("extension") String extension);

  // Получить только разрешенные типы файлов
  // List<FileType> findByIsAllowedTrueOrderByExtensionAsc();
}
