# Authors : Petar Vasiljevic 0007/2020

from django.db import models
from django.contrib.auth.models import AbstractUser


class Administrator(models.Model):
    """
        Attributes
            ida : Primary key for Administrator table
    """
    ida = models.OneToOneField('User', models.DO_NOTHING, db_column='idA', primary_key=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'administrator'


class Comment(models.Model):
    """
        Attributes
            idc : Primary key for Comment table
            comment : The content of the comment itself
            idt : Foreign key representing the Traveller that gave the comment
            iddr : Foreign key representing the identification of Drive
            grade : The rating that the driver gets from traveller
    """
    idc = models.AutoField(db_column='idC', primary_key=True)  # Field name made lowercase.
    comment = models.CharField(max_length=255)
    idt = models.ForeignKey('Traveller', models.DO_NOTHING, db_column='idT')  # Field name made lowercase.
    iddr = models.ForeignKey('Drive', models.DO_NOTHING, db_column='idDr')  # Field name made lowercase.
    grade = models.IntegerField()

    class Meta:
        managed = True
        db_table = 'comment'


class Drive(models.Model):
    """
        Attributes
            iddr : Primary key for Drive table
            datetime : Date and time when drive will happen
            estdatetime : Estimated date and time when drive will finish
            status : Status of ride : UNFINISHED, FINISHED, CANCELED
            price : Cost of the ride
            seats : Maximum number of available seats for the ride
            available : Number of currently available seats
            idd : Foreign key representing the Driver
            idto1 : Foreign key representing the source Town of the drive
            idto2 : Foreign key representing the destination Town of the drive
    """
    iddr = models.AutoField(db_column='idDr', primary_key=True)  # Field name made lowercase.
    datetime = models.DateTimeField(db_column='DateTime')  # Field name made lowercase.
    estdatetime = models.DateTimeField(db_column='estDateTime', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(max_length=20,default="UNFINISHED")
    price = models.FloatField()
    seats = models.IntegerField()
    available = models.IntegerField()
    idd = models.ForeignKey('Driver', models.DO_NOTHING, db_column='idD')  # Field name made lowercase.
    idto1 = models.ForeignKey('Town', models.DO_NOTHING, db_column='idTo1', related_name="drive_idto1")  # Field name made lowercase.
    idto2 = models.ForeignKey('Town', models.DO_NOTHING, db_column='idTo2', related_name="drive_idto2")  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'drive'


class Driver(models.Model):
    """
        Attributes
            idd : Foreign key representing the User
    """
    idd = models.OneToOneField('User', models.DO_NOTHING, db_column='idD', primary_key=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'driver'


class Moderator(models.Model):
    """
        Attributes
            idd : Foreign key representing the User
    """
    idm = models.OneToOneField('User', models.DO_NOTHING, db_column='idM', primary_key=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'moderator'


class Request(models.Model):
    """
        Attributes
            idr : Primary key for the Request table
            idu : Foreign key representing the User that made request for becoming moderator
    """
    idr = models.AutoField(db_column='idR', primary_key=True)  # Field name made lowercase.
    idu = models.ForeignKey('User', models.DO_NOTHING, db_column='idU')  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'request'


class Rezervation(models.Model):
    """
        Attributes
            idrez : Primary key for the Request table
            seats : Foreign key representing the User that made request for becoming moderator
            price : Cost of drive
            idt : Foreign key representing the Traveller
            status : Status of reservation: BOOKED, CANCELED, FINISHED
            iddr : Foreign key representing the Drive
    """
    idrez = models.AutoField(db_column='idRez', primary_key=True)  # Field name made lowercase.
    seats = models.IntegerField()
    price = models.FloatField()
    idt = models.ForeignKey('Traveller', models.DO_NOTHING, db_column='idT')  # Field name made lowercase.
    status = models.CharField(max_length=20, default='BOOKED')
    iddr = models.ForeignKey(Drive, models.DO_NOTHING, db_column='idDr')  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'rezervation'
        unique_together = (('idrez', 'seats'),)


class Road(models.Model):
    """
        Attributes:
            idro : Primary key for the Road table
            length : The length of the road between two cities
            idto1 : Foreign key representing the source Town
            idto2 : Foreign key representing the distance Town
    """
    idro = models.AutoField(db_column='idRo', primary_key=True)  # Field name made lowercase.
    length = models.IntegerField()
    idto1 = models.ForeignKey('Town', models.CASCADE, db_column='idTo1', related_name="road_idto1")  # Field name made lowercase.
    idto2 = models.ForeignKey('Town', models.CASCADE, db_column='idTo2', related_name="road_idto2")  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'road'


class Town(models.Model):
    """
        Attributes:
            idto : Primary key for the Town table
            name : Name of the town
    """
    idto = models.AutoField(db_column='idTo', primary_key=True)  # Field name made lowercase.
    name = models.CharField(db_column='Name', max_length=40)  # Field name made lowercase.
    class Meta:
        managed = True
        db_table = 'town'

    def __str__(self):
        return self.name

class Shortestpath(models.Model):
    """
        Attributes:
            idsp : Primary key for the Shortestpath table
            idto1 : Foreign key representing the source Town
            idto2 : Foreign key representing the distance Town
            shortest_path : Shortest path length between cities idto1 and idto2
    """
    idsp = models.AutoField(db_column='idSP', primary_key=True)  # Field name made lowercase.
    idto1 = models.ForeignKey('Town', models.CASCADE, db_column='idTo1', related_name="shortestpath_idto1")  # Field name made lowercase.
    idto2 = models.ForeignKey('Town', models.CASCADE, db_column='idTo2', related_name="shortestpath_idto2")  # Field name made lowercase.
    shortest_path = models.IntegerField()

    class Meta:
        managed = True
        db_table = 'shortestpath'

class Traveller(models.Model):
    """
        Attributes:
            idt : Foreign key representing the User
    """
    idt = models.OneToOneField('User', models.DO_NOTHING, db_column='idT', primary_key=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'traveller'


class User(AbstractUser):
    """
        Attributes
            idu : Primary key for the User table
            username : Username for User
            password : Password for User
            name : Name of User
            surname : Surname of User
            email : Email of User
            phone : Phone number of User
            gender : Gender of User
            image : Profile picture for User. User can have no image
    """
    # idu = models.AutoField(db_column='idU', primary_key=True)  # Field name made lowercase.
    # username = models.CharField(max_length=40)
    # password = models.CharField(max_length=40)
    # name = models.CharField(max_length=25)
    # surname = models.CharField(max_length=25)
    # email = models.CharField(max_length=40)
    phone = models.CharField(max_length=15)
    gender = models.BooleanField()  # This field type is a guess.
    # image = models.CharField(max_length=1, blank=True, null=True)
    image = models.ImageField(null=True, upload_to='imgs/')

    class Meta:
        managed = True
        db_table = 'user'
