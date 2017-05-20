package fingo.recruitment.task.repository;

import fingo.recruitment.task.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    Optional<Product> findOneByName(String name);
    Long removeOneByName(String name);
}
