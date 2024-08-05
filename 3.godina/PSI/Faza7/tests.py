import random
from string import ascii_lowercase, digits

from django.test import TestCase

# Create your tests here.
from django.test import TestCase
from django.db.models import Q
from django.utils import timezone

from .models import *
from random import randint, randrange, choice
import datetime
from init_db import init_db_search_drives_tests
# Create your tests here.


def create_user(username,password,first_name,last_name,email,phone,gender,a,m,d,t ):
    user =User(username=username,
        password=password,
        first_name=first_name,
        last_name=last_name,
        email=email,
        phone=phone,
        gender=gender)
    user.set_password(password)
    user.save()
    if a:
        admin=Administrator(ida=user)
        admin.save()
    if m:
        mod=Moderator(idm=user)
        mod.save()
    if t:
        traveller=Traveller(idt=user)
        traveller.save()
    if d:
        driver=Driver(idd=user)
        driver.save()
    return user


def rand_str(length):
    letters = ascii_lowercase
    result_str = ''.join(choice(letters) for i in range(length))
    return result_str


def rand_num_str(length):
    letters = digits
    result_str = ''.join(choice(letters) for i in range(length))
    return result_str

def insert_random_users(num, username_len=5, a = None,m = None,d = None,t = None):
    try:
        for i in range(num):
            create_user(rand_str(username_len), rand_str(5), rand_str(5), rand_str(5), rand_str(5) + "@g.c", rand_num_str(5),
                        random.choice([True, False]), a or random.choice([True, False]), m or random.choice([True, False]),
                        d or random.choice([True, False]), t or random.choice([True, False]))
    except:
        pass


def insert_random_cities(num, num_roads):
    Town(name=rand_str(5)).save()
    for i in range(num - 1):
        t = Town(name=rand_str(5))
        t.save()
        for j in range(num_roads):
            ot = Town.objects.all()[randrange(0, Town.objects.count() - 1)]
            if ot != t and not Road.objects.filter(idto1=t).filter(idto2=ot).exists():
                Road(idto1=t, idto2=ot, length=randint(10, 1000)).save()


