package com.tan00xu.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tan00xu.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.tan00xu.constant.MQPrefixConst.EMAIL_QUEUE;


/**
 * RabbitMQ配置类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/04 11:41:45
 */
@Configuration
public class RabbitMQConfig {


    /**
     * 电子邮件交换机
     *
     * @return {@link FanoutExchange}
     */
    @Bean(name = "emailExchange")
    public FanoutExchange emailExchange() {
        return ExchangeBuilder
                .fanoutExchange(EMAIL_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * 邮件队列
     *
     * @return {@link Queue}
     */
    @Bean(name = "emailQueue")
    public Queue emailQueue() {
        return QueueBuilder
                .durable(EMAIL_QUEUE)
                .build();
    }


    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder
                .bind(emailQueue())
                .to(emailExchange());
    }

    /**
     * 提供自定义RabbitTemplate,将对象序列化为json串
     *
     * @param connectionFactory 连接工厂
     * @return {@link RabbitTemplate}
     */
    @Bean
    public RabbitTemplate jacksonRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * consumer进行序列化
     * rabbit侦听器容器工厂
     *
     * @param connectionFactory 连接工厂
     * @return {@link RabbitListenerContainerFactory}<{@link ?}>
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
