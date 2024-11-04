package com.santos.proposal_app.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    
    final private ConnectionFactory connectionFactory;

    @Value("${rabbitmq.proposalpeding.exchange}")
    private String exchange;
    public RabbitMQConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public Queue createQueueProposalPendingMsAnalysisCredit() {
        return QueueBuilder.durable("proposal_pending.ms_analysis_credit").build();
    }

    @Bean
    public Queue createQueueProposalPendingMsNotification() {
        return QueueBuilder.durable("proposal_pending.ms_notification").build();
    }

    @Bean
    public Queue createQueueProposalMsAnalysisCredit() {
        return QueueBuilder.durable("proposal_conclusion.ms_proposal").build();
    }

    @Bean
    public Queue createQueueProposalConclusionMsNotification() {
        return QueueBuilder.durable("proposal_conclusion.ms_notification").build();
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(this.connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> startAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public FanoutExchange createFanoutExchangeProposalPending() {
        return ExchangeBuilder.fanoutExchange(this.exchange).build();
    }

    @Bean
    public Binding createBindingProposalPending() {
        return BindingBuilder.bind(createQueueProposalPendingMsAnalysisCredit()).to(createFanoutExchangeProposalPending());
    }

    @Bean
    public Binding createBindingMsNotification() {
        return BindingBuilder.bind(createQueueProposalPendingMsNotification()).to(createFanoutExchangeProposalPending());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
