package carshowroom.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll();

    Optional<T> findById(int id);

    T save(T entity);

    T update(T entity);

    void deleteById(int id);
}