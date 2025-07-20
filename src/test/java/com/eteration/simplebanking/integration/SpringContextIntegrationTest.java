package com.eteration.simplebanking.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.eteration.simplebanking.controller.BankAccountController;
import com.eteration.simplebanking.service.BankingFacadeService;
import com.eteration.simplebanking.service.BankAccountService;
import com.eteration.simplebanking.service.TransactionService;
import com.eteration.simplebanking.DemoApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class SpringContextIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BankAccountController bankAccountController;

	@Autowired
	private BankingFacadeService bankingFacadeService;

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private TransactionService transactionService;

	@Test
	void shouldLoadSpringContextSuccessfully() {
		assertNotNull(applicationContext);
	}

	@Test
	void shouldHaveMainApplicationClass() {
		assertNotNull(DemoApplication.class);
	}

	@Test
	void shouldStartApplicationWithoutErrors() {
		// Test that the main method can be called without throwing exceptions
		DemoApplication.main(new String[]{});
		// If we reach here, it means no exception was thrown
		assertTrue(true, "Application should start without errors");
	}

	@Test
	void shouldInjectBankAccountController() {
		assertNotNull(bankAccountController);
	}

	@Test
	void shouldInjectBankingFacadeService() {
		assertNotNull(bankingFacadeService);
	}

	@Test
	void shouldInjectBankAccountService() {
		assertNotNull(bankAccountService);
	}

	@Test
	void shouldInjectTransactionService() {
		assertNotNull(transactionService);
	}

	@Test
	void shouldConfigureMockMvc() {
		assertNotNull(mockMvc);
	}

	@Test
	void shouldAccessActuatorHealthEndpoint() throws Exception {
		mockMvc.perform(get("/actuator/health"))
				.andExpect(status().isOk());
	}

	@Test
	void shouldContainAllRequiredBeansInContext() {
		assertNotNull(applicationContext.getBean(BankAccountController.class));
		assertNotNull(applicationContext.getBean(BankingFacadeService.class));
		assertNotNull(applicationContext.getBean(BankAccountService.class));
		assertNotNull(applicationContext.getBean(TransactionService.class));
	}

	@Test
	void shouldHaveTestProfileActive() {
		String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
		assertNotNull(activeProfiles);
		boolean testProfileActive = false;
		for (String profile : activeProfiles) {
			if ("test".equals(profile)) {
				testProfileActive = true;
				break;
			}
		}
		assertTrue(testProfileActive, "Test profile should be active");
	}
} 