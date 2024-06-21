package com.hrafty.web_app.services;

import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.mapper.SellerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final UserService userService;

    private final UserMapper userMapper;

    public SellerService(SellerRepository sellerRepository, SellerMapper sellerMapper, UserService userService, UserMapper userMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }




}
