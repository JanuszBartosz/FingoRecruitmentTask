package fingo.recruitment.task.service;

import fingo.recruitment.task.domain.Product;
import fingo.recruitment.task.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto mapEntityToDto(Product product);

    Product mapDtoToEntity(ProductDto productDto);

    void saveOrUpdate(ProductDto productDto);

    ProductDto get(Long id);

    List<ProductDto> getAll();

    void remove(Long productId);

    List<ProductDto> getAllSortedByCategory();

    void setBought(Long productId);
}
