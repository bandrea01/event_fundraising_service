package it.unisalento.music_virus_project.event_fundraising_service.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfiguration {

    @Value("${app.rabbitmq.user-events-exchange}")
    private String userEventsExchangeName;

    @Value("${app.rabbitmq.user-events-queue}")
    private String userEventsQueueName;

    @Bean
    public TopicExchange userEventsExchange() {
        return new TopicExchange(userEventsExchangeName, true, false);
    }

    @Bean
    public Queue userEventsQueue() {
        return QueueBuilder.durable(userEventsQueueName).build();
    }

    @Bean
    public Binding userRegisteredBinding(Queue userEventsQueue, TopicExchange userEventsExchange) {
        // ascoltiamo TUTTI gli eventi user.* su una singola coda
        return BindingBuilder.bind(userEventsQueue)
                .to(userEventsExchange)
                .with("user.*");
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
