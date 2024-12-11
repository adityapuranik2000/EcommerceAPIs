package com.src.Ecommerce.service;


import com.src.Ecommerce.model.Product;
import com.src.Ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(new Product());
    }

    public Product addOrUpdateProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageData(imageFile.getBytes());

        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
}
