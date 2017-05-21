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
        Long id = Long.valueOf(1);
        String name = "apple";
        ProductCategory category = ProductCategory.FRUIT;
        Integer number = 1;
        Boolean bought = Boolean.FALSE;

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setNumber(number);
        product.setBought(bought);

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
        Long id = Long.valueOf(1);
        String name = "apple";
        ProductCategory category = ProductCategory.FRUIT;
        Integer number = 1;
        Boolean bought = Boolean.FALSE;

        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setCategory(category);
        productDto.setNumber(number);
        productDto.setBought(bought);

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
        Long id = Long.valueOf(1);
        String name = "apple";
        ProductCategory category = ProductCategory.FRUIT;
        Integer number = 1;
        Boolean bought = Boolean.FALSE;

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setNumber(number);
        product.setBought(bought);

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
        Long id = Long.valueOf(1);
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
                new ProductDto(Long.valueOf(1), "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new ProductDto(Long.valueOf(2), "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new ProductDto(Long.valueOf(3), "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new ProductDto(Long.valueOf(4), "orange", ProductCategory.FRUIT, 4, Boolean.FALSE)
        );
        Iterable<Product> productIterable = Arrays.asList(
                new Product(Long.valueOf(1), "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new Product(Long.valueOf(2), "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new Product(Long.valueOf(3), "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new Product(Long.valueOf(4), "orange", ProductCategory.FRUIT, 4, Boolean.FALSE)
        );

        Mockito.when(productRepository.findAll()).thenReturn(productIterable);

        //When
        List<ProductDto> productDtoList1 = productServiceImpl.getAll();

        //Then
        assertEquals(productDtoList, productDtoList1);
    }

    @Test
    public void getAllSortedByCategory() throws Exception {
        //Given
        List<ProductDto> productDtoList = Arrays.asList(
                new ProductDto(Long.valueOf(3), "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new ProductDto(Long.valueOf(1), "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new ProductDto(Long.valueOf(2), "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new ProductDto(Long.valueOf(4), "broccoli", ProductCategory.VEGETABLE, 4, Boolean.FALSE)
        );

        Iterable<Product> productIterable = Arrays.asList(
                new Product(Long.valueOf(1), "apple", ProductCategory.FRUIT, 3, Boolean.TRUE),
                new Product(Long.valueOf(2), "chicken", ProductCategory.MEAT, 1, Boolean.FALSE),
                new Product(Long.valueOf(3), "milk", ProductCategory.DAIRY, 2, Boolean.TRUE),
                new Product(Long.valueOf(4), "broccoli", ProductCategory.VEGETABLE, 4, Boolean.FALSE)
        );

        Mockito.when(productRepository.findAll()).thenReturn(productIterable);

        //When
        List<ProductDto> productDtoList1 = productServiceImpl.getAllSortedByCategory();

        //Then
        assertEquals(productDtoList, productDtoList1);
    }

    @Test
    public void saveOrUpdate() throws Exception {

    }

    @Test
    public void setBought() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }
}