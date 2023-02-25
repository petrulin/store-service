package com.otus.storeservice.repository;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {

}
