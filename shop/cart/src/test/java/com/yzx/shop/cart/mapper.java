package com.yzx.shop.cart;

import com.yzx.shop.cart.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface mapper extends MongoRepository<Test,Long> {
}
