package com.lanchonete.customer.bdd;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.lanchonete.customer.core.application.usecases.CreateCustomerUseCase;
import com.lanchonete.customer.core.application.usecases.FindCustomerUseCase;
import com.lanchonete.customer.core.application.usecases.UpdateCustomerUseCase;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class CucumberTestConfiguration {

    @Bean
    @Primary
    public CreateCustomerUseCase createCustomerUseCase() {
        return mock(CreateCustomerUseCase.class);
    }

    @Bean
    @Primary
    public FindCustomerUseCase findCustomerUseCase() {
        return mock(FindCustomerUseCase.class);
    }

    @Bean
    @Primary
    public UpdateCustomerUseCase updateCustomerUseCase() {
        return mock(UpdateCustomerUseCase.class);
    }
}