class ManagingTownsAndRoadsTest(TestCase):
    def init_database(self):
        insert_random_users(10)
        insert_random_cities(10, 4)
        self.user = create_user('savo', '123', 'Savo', 'Cvijetic', 'savo@g.c', '5363636321',
                                False, False, True, True, False)
        self.city = Town(name='Beograd')
        self.city.save()
        ns = Town(name='Novi Sad')
        ns.save()
        Road(length=randint(10, 1000), idto1=self.city, idto2=ns).save()
        city2 = Town(name='Valjevo')
        city2.save()
        Road(length=randint(10, 1000), idto1=self.city, idto2=city2).save()
        driver = Driver.objects.get(pk=self.user.pk)
        dr = Drive(datetime=timezone.now() - datetime.timedelta(days=30), price=randint(1, 1000), seats=2,
              available=2, idd=driver, idto1=self.city, idto2=city2)
        dr.save()
        insert_random_users(10)
        insert_random_cities(10, 4)


    def login(self):
        self.client.post('/cityhopper/login', data={
            'username': 'savo',
            'password': '123'
        })
        response = self.client.get('/cityhopper/homepage')
        self.assertContains(response, 'Cities')

    def init(self):
        self.init_database()
        self.login()

    def test_add_town(self):
        self.init()
        self.client.post('/cityhopper/moderator_add', data={
            'lname': 'Veliko Gradiste',
            'cloc0': self.city.idto,
            'dist_cloc0': randint(10, 1000)
        })
        try:
            vg = Town.objects.get(name='Veliko Gradiste')
        except Town.DoesNotExist:
            vg = None
        self.assertIsNotNone(vg)
        r = Road.objects.filter(Q(idto1=vg) | Q(idto2=vg))
        self.assertTrue(r.count() == 1)
        self.assertTrue(r[0].idto1.name == 'Veliko Gradiste' or r[0].idto2.name == 'Veliko Gradiste')

    def test_delete_town(self):
        self.init()
        t = Town.objects.get(name='Novi Sad')
        print(t.name)
        num_of_towns = Town.objects.count()
        tmp = self.client.post('/cityhopper/delete_town', data={
            'town_id': t.idto,
        })
        self.assertFalse(Town.objects.filter(name='Novi Sad').exists(), "Town isn't deleted properly")
        self.assertTrue(Town.objects.count() == num_of_towns - 1, "Number of towns after deleting isn't good")

    def test_unsuccessful_deleting_town(self):
        self.init()
        for city_name in ['Valjevo', 'Beograd']:
            t = Town.objects.get(name=city_name)
            num_of_towns = Town.objects.count()
            self.client.post('/cityhopper/delete_town', data={
                'town_id': t.idto,
            })
            self.assertTrue(Town.objects.filter(name=city_name).exists(), "Town is deleted")
            self.assertTrue(Town.objects.count() == num_of_towns, "Number of towns changed")

    def test_changing_town_name(self):
        self.init()
        data = {
            'edit_lname': 'Belgrade',
        }
        for road in Road.objects.filter(Q(idto1=self.city)|Q(idto2=self.city)):
            data['dist_' + str(road.idro)] = road.length
        self.client.post('/cityhopper/edit_town/' + str(self.city.idto), data=data)
        self.assertFalse(Town.objects.filter(name='Beograd').exists())
        self.assertTrue(Town.objects.filter(name='Belgrade').exists())

    def test_adding_road(self):
        self.init()
        city1_name = 'Valjevo'
        city2_name = 'Novi Sad'
        city1 = Town.objects.get(name=city1_name)
        city2 = Town.objects.get(name=city2_name)
        data = {
            'edit_lname': city1_name,
        }
        for road in Road.objects.filter(Q(idto1=city1) | Q(idto2=city1)):
            data['dist_' + str(road.idro)] = road.length
        data['new_road0'] = city2.idto
        data['dist_new_road0'] = 120
        num_roads = Road.objects.count()
        self.client.post('/cityhopper/edit_town/' + str(city1.idto), data=data)
        self.assertTrue(Road.objects.count() == num_roads + 1)
        self.assertTrue(Road.objects.filter((Q(idto1=city1) & Q(idto2=city2)) |
                                            (Q(idto2=city1) & Q(idto1=city2))).exists())

    def test_deleting_road(self):
        self.init()
        city1_name = 'Valjevo'
        city2_name = 'Beograd'
        city1 = Town.objects.get(name=city1_name)
        city2 = Town.objects.get(name=city2_name)
        data = {
            'edit_lname': city1_name,
        }
        for road in Road.objects.filter(Q(idto1=city1) | Q(idto2=city1)):
            data['dist_' + str(road.idro)] = road.length
        ro = Road.objects.get((Q(idto1=city1) & Q(idto2=city2)) | (Q(idto2=city1) & Q(idto1=city2)))
        data['del_'+str(ro.idro)] = 'checked'
        num_roads = Road.objects.count()
        self.client.post('/cityhopper/edit_town/' + str(city1.idto), data=data)
        self.assertTrue(Road.objects.count() == num_roads - 1)
        self.assertFalse(Road.objects.filter((Q(idto1=city1) & Q(idto2=city2)) |
                                            (Q(idto2=city1) & Q(idto1=city2))).exists())

    def test_unsuccessful_adding_road(self):
        self.init()
        city1_name = 'Valjevo'
        city2_name = 'Beograd'
        city1 = Town.objects.get(name=city1_name)
        city2 = Town.objects.get(name=city2_name)
        data = {
            'edit_lname': city1_name,
        }
        for road in Road.objects.filter(Q(idto1=city1) | Q(idto2=city1)):
            data['dist_' + str(road.idro)] = road.length
        data['new_road0'] = city2.idto
        data['dist_new_road0'] = 120
        num_roads = Road.objects.count()
        self.client.post('/cityhopper/edit_town/' + str(city1.idto), data=data)
        self.assertTrue(Road.objects.count() == num_roads)
        self.assertTrue(Road.objects.filter((Q(idto1=city1) & Q(idto2=city2)) |
                                            (Q(idto2=city1) & Q(idto1=city2))).count() == 1)

    def test_changing_road_length(self):
        self.init()
        city1_name = 'Valjevo'
        city2_name = 'Beograd'
        city1 = Town.objects.get(name=city1_name)
        city2 = Town.objects.get(name=city2_name)
        data = {
            'edit_lname': city1_name,
        }
        for road in Road.objects.filter(Q(idto1=city1) | Q(idto2=city1)):
            data['dist_' + str(road.idro)] = road.length
        road = Road.objects.get((Q(idto1=city1) & Q(idto2=city2)) |
                                            (Q(idto2=city1) & Q(idto1=city2)))
        new_distance = road.length + 100
        data['dist_' + str(road.idro)] = new_distance
        num_roads = Road.objects.count()
        self.client.post('/cityhopper/edit_town/' + str(city1.idto), data=data)
        self.assertTrue(Road.objects.count() == num_roads)
        self.assertTrue(Road.objects.get((Q(idto1=city1) & Q(idto2=city2)) |
                                            (Q(idto2=city1) & Q(idto1=city2))).length == new_distance)

