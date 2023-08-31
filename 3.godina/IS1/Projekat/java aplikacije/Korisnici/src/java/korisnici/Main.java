/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package korisnici;

import entiteti.*;
import java.util.List;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author Ana Zoric
 */
public class Main {

    @Resource(lookup="MyConnFac")
    private static ConnectionFactory cf;
    @Resource(lookup="Topic2")
    private static Topic t;
    @Resource(lookup="Queue2")
    private static Queue q;
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    private static JMSContext c;
    private static JMSProducer producer;
    private static JMSConsumer consumer;
        
    
    private static void kreirajGrad(String naziv){
        Grad g=new Grad(naziv);
        try{
            em.getTransaction().begin();
            em.persist(g);
            em.getTransaction().commit();
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch  (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
    private static void kreirajKorisnika(String[] params){
        
        Korisnik k=new Korisnik(params[1],params[2],params[3],params[4],params[5],Integer.parseInt(params[6]));
        
        TypedQuery<Grad> query=em.createNamedQuery("Grad.findByNaziv",Grad.class);
        query.setParameter("naziv", params[7]);
    
        try{
            Grad g= query.getSingleResult();
            
            em.getTransaction().begin();
            em.persist(k);
            em.getTransaction().commit();
            
            TypedQuery<Korisnik> qk=em.createNamedQuery("Korisnik.findByUsername", Korisnik.class);
            qk.setParameter("username",params[1]);
            Korisnik kk=(Korisnik)qk.getSingleResult();
            UGradu ugradu=new UGradu();
            ugradu.setIdG(g);
            ugradu.setIdK(kk.getIdK());
            ugradu.setKorisnik(kk);

            
            em.getTransaction().begin();
            em.persist(ugradu);
            em.getTransaction().commit();
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch (NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci grad");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
    private static void dodajNovac(String username, int novac){
        TypedQuery<Korisnik> query=em.createNamedQuery("Korisnik.findByUsername", Korisnik.class);
        query.setParameter("username", username);
        
        try{
            Korisnik k=(Korisnik)query.getSingleResult();
            
            em.getTransaction().begin();
            k.setNovac(k.getNovac()+novac);
            em.getTransaction().commit();
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch (NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci korisnik");
            producer.send(q,tm);
            return;
        }catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
    private static void promeniAdresu(String username,String adresa, String grad){
        TypedQuery<Korisnik> query=em.createNamedQuery("Korisnik.findByUsername", Korisnik.class);
        query.setParameter("username", username);
        TypedQuery<Grad> query_g=em.createNamedQuery("Grad.findByNaziv", Grad.class);
        query_g.setParameter("naziv", grad);

        try{
            Korisnik k=(Korisnik)query.getSingleResult();
            Grad  g=(Grad)query_g.getSingleResult();
            
            TypedQuery<UGradu> query_ug=em.createNamedQuery("UGradu.findByIdK",UGradu.class);
            query_ug.setParameter("idK", k.getIdK());
            UGradu ug=(UGradu)query_ug.getSingleResult();
            
            em.getTransaction().begin();
            k.setAdresa(adresa);
            ug.setIdG(g);
            em.getTransaction().commit();
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch (NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci korisnik ili grad");
            producer.send(q,tm);
            return;
        }catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
    private static void dohvatiGradove(){
        TypedQuery<Grad> query=em.createNamedQuery("Grad.findAll",Grad.class);
        List<Grad> gradovi=query.getResultList();
        String poruka="";
        for (int i=0;i<gradovi.size();i++){
            poruka+="Grad ["+gradovi.get(i).getIdG()+"] "+gradovi.get(i).getNaziv()+";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    private static void dohvatiKorisnike(){
        TypedQuery<Korisnik> query=em.createNamedQuery("Korisnik.findAll",Korisnik.class);
        List<Korisnik> korisnici=query.getResultList();
        String poruka="";
        for (int i=0;i<korisnici.size();i++){
            Korisnik k=korisnici.get(i);
            poruka+="Korisnik ["+k.getIdK()+"] "+k.getIme()+" "+k.getPrezime()+": "+k.getUsername()+";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    private static void skiniNovac(String username, int novac) throws JMSException{
        TypedQuery<Korisnik> query=em.createNamedQuery("Korisnik.findByUsername", Korisnik.class);
        query.setParameter("username", username);
        
        try{
            Korisnik k=(Korisnik)query.getSingleResult();
            
            em.getTransaction().begin();
            k.setNovac(k.getNovac()-novac);
            em.getTransaction().commit();
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            tm.setStringProperty("type", "narudzbine");
            producer.send(t,tm);
        }catch (NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci korisnik");
            tm.setStringProperty("type", "narudzbine");
            producer.send(t,tm);
            return;
        }catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }
    }
    
    public static void main(String[] args) throws JMSException {
        c=cf.createContext();
        producer=c.createProducer();
        consumer=c.createSharedDurableConsumer(t, "korisnici", "type='korisnici'");
        emf=Persistence.createEntityManagerFactory("KorisniciPU");
        em=emf.createEntityManager();
        
        System.out.println("Korisnici pokrenuti uspesno");
        boolean end=false;
        while (!end){
            Message msg=consumer.receive();
            if (msg instanceof TextMessage){
                String txt=((TextMessage) msg).getText();
                System.out.println("Received "+txt);
                String[] contents =txt.split("#");
                int which=Integer.parseInt(contents[0]);
                
                switch (which){
                    case 0: 
                        Main.kreirajKorisnika(contents);
                        break;
                    case 1:
                        Main.kreirajGrad(contents[1]);
                        break;
                    case 2:
                        Main.dodajNovac(contents[1],Integer.parseInt(contents[2]));
                        break;
                    case 3:
                        Main.promeniAdresu(contents[1], contents[2], contents[3]);
                        break;
                    case 4:
                        Main.dohvatiGradove();
                        break;
                    case 5:
                        Main.dohvatiKorisnike();
                        break;
                    case 6:
                        end=true;
                        break;
                    case 7:
                        Main.skiniNovac(contents[1],Integer.parseInt(contents[2]));
                        
                }
                System.out.println("Izvrsena komanda");
            }
        }
        TextMessage tm=c.createTextMessage("Korisnici uspesno prestali sa radom");
        producer.send(q, tm);
        em.close();
        emf.close();
    }

         
}
