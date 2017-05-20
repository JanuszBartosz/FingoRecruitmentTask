package fingo.recruitment.task.contoller;

import fingo.recruitment.task.dto.ProductDto;
import fingo.recruitment.task.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> listProducts(@RequestParam(defaultValue = "false") Boolean sorted){
        if (sorted)
            return productService.getAllSortedByCategory();
        else
            return productService.getAll();
    }

//    @RequestMapping(value = "/products/sorted", method = RequestMethod.GET)
//    public List<ProductDto> listProductsSorted(){
//        return productService.getAllSortedByCategory();
//    }

    @RequestMapping(value = "/products", method = RequestMethod.PUT)
    public HttpStatus addProduct(@RequestBody ProductDto productDto){
        productService.saveOrUpdate(productDto);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/products", method = RequestMethod.DELETE)
    public HttpStatus removeProduct(@RequestBody ProductDto productDto){
        productService.remove(productDto);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public HttpStatus setBought(@RequestBody ProductDto productDto){
        productService.setBought(productDto);
        return HttpStatus.OK;
    }

}