class TestsProfile(TestCase):

    def test_my_profile(self):
        user=create_user('anaz','123','Ana','Zoric','ana.zoricc@gmail.com','0603144447',True,False,False,True,True)
        self.client.post("/cityhopper/login",data={
            'username':'anaz',
            'password':'123'
        })
        response=self.client.get("/cityhopper/profile")
        self.assertContains(response,"anaz",html=True)
        self.assertContains(response,"Ana",html=True)
        self.assertContains(response,"Zoric",html=True)
        self.assertContains(response,"ana.zoricc@gmail.com",html=True)
        self.assertContains(response,"0603144447",html=True)
        self.assertContains(response,"Female",html=True)

    def test_my_profile_driver_passenger(self):
        user=create_user('anaz','123','Ana','Zoric','ana.zoricc@gmail.com','0603144447',True,False,False,True,True)
        self.client.post("/cityhopper/login",data={
            'username':'anaz',
            'password':'123'
        })
        response=self.client.get("/cityhopper/homepage")
        self.assertContains(response,"Drives")
        self.assertContains(response,"Reservations")
        self.assertContains(response,"Search")

    def test_my_profile_admin(self):
        user=create_user('anaz','123','Ana','Zoric','ana.zoricc@gmail.com','0603144447',True,True,False,False,False)
        self.client.post("/cityhopper/login",data={
            'username':'anaz',
            'password':'123'
        })
        response=self.client.get("/cityhopper/homepage")
        self.assertContains(response,"Requests")

    def test_my_profile_moderator(self):
        user=create_user('anaz','123','Ana','Zoric','ana.zoricc@gmail.com','0603144447',True,False,True,False,False)
        self.client.post("/cityhopper/login",data={
            'username':'anaz',
            'password':'123'
        })
        response=self.client.get("/cityhopper/homepage")
        self.assertContains(response,"Cities")

    def test_other_profile(self):
        user1 = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                           True, True)
        user2 = create_user('anazz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                            True, True)
        self.client.post("/cityhopper/login", data={
            'username': 'anazz',
            'password': '123'
        })
        id_user=User.objects.get(username='anaz')
        response = self.client.get("/cityhopper/profile/"+str(id_user.id))
        self.assertContains(response, "anaz", html=True)
        self.assertContains(response, "Ana", html=True)
        self.assertContains(response, "Zoric", html=True)
        self.assertContains(response, "ana.zoricc@gmail.com", html=True)
        self.assertContains(response, "0603144447", html=True)
        self.assertContains(response, "Female", html=True)

    def test_change_info(self):
        user = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                            True, True)
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '123'
        })
        response=self.client.post("/cityhopper/change_info", data={
            'email':'ana.z@gmail.com',
            'first_name':'Ana',
            'last_name':'Zoric',
            'phone':'0604144447'
        })
        response = self.client.get("/cityhopper/profile")
        self.assertContains(response, "Ana", html=True)
        self.assertContains(response, "Zoric", html=True)
        self.assertContains(response, "ana.z@gmail.com", html=True)
        self.assertContains(response, "0604144447", html=True)

    def test_change_password_successful(self):
        user = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                           True, True)
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '123'
        })
        response=self.client.post("/cityhopper/change_password", data={
            'old_password':'123',
            'new_password1':'12345trewq',
            'new_password2':'12345trewq',
        })
        self.assertContains(response,'Successfully changed password',html=True)
        self.client.get("/cityhopper/logout")
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '12345trewq'
        })
        response=self.client.get("/cityhopper/profile")
        self.assertContains(response,'anaz',html=True)

    def test_change_password_not_strong_enough(self):
        user = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                           True, True)
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '123'
        })
        response = self.client.post("/cityhopper/change_password", data={
            'old_password': '123',
            'new_password1': '1234',
            'new_password2': '1234',
        })
        self.assertNotContains(response, 'Successfully changed password', html=True)

    def test_change_password_old_wrong(self):
        user = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                           True, True)
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '123'
        })
        response = self.client.post("/cityhopper/change_password", data={
            'old_password': '1234',
            'new_password1': '12345trewq',
            'new_password2': '12345trewq',
        })
        self.assertNotContains(response, 'Successfully changed password', html=True)

    def test_change_password_passwords_dont_match(self):
        user = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, False, False,
                           True, True)
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '123'
        })
        response = self.client.post("/cityhopper/change_password", data={
            'old_password': '1234',
            'new_password1': '12345trewq',
            'new_password2': '12345trew',
        })
        self.assertNotContains(response, 'Successfully changed password', html=True)

