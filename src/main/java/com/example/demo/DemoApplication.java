package com.example.demo;

import com.example.demo.consumer.FailedPromoConsumer;
import com.example.demo.producer.FailedPromoProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            FailedPromoProducer producerBean = ctx.getBean(FailedPromoProducer.class);
            producerBean.produceMsg();
            FailedPromoConsumer consumerBean = ctx.getBean(FailedPromoConsumer.class);
            consumerBean.startConsuming();
        };
    }
}
