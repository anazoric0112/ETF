/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package narudzbin;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import entiteti.*;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

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
    
    private static boolean proveriAuth(String u,String pw){
        Query query=em.createNamedQuery("Korisnik.findByUsername", Korisnik.class);
        query.setParameter("username", u);
        try{
            Korisnik k=(Korisnik)query.getSingleResult();
            if (!k.getPassword().equals(pw)){
                TextMessage tm=c.createTextMessage("Pogresna lozinka");
                producer.send(q,tm);
                return false;
            }
            return true;
        }catch (NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci korisnik");
            producer.send(q,tm);
            return false;
        }
    }
    private static void placanje(String u, String pw){
        if (!proveriAuth(u, pw)) return;
        Query q_k=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
        q_k.setParameter("username", u);
        Korisnik k=(Korisnik)q_k.getSingleResult();
        
        try{
            Query q_g=em.createNamedQuery("UGradu.findByIdK",UGradu.class);
            q_g.setParameter("idK", k.getIdK());
            UGradu g=(UGradu)q_g.getSingleResult();
        
            Query q_korpa=em.createNamedQuery("Korpa.findByIdK",Korpa.class);
            q_korpa.setParameter("idK", k);
            Korpa korpa=(Korpa)q_korpa.getSingleResult();
            
            if (korpa.getCena()>k.getNovac()){
                TextMessage tm=c.createTextMessage("Nemate dovoljno novca");
                producer.send(q,tm);
                return;
            }
            
            TypedQuery<UKorpi> q_uk=em.createNamedQuery("UKorpi.findByIdKorpa",UKorpi.class);
            q_uk.setParameter("idKorpa", korpa.getIdKorpa());
            List<UKorpi> uk_lista=q_uk.getResultList();
            
            Date dt=new Date();
            Timestamp ts= new Timestamp(dt.getTime());
            Narudzbina n=new Narudzbina(korpa.getCena(),korpa.getIdKorpa(),ts,k.getAdresa());
            n.setIdG(g.getIdG());
            n.setIdK(k);
            
            em.getTransaction().begin();
            
            em.persist(n);
            Query q_n=em.createNamedQuery("Narudzbina.findByIdKorpa",Narudzbina.class);
            q_n.setParameter("idKorpa", korpa.getIdKorpa());
            n=(Narudzbina)q_n.getSingleResult();
            
            for (int i=0;i<uk_lista.size();i++){
                UKorpi uk=uk_lista.get(i);
                Stavka s=new Stavka(new StavkaPK(n.getIdN(),uk.getArtikal().getIdA()),uk.getKolicina(),uk.getCena());
                em.persist(s);
                em.remove(uk_lista.get(i));
            }
            em.remove(korpa);
            
            Transakcija tr=new Transakcija(n,n.getCena(),ts);
            em.persist(tr);
            em.getTransaction().commit();
            
            //k.setNovac(k.getNovac()-n.getCena());
            TextMessage tm_plati=c.createTextMessage("7#"+u+"#"+n.getCena());
            tm_plati.setStringProperty("type","korisnici");
            producer.send(t,tm_plati);
            consumer.receive();
                    
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojece");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void dohvNK(String u, String pw){
        if (!proveriAuth(u, pw)) return;
        Query q_k=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
        q_k.setParameter("username", u);
        Korisnik k=(Korisnik)q_k.getSingleResult();
        
        TypedQuery<Narudzbina> query=em.createNamedQuery("Narudzbina.findByIdK",Narudzbina.class);
        query.setParameter("idK", k);
        List<Narudzbina> narudzbine=query.getResultList();
        String poruka="";
        for (int i=0;i<narudzbine.size();i++){
            Narudzbina n=narudzbine.get(i);
            poruka+="Narudzbina na adresu "+n.getAdresa()+" "+n.getIdG().getNaziv()+", "+n.getVreme()+" - "+n.getCena()+";\n";
        }
        if (narudzbine.isEmpty()) poruka="Korisnik nema narudzbine";
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    private static void dohvN(){
        TypedQuery<Narudzbina> query=em.createNamedQuery("Narudzbina.findAll",Narudzbina.class);
        List<Narudzbina> narudzbine=query.getResultList();
        String poruka="";
        for (int i=0;i<narudzbine.size();i++){
            Narudzbina n=narudzbine.get(i);
            poruka+="Narudzbina na adresu "+n.getAdresa()+" "+n.getIdG().getNaziv()+", "+n.getVreme()+" - "+n.getCena()+";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    private static void dohvT(){
        TypedQuery<Transakcija> query=em.createNamedQuery("Transakcija.findAll",Transakcija.class);
        List<Transakcija> transakcije=query.getResultList();
        String poruka="";
        for (int i=0;i<transakcije.size();i++){
            Transakcija t=transakcije.get(i);
            poruka+="Transakcija u vreme: "+t.getVreme()+" - "+t.getCena()+";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    
    public static void main(String[] args) throws JMSException {
        c=cf.createContext();
        producer=c.createProducer();
        consumer=c.createSharedDurableConsumer(t, "narudzbine", "type='narudzbine'");
        
        emf=Persistence.createEntityManagerFactory("NarudzbinPU");
        em=emf.createEntityManager();
        
        System.out.println("Narudzbine pokrenute uspesno");
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
                        Main.placanje(contents[1],contents[2]);
                        break;
                    case 1:
                        Main.dohvNK(contents[1],contents[2]);
                        break;
                    case 2:
                        Main.dohvN();
                        break;
                    case 3:
                        Main.dohvT();
                        break;
                    case 4:
                        end=true;
                }
                 System.out.println("Izvrsena komanda");
            }
        }
        TextMessage tm=c.createTextMessage("Narudzbine uspesno prestale sa radom");
        producer.send(q, tm);
        em.close();
        emf.close();
    }
    
}
