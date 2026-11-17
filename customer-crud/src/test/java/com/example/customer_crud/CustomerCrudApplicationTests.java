
package com.example.customer_crud;

import com.example.customer_crud.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerCrudApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    private String basicAuth(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(
                credentials.getBytes(StandardCharsets.UTF_8));
    }

    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setName("Test Technologies");
        customer.setGstin("27ABCDE1234F1Z5");
        customer.setBillingAddress("Nagpur, Maharashtra");
        customer.setShippingAddress("Pune, Maharashtra");
        return customerRepository.save(customer);
    }

    @Test
    void adminCanCreateCustomer() throws Exception {
        String request = """
                {
                    "name": "ABC Technologies",
                    "gstin": "27ABCDE1234F1Z5",
                    "billingAddress": "Nagpur, Maharashtra",
                    "shippingAddress": "Pune, Maharashtra"
                }
                """;

        mockMvc.perform(post("/api/customers")
                .header("Authorization", basicAuth("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated());
    }

    @Test
    void userCannotCreateCustomer() throws Exception {
        String request = """
                {
                    "name": "ABC Technologies",
                    "gstin": "27ABCDE1234F1Z5",
                    "billingAddress": "Nagpur, Maharashtra",
                    "shippingAddress": "Pune, Maharashtra"
                }
                """;

        mockMvc.perform(post("/api/customers")
                .header("Authorization", basicAuth("user", "user123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isForbidden());
    }

    @Test
    void userCanViewAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers")
                .header("Authorization", basicAuth("user", "user123")))
                .andExpect(status().isOk());
    }

    @Test
    void adminCanViewCustomerById() throws Exception {
        Customer customer = createTestCustomer();

        mockMvc.perform(get("/api/customers/" + customer.getId())
                .header("Authorization", basicAuth("admin", "admin123")))
                .andExpect(status().isOk());
    }

    @Test
    void userCannotUpdateCustomer() throws Exception {
        Customer customer = createTestCustomer();

        String request = """
                {
                    "name": "Updated Technologies",
                    "gstin": "27ABCDE1234F1Z5",
                    "billingAddress": "Mumbai, Maharashtra",
                    "shippingAddress": "Pune, Maharashtra"
                }
                """;

        mockMvc.perform(put("/api/customers/" + customer.getId())
                .header("Authorization", basicAuth("user", "user123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanUpdateCustomer() throws Exception {
        Customer customer = createTestCustomer();

        String request = """
                {
                    "name": "Updated Technologies",
                    "gstin": "27ABCDE1234F1Z5",
                    "billingAddress": "Mumbai, Maharashtra",
                    "shippingAddress": "Pune, Maharashtra"
                }
                """;

        mockMvc.perform(put("/api/customers/" + customer.getId())
                .header("Authorization", basicAuth("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void userCannotDeleteCustomer() throws Exception {
        Customer customer = createTestCustomer();

        mockMvc.perform(delete("/api/customers/" + customer.getId())
                .header("Authorization", basicAuth("user", "user123")))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanDeleteCustomer() throws Exception {
        Customer customer = createTestCustomer();

        mockMvc.perform(delete("/api/customers/" + customer.getId())
                .header("Authorization", basicAuth("admin", "admin123")))
                .andExpect(status().isNoContent());
    }

    @Test
    void invalidGstinReturns400ForAdmin() throws Exception {
        String request = """
                {
                    "name": "ABC Technologies",
                    "gstin": "INVALIDGSTIN",
                    "billingAddress": "Nagpur, Maharashtra",
                    "shippingAddress": "Pune, Maharashtra"
                }
                """;

        mockMvc.perform(post("/api/customers")
                .header("Authorization", basicAuth("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest());
    }
}
