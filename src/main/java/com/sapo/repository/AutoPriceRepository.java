package com.sapo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapo.model.AutoPrice;
@Repository
public interface AutoPriceRepository extends JpaRepository<AutoPrice, Long> {
	List<AutoPrice> findByItemid(Long id);
	List<AutoPrice> findByItemidOrderByIdDesc(Long id);
}
