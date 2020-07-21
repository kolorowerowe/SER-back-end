//package com.github.ser.email;
//
//import com.icegreen.greenmail.util.GreenMail;
//import com.icegreen.greenmail.util.ServerSetup;
//import org.junit.rules.ExternalResource;
//
//import javax.mail.internet.MimeMessage;
//
//public class SmtpServerRule extends ExternalResource {
//
//    private GreenMail smtpServer;
//
//    public SmtpServerRule(int port) throws Throwable {
//        super.before();
//        smtpServer = new GreenMail(new ServerSetup(port, "localhost", "smtp"));
//        smtpServer.start();
//    }
//
//    @Override
//    protected void before(){
//    }
//
//    public MimeMessage[] getMessages() {
//        return smtpServer.getReceivedMessages();
//    }
//
//    @Override
//    protected void after() {
//        super.after();
//        smtpServer.stop();
//    }
//}