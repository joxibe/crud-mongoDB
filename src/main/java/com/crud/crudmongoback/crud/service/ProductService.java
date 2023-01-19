package com.crud.crudmongoback.crud.service;

import com.crud.crudmongoback.crud.dto.ProductDto;
import com.crud.crudmongoback.crud.entity.Product;
import com.crud.crudmongoback.crud.repository.ProductRepository;
import com.crud.crudmongoback.global.exceptions.AttributeException;
import com.crud.crudmongoback.global.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getOne(int id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found")); //llamamos el mensaje de exception
    }

    public Product save(ProductDto productDto) throws AttributeException {
        if(productRepository.existsByName(productDto.getName())){
            throw  new AttributeException("name already in use");
        }
        int id = autoIncrement();
        Product product = new Product(id, productDto.getName(), productDto.getPrice());
        return productRepository.save(product);
    }

    public Product update (int id, ProductDto productDto) throws ResourceNotFoundException, AttributeException {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("not found"));
        if(productRepository.existsByName(productDto.getName()) && productRepository.findByName(productDto.getName())
                .get().getId() != id){
            throw  new AttributeException("name already in use");
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    public Product delete(int id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("not found"));
        productRepository.delete(product);
        return product;
    }

    //private methods autoincrement
    private int autoIncrement(){
        List<Product> products = productRepository.findAll();
        return products.isEmpty()? 1: //si products esta vacio, devolvemos 1
                products.stream().max(Comparator.comparing(Product::getId)).get().getId() + 1; //en caso contrario, comparamos todos los id y obtenemos el maximo id y lo incrementamos en 1

    }
}
