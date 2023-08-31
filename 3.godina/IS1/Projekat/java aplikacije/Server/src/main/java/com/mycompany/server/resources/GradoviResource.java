/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ana Zoric
 */
@Path("gradovi")
public class GradoviResource {
    @Resource(lookup="MyConnFac")
    private ConnectionFactory cf;
    @Resource(lookup="Topic2")
    private Topic t;
    @Resource(lookup="Queue2")
    private Queue q;

    @GET
    public Response getGradovi() throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("4");
        tm.setStringProperty("type","korisnici");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
    @POST
    @Path("{naziv}")
    public Response napraviGrad(@PathParam("naziv") String naziv) throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("1#"+naziv);
        tm.setStringProperty("type","korisnici");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
    
}
