package com.sapo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapo.model.Category;
import com.sapo.model.Item;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Set<Category> findByItem(Item item);
}
