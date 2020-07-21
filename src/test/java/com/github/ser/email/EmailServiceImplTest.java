//package com.github.ser.email;
//
//import com.github.ser.service.EmailService;
//import org.apache.commons.mail.util.MimeMessageParser;
//import org.junit.Rule;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.mail.internet.MimeMessage;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.AssertTrue.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
//class EmailServiceImplTest {
//
//    @Rule
//    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);
//
//    @Autowired
//    private EmailService emailService;
//
//    EmailServiceImplTest() throws Throwable {
//    }
//
//    @Test
//    public void sendMessage_shouldSendPlainMail() throws Exception {
//
//        // arrange
//        String mail_to = "test@domena.pl";
//        String title = "Title";
//        String content = "Content XXX";
//
//        // act
//        emailService.sendMessage(mail_to, title, content, false);
//
//        // assert
//        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
//        assertEquals(1, receivedMessages.length);
//
//        MimeMessage current = receivedMessages[0];
//
//        assertEquals(title, current.getSubject());
//        assertEquals(mail_to, current.getAllRecipients()[0].toString());
//
//        MimeMessageParser parser = new MimeMessageParser(current);
//        parser.parse();
//        assertTrue(parser.getPlainContent().contains(content));
//    }
//}