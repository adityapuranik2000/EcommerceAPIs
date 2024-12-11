package com.src.Ecommerce.controller;

import com.src.Ecommerce.model.Product;
import com.src.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin // This denotes that from any url you can access this controller
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){

        Product product = service.getProductById(id);

        if(product!=null)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        if (product!=null) {
            try {
               return new ResponseEntity<>(service.addOrUpdateProduct(product,imageFile),HttpStatus.CREATED);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable int id){
        Product product = service.getProductById(id);
        if (product!=null)
            return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable int id,@RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = service.getProductById(id);
        if (product1!=null)
            return new ResponseEntity<>(service.addOrUpdateProduct(product,imageFile),HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable int id){
        service.deleteProduct(id);
    }
}
