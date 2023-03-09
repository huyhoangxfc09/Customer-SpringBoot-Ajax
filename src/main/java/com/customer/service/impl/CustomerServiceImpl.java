package com.customer.service.impl;

import com.customer.model.Customer;
import com.customer.repository.ICustomerRepository;
import com.customer.service.my_interface.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAllListCustomer();
    }
    @Override
    public Page<Customer> findAllPage(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> findAllByNameContaining(String name, Pageable pageable) {
        return customerRepository.findAllByNameContaining(name,pageable);
    }


    @Value("${upload.path}")
    private String link;

    @Value("${display.path}")
    private String displayLink;
    @Override
    public Customer save(MultipartFile file,Customer customer) {
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
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {
        customerRepository.removeCustomer(id);
    }
}
