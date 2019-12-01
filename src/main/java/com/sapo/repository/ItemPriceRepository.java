package com.sapo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.Item;
import com.sapo.model.ItemPrice;

@Repository
public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
	List<ItemPrice> findByItem(Item item);
	List<ItemPrice> findByItemOrderByIdDesc(Item item);

	void deleteByItem(Item item);
}
