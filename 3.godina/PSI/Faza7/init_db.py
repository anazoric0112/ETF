# Authors : Ilija Obradovic 0049/2020
#           Petar Vasiljevic 0007/2020
import datetime

from django.db import connection
from django.db.models import DateTimeField
from django.utils import timezone

from CityHopper.models import *
import datetime
import random
import pytz


def init():
    # to be same data in tests
    random.seed(123)

    users = []

    users.append(User(
        username="eliyah",
        first_name="Ilija",
        last_name="Obradovic",
        email="jooj@jooj.jooj",
        phone="123456789",
        gender=False,
    ))
    users[0].set_password("1234")

    users.append(User(
        username="ReMa",
        password="12345",
        first_name="Marko",
        last_name="Matovic",
        email="majko@jooj.jooj",
        phone="987654321",
        gender=False,
    ))
    users[1].set_password("12345")

    users.append(User(
        username="gagi",
        password="12345",
        first_name="Dragan",
        last_name="Milicev",
        email="uh@jooj.jooj",
        phone="987654321",
        gender=False,
    ))
    users[2].set_password("12345")

    users.append(User(
        username="moderator",
        password="1234",
        first_name="Momir",
        last_name="Momicic",
        email="dobarmejl@mejl.jooj",
        phone="123456789",
        gender=False,
    ))
    users[3].set_password("1234")

    users.append(User(
        username="admin",
        first_name="Aleksandra",
        last_name="Aleksic",
        email="joojmajko@jooj.jooj",
        phone="123456789",
        gender=True,
    ))
    users[4].set_password("admin")

    users.append(User(
        username="radisa",
        password="123",
        first_name="Radisa",
        last_name="Kovacevic",
        email="rale@moj.dobri",
        phone="064063062061",
        gender=False
    ))
    users[5].set_password("123")

    users.append(User(
        username="ana",
        password="123",
        first_name="Ana",
        last_name="Zoric",
        email="ana@z.gmail",
        phone="064063062061",
        gender=True
    ))
    users[6].set_password("123")

    users.append(User(
        username="savo",
        password="123",
        first_name="Savo",
        last_name="Cvijetic",
        email="savo@c.gmail",
        phone="064063062061",
        gender=False
    ))
    users[7].set_password("123")

    users.append(User(
        username="pekora",
        password="123",
        first_name="Petar",
        last_name="Vasiljevic",
        email="petar@v.gmail",
        phone="064063062061",
        gender=False
    ))
    users[8].set_password("123")

    for u in users:
        u.save()


    travelers = [
        Traveller(idt=users[0]),
        Traveller(idt=users[1]),
        Traveller(idt=users[6]),
        Traveller(idt=users[7]),
        Traveller(idt=users[8])
        ]

    for t in travelers:
        t.save()

    drivers = [
        Driver(idd=users[2]),
        Driver(idd=users[6]),
        Driver(idd=users[7]),
        Driver(idd=users[8])
    ]

    for d in drivers:
        d.save()

    moderators = [
        Moderator(idm=users[3]),
        Moderator(idm=users[6])
    ]

    for m in moderators:
        m.save()

    admins = [
        Administrator(ida=users[4]),
        Administrator(ida=users[6])
    ]

    for a in admins:
        a.save()

    town_distances = []
    with open("towns", "r", encoding="utf8") as fin:
        town_names = fin.readlines()
        for i in range(len(town_names)):
            town_names[i] = town_names[i].strip()

    for i in range(1, len(town_names)):
        num_dis = random.randint(0, i)
        selections = []
        for j in range(num_dis):
            while True:
                selection = random.randint(0, i)
                if selection not in selections:
                    selections.append(selection)
                    break
            length = random.randint(10, 600)
            town_distances.append((selection, i, length))

    towns = []
    for i in range(len(town_names)):
        towns.append(Town(name=town_names[i]))
        towns[i].save()

    roads = []

    for i in range(len(town_distances)):
        road = town_distances[i]
        roads.append(Road(length=road[2], idto1=towns[road[0]], idto2=towns[road[1]]))
        roads[i].save()

    drives = []
    first_date = timezone.now()
    last_date = timezone.now() + datetime.timedelta(days=30)
    for i in range(30):
        t1 = random.randint(0, len(towns) - 1)
        t2 = random.randint(0, len(towns) - 1)
        s = random.randint(1,5)
        while t1 == t2:
            t2 = random.randint(0, len(towns) - 1)
        drives.append(Drive(
            datetime=first_date + (last_date-first_date) * random.random(),
            price=random.random() * 2000,
            seats=s,
            available=s,
            idd=drivers[random.randint(0, len(drivers)-1)],
            idto1=towns[t1],
            idto2=towns[t2]
        ))
        drives[i].save()

    # FINISHED DRIVES
    finished_drives = []
    first_date = timezone.now()
    last_date = timezone.now() + datetime.timedelta(days=30)
    for i in range(7):
        t1 = random.randint(0, len(towns) - 1)
        t2 = random.randint(0, len(towns) - 1)
        s = random.randint(1, 5)
        while t1 == t2:
            t2 = random.randint(0, len(towns) - 1)
        finished_drives.append(Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            status="FINISHED",
            price=random.random() * 2000,
            seats=s,
            available=s,
            idd=drivers[random.randint(0, len(drivers)-1)],
            idto1=towns[t1],
            idto2=towns[t2]
        ))
        finished_drives[i].save()

    # FINISHED RESERVATIONS
    traveler = travelers[0]
    comments = [
        "Bata vozi ubija",
        "Malo je krivudao, osecao sam se ugrozeno",
        "Kida kako vozi",
        "Dobra voznja",
        "Svidja mi se",
        "Moze to bolje",
        "Kupio mi je sladoled"
    ]
    grades = [5, 3, 5,4,4,2,5]
    for i in range(7):
        c = random.randint(1, finished_drives[i].available)
        traveler = travelers[random.randint(0,len(travelers)-1)]
        Rezervation(seats=c,
                    price=c * finished_drives[i].price,
                    idt=traveler,
                    iddr=finished_drives[i],
                    status="FINISHED").save()

        finished_drives[i].available -= c
        finished_drives[i].save()

        Comment(comment=comments[i], grade=grades[i], iddr=finished_drives[i], idt = traveler).save()

    reqs = [
        Request(idu=users[5]),
        Request(idu=users[7])
    ]

    for r in reqs:
        r.save()

    # INIT RESERVATIONS

    for i in range(5):
        drives = Drive.objects.all()
        j = random.randint(0, len(drives) - 1)
        while drives[j].status == "FINISHED" or drives[j].available == 0:
            j = random.randint(0, len(drives) - 1)

        c = random.randint(1, drives[j].available)
        traveler = travelers[random.randint(0, len(travelers) - 1)]

        Rezervation(seats=c,
                    price=c * drives[j].price,
                    idt=traveler,
                    iddr=drives[j],
                    status="BOOKED").save()

        drives[j].available -= c
        drives[j].save()




