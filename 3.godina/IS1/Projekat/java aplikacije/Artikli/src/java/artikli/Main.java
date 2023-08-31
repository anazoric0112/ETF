/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package artikli;

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
    
    private static void kreirajKat(String naziv,String potkat){
        TypedQuery<Kategorija> query=em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class);
        query.setParameter("naziv", naziv);
        List<Kategorija> ima_li=query.getResultList();
        if (ima_li.size()>0){
            TextMessage tm=c.createTextMessage("Kategorija sa tim imenom vec postoji");
            producer.send(q,tm);
            return;
        }
        Kategorija k=new Kategorija(naziv);
        System.out.println("potkat:"+potkat+".");
        try{
            if (!potkat.equals("null")) {
                Query query_pk=em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class);
                query_pk.setParameter("naziv", potkat);
                Kategorija pk=(Kategorija)query_pk.getSingleResult();
                k.setPotKat(pk);
            }
            em.getTransaction().begin();
            em.persist(k);
            em.getTransaction().commit();
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch (NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeca potkategorija");
            producer.send(q,tm);
            return;
        }
        catch  (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void kreirajArt(String naziv, String opis, int cena, int pop,String kat,String u,String pw){
        if (!proveriAuth(u, pw)) return;
        if (cena<=0 || pop<0){
            TextMessage tm=c.createTextMessage("Pogresni cena ili popust");
            producer.send(q,tm);
            return;
        }
        try{
            Query query_k=em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class);
            query_k.setParameter("naziv", kat);
            Kategorija k=(Kategorija)query_k.getSingleResult();
            
            Artikal a=new Artikal(naziv,opis,cena,pop,k);
            
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            
            query_k=em.createNamedQuery("Korisnik.findByUsername", Korisnik.class);
            query_k.setParameter("username", u);
            Korisnik kor=(Korisnik)query_k.getSingleResult();
            Query query_a=em.createNamedQuery("Artikal.findByNaziv", Artikal.class);
            query_a.setParameter("naziv", naziv);
            Artikal a2=(Artikal)query_a.getSingleResult();
            
            Prodaje prodaje=new Prodaje(kor.getIdK(),a2.getIdA());
            
            em.getTransaction().begin();
            em.persist(prodaje);
            em.getTransaction().commit();
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeca kategorija");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void promeniCenu(String art, int cena,String u,String pw){
        if (!proveriAuth(u, pw)) return;
        if (cena<=0){
            TextMessage tm=c.createTextMessage("Pogresna cena");
            producer.send(q,tm);
            return;
        }
        try{
            Query query=em.createNamedQuery("Artikal.findByNaziv",Artikal.class);
            query.setParameter("naziv", art);
            Artikal a=(Artikal)query.getSingleResult();
            
            Query q_p=em.createNamedQuery("Prodaje.findByIdA",Prodaje.class);
            q_p.setParameter("idA", a.getIdA());
            Prodaje prodaje=(Prodaje)q_p.getSingleResult();
            
            Query q_k=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
            q_k.setParameter("username", u);
            Korisnik k=(Korisnik)q_k.getSingleResult();
            
            if (!prodaje.getKorisnik().getIdK().equals(k.getIdK())){
                TextMessage tm=c.createTextMessage("Nije vas artikal");
                producer.send(q,tm);
                return;
            }
            em.getTransaction().begin();
            a.setCena(cena);
            em.getTransaction().commit();
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci artikal");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void promeniPop(String art, int pop,String u,String pw){
        if (!proveriAuth(u, pw)) return;
        if (pop<0){
            TextMessage tm=c.createTextMessage("Pogresan popust");
            producer.send(q,tm);
            return;
        }
        try{
            Query query=em.createNamedQuery("Artikal.findByNaziv",Artikal.class);
            query.setParameter("naziv", art);
            Artikal a=(Artikal)query.getSingleResult();
            
            Query q_p=em.createNamedQuery("Prodaje.findByIdA",Prodaje.class);
            q_p.setParameter("idA", a.getIdA());
            Prodaje prodaje=(Prodaje)q_p.getSingleResult();
            
            Query q_k=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
            q_k.setParameter("username", u);
            Korisnik k=(Korisnik)q_k.getSingleResult();
            
            if (prodaje.getKorisnik().getIdK()!=k.getIdK()){
                TextMessage tm=c.createTextMessage("Nije vas artikal");
                producer.send(q,tm);
                return;
            }
            
            em.getTransaction().begin();
            a.setPopust(pop);
            em.getTransaction().commit();
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci artikal");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void dodajUKorpu(String art, int cnt, String u,String pw){
        if (!proveriAuth(u, pw)) return;
        if (cnt<=0){
            TextMessage tm=c.createTextMessage("Pogresan broj artikala");
            producer.send(q,tm);
            return;
        }
        try{
            Query query=em.createNamedQuery("Artikal.findByNaziv",Artikal.class);
            query.setParameter("naziv", art);
            Artikal a=(Artikal)query.getSingleResult();
            
            Query query_kor=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
            query_kor.setParameter("username", u);
            Korisnik k=(Korisnik)query_kor.getSingleResult();
            
            Korpa korpa;
            try{
                query=em.createNamedQuery("Korpa.findByIdK",Korpa.class);
                query.setParameter("idK", k);
                korpa=(Korpa)query.getSingleResult();
            }catch (NoResultException e){
                korpa=new Korpa(0);
                korpa.setIdK(k);
                em.getTransaction().begin();
                em.persist(korpa);
                em.getTransaction().commit();
            }
            korpa=(Korpa)query.getSingleResult();
            em.getTransaction().begin();
            korpa.setCena(korpa.getCena()+cnt*a.getCena());
            em.getTransaction().commit();
            //u_korpi
            UKorpi u_korpi;
            Query query_u = em.createNamedQuery("UKorpi.findByIdAIdKorpa",UKorpi.class);
            query_u.setParameter("idA", a.getIdA());
            query_u.setParameter("idKorpa",korpa.getIdKorpa());
            try {
                u_korpi=(UKorpi)query_u.getSingleResult();
                em.getTransaction().begin();
                u_korpi.setKolicina(u_korpi.getKolicina()+cnt);
                em.getTransaction().commit();
            }catch(NoResultException e){
                u_korpi=new UKorpi(new UKorpiPK(korpa.getIdKorpa(),a.getIdA()));
                u_korpi.setKolicina(cnt);
                u_korpi.setCena(a.getCena());
                em.getTransaction().begin();
                em.persist(u_korpi);
                em.getTransaction().commit();
            }
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci artikal");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void izbaciIzKorpe(String art, int cnt, String u,String pw){
        if (!proveriAuth(u, pw)) return;
        if (cnt<=0){
            TextMessage tm=c.createTextMessage("Pogresan broj artikala");
            producer.send(q,tm);
            return;
        }
        try{
            Query query=em.createNamedQuery("Artikal.findByNaziv",Artikal.class);
            query.setParameter("naziv", art);
            Artikal a=(Artikal)query.getSingleResult();
            
            Query query_kor=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
            query_kor.setParameter("username", u);
            Korisnik k=(Korisnik)query_kor.getSingleResult();
            
            query=em.createNamedQuery("Korpa.findByIdK",Korpa.class);
            query.setParameter("idK", k);
            Korpa korpa=(Korpa)query.getSingleResult();
            
            Query query_u = em.createNamedQuery("UKorpi.findByIdAIdKorpa",UKorpi.class);
            query_u.setParameter("idA", a.getIdA());
            query_u.setParameter("idKorpa",korpa.getIdKorpa());
            UKorpi u_korpi=(UKorpi)query_u.getSingleResult();
            
            if (u_korpi.getKolicina()<cnt){
                TextMessage tm=c.createTextMessage("Nepostojeca kolicina artikala u korpi");
                producer.send(q,tm);
                return;
            }
            
            em.getTransaction().begin();
            korpa.setCena(korpa.getCena()-cnt*a.getCena());
            u_korpi.setKolicina(u_korpi.getKolicina()-cnt);
            //if (u_korpi.getKolicina()==0) em.remove(u_korpi);
            em.getTransaction().commit();
            
            TextMessage tm=c.createTextMessage("Uspesno izvrsena komanda");
            producer.send(q,tm);
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeci artikal ili korpa");
            producer.send(q,tm);
            return;
        } catch (Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            TextMessage tm=c.createTextMessage("Error");
            producer.send(q,tm);
        }
    }
    private static void dohvKat(){
        TypedQuery<Kategorija> query=em.createNamedQuery("Kategorija.findAll",Kategorija.class);
        List<Kategorija> kategorije=query.getResultList();
        String poruka="";
        for (int i=0;i<kategorije.size();i++){
            Kategorija k=kategorije.get(i);
            poruka+="Kategorija ["+k.getIdKat()+"] "+k.getNaziv();
            if (k.getPotKat()!=null) poruka+=" ("+k.getPotKat().getNaziv()+")";
            poruka+=";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    private static void dohvArt(String u, String pw){
        if (!proveriAuth(u,pw)) return;
        Query query=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
        query.setParameter("username", u);
        Korisnik k=(Korisnik)query.getSingleResult();
        
        TypedQuery<Prodaje> q_p=em.createNamedQuery("Prodaje.findByIdK",Prodaje.class);
        q_p.setParameter("idK", k.getIdK());
        List<Prodaje> prodaje=q_p.getResultList();
        if (prodaje.isEmpty()){
            TextMessage tm=c.createTextMessage("Korisnik ne prodaje nista");
            producer.send(q, tm);
            return;
        }
        String poruka="";
        for (int i=0;i<prodaje.size();i++){
            Artikal a=prodaje.get(i).getArtikal();
            Kategorija kat=a.getIdKat();
            poruka+="Artikal ["+a.getIdA()+"] "+a.getNaziv()+" ("+a.getOpis()+") "+a.getCena()+" "+a.getPopust()+" "+kat.getNaziv()+";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    private static void dohvKorpu(String u, String pw){
        if (!proveriAuth(u,pw)) return;
        Query query=em.createNamedQuery("Korisnik.findByUsername",Korisnik.class);
        query.setParameter("username", u);
        Korisnik k=(Korisnik)query.getSingleResult();
        List<UKorpi> u_korpi;
        try{
            Query q_k=em.createNamedQuery("Korpa.findByIdK",Korpa.class);
            q_k.setParameter("idK", k);
            Korpa korpa=(Korpa)q_k.getSingleResult();
            TypedQuery<UKorpi> q_u=em.createNamedQuery("UKorpi.findByIdKorpa",UKorpi.class);
            q_u.setParameter("idKorpa", korpa.getIdKorpa());
            u_korpi=q_u.getResultList();
        }catch(NoResultException e){
            TextMessage tm=c.createTextMessage("Nepostojeca korpa - korisniku je korpa prazna");
            producer.send(q, tm);
            return;
        }
        String poruka="";
        for (int i=0;i<u_korpi.size();i++){
            UKorpi uk=u_korpi.get(i);
            Artikal a=uk.getArtikal();
            poruka+="Artikal ["+a.getIdA()+"] "+a.getNaziv()+" x"+uk.getKolicina()+", "+uk.getCena()+";\n";
        }
        TextMessage tm=c.createTextMessage(poruka);
        producer.send(q, tm);
    }
    
    public static void main(String[] args) throws JMSException {
        c=cf.createContext();
        producer=c.createProducer();
        consumer=c.createSharedDurableConsumer(t, "artikli", "type='artikli'");
        emf=Persistence.createEntityManagerFactory("ArtikliPU");
        em=emf.createEntityManager();
        
        System.out.println("Artikli pokrenuti uspesno");
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
                        String kat=contents[2]!="null"?contents[2]:"";
                        Main.kreirajKat(contents[1], kat);
                        break;
                    case 1:
                        Main.kreirajArt(contents[1],contents[2],Integer.parseInt(contents[3]),
                                Integer.parseInt(contents[4]),contents[5],contents[6],contents[7]);
                        break;
                    case 2:
                        Main.promeniCenu(contents[1],Integer.parseInt(contents[2]),contents[3],contents[4]);
                        break;
                    case 3:
                        Main.promeniPop(contents[1],Integer.parseInt(contents[2]),contents[3],contents[4]);
                        break;
                    case 4:
                        Main.dodajUKorpu(contents[1], Integer.parseInt(contents[2]), contents[3], contents[4]);
                        break;
                    case 5:
                        Main.izbaciIzKorpe(contents[1], Integer.parseInt(contents[2]), contents[3], contents[4]);
                        break;
                    case 6:
                        Main.dohvKat();
                        break;
                    case 7:
                        Main.dohvArt(contents[1], contents[2]);
                        break;
                    case 8:
                        Main.dohvKorpu(contents[1], contents[2]);
                        break;
                    case 9:
                        end=true;
                }
                System.out.println("Izvrsena komanda");
            }
        }
        TextMessage tm=c.createTextMessage("Artikli uspesno prestali sa radom");
        producer.send(q, tm);
        em.close();
        emf.close();
    }
    
}
