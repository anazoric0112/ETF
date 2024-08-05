# Authors : Petar Vasiljevic 0007/2020
#           Ana Zoric        0009/2020
#           Savo Cvijetic    0054/2020
#           Ilija Obradovic  0049/2020
import math

import django.utils.timezone
from datetime import datetime, timedelta

import pytz
from django.forms import Form
from django.shortcuts import render, redirect
from django.http import HttpResponse, Http404, HttpResponseForbidden, HttpResponseBadRequest, HttpRequest
from django.template.context_processors import request
from starlette.status import HTTP_404_NOT_FOUND

from .utils import *
from .models import *
from .forms import *
from django.db.models import Q
from django.contrib.auth.forms import AuthenticationForm, UserCreationForm
from django.contrib.auth import authenticate, login, logout
from django.db.transaction import commit, rollback
from django.contrib.auth.hashers import make_password, check_password
from django.contrib import messages
from django.contrib.auth.decorators import login_required


@login_required(login_url='login')
def show_drives(request):
    """
    Shows all the drives that fit the parameters provided by user in search_drives view
    @param request: HttpRequest
    @return: HttpResponse of passenger homepage after search page or
                Http404
    """
    src = request.session.get('src')
    dst = request.session.get('dst')
    date = datetime.strptime(request.session.get('date'), '%Y-%m-%d').date()
    num_passengers = request.session.get('num_passengers')

    drives = Drive.objects.filter(Q(idto1_id=src) & Q(idto2_id=dst) & Q(datetime__gte=date) & Q(available__gte=num_passengers)
                                  & Q(status="UNFINISHED"))

    form = SearchDrivesForm()

    if request.method == "POST":
        form = SearchDrivesForm(request.POST)

        if form.is_valid():
            try:
                src = form.cleaned_data['src']
                dst = form.cleaned_data['dst']
                date = form.cleaned_data['date'].strftime("%Y-%m-%d")
                num_passengers = form.cleaned_data['num_passengers']

                src = Town.objects.get(name=src)
                dst = Town.objects.get(name=dst)

                request.session['src'] = src.idto
                request.session['dst'] = dst.idto
                request.session['date'] = date
                request.session['num_passengers'] = num_passengers

                return redirect('passenger_homepage_after_search')
            except Town.DoesNotExist:
                return Http404("Town not found")

    context = {
        'drives' : drives,
        'form' : form,
    }

    return render(request, 'cityhopper/passenger_homepage_after_search.html', add_user_type(request, context))

@login_required(login_url='login')
def search_drives(request):
    """
    Shows the page where user can search for drives
    @param request: HttpRequest
    @return: HttpResponse of passenger homepage after search page or
                Http404
    """
    form = SearchDrivesForm()

    if request.method == "POST":
        form = SearchDrivesForm(request.POST)

        if form.is_valid():
            try:
                src = form.cleaned_data['src']
                dst = form.cleaned_data['dst']
                date = form.cleaned_data['date'].strftime("%Y-%m-%d")
                num_passengers = form.cleaned_data['num_passengers']

                src = Town.objects.get(name=src)
                dst = Town.objects.get(name=dst)

                request.session['src'] = src.idto
                request.session['dst'] = dst.idto
                request.session['date'] = date
                request.session['num_passengers'] = num_passengers

                return redirect('passenger_homepage_after_search')
            except Town.DoesNotExist:
                return Http404("Town not found")

    context = {
        'form': form
    }
    return render(request, 'cityhopper/passenger_homepage.html', add_user_type(request, context))