def clean():
    cleaning_list = [
        Comment.objects.all(),
        Rezervation.objects.all(),
        Request.objects.all(),
        Drive.objects.all(),
        Road.objects.all(),
        Town.objects.all(),
        Administrator.objects.all(),
        Moderator.objects.all(),
        Driver.objects.all(),
        Traveller.objects.all(),
        User.objects.all(),
        ]

    for c in cleaning_list:
        for o in c:
            o.delete()

    reset_autoincrement()


def clean_reservations():
    ress = Rezervation.objects.all()
    for res in ress:
        res.iddr.available = res.iddr.seats
        res.iddr.save()
    ress.delete()


def reset_autoincrement():
    with connection.cursor() as cursor:
        cursor.execute("ALTER TABLE road AUTO_INCREMENT = 1")
        cursor.execute("ALTER TABLE town AUTO_INCREMENT = 1")
        cursor.execute("ALTER TABLE drive AUTO_INCREMENT = 1")
        cursor.execute("ALTER TABLE comment AUTO_INCREMENT = 1")
        cursor.execute("ALTER TABLE request AUTO_INCREMENT = 1")
        cursor.execute("ALTER TABLE rezervation AUTO_INCREMENT = 1")
        cursor.execute("ALTER TABLE user AUTO_INCREMENT = 1")


def clean_and_init():
    clean()
    init()


def init_db_search_drives_tests():
    random.seed(123)

    users = []

    users.append(User(
        username="eliyah",
        first_name="Ilija",
        last_name="Obradovic",
        email="jooj@jooj.jooj",
        phone="123456789",
        gender=False,
    ))
    users[0].set_password("1234")

    users.append(User(
        username="ReMa",
        password="12345",
        first_name="Marko",
        last_name="Matovic",
        email="majko@jooj.jooj",
        phone="987654321",
        gender=False,
    ))
    users[1].set_password("12345")

    users.append(User(
        username="gagi",
        password="12345",
        first_name="Dragan",
        last_name="Milicev",
        email="uh@jooj.jooj",
        phone="987654321",
        gender=False,
    ))
    users[2].set_password("12345")

    users.append(User(
        username="ana",
        password="123",
        first_name="Ana",
        last_name="Zoric",
        email="ana@z.gmail",
        phone="064063062061",
        gender=True
    ))
    users[3].set_password("123")

    users.append(User(
        username="savo",
        password="123",
        first_name="Savo",
        last_name="Cvijetic",
        email="savo@c.gmail",
        phone="064063062061",
        gender=False
    ))
    users[4].set_password("123")

    users.append(User(
        username="pekora",
        password="123",
        first_name="Petar",
        last_name="Vasiljevic",
        email="petar@v.gmail",
        phone="064063062061",
        gender=False
    ))
    users[5].set_password("123")

    for u in users:
        u.save()

    moderators = [
        Moderator(idm=users[0])
    ]

    for m in moderators:
        m.save()

    admins = [
        Administrator(ida=users[0])
    ]

    for a in admins:
        a.save()

    travelers = [
        Traveller(idt=users[0]),
        Traveller(idt=users[1]),
        Traveller(idt=users[2])
    ]

    for t in travelers:
        t.save()

    drivers = [
        Driver(idd=users[2]),
        Driver(idd=users[3]),
        Driver(idd=users[4])
    ]

    for d in drivers:
        d.save()

    town_distances = []
    with open("towns", "r", encoding="utf8") as fin:
        town_names = fin.readlines()
        for i in range(len(town_names)):
            town_names[i] = town_names[i].strip()
        n=len(town_names)
        for i in range(6,n): town_names.pop(-1)

    for i in range(1, len(town_names)):
        num_dis = random.randint(0, i)
        selections = []
        for j in range(num_dis):
            while True:
                selection = random.randint(0, i)
                if selection not in selections:
                    selections.append(selection)
                    break
            length = random.randint(10, 600)
            town_distances.append((selection, i, length))

    towns = []
    for i in range(len(town_names)):
        towns.append(Town(name=town_names[i]))
        towns[i].save()

    roads = []

    for i in range(len(town_distances)):
        road = town_distances[i]
        roads.append(Road(length=road[2], idto1=towns[road[0]], idto2=towns[road[1]]))
        roads[i].save()

    drives = []
    first_date = timezone.now()
    last_date = timezone.now() + datetime.timedelta(days=30)
    for i in range(20):
        t1 = random.randint(0, len(towns) - 1)
        t2 = random.randint(0, len(towns) - 1)
        s = random.randint(1, 5)
        while t1 == t2:
            t2 = random.randint(0, len(towns) - 1)
        drives.append(Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            price=random.random() * 2000,
            seats=s,
            available=s,
            idd=drivers[random.randint(0, len(drivers) - 1)],
            idto1=towns[t1],
            idto2=towns[t2]
        ))
        drives[i].save()

    # FINISHED DRIVES
    finished_drives = []
    first_date = timezone.now()
    last_date = timezone.now() + datetime.timedelta(days=30)
    for i in range(3):
        t1 = random.randint(0, len(towns) - 1)
        t2 = random.randint(0, len(towns) - 1)
        s = random.randint(1, 5)
        while t1 == t2:
            t2 = random.randint(0, len(towns) - 1)
        finished_drives.append(Drive(
            datetime=first_date + (last_date - first_date) * random.random(),
            status="FINISHED",
            price=random.random() * 2000,
            seats=s,
            available=s,
            idd=drivers[random.randint(0, len(drivers) - 1)],
            idto1=towns[t1],
            idto2=towns[t2]
        ))
        finished_drives[i].save()


    # INIT RESERVATIONS

    for i in range(5):
        drives = Drive.objects.all()
        j = random.randint(0, len(drives) - 1)
        while drives[j].status == "FINISHED" or drives[j].available == 0:
            j = random.randint(0, len(drives) - 1)

        c = random.randint(1, drives[j].available)
        traveler = travelers[random.randint(0, len(travelers) - 1)]

        Rezervation(seats=c,
                    price=c * drives[j].price,
                    idt=traveler,
                    iddr=drives[j],
                    status="BOOKED").save()

        drives[j].available -= c
        drives[j].save()