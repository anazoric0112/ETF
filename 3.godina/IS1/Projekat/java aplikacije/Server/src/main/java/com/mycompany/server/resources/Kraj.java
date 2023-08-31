/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.resources;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ana Zoric
 */
@Path("kraj")
public class Kraj {
    @Resource(lookup="MyConnFac")
    private ConnectionFactory cf;
    @Resource(lookup="Topic2")
    private Topic t;
    @Resource(lookup="Queue2")
    private Queue q;    
    
    @POST
    public Response zavrsi() throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm;
        tm=c.createTextMessage("6");
        tm.setStringProperty("type","korisnici");
        producer.send(t,tm);
        
        tm=c.createTextMessage("9");
        tm.setStringProperty("type","artikli");
        producer.send(t,tm);
        
        tm=c.createTextMessage("4");
        tm.setStringProperty("type","narudzbine");
        producer.send(t,tm);
        String txt="";
        for (int i=0;i<3;i++)
            txt+=((TextMessage)consumer.receive()).getText()+"\n";
        return Response.ok().entity(txt).build();
    }
    
    
}