class TestsSearch(TestCase):

    def login(self):
        return self.client.post("/cityhopper/login", data={
            'username': 'eliyah',
            'password': '1234'
        })
    def test_search_1(self):
        init_db_search_drives_tests()
        self.login()
        self.client.post("/cityhopper/search",data={
            'src':Town.objects.get(name="Trstenik").idto,
            'dst':Town.objects.get(name="Zemun").idto,
            'date':'2023-07-04',
            'num_passengers':3
        })
        response=self.client.get("/cityhopper/results")
        self.assertContains(response,"July 4")
        self.assertNotContains(response,"July 9",html=True)
        self.assertNotContains(response,"July 13",html=True)
        self.assertNotContains(response,"July 17",html=True)
    def test_search_2(self):
        init_db_search_drives_tests()
        self.login()
        self.client.post("/cityhopper/search", data={
            'src': Town.objects.get(name="Trstenik").idto,
            'dst': Town.objects.get(name="Zemun").idto,
            'date': '2023-07-04',
            'num_passengers': 1
        })
        response = self.client.get("/cityhopper/results")
        self.assertContains(response, "July 4")
        self.assertContains(response, "July 9")
        self.assertNotContains(response, "July 13")
        self.assertContains(response, "July 17")
    def test_search_3(self):
        init_db_search_drives_tests()
        self.login()
        self.client.post("/cityhopper/search", data={
            'src': Town.objects.get(name="Trstenik").idto,
            'dst': Town.objects.get(name="Zemun").idto,
            'date': '2023-07-06',
            'num_passengers': 2
        })
        response = self.client.get("/cityhopper/results")
        self.assertNotContains(response, "July 4")
        self.assertContains(response, "July 9")
        self.assertNotContains(response, "July 13")
        self.assertNotContains(response, "July 17")
    def test_search_4(self):
        init_db_search_drives_tests()
        self.login()
        self.client.post("/cityhopper/search", data={
            'src': Town.objects.get(name="Zrenjanin").idto,
            'dst': Town.objects.get(name="Zemun").idto,
            'date': '2023-07-01',
            'num_passengers': 1
        })
        response = self.client.get("/cityhopper/results")
        self.assertNotContains(response, "July")
    def test_make_reservation(self):
        init_db_search_drives_tests()
        self.login()
        t1=Town.objects.get(name="Trstenik")
        t2=Town.objects.get(name="Zemun")
        d=datetime.datetime(2023,7,4)
        self.client.get("/cityhopper/search")
        self.client.post("/cityhopper/search", data={
            'src': t1.idto,
            'dst': t2.idto,
            'date': '2023-07-04',
            'num_passengers': 3
        })
        drive=Drive.objects.filter(Q(idto1=t1) & Q(idto2=t2) & Q(available__gte=3)&Q(datetime__gte=d))[0]
        iddr=drive.iddr
        self.client.get("/cityhopper/results")
        self.client.post("/cityhopper/add_reservation/"+str(drive.iddr)+"?seats="+str(drive.seats) +"&price="+str(drive.price),data={
            'num_passengers':3
        })
        drive=Drive.objects.get(pk=iddr)
        self.assertEquals(1,drive.available)

class TestsAdmin(TestCase):

    def moderator_request(self, id, accept):
        user = create_user('anaz', '123', 'Ana', 'Zoric', 'ana.zoricc@gmail.com', '0603144447', True, True, False,
                           False, False)
        self.client.post("/cityhopper/login", data={
            'username': 'anaz',
            'password': '123'
        })
        self.client.get("/cityhopper/admin_homepage")
        if accept: self.client.get("/cityhopper/accept_moderator/"+str(id))
        else: self.client.get("/cityhopper/reject_moderator/"+str(id))
        self.client.get("/cityhopper/logout")

    def make_and_login_as_moderator(self):
        resp=self.client.post("/cityhopper/signup", data={
            'username': 'anam',
            'password1': '54321trewq',
            'password2': '54321trewq',
            'first_name': 'Ana',
            'last_name': 'Zoric',
            'email': 'ana.zoricc@gmail.com',
            'phone': '0603144447',
            'gender': '1',
            'moderator': 1
        })
        resp=self.client.get("/cityhopper/profile")
        return resp

    def test_approve_moderator(self):
        response=self.make_and_login_as_moderator()
        self.assertNotContains(response, 'Approved moderator',html=True)
        self.assertContains(response, 'Request for moderator on wait',html=True)
        self.assertNotContains(response, 'Cities', html=True)
        self.client.get('/cityhopper/logout')
        for u in User.objects.all():
            i=1
        self.moderator_request(User.objects.get(username='anam').id,True)

        self.client.post("/cityhopper/login", data={
            'username': 'anam',
            'password': '54321trewq'
        })
        response=self.client.get("/cityhopper/profile")
        self.assertContains(response, 'Approved moderator',html=True)
        self.assertNotContains(response, 'Request for moderator on wait',html=True)

    def test_deny_moderator(self):
        self.make_and_login_as_moderator()
        response = self.client.get('/cityhopper/profile')
        self.assertNotContains(response, 'Approved moderator', html=True)
        self.assertContains(response, 'Request for moderator on wait',html=True)
        self.assertNotContains(response, 'Cities', html=True)
        self.client.get('/cityhopper/logout')

        self.moderator_request(User.objects.get(username='anam').id, False)

        self.client.post("/cityhopper/login", data={
            'username': 'anam',
            'password': '54321trewq'
        })
        response=self.client.get("/cityhopper/profile")
        self.assertNotContains(response, 'Approved moderator', html=True)
        self.assertNotContains(response, 'Request for moderator on wait',html=True)
        response=self.client.get("/cityhopper/homepage")
        self.assertNotContains(response, 'Cities', html=True)

