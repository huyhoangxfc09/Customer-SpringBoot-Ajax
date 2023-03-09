package com.customer.controller;

import com.customer.model.Customer;
import com.customer.service.my_interface.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

//    @GetMapping
//    public ResponseEntity<List<Customer>> findAll(){
//        List<Customer> customers = customerService.findAll();
//        if (customers.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(customers,HttpStatus.OK);
//    }
    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> findAll(@PageableDefault(size = 2) Pageable pageable) {
        return new ResponseEntity<>(customerService.findAllPage(pageable), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<Customer>> findAll(@RequestParam("search") String search,@PageableDefault(size = 2) Pageable pageable) {
        return new ResponseEntity<>(customerService.findAllByNameContaining(search,pageable), HttpStatus.OK);
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

        return new ResponseEntity<>(customerService.save(file,customer), HttpStatus.CREATED);
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
