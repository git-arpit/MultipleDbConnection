package com.arpit.multipleDbConnection.postgresDB.repo;

import com.arpit.multipleDbConnection.postgresDB.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
