package com.customer.controller;

import com.customer.model.Customer;
import com.customer.service.my_interface.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/customers")
@PropertySource("classpath:application.properties")
public class CustomerService {
    @Autowired
    private ICustomerService customerService;
    @Value("${upload.path}")
    private String link;

    @Value("${display.path}")
    private String displayLink;
    @GetMapping
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if (customer!=null){
            return new ResponseEntity<>(customer,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestPart(value = "file", required = false) MultipartFile file,
                                                 @RequestPart("customer") Customer customer){
        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(link + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            customer.setImagePath(displayLink + fileName);
        } else {
            customer.setImagePath(displayLink + "default.jpg");
        }
        return new ResponseEntity<>(customerService.save(customer), HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if (customer!=null){
            customerService.remove(id);
            return new ResponseEntity<>(customer,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
