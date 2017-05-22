package fingo.recruitment.task.service;

import fingo.recruitment.task.domain.Product;
import fingo.recruitment.task.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto mapEntityToDto(Product product);

    Product mapDtoToEntity(ProductDto productDto);

    ProductDto get(Long id);

    List<ProductDto> getAll();

    List<ProductDto> getAllSortedByCategory();

    Product saveOrUpdate(ProductDto productDto);

    Product setBought(Long productId);

    void remove(Long productId);
}
