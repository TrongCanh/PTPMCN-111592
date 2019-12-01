package com.sapo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapo.model.Rival;

public interface RivalRepository extends JpaRepository<Rival, Long> {
	Rival findByItemidAndRivalItemid(Long item , long rival);
	List<Rival> findByItemidAndAuto(Long item, boolean auto);
	List<Rival> findByShopidAndItemid(Long shopid,Long itemid);
	List<Rival> findByItemid(Long item);
}
