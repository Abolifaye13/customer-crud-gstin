
package com.example.customer_crud.repository;

import com.example.customer_crud.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
