package com.sapo.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.Shop;
import com.sapo.model.User;
@Repository
@Transactional
public interface ShopRepository extends JpaRepository<Shop, Long>{
	Shop findByName(String name);
	Shop findByShopid(Long id);
	Shop findByShopidAndUser(Long id, User user);
}
