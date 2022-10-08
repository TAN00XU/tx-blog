package com.tan00xu.consumer;

import com.tan00xu.dto.EmailDTO;
import com.tan00xu.util.CmdOutputInformationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.tan00xu.constant.MQPrefixConst.EMAIL_QUEUE;

/**
 * 电子邮件消费者类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/04 12:06:56
 */
@Component
@RabbitListener(queues = EMAIL_QUEUE)
public class EmailConsumer {


    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Value("${spring.mail.nickname}")
    private String nickname;

    @Autowired
    private JavaMailSender javaMailSender;


    @RabbitHandler
    public void process(@Payload EmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname + "<" + email + ">");
        message.setTo(emailDTO.getEmail());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getContent());
        CmdOutputInformationUtils.info(emailDTO);

        //发送邮件
        javaMailSender.send(message);
    }

    /**
     *  @RabbitHandler
     *  public void process(byte[] data) {
     *      EmailDTO emailDTO = JSON.parseObject(new String(data), EmailDTO.class);
     *      SimpleMailMessage message = new SimpleMailMessage();
     *      message.setFrom(nickname + "<" + email + ">");
     *      message.setTo(emailDTO.getEmail());
     *      message.setSubject(emailDTO.getSubject());
     *      message.setText(emailDTO.getContent());
     *      javaMailSender.send(message);
     *  }
     *
     */

}
