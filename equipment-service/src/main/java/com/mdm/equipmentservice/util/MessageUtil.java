package com.mdm.equipmentservice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MessageUtil {
    private final MessageSource messageSource;

    @Autowired
    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Get messages from messages.properties file with the given param
     *
     * @param messageKey
     * @param args
     * @return
     */
    public String getMessage(String messageKey, String... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

    /**
     * Get messages from messages.properties file while the params is another messages from messages.properties <br><br>
     * Ex: resourceNotFoundException={0} not found; equipment=equipment<br><br>
     * In the example above, messageKey=resourceNotFoundException. While the messageKeyAsParams=equipment<br><br>
     * The result will be like: equipment not found
     * @param messageKey
     * @param messageKeyAsParams
     * @return
     */
    public String getMessageWithMessageKeyAsParam(String messageKey, String... messageKeyAsParams) {
        List<String> messageFromMessageKeyParams = Arrays.stream(messageKeyAsParams).map(this::getMessage).toList();
        return getMessage(messageKey, messageFromMessageKeyParams.toArray(new String[0]));
    }

}
