package fingo.recruitment.task.service.impl;

import fingo.recruitment.task.domain.Product;
import fingo.recruitment.task.dto.ProductDto;
import fingo.recruitment.task.repository.ProductRepository;
import fingo.recruitment.task.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto mapEntityToDto(Product product) {

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setCategory(product.getCategory());
        productDto.setNumber(product.getNumber());
        productDto.setBought(product.getBought());

        return productDto;
    }

    @Override
    public Product mapDtoToEntity(ProductDto productDto) {

        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setNumber(productDto.getNumber());
        product.setBought(productDto.getBought());

        return product;
    }

    @Override
    public ProductDto get(Long id) {
        Product product = productRepository.findOne(id);
        return (product != null) ? mapEntityToDto(product) : null;
    }

    @Override
    public List<ProductDto> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(p -> mapEntityToDto(p))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllSortedByCategory() {
        return getAll().stream()
                .sorted(Comparator.comparing(ProductDto::getCategory))
                .collect(Collectors.toList());
    }

    //If product exists, but bought=false, product is updated with new number.
    //If product exists and is bought, new product is added.
    @Override
    public Product saveOrUpdate(ProductDto productDto) {
        Optional<Product> product = productRepository.findOneByName(productDto.getName());
        if (product.isPresent() && !product.get().getBought()) {
            Product oldProduct = product.get();
            oldProduct.setNumber(productDto.getNumber());
            return productRepository.save(oldProduct);
        } else
            return productRepository.save(mapDtoToEntity(productDto));
    }

    @Override
    public Product setBought(Long productId) {
        Product product = productRepository.findOne(productId);
        if (product != null && !product.getBought()) {
            product.setBought(Boolean.TRUE);
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    @Override
    public void remove(Long productId) {
        Product product = productRepository.findOne(productId);
        if (product != null && !product.getBought())
            productRepository.delete(product);
    }
}
