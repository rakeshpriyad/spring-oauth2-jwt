package com.example.spring.oauth.jwt.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.example.spring.oauth.jwt.models.TokenBlackList;

public interface TokenBlackListRepo  extends Repository<TokenBlackList, Long> {
   Optional<TokenBlackList> findByJti(String jti);
   List<TokenBlackList> queryAllByUserIdAndIsBlackListedTrue(Long userId);
   void save(TokenBlackList tokenBlackList);
   List<TokenBlackList> deleteAllByUserIdAndExpiresBefore(Long userId, Long date);
}
