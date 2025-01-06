
package com.crm.springbootjwtimplementation.repository;

// import com.crm.springbootjwtimplementation.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crm.springbootjwtimplementation.domain.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
