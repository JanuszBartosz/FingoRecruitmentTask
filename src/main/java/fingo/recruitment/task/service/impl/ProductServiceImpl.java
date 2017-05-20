package fingo.recruitment.task.service.impl;

import fingo.recruitment.task.domain.Product;
import fingo.recruitment.task.dto.ProductDto;
import fingo.recruitment.task.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements fingo.recruitment.task.service.ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
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
    public void saveOrUpdate(ProductDto productDto) {
        Optional<Product> product = productRepository.findOneByName(productDto.getName());
        if (product.isPresent()) {
            Product oldProduct = product.get();
            oldProduct.setCategory(productDto.getCategory());
            oldProduct.setNumber(productDto.getNumber());
            productRepository.save(oldProduct);
        }else
            productRepository.save(mapDtoToEntity(productDto));
    }

    @Override
    public ProductDto get(Long id) {
        return mapEntityToDto(productRepository.findOne(id));
    }

    @Override
    public List<ProductDto> getAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(p -> mapEntityToDto(p))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(ProductDto productDto) {
        Product product = null;
        if(productDto.getId()!=null)
            product = productRepository.findOne(productDto.getId());
            if(product==null)
                return;
        else if(productDto.getName()!=null) {
                Optional<Product> productOptional = productRepository.findOneByName(productDto.getName());
                if(productOptional.isPresent())
                    product = productOptional.get();
        }
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllSortedByCategory() {
        return getAll().stream()
                .sorted((p1, p2) -> p1.getCategory().compareTo(p2.getCategory())) //można dać Comparator.comparing
                .collect(Collectors.toList());
    }

    @Override
    public void setBought(ProductDto productDto){
        if(productDto.getId()!=null) {
            Product product = productRepository.findOne(productDto.getId());
            product.setBought(Boolean.TRUE);
            productRepository.save(product);
        }
    }

}
