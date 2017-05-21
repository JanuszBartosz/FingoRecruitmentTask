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
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ProductDto getProduct(@PathVariable Long productId) {
        ProductDto productDto = productService.get(productId);
        if(productDto !=null)
            return productDto;
        else
            return null;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> getProducts(@RequestParam(defaultValue = "false") Boolean sorted) {
        if (sorted)
            return productService.getAllSortedByCategory();
        else
            return productService.getAll();
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public HttpStatus addProduct(@RequestBody ProductDto productDto) {
        productService.saveOrUpdate(productDto);
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PATCH)
    public HttpStatus setBought(@PathVariable Long productId) {
        productService.setBought(productId);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
    public HttpStatus removeProduct(@PathVariable Long productId) {
        productService.remove(productId);
        return HttpStatus.OK;
    }
}
