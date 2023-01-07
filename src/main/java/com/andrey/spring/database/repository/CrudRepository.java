package com.andrey.spring.database.repository;

import java.util.Optional;

public interface CrudRepository<K, E>{//нигде не используется
    Optional<E> findById(K id);

    void delete(E entity);
}
