package com.snd.storefinder.repositories;

import com.snd.storefinder.models.store.StoreManager;
import com.snd.storefinder.models.store.StoreManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreManagerRepository extends JpaRepository<StoreManager, StoreManagerId> {
}
