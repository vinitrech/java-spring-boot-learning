package com.vinitrech.springbootlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/customers")
public class SpringBootLearningApplication {

    private final CustomerRepository customerRepository;

    public SpringBootLearningApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLearningApplication.class, args);
    }

    @GetMapping()
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age) {
    }

    @PostMapping()
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        customerRepository.save(customer);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable Integer customerId, @RequestBody NewCustomerRequest request) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            customer.get().setName(request.name());
            customer.get().setEmail(request.email());
            customer.get().setAge(request.age());

            customerRepository.save(customer.get());
        }
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }
}
