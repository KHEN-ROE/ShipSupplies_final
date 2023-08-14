package com.shipsupply.service;

import com.shipsupply.dto.AddWishListDto;
import com.shipsupply.dto.DeleteWishListDto;
import com.shipsupply.dto.WishListDto;
import com.shipsupply.entity.User;
import com.shipsupply.entity.WishList;
import com.shipsupply.repository.UserRepository;
import com.shipsupply.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;

    private final UserRepository userRepository;

    public List<WishListDto> getList(String userId) {
        userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));
        List<WishList> findWishList = wishListRepository.findByUserId(userId);

        return findWishList.stream()
                .map(wishList -> new WishListDto(wishList.getId(), wishList.getItem(), wishList.getCategory(),
                        wishList.getMachinery(), wishList.getCurrency(),
                        wishList.getPrice(), wishList.getCompany(), wishList.getLeadtime()))
                .collect(Collectors.toList());
    }


    public void addList(AddWishListDto addWishListDto) {

        User user = userRepository.findById(addWishListDto.getUserId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));
        WishList wishList = WishList.addWishList(addWishListDto, user);

        wishListRepository.save(wishList);
    }


    public void deleteList(DeleteWishListDto deleteWishListDto) {
        User user = userRepository.findById(deleteWishListDto.getUserId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));
        WishList wishList = wishListRepository.findById(deleteWishListDto.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 위시리스트"));

        if (user.getId().equals(wishList.getUser().getId())) {
            wishListRepository.deleteById(wishList.getId());
        }
    }
}
