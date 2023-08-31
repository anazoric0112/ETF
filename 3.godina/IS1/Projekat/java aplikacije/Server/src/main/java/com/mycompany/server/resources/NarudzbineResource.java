/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.resources;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ana Zoric
 */
@Path("narudzbine")
public class NarudzbineResource {
    @Resource(lookup="MyConnFac")
    private ConnectionFactory cf;
    @Resource(lookup="Topic2")
    private Topic t;
    @Resource(lookup="Queue2")
    private Queue q;       
    
    @GET
    @Path("{u}/{pw}")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response narudzbineKorisnika(@PathParam("u") String u, @PathParam("pw") String pw) throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("1#"+u+"#"+pw);
        tm.setStringProperty("type","narudzbine");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            if (txt.startsWith("Nepostojec"))
                return Response.status(404).entity(txt).build();
            if (txt.equals("Pogresna lozinka"))
                return Response.status(401).entity(txt).build();
            if (txt.startsWith("Pogresn"))
                return Response.status(400).entity(txt).build();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
    
    @GET
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response narudzbine() throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("2");
        tm.setStringProperty("type","narudzbine");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
}
