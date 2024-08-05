# Authors : Petar Vasiljevic 0007/2020
#           Ilija Obradovic  0049/2020
from django.contrib import admin
from django.urls import path
from .views import *

urlpatterns = [
    path('', get_homepage, name='index'),
    path('results', show_drives, name='passenger_homepage_after_search'),
    path('search', search_drives, name='passenger_homepage'),
    path('login', login_req, name='login'),
    path('logout', logout_req, name='logout'),
    path('signup', signup, name='signup'),

    path('create_drive',create_drive,name='driver_homepage'),
    path('finished_drives',finished_drives,name='finished_drives'),
    path('active_drives',active_drives,name='active_drives'),
    path('cancel_drive/<int:id>',cancel_drive,name='cancel_drive'),
    path('finish_drive/<int:id>',finish_drive,name='finish_drive'),

    path('moderator_add', add_town, name='add_town'),
    path('delete_town', delete_town, name='delete_town'),
    path('edit_town/<int:id>', edit_town, name='edit_town'),
    path('moderator_edit', edit_town, name='edit_town_get'),
    path('my_reservations', passanger_active, name='active_ress'),
    path('cancel_res', cancel_reservation, name='cancel_res'),
    path('update_res', update_reservation, name='update_res'),
    path('my_finished_reservations', passenger_finished, name='finished_ress'),
    path('add_comment', add_comment,  name='add_comment'),
    path('my_cancelled_reservations', passenger_cancelled, name='canc_ress'),

    path('admin_homepage', admin_homepage, name='admin_homepage'),
    path('admin_profile', admin_profile, name='admin_profile'),
    path('passenger_profile/<int:id>', passenger_profile, name='passenger_profile'),
    path('passenger_profile', passenger_profile_private, name='passenger_profile_private'),

    path('homepage',get_homepage,name='homepage'),

    path('profile',get_my_profile,name='get_my_profile'),
    path('change_info', change_info, name='change_info'),
    path('profile/<int:id>',get_profile,name='get_profile'),
    path('add_reservation/<int:id>', add_reservation, name='add_reservation'),
    path('change_password', change_password, name='change_password'),
    path('accept_moderator/<int:mod_id>', accept_moderator, name='accept_moderator'),
    path('reject_moderator/<int:mod_id>', reject_moderator, name='reject_moderator'),
]