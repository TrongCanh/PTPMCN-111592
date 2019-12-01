package com.sapo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByShopid(Long shopid);
	Item findByShopidAndItemid(Long shopid , Long itemid);
	Item findByItemid(Long itemid);
}
