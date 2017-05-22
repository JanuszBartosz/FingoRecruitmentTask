package fingo.recruitment.task.service.impl;

import fingo.recruitment.task.domain.Product;
import fingo.recruitment.task.domain.ProductCategory;
import fingo.recruitment.task.dto.ProductDto;
import fingo.recruitment.task.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl = new ProductServiceImpl(productRepository);

    @Test
    public void mapEntityToDto() throws Exception {
        //Given
        Long id = 1L;
        String name = "apple";
        ProductCategory category = ProductCategory.FRUIT;
        Integer number = 1;
        Boolean bought = Boolean.FALSE;
        Product product = new Product(1L, "apple", ProductCategory.FRUIT, 1, Boolean.FALSE);

        //When
        ProductDto productDto = productServiceImpl.mapEntityToDto(product);

        //Then
        assertEquals(id, product.getId());
        assertEquals(name, productDto.getName());
        assertEquals(category, productDto.getCategory());
        assertEquals(number, productDto.getNumber());
        assertEquals(bought, productDto.getBought());
    }

    @Test
    public void mapDtoToEntity() throws Exception {
        //Given
        Long id = 1L;
        String name = "apple";
        ProductCategory category = ProductCategory.FRUIT;
        Integer number = 1;
        Boolean bought = Boolean.TRUE;
        ProductDto productDto = new ProductDto(id, name, category, number, bought);

        //When
        Product product = productServiceImpl.mapDtoToEntity(productDto);

        //Then
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(number, product.getNumber());
        assertEquals(bought, product.getBought());
    }

    @Test
    public void get() throws Exception {
        //Given
        Long id = 1L;
        String name = "apple";
        ProductCategory category = ProductCategory.FRUIT;
        Integer number = 1;
        Boolean bought = Boolean.FALSE;
        Product product = new Product(id, name, category, number, bought);

        Mockito.when(productRepository.findOne(id)).thenReturn(product);

        //When
        ProductDto productDto = productServiceImpl.get(id);

        //Then
        assertEquals(id, product.getId());
        assertEquals(name, productDto.getName());
        assertEquals(category, productDto.getCategory());
        assertEquals(number, productDto.getNumber());
        assertEquals(bought, productDto.getBought());
    }

    @Test
    public void getNull() throws Exception {
        //Given
        Long id = 1L;
        Mockito.when(productRepository.findOne(id)).thenReturn(null);

        //When
        ProductDto productDto = productServiceImpl.get(id);

        //Then
        assertEquals(null, productDto);
    }

    @Test
    public void getAll() throws Exception {
        //Given
        List<ProductDto> productDtoList = Arrays.asList(
                new ProductDto(1L, "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new ProductDto(2L, "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new ProductDto(3L, "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new ProductDto(4L, "orange", ProductCategory.FRUIT, 4, Boolean.FALSE)
        );
        Iterable<Product> productIterable = Arrays.asList(
                new Product(1L, "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new Product(2L, "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new Product(3L, "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new Product(4L, "orange", ProductCategory.FRUIT, 4, Boolean.FALSE)
        );

        Mockito.when(productRepository.findAll()).thenReturn(productIterable);

        //When
        List<ProductDto> returnedProductDtoList = productServiceImpl.getAll();

        //Then
        assertEquals(productDtoList, returnedProductDtoList);
    }

    @Test
    public void getAllSortedByCategory() throws Exception {
        //Given
        List<ProductDto> productDtoList = Arrays.asList(
                new ProductDto(3L, "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new ProductDto(1L, "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new ProductDto(2L, "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new ProductDto(4L, "broccoli", ProductCategory.VEGETABLE, 4, Boolean.FALSE)
        );

        Iterable<Product> productIterable = Arrays.asList(
                new Product(1L, "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new Product(2L, "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new Product(3L, "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new Product(4L, "broccoli", ProductCategory.VEGETABLE, 4, Boolean.FALSE)
        );

        Mockito.when(productRepository.findAll()).thenReturn(productIterable);

        //When
        List<ProductDto> returnedProductDtoList = productServiceImpl.getAllSortedByCategory();

        //Then
        assertEquals(productDtoList, returnedProductDtoList);
    }

    @Test
    public void saveOrUpdateExistingNotBought() throws Exception {
        //Given
        Long id = 1L;
        String name = "apple";
        ProductCategory productCategory = ProductCategory.FRUIT;
        Integer number = 1;
        Integer newNumber = 10;
        Boolean bought = Boolean.FALSE;

        ProductDto productDto = new ProductDto(name, productCategory, number);
        Product existingProduct = new Product(id, name, productCategory, number, bought);
        Product newProduct = new Product(id, name, productCategory, newNumber, bought);

        Mockito.when(productRepository.findOneByName(productDto.getName())).thenReturn(Optional.of(existingProduct));
        Mockito.when(productRepository.save(existingProduct)).thenReturn(newProduct);
        Mockito.when(productRepository.save(newProduct)).thenReturn(newProduct);

        //When
        Product returnedProduct = productServiceImpl.saveOrUpdate(productDto);

        //Then
        assertEquals(name, returnedProduct.getName());
        assertEquals(productCategory, returnedProduct.getCategory());
        assertEquals(newNumber, returnedProduct.getNumber());
        assertEquals(bought, returnedProduct.getBought());
    }

    @Test
    public void setBought() throws Exception {
        //Given
        Long id = 1L;
        Product product = new Product(id, "apple", ProductCategory.FRUIT, 10, Boolean.FALSE);

        Mockito.when(productRepository.findOne(id)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        //When
        Product returnedProduct = productServiceImpl.setBought(id);

        //Then
        assertEquals(Boolean.TRUE, returnedProduct.getBought());
    }

    @Test
    public void setBoughtAlreadyBought() throws Exception {
        //Given
        Long id = 1L;
        Product product = new Product(id, "apple", ProductCategory.FRUIT, 10, Boolean.TRUE);

        Mockito.when(productRepository.findOne(id)).thenReturn(product);

        //When
        Product returnedProduct = productServiceImpl.setBought(id);

        //Then
        assertEquals(null, returnedProduct);
    }

    @Test
    public void setBoughtProductNotExists() throws Exception {
        //Given
        Long id = 1L;

        Mockito.when(productRepository.findOne(id)).thenReturn(null);

        //When
        Product returnedProduct = productServiceImpl.setBought(id);

        //Then
        assertEquals(null, returnedProduct);
    }
}