/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.resources;

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
@Path("korisnici")
public class KorisniciResource {
    @Resource(lookup="MyConnFac")
    private ConnectionFactory cf;
    @Resource(lookup="Topic2")
    private Topic t;
    @Resource(lookup="Queue2")
    private Queue q;    
    
    @GET
    public Response getKorisnici() throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("5");
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
    @Path("{u}/{pw}/{i}/{p}/{a}/{g}/{n}")
    public Response napraviKorisnika(@PathParam("u") String u,@PathParam("pw") String pw,
                                @PathParam("i") String i,@PathParam("p") String p,
                                @PathParam("a") String a,@PathParam("g") String g,
                                @PathParam("n") String n) throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("0#"+u+"#"+pw+"#"+i+"#"+p+"#"+a+"#"+n+"#"+g);
        tm.setStringProperty("type","korisnici");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            if (txt.startsWith("Nepostojeci"))
                return Response.status(404).entity(txt).build();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
    
    @PUT
    @Path("{u}/{novac}")
    public Response dodajNovac(@PathParam("u") String u,@PathParam("novac") int novac) throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("2#"+u+"#"+novac);
        tm.setStringProperty("type","korisnici");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            if (txt.startsWith("Nepostojeci"))
                return Response.status(404).entity(txt).build();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
    
    @PUT
    @Path("{u}/{a}/{g}")
    public Response promeniAdresu(@PathParam("u") String u,@PathParam("a") String a,
                                  @PathParam("g") String g) throws JMSException{
        JMSContext c=cf.createContext();
        JMSProducer producer=c.createProducer();
        JMSConsumer consumer=c.createConsumer(q);
        
        TextMessage tm=c.createTextMessage("3#"+u+"#"+a+"#"+g);
        tm.setStringProperty("type","korisnici");
        producer.send(t,tm);
        
        Message msg=consumer.receive();
        if (msg instanceof TextMessage){
            String txt=((TextMessage) msg).getText();
            if (txt.startsWith("Nepostojeci"))
                return Response.status(404).entity(txt).build();
            return Response.ok().entity(txt).build();
        }
        return Response.serverError().build();
    }
}
