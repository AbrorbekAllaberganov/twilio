package com.example.twilio.service;

import com.example.twilio.SmsRequest;
import com.example.twilio.SmsSender;
import com.example.twilio.config.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {
    private final TwilioConfiguration twilioConfiguration;
    private final Logger logger= LoggerFactory.getLogger(TwilioSmsSender.class);

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();

        MessageCreator creator = Message.creator(
                to,
                from,
                message
        );
        creator.create();
        logger.info("Send sms {}",smsRequest);
    }
}
