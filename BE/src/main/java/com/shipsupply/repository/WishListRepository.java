package com.shipsupply.repository;

import com.shipsupply.dto.WishListDto;
import com.shipsupply.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByUserId(String userId);
}
