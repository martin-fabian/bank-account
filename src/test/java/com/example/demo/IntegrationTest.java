package com.example.demo;

import com.example.demo.client.PrefixClient;
import com.example.demo.resource.BankResource;
import com.example.demo.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@SpringBootTest
@AutoConfigureWireMock(port = 8082, stubs = "classpath:/stubs")
public class IntegrationTest {

  @MockBean
  private TransactionService transactionService;

  @Autowired
  private BankResource bankResource;
  @Autowired
  private PrefixClient prefixClient;

  @Test
  void demo() {

    System.out.println(prefixClient.getPrefix());
  }
}