def login_req(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of login page or
                HttpResponseRedirect to homepage
    """
    form = CustomAuthenticationForm(request=request, data=request.POST or None)
    if form.is_valid():
        username = form.cleaned_data['username']
        password = form.cleaned_data['password']
        user = authenticate(request, username=username, password=password)
        if user:
            login(request, user)
            return redirect('/cityhopper/homepage')
    context = {
        'form': form
    }
    return render(request, 'cityhopper/login.html', add_user_type(request, context))


def signup(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of sign up page or
                HttpResponseRedirect to homepage
    """
    if request.method != 'POST':
        form = CustomUserCreationForm()
    else:
        form = CustomUserCreationForm(request.POST, request.FILES)
        if form.is_valid():
            form.save(commit=False)
            user = User()
            user.username = form.cleaned_data['username']
            user.password = make_password(form.cleaned_data['password1'])
            user.email = form.cleaned_data['email']
            user.first_name = form.cleaned_data['first_name']
            user.last_name = form.cleaned_data['last_name']
            user.phone = form.cleaned_data['phone']
            user.image = form.cleaned_data['image']
            if form.cleaned_data['gender'] == '1':
                user.gender = 0
            else:
                user.gender = 1
            user.save()
            if form.cleaned_data['passenger'] == 1:
                traveller = Traveller()
                traveller.idt = user
                traveller.save()
            if form.cleaned_data['driver'] == 1:
                driver = Driver()
                driver.idd = user
                driver.save()
            if form.cleaned_data['moderator'] == 1:
                req = Request()
                req.idu = user
                req.save()
            login(request, user)
            return redirect('/cityhopper/homepage')
    context = {
        'form': form
    }
    return render(request, 'cityhopper/signup.html', add_user_type(request, context))


def logout_req(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of sign up page or
                HttpResponseRedirect to homepage
    """
    logout(request)
    return redirect('/cityhopper/homepage')


def create_drive(request):
    """
        If form method is POST, creates drive with the data specified in form

        @param request: HttpRequest
        @return: HttpResponse of create_drive page
                 Redirects to homepage if user is not driver
    """
    try:
        driver=Driver.objects.get(pk=request.user.id)
    except Driver.DoesNotExist:
        return redirect('/cityhopper/homepage')

    form = CreateDriveForm()

    if request.method == "POST":
        form = CreateDriveForm(request.POST or None)

        if form.is_valid():
            try:
                src = form.cleaned_data['src']
                dst = form.cleaned_data['dst']
                date_start = form.cleaned_data['date_start']
                src = Town.objects.get(name=src)
                dst = Town.objects.get(name=dst)

                date_end=None
                try:
                    date_end=form.cleaned_data['date_end']
                except AttributeError:
                    date_end=None

                if date_end is None:
                    calc_shortest_paths(request)
                    shortest_path = Shortestpath.objects.get(Q(idto1=src) & Q(idto2=dst))
                    length = shortest_path.shortest_path

                    if length != 30000000:
                        time_delta = timedelta(seconds=math.floor(length/100*3600))
                        date_end = date_start + time_delta

                num_passengers = form.cleaned_data['num_passengers']
                price = form.cleaned_data['price']

                if date_end!=None:
                    if date_end<=date_start:
                        context = {
                            "msg": "Date of arrival must be after the starting date",
                            "form": form
                        }
                        return render(request, "cityhopper/driver_homepage.html", add_user_type(request, context))
                if date_start<datetime.now(pytz.timezone('Europe/Belgrade')):
                    context = {
                        "msg": "Starting date must be in future",
                        "form": form
                    }
                    return render(request, "cityhopper/driver_homepage.html", add_user_type(request, context))

                #change idd to request.user.id when logins are done
                drive = Drive(datetime=date_start, estdatetime=date_end, idto1=src, idto2=dst,
                              idd=Driver.objects.get(pk=request.user.id), price=price, seats=num_passengers, available=num_passengers)
                drive.save()
                form=CreateDriveForm()
                context={
                    "msg":"You have created a drive successfully",
                    "form":form
                }
                return render(request,"cityhopper/driver_homepage.html",add_user_type(request, context))
            except Town.DoesNotExist:
                context={
                    "msg":"The specified city does not exist",
                    "form":form
                }
                return render(request,"cityhopper/driver_homepage.html",add_user_type(request, context))
    context = {
        'form': form
    }
    return render(request, 'cityhopper/driver_homepage.html', add_user_type(request, context))

def cancel_drive(request, id):
    """
        Cancels drive with the specified id

        @param request: HttpRequest
        @param id: Integer
        @return: Redirects to active_rides of logged in driver
    """
    try:
        driver=Driver.objects.get(pk=request.user.id)
    except Driver.DoesNotExist:
        return redirect('/cityhopper/homepage')
    try:
        drive=Drive.objects.get(pk=id)
        drive.status="CANCELLED"
        reservations = Rezervation.objects.filter(iddr__iddr=drive.iddr)

        for r in reservations:
            r.status = "CANCELLED"
            r.save()
        drive.save()
    except Drive.DoesNotExist:
        return Http404("Drive not found")
    return redirect("active_drives")

def finish_drive(request, id):
    """
        Finishes drive with the specified id

        @param request: HttpRequest
        @param id: Integer
        @return: Redirects to active_rides of logged in driver
    """
    try:
        driver=Driver.objects.get(pk=request.user.id)
    except Driver.DoesNotExist:
        return redirect('/cityhopper/homepage')
    try:
        drive=Drive.objects.get(pk=id)
        drive.status="FINISHED"
        reservations=Rezervation.objects.filter(iddr__iddr=drive.iddr)
        for r in reservations:
            r.status="FINISHED"
            r.save()
        drive.save()
    except Drive.DoesNotExist:
        return Http404("Drive not found")
    return redirect("active_drives")

def add_user_type(request, oldcontext=None):
    """
        Adds parameters specifying user types of logged user to context

        @param request: HttpRequest
        @param oldcontext: Dictionary
        @return: context with added user_type parameters
    """
    if oldcontext is None:
        oldcontext = {}
    if Administrator.objects.filter(pk=request.user.pk).exists():
        oldcontext['user_a']=1
    if Moderator.objects.filter(pk=request.user.pk).exists():
        oldcontext['user_m']=1
    if Driver.objects.filter(pk=request.user.pk).exists():
        oldcontext['user_d']=1
    if Traveller.objects.filter(pk=request.user.pk).exists():
        oldcontext['user_p']=1
    return oldcontext


def accept_moderator(request, mod_id):
    if not Administrator.objects.filter(pk=request.user.pk).exists():
        return HttpResponseForbidden('You are not admin')
    try:
        u = User.objects.get(id=mod_id)
        Request.objects.get(idu=u).delete()
        mod = Moderator()
        mod.idm = u
        mod.save()
        return redirect('admin_homepage')
    except Exception:
        return redirect('admin_homepage')


def reject_moderator(request, mod_id):
    if not Administrator.objects.filter(pk=request.user.pk).exists():
        return HttpResponseForbidden('You are not admin')
    try:
        u = User.objects.get(id=mod_id)
        Request.objects.get(idu=u).delete()
        return redirect('admin_homepage')
    except Exception:
        return redirect('admin_homepage')


def admin_homepage(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of administrator homepage or
                HttpResponseForbidden if administrator isn't logged in
    """
    if not Administrator.objects.filter(pk=request.user.pk).exists():
        return HttpResponseForbidden('You are not admin')
    mod_req = []
    for i in Request.objects.all():
        # form =
        user = i.idu
        mod_req.append({
            'form': AdminApproveForm(request.POST or None),
            'id': user.id,
            'username': user.username,
            'first_name': user.first_name,
            'last_name': user.last_name,
            'phone': user.phone,
            'email': user.email
        })
    context = {
        'mod_req': mod_req
    }
    return render(request, 'cityhopper/admin_homepage.html', add_user_type(request, context))


def admin_profile(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of administrator profile of logged-in user or
                HttpResponseForbidden if administrator isn't logged in
    """
    if Administrator.objects.filter(pk=request.user.pk).exists():
        context = {
            'user_': User.objects.get(pk=request.user.pk)
        }
        return render(request, 'cityhopper/profile.html', add_user_type(request, context))
    return HttpResponseForbidden('You are not admin')


def passenger_profile(request, idu):
    """
    @param request: HttpRequest
    @param idu: Identifier of user
    @return: HttpResponse of passenger profile with identifier idu,
                HttpResponseBadRequest if passenger with identifier idu doesn't exist
    """
    if Traveller.objects.filter(pk=idu).exists():
        context = {
            'user_': User.objects.get(pk=idu)
        }
        return render(request, 'cityhopper/profile.html', add_user_type(request, context))
    return HttpResponseBadRequest("Required passenger doesn't exist")

def passenger_profile_private(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of passenger profile of logged-in user or
                HttpResponseBadRequest if passenger isn't logged in
    """
    return passenger_profile(request, request.user.pk)

def get_homepage(request):
    """
        @param request: HttpRequest
        @return: HttpResponse of homepage
    """
    return render(request,'cityhopper/homepage.html',add_user_type(request))

def finished_drives(request):
    """
        @param request: HttpRequest
        @return: HttpResponse of finished_drives of logged in driver
                Redirects to homepage if the user is not driver
    """
    try:
        driver=Driver.objects.get(pk=request.user.id)
    except Driver.DoesNotExist:
        return redirect('/cityhopper/homepage')
    driver=Driver.objects.get(pk=request.user.id)
    drives=Drive.objects.filter(idd=driver)

    comments=[]
    for drive in drives:
        c=Comment.objects.filter(iddr__iddr=drive.iddr)
        comments.extend(c)
    comments_struct=[]
    i=0
    for c in comments:
        comments_struct.append({
            "comment":c,
            "grade": [0 for i in range(c.grade)],
            "rest_grade": [0 for i in range(5-c.grade)],
            "ride":c.iddr,
            "id":"comment"+str(i)
        })
        i+=1
    finished_rides=[]
    for ride in drives:
        if ride.status=="FINISHED":
            finished_rides.append(ride)
    context={
        "user_":driver,
        "comments": comments_struct,
        "f_rides": finished_rides,
        "n":len(comments_struct)
    }
    context=add_user_type(request, context)
    return render(request, "cityhopper/finished_drives.html", context)


def active_drives(request):
    """
        @param request: HttpRequest
        @return: HttpResponse of active_drives of logged in driver
                Redirects to homepage if the user is not driver
    """
    try:
        driver=Driver.objects.get(pk=request.user.id)
    except Driver.DoesNotExist:
        return redirect('/cityhopper/homepage')
    driver=Driver.objects.get(pk=request.user.id)
    drives = Drive.objects.filter(idd=driver)

    active_rides = []
    for ride in drives:
        if ride.status == "UNFINISHED":
            rezerved=Rezervation.objects.filter(Q(iddr=ride)& Q(status="BOOKED"))
            active_rides.append({
                "ride":ride,
                "id1":ride.iddr*3,
                "id2":ride.iddr*3+1,
                "id3":ride.iddr*3+2,
                "rezerved":rezerved
            })

    context = {
        "user_": driver,
        "a_rides": active_rides
    }
    return render(request, "cityhopper/active_drives.html", add_user_type(request, context))


@login_required(login_url='login')
def get_my_profile(request):
    '''
        @param request: HttpRequest
        @return: HttpResponse of profile of logged in user
    '''
    context={
        "user_": request.user
    }
    if Moderator.objects.filter(pk=request.user.pk).exists():
        context['moderator_req'] = 'Approved moderator'
    elif Request.objects.filter(idu=request.user.pk).exists():
        context['moderator_req'] = 'Request for moderator on wait'
    return render(request, "cityhopper/profile.html", add_user_type(request, context))


@login_required(login_url='login')
def change_info(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of sign up page or
                HttpResponseRedirect to homepage
    """
    u = request.user
    form = ChangeUserInfoForm(data=request.POST or None,
                              email=u.email, first_name=u.first_name, last_name=u.last_name,
                              phone=u.phone)
    if form.is_valid():
        user = request.user
        user.email = form.cleaned_data['email']
        user.first_name = form.cleaned_data['first_name']
        user.last_name = form.cleaned_data['last_name']
        user.phone = form.cleaned_data['phone']
        user.save()
        return redirect('/cityhopper/profile')

    context = {
        'form': form
    }
    return render(request, 'cityhopper/change_info.html', add_user_type(request, context))


@login_required(login_url='login')
def change_password(request):
    """
    @param request: HttpRequest
    @return: HttpResponse of change password page
    """
    form = ChangePasswordForm(user=request.user, data=request.POST or None)
    comm = ''
    if form.is_valid():
        comm = "Successfully changed password"
        request.user.password = make_password(form.cleaned_data['new_password1'])
        request.user.save()
    context = {
        'form': form,
        'comm': comm
    }
    return render(request, 'cityhopper/change_password.html', add_user_type(request, context))


def get_profile(request,id):
    '''
        @param request: HttpRequest
        @param id: Integer
        @return: HttpResponse of profile of user with the specified id
    '''
    if id==request.user.id:
        return redirect('get_my_profile')
    u=User.objects.get(pk=id)
    try:
        driver = Driver.objects.get(pk=id)
        drives = Drive.objects.filter(idd=driver)
        comments = []
        for drive in drives:
            c = Comment.objects.filter(iddr__iddr=drive.iddr)
            comments.extend(c)

        comments_struct = []
        i = 0
        for c in comments:
            comments_struct.append({
                "comment": c,
                "grade": [0 for i in range(c.grade)],
                "rest_grade": [0 for i in range(5 - c.grade)],
                "ride": c.iddr,
                "id": "commentt" + str(i)
            })
            i += 1
        context = {
            'user_': User.objects.get(pk=id),
            'comments': comments_struct,
            'n': len(comments_struct),
            'user_dd': 1
        }

        return render(request, 'cityhopper/profile_others.html', context=add_user_type(request, context))
    except Driver.DoesNotExist:
        context={
            'user_':User.objects.get(pk=id)
        }
        return render(request,'cityhopper/profile_others.html',context=add_user_type(request,context))


def add_reservation(request : HttpRequest, id):
    """
    View is called when user wants to book a reservation
    @param request: HttpRequest
    @return: HttpResponseRedirect to the passenger homepage after search
    """
    if not Traveller.objects.filter(pk=request.user.pk).exists():
        return HttpResponseForbidden("You are not passenger")
    if request.method == 'POST':
        user = request.user
        traveller = Traveller.objects.get(idt=user.id)
        seats = request.GET.get('seats')
        price = request.GET.get('price')
        num_passengers = request.session.get('num_passengers')

        drive = Drive.objects.get(iddr=id)
        drive.available -= int(num_passengers)
        drive.save()

        reservation = Rezervation(seats=num_passengers, price=float(price)*int(num_passengers), idt=traveller, iddr = drive)
        reservation.save()

        messages.success(request, "Reservation booked successfully")
    return redirect("passenger_homepage_after_search")

def add_town(req: HttpRequest):
    """
        View is called when moderator wants to add a city.
        @param req: HttpRequest
        @return: HttpResponse of add city page or
                HttpResponseRedirect to the homepage
                if the user isn't moderator
    """
    try:
        m=Moderator.objects.get(pk=req.user.id)
    except Moderator.DoesNotExist:
        return redirect('/cityhopper/homepage')
    msg = None
    get_form = TownForm()
    context = {
        'form': get_form
    }
    if req.POST:
        post_form = TownForm(req.POST or None)
        if post_form.is_valid():
            town = Town(name=post_form.cleaned_data['lname'])
            roads = []
            data = list(post_form.cleaned_data.values())
            for k in post_form.cleaned_data.keys():
                if k[:4] == "cloc":
                    if data.count(post_form.cleaned_data[k]) == 1:
                        roads.append(Road(idto1=town,
                         idto2=post_form.cleaned_data[k],
                         length=post_form.cleaned_data['dist_'+k]))
                    else:
                        msg = {
                            "id": -1,
                            "msg": "Cannot add multiple roads that leads to the same city."
                        }
                        context['msg'] = msg
                        return render(req, "cityhopper/moderator_add.html", add_user_type(req, context))

            town.save()
            for r in roads:
                r.save()
            calc_shortest_paths(req)
        else:
            for field in post_form:
                print("Field Error:", field.name, field.errors)
            msg = {
                "id": -1,
                "msg": "Bad request: invalid parameters!"
            }
    context['msg'] = msg

    return render(req, "cityhopper/moderator_add.html", add_user_type(req, context))


def delete_town(req: HttpRequest):
    """
        View is called when moderator wants to delete a city.
        @param req: HttpRequest
        @return: HttpResponse of list of cities to edit page or
                HttpResponseRedirect to the homepage
                if the user isn't moderator
    """
    try:
        m=Moderator.objects.get(pk=req.user.id)
    except Moderator.DoesNotExist:
        return redirect('/cityhopper/homepage')
    msg=None
    if req.POST:
        post_form = TownDeleteForm(req.POST or None)
        if post_form.is_valid():
            town = post_form.cleaned_data["town_id"]
            try:
                # this if is unnecessary, but there are bug in django testing, atomic block isn't closed when exception is thrown
                if not Drive.objects.filter(Q(idto1=town.idto) | Q(idto2=town.idto)).exists():
                    town.delete()
                    calc_shortest_paths(req)
                else:
                    msg = {
                        "id": town.idto,
                        "msg": "Couldn't delete this city!"
                    }
            except:
                msg = {
                    "id": town.idto,
                    "msg": "Couldn't delete this city!"
                }
        else:
            for field in post_form:
                print("Field Error:", field.name, field.errors)
            msg = {
                "id": -1,
                "msg": "Bad request: invalid parameters!"
            }
    return edit_town(req, msg=msg)


def edit_town(req: HttpRequest, id: int = None, msg = None):
    """
        View is called when moderator wants to edit a city.
        @param req: HttpRequest
        @param id: id of a city that moderator wants to edit or
                None if moderator wants to get an list of existing cities
                to edit or delete
        @param msg: because this function is called from other functions
                this parrameter is added to represent the error message if
                the error happens in the calling function, this msg variable has
                information of where to put it in the page and the message itself,
                if it is None that means there is no error message
        @return: HttpResponse
                of list of cities to edit page
                if there is no id parameter provided

                or

                HttpResponse of particular city edit page
                if the id is provided

                or

                HttpResponseRedirect to the homepage
                if the user isn't moderator
    """
    try:
        m=Moderator.objects.get(pk=req.user.id)
    except Moderator.DoesNotExist:
        return redirect('/cityhopper/homepage')
    if id == None:
        get_form = TownForm()
        context = {
            'form': get_form,
            'towns': Town.objects.all().order_by("name"),
            'msg': msg
        }
        return render(req, "cityhopper/moderator_edit.html", add_user_type(req, context))

    if req.POST:
        town = Town.objects.filter(idto=id)
        if len(town) == 0:
            redirect('homepage')
        post_form = TownEditForm(id, req.POST or None)
        if post_form.is_valid():

            town = town[0]
            town.name = post_form.cleaned_data['edit_lname']
            town.save()

            for k in post_form.cleaned_data.keys():
                if k[:3] == "new":
                    e = Road.objects.filter(Q(idto1=town, idto2=post_form.cleaned_data[k]) |
                                            Q(idto1=post_form.cleaned_data[k], idto2=town))
                    if len(e) == 0:
                        Road(idto1=town, idto2=post_form.cleaned_data[k], length=post_form.cleaned_data["dist_" + k])\
                            .save()
                    else:
                        msg = {
                            "id": -1,
                            "msg": "Cannot add a road that already exists!"
                        }
                elif k[:3] == "del":
                    idro = k.split("_")[-1]
                    road = Road.objects.get(idro=int(idro))
                    if post_form.cleaned_data[k]:
                        road.delete()
                    else:
                        road.length = post_form.cleaned_data["dist_" + idro]
                        road.save()
            calc_shortest_paths(req)

        else:
            for field in post_form:
                print("Field Error:", field.name, field.errors)
            msg = {
                "id": -1,
                "msg": "Bad request: invalid parameters!"
            }
    post_form = TownEditForm(id)
    context = {
        'msg': msg,
        'form': post_form,
        'town': Town.objects.get(pk=id),
        'roads': Road.objects.filter(Q(idto1=id) | Q(idto2=id))
    }
    return render(req, 'cityhopper/edit_page.html', context=add_user_type(req, context))


def passanger_active(req: HttpRequest, form=None, msg=None):
    """
        View is called when passenger wants to view his/her active reservations.
        @param req: HttpRequest
        @param msg: because this function is called from other functions
                this parameter is added to represent the error message if
                the error happens in the calling function, this msg variable has
                information of where to put it in the page and the message itself,
                if it is None that means there is no error message
        @param form: because this function is called from other functions on different
                path, those functions may create a form in them so the form could be passed
                to this function (not needed parameter)
        @return: HttpResponse page with all user's active reservations or
                HttpResponseRedirect to the homepage
                if the user isn't passenger
    """
    try:
        Traveller.objects.get(pk=req.user.id)
    except Traveller.DoesNotExist:
        return redirect('/cityhopper/homepage')
    context = {
        'reservations': Rezervation.objects.filter(Q(idt=req.user.pk) & Q(status="BOOKED")),
        'edit_form': form or ReservationUpdateForm(id=req.user.pk, data=req.POST),
        'msg': msg
    }
    return render(req, "cityhopper/passenger_profile_private.html", add_user_type(req, context))


def cancel_reservation(req: HttpRequest):
    """
        View is called when passenger wants to cancel his/her active reservation.
        @param req: HttpRequest
        @return:
                HttpResponse with page with all user's active reservations with error message
                when error happens

                or

                HttpResponseRedirect to page with all user's active reservations if the
                cancel is successful

                or

                HttpResponseRedirect to the homepage
                if the user isn't passenger
    """
    try:
        Traveller.objects.get(pk=req.user.id)
    except Traveller.DoesNotExist:
        return redirect('/cityhopper/homepage')
    if req.POST:
        post_form = ReservationCancelForm(id=req.user.pk, data=req.POST)
        if post_form.is_valid():
            res=post_form.cleaned_data["id"]
            res.status = "CANCELLED"
            drive=Drive.objects.get(pk=res.iddr.iddr)
            drive.available+=res.seats
            res.save()
            drive.save()
        else:
            for field in post_form:
                print("Field Error:", field.name, field.errors)
            msg = {
                "id": -1,
                "msg": "Bad request: invalid parameters!"
            }
            return passanger_active(req, msg=msg)

    return redirect("active_ress")


def update_reservation(req: HttpRequest):
    """
        View is called when passenger wants to change the number of seats on his/her reservations.
        @param req: HttpRequest
        @return:
                HttpResponse with page with all user's active reservations with error message
                when error happens or with that message = None if there is no error.

                or

                HttpResponseRedirect to the homepage
                if the user isn't passenger
        """
    try:
        Traveller.objects.get(pk=req.user.id)
    except Traveller.DoesNotExist:
        return redirect('/cityhopper/homepage')
    msg = None
    if req.POST:
        post_form = ReservationUpdateForm(id=req.user.pk, data=req.POST)
        if post_form.is_valid():
            res: Rezervation = post_form.cleaned_data["id"]
            if res.iddr.available + res.seats < post_form.cleaned_data["new_seats"]:
                msg = {
                    "id": res.idrez,
                    "msg": "Number of seats to large!"
                }
            else:
                res.iddr.available += res.seats - post_form.cleaned_data["new_seats"]
                res.seats = post_form.cleaned_data["new_seats"]
                res.price = res.seats * res.iddr.price
                res.iddr.save()
                res.save()
        else:
            msg = {
                "id": -1,
                "msg": "Bad request: invalid parameters!"
            }

    return passanger_active(req, msg=msg)


def passenger_finished(req: HttpRequest, msg=None):
    """
        View is called when passenger wants to cancel his/her active reservation.
        @param req: HttpRequest
        @param msg: because this function is called from other functions
                this parameter is added to represent the error message if
                the error happens in the calling function, this msg variable has
                information of where to put it in the page and the message itself,
                if it is None that means there is no error message
        @return:
                HttpResponse with page with all user's finished reservations

                 or

                HttpResponseRedirect to the homepage
                if the user isn't passenger
        """
    try:
        Traveller.objects.get(pk=req.user.id)
    except Traveller.DoesNotExist:
        return redirect('/cityhopper/homepage')
    context = {
        "msg": msg,
        "reservations": Rezervation.objects.filter(Q(idt=req.user.pk) & Q(status="FINISHED"))
    }
    return render(req, "cityhopper/passenger_finished.html", add_user_type(req, context))


def add_comment(req: HttpRequest):
    """
        View is called when passenger wants to add a comment on his/her finished drive.
        @param req: HttpRequest
        @return:
                HttpResponse with page with all user's finished reservations

                or

                HttpResponseRedirect to the homepage
                if the user isn't passenger
    """
    try:
        Traveller.objects.get(pk=req.user.id)
    except Traveller.DoesNotExist:
        return redirect('/cityhopper/homepage')
    form = CommentForm(req.user.pk, req.POST or None)
    msg = None
    if form.is_valid():
        if form.cleaned_data["rat"] not in range(1, 6):
            msg = {
                "id": -1,
                "msg": "Bad request: invalid parameters!"
            }
        else:
            Comment(comment=form.cleaned_data["comment"], idt=Traveller.objects.get(idt=req.user.pk),
                    iddr=form.cleaned_data["id"].iddr, grade=form.cleaned_data["rat"]).save()

    else:
        msg = {
            "id": -1,
            "msg": "Bad request: invalid parameters!"
        }
    return passenger_finished(req, msg)


def passenger_cancelled(req: HttpRequest):
    """
        View is called when passenger wants to view his/her cancelled drives.
        @param req: HttpRequest
        @return:
                HttpResponse with page with all user's cancelled reservations

                or

                HttpResponseRedirect to the homepage
                if the user isn't passenger
    """
    try:
        Traveller.objects.get(pk=req.user.id)
    except Traveller.DoesNotExist:
        return redirect('/cityhopper/homepage')
    context = {
        "reservations": Rezervation.objects.filter(Q(idt=req.user.pk) & Q(status="CANCELLED"))
    }
    return render(req, "cityhopper/passenger_cancelled.html", add_user_type(req, context))
