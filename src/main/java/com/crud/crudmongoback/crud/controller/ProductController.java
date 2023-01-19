package com.crud.crudmongoback.crud.controller;

import com.crud.crudmongoback.crud.dto.ProductDto;
import com.crud.crudmongoback.crud.entity.Product;
import com.crud.crudmongoback.crud.service.ProductService;
import com.crud.crudmongoback.global.dto.MessageDto;
import com.crud.crudmongoback.global.exceptions.AttributeException;
import com.crud.crudmongoback.global.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") int id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<MessageDto> save(@Valid @RequestBody ProductDto productDto) throws AttributeException {
        Product product = productService.save(productDto);
        String message = "product " + product.getName() + " have been saved";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update (@PathVariable("id") int id, @Valid @RequestBody ProductDto productDto) throws ResourceNotFoundException, AttributeException {
        //para que cuando se actualice el producto muestre el mensaje con el producto
        Product product = productService.update(id, productDto);
        String message = "product " + product.getName() + " have been updated";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete (@PathVariable("id") int id) throws ResourceNotFoundException {
        Product product = productService.delete(id);
        String message = "product " + product.getName() + " have been deleted";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK, message));
    }
}