class DriverTesting(TestCase):
    def setUp(self):
        self.user = create_user('mile','123','Misko','Vozac','vozi.misko@gmail.com','131213121312',False,False,False,True,False)
        self.driver_user = Driver.objects.get(idd=self.user.id)
        self.password = '123'
        insert_random_users(5, t=True)

        town_names = ["Valjevo", "Zajecar", "Beograd"]

        towns = []
        for i in range(len(town_names)):
            towns.append(Town(name=town_names[i]))
            towns[i].save()

        finished_drives = []
        first_date = timezone.now()
        last_date = timezone.now() + datetime.timedelta(days=30)
        finished_drives.append(Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            status="FINISHED",
            price=random.random() * 2000,
            seats=3,
            available=0,
            idd=self.driver_user,
            idto1=towns[0],
            idto2=towns[1]
        ))

        finished_drives.append(Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            status="FINISHED",
            price=random.random() * 2000,
            seats=3,
            available=0,
            idd=self.driver_user,
            idto1=towns[0],
            idto2=towns[2]
        ))

        for drive in finished_drives:
            drive.save()

        Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            price=random.random() * 2000,
            seats=5,
            available=5,
            idd=self.driver_user,
            idto1=towns[1],
            idto2=towns[0]
        ).save()

        Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            price=random.random() * 2000,
            seats=1,
            available=1,
            idd=self.driver_user,
            idto1=towns[1],
            idto2=towns[2]
        ).save()

        travelers = Traveller.objects.all()

        drives = Drive.objects.filter(status="UNFINISHED")

        Rezervation(seats=3,
                    price=3 * drives[0].price,
                    idt=travelers[1],
                    iddr=drives[0],
                    status="BOOKED").save()
        drives[0].available -= 3
        drives[0].save()

        Rezervation(seats=1,
                    price=1 * drives[1].price,
                    idt=travelers[0],
                    iddr=drives[1],
                    status="BOOKED").save()

        drives[1].available -= 1
        drives[1].save()
        comments = [
            "Bata vozi ubija",
            "Malo je krivudao, osecao sam se ugrozeno",
        ]
        grades = [5, 3]
        self.grade_sum = 8
        comment_objs = [] #IMPORTANT

        for i in range(2):
            com = Comment(comment=comments[i], grade=grades[i], iddr=finished_drives[i], idt=travelers[i])
            comment_objs.append(com)
            com.save()

        res = self.client.post("/cityhopper/login", data={
            'username': self.user.username,
            'password': self.password
        })

    def test_reviews(self):
        comments = Comment.objects.all()

        res = self.client.get('/cityhopper/finished_drives')
        self.assertContains(res, text='<img src="/static/cityhopper/imgs/star_full.png" class="star">',
                            count=self.grade_sum,
                            html=True)
        for c in comments:
            #travelers name
            self.assertContains(res, "Drive from " + c.iddr.idto1.name + " to " + c.iddr.idto2.name)
            self.assertContains(res, '<td colspan=2>' + c.comment + '</td>', html=True)

    def test_finished_drives(self):
        finished_drives = Drive.objects.filter(status="FINISHED")

        res = self.client.get('/cityhopper/finished_drives')

        for d in finished_drives:
            self.assertContains(res, "<td><b>" + d.idto1.name +"</b></td>",
                                html=True)
            # datum

    def test_active_drives(self):
        active_drives = Drive.objects.filter(status="UNFINISHED")

        res = self.client.get('/cityhopper/active_drives')

        for d in active_drives:
            self.assertContains(res, "<td><b>" + d.idto1.name + "</b></td>",
                                html=True)
            self.assertContains(res, "<td>Available seats:</td><td>"+ str(d.available) + "</td>",
                                html=True)
            # datum


    def test_reservation_view(self):
        reservations = Rezervation.objects.filter(status="BOOKED")

        res = self.client.get('/cityhopper/active_drives')

        for r in reservations:
            self.assertContains(res, "<td><h5>" + r.idt.idt.username + "</h5></td>"+
                                      "<td>Reserved number of seats: " + str(r.seats) + "</td>", html=True)

    def test_cancel_drive(self):
        towns = Town.objects.all()
        first_date = timezone.now()
        last_date = timezone.now() + datetime.timedelta(days=30)
        d = Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            price=random.random() * 2000,
            seats=1,
            available=1,
            idd=self.driver_user,
            idto1=towns[0],
            idto2=towns[2]
        )
        d.save()

        self.client.get('/cityhopper/cancel_drive/' + str(d.pk))

        res = self.client.get('/cityhopper/active_drives')
        self.assertNotContains(res, f"""
                    <tr>
                        <td>From:</td>
                        <td><b>{towns[0].name}</b></td>
                    </tr>
                    <tr>
                        <td>To:</td>
                        <td><b>{towns[1].name}</b></td>
                    </tr>
        """, html=True)

    def test_finish_drive(self):
        towns = Town.objects.all()
        first_date = timezone.now()
        last_date = timezone.now() + datetime.timedelta(days=30)
        d = Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            price=random.random() * 2000,
            seats=1,
            available=1,
            idd=self.driver_user,
            idto1=towns[0],
            idto2=towns[2]
        )
        d.save()

        self.client.get('/cityhopper/finish_drive/' + str(d.pk))

        res = self.client.get('/cityhopper/active_drives')
        self.assertNotContains(res, f"""
                            <tr>
                                <td>From:</td>
                                <td><b>{towns[0].name}</b></td>
                            </tr>
                            <tr>
                                <td>To:</td>
                                <td><b>{towns[1].name}</b></td>
                            </tr>
                """, html=True)

        res = self.client.get('/cityhopper/finished_drives')
        self.assertContains(res, f"""
                            <tr>
                                <td>From:</td>
                                <td><b>{towns[0].name}</b></td>
                            </tr>
                            <tr>
                                <td>To:</td>
                                <td><b>{towns[1].name}</b></td>
                            </tr>
                """, html=True)



    def test_create_drive(self):
        towns = Town.objects.all()

        num_drives = Drive.objects.count()
        # start date in the past
        res = self.client.post("/cityhopper/create_drive", data={
            'src': towns[0].idto,
            'dst': towns[1].idto,
            'num_passengers': 5,
            'price': 100,
            'date_end': datetime.datetime(2023, 10, 26, 13, 20, 30),
            'date_start': datetime.datetime(2023, 10, 25, 14, 30, 59)
        })
        self.assertContains(res, "You have created a drive successfully")
        self.assertTrue(Drive.objects.count() == num_drives + 1)
        self.assertTrue(Drive.objects.get(datetime=datetime.datetime(2023, 10, 25, 14, 30, 59)))

        num_drives = Drive.objects.count()
        # start date in the past
        res = self.client.post("/cityhopper/create_drive", data={
            'src': towns[0].idto,
            'dst': towns[1].idto,
            'num_passengers': 5,
            'price': 100,
            'date_end': datetime.datetime(2023,10,26,13,20,30),
            'date_start': datetime.datetime(2023,1,25,14,30,59)
        })
        self.assertContains(res, "Starting date must be in future")
        self.assertTrue(Drive.objects.count() == num_drives)

        # start date after end date
        res = self.client.post("/cityhopper/create_drive", data={
            'src': towns[0].idto,
            'dst': towns[1].idto,
            'num_passengers': 5,
            'price': 100,
            'date_end': datetime.datetime(2023, 10, 26, 13, 20, 30),
            'date_start': datetime.datetime(2023, 10, 27, 14, 30, 59)
        })
        self.assertContains(res, "Date of arrival must be after the starting date")
        self.assertTrue(Drive.objects.count() == num_drives)


