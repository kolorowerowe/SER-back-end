package com.github.ser.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class MailMessage {
    private String to;
    private String subject;
    private String body;

    @Builder.Default
    private Boolean isHtml = false;

    public static MailMessage getVerificationMailMessage(String to, String code) {
        return MailMessage.builder()
                .to(to)
                .subject("Verification")
                .body("<h3>Hello,</h3>" +
                        "<p>here is your verification code: </p>"
                        + "<h1>" + code + "</h1>")
                .isHtml(true)
                .build();
    }
}
