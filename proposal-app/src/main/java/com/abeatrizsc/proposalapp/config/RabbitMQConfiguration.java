package com.abeatrizsc.proposalapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.pendingproposal.exchange}")
    private String exchange;

    @Bean
    public Queue createQueuePendingProposalCreditAnalysisMs(){
        return QueueBuilder.durable("pending-proposal.credit-analysis-ms").build();
    }

    @Bean
    public Queue createQueuePendingProposalNotificationMs(){
        return QueueBuilder.durable("pending-proposal.notification-ms").build();
    }

    @Bean
    public Queue createQueueCompleteProposalCreditAnalysisMs(){
        return QueueBuilder.durable("completed-proposal.credit-analysis-ms").build();
    }

    @Bean
    public Queue createQueueCompleteProposalNotificationsMs(){
        return QueueBuilder.durable("completed-proposal.notification-ms").build();
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange createFanoutExchangePendingProposal(){
        return ExchangeBuilder.fanoutExchange(exchange).build();
    }

    @Bean
    public Binding createBindingPendingProposalCreditAnalysisMs(){
        return BindingBuilder.bind(createQueuePendingProposalCreditAnalysisMs()).to(createFanoutExchangePendingProposal());
    }

    @Bean
    public Binding createBindingPendingProposalNotificationMs(){
        return BindingBuilder.bind(createQueuePendingProposalNotificationMs()).to(createFanoutExchangePendingProposal());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());

        return rabbitTemplate;
    }
}