class TestsPetar(TestCase):
    def init_database(self):
        insert_random_users(10)
        insert_random_cities(10, 4)
        self.user = create_user('vasiljko', '123', 'Petar', 'Vasiljevic', 'vasiljko@g.c', '0600000000',
                                False, False, True, True, True)
        self.driver = create_user('gagi', '123', 'Dragan', 'Milicev', 'gagi@gmail.com', '041243124', False, False, False, True, False)

        self.idto1 = Town(name="Beograd")
        self.idto1.save()
        self.idto2 = Town(name="Novi Sad")
        self.idto2.save()

        self.drive = Drive(datetime=datetime.datetime.now(), price=20.5, seats=5, available=3, idd = Driver.objects.get(idd=self.driver.pk), idto1=self.idto1, idto2=self.idto2)
        self.drive.save()

        self.reservation = Rezervation(seats = 2, price = 41, idt = Traveller.objects.get(idt=self.user.pk), status="BOOKED", iddr = self.drive)
        self.reservation.save()

        self.reservation_canceled = Rezervation(seats=1, price=20.5, idt=Traveller.objects.get(idt=self.user.pk), status="CANCELLED",
                                       iddr=self.drive)
        self.reservation_canceled.save()

        self.idto1_finished = Town(name="Valjevo")
        self.idto1_finished.save()
        self.idto2_finished = Town(name="Smederevo")
        self.idto2_finished.save()

        self.drive_finished = Drive(datetime=datetime.datetime.now(), price=50, seats=4, available=2,
                           idd=Driver.objects.get(idd=self.driver.pk), idto1=self.idto1_finished, idto2=self.idto2_finished, status="FINISHED")
        self.drive_finished.save()

        self.reservation_finished = Rezervation(seats=2, price=100, idt=Traveller.objects.get(idt=self.user.pk), status="FINISHED",
                                       iddr=self.drive_finished)
        self.reservation_finished.save()

    def login(self):
        self.client.post('/cityhopper/login', data={
            'username': 'vasiljko',
            'password': '123'
        })
        response = self.client.get('/cityhopper/homepage')
        self.assertContains(response, 'Reservations')

    def init(self):
        self.init_database()
        self.login()


    def test_view_active_reservations(self):
        self.init_database()
        self.login()

        response = self.client.get("/cityhopper/my_reservations")
        self.assertContains(response, "Beograd")
        self.assertContains(response, "Novi Sad")
        self.assertContains(response, "gagi")
        self.assertContains(response, "2")
        self.assertContains(response, "41")

    def test_view_canceled_reservations(self):
        self.init_database()
        self.login()

        response = self.client.get("/cityhopper/my_cancelled_reservations")
        self.assertContains(response, "Beograd")
        self.assertContains(response, "Novi Sad")
        self.assertContains(response, "gagi")
        self.assertContains(response, "1")
        self.assertContains(response, "20.5")

    def test_view_finished_reservations(self):
        self.init_database()
        self.login()

        response = self.client.get("/cityhopper/my_finished_reservations")
        self.assertContains(response, "Valjevo")
        self.assertContains(response, "Smederevo")
        self.assertContains(response, "gagi")
        self.assertContains(response, "Rating")
        self.assertContains(response, "Add comment")

    def test_add_comment(self):
        self.init_database()
        self.login()

        self.client.post('/cityhopper/add_comment', data={
            'comment': 'Odlicna voznja, bravo',
            'rat': 5,
            'id': self.reservation_finished.idrez
        })

        try:
            comm = Comment.objects.get(Q(grade=5) & Q(comment="Odlicna voznja, bravo"))
        except Comment.DoesNotExist:
            comm = None

        self.assertIsNotNone(comm)

    def test_change_number_seats(self):
        self.init_database()
        self.login()

        self.reservation.seats=3
        self.reservation.price=61.5

        self.reservation.save()

        self.drive.available = 2
        self.drive.save()

        response = self.client.get("/cityhopper/my_reservations")
        self.assertContains(response, "Beograd")
        self.assertContains(response, "Novi Sad")
        self.assertContains(response, "gagi")
        self.assertContains(response, "3")
        self.assertContains(response, "61.5")

    def test_cancel_reservation(self):
        self.init_database()
        self.login()

        response = self.client.get("/cityhopper/my_cancelled_reservations")
        self.assertContains(response, "Beograd")
        self.assertContains(response, "Novi Sad")
        self.assertContains(response, "gagi")
        self.assertContains(response, "1")
        self.assertContains(response, "20.5")
