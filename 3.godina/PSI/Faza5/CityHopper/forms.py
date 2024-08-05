# Authors : Petar Vasiljevic 0007/2020
#           Ana Zoric        0009/2020
#           Savo Cvijetic    0054/2020
#           Ilija Obradovic  0049/2020

from django.core.exceptions import ValidationError
from django.forms import forms, CharField, DateField, TextInput, IntegerField, ModelChoiceField
from django.contrib.auth.forms import UserCreationForm, AuthenticationForm
from django.utils.safestring import mark_safe

from .models import *
from django import forms

from django.forms import forms, CharField, DateField, TextInput, IntegerField, DateTimeField
from django.core.validators import MinValueValidator
from datetime import datetime
from django.contrib.auth.forms import UserCreationForm, PasswordChangeForm
from .models import *
from django import forms
from django.db.models import Q

class SearchDrivesForm(forms.Form):
    """
        A class that represents form for searching drives

        Attributes
        ----------
        src : Town
            a town from which the drive starts
        dst : Town
            a town to which the drive goes
        date : DateTime
            date and time when the user wants to have a drive
        num_passengers : Integer
            number of passengers needed in a drive
    """
    src = ModelChoiceField(label='', queryset=Town.objects.all().order_by("name"),
                           widget=forms.Select(attrs={'class': 'input_field input_passenger'}), empty_label="From",
                           required=True)
    dst = ModelChoiceField(label='', queryset=Town.objects.all().order_by("name"),
                           widget=forms.Select(attrs={'class': 'input_field input_passenger'}), empty_label="To",
                           required=True)
    date = DateField(label='', widget=TextInput(attrs={'class': 'input_field input_passenger', 'type': 'date'}))
    num_passengers = IntegerField(label='', widget=TextInput(attrs={'class': 'input_field input_passenger','placeholder':'Seats'} ),
                                  validators=[MinValueValidator(1)])


class CreateDriveForm(forms.Form):
    """
        A class that represents form for creating a drive

        Attributes
        ----------
        src : Town
            a town from which the drive starts
        dst : Town
            a town to which the drive goes
        num_passengers : Integer
            number of passengers allowed in drive
        price : Integer
            price in $ per passenger
        date_start : DateTime
            date and time when the drive occurs
        date_end : DateTime
            estimated date and time of arrival
    """
    src = ModelChoiceField(label='',queryset=Town.objects.all().order_by("name"),
                    widget=forms.Select( attrs={'class': 'input_field fit_cont'}), empty_label="From",  required=True)
    dst = ModelChoiceField(label='',queryset=Town.objects.all().order_by("name"),
                    widget=forms.Select( attrs={'class': 'input_field fit_cont'}), empty_label="To",  required=True)
    num_passengers = IntegerField(label='', widget=TextInput(attrs={'class': 'input_field fit_cont', 'placeholder':'Free seats'}), validators=[MinValueValidator(1)])
    price = IntegerField(label='', widget=TextInput(attrs={'class': 'input_field fit_cont', 'placeholder': 'Price per person in $'}),validators=[MinValueValidator(1)])
    date_start = DateTimeField(label='', widget=TextInput(attrs={'class': 'form-control input_field fit_cont', 'type': 'datetime-local', 'placeholder':'Start date and time'}))
    date_end = DateTimeField(label='', widget=TextInput(attrs={'class': 'form-control input_field fit_cont', 'type': 'datetime-local', 'placeholder':'Arrival date and time'}), required=False)


class CustomUserCreationForm(UserCreationForm):
    """
    Form for sign up

        Attributes
            gender_choices : possible choices for gender
            user_choices : possible choices for user type
            gender : ChoiceField which will displayed for choosing gender
            passenger : BooleanField, checkbox for passenger role
            driver : BooleanField, checkbox for driver role
            moderator : BooleanField, checkbox for moderator role
    """

    def __init__(self, *args, **kwargs):
        """
        Initialization of form, stylizes the form
        @param args: param that will be passed to parent init function
        @param kwargs: param that will be passed to parent init function
        """
        super().__init__(*args, **kwargs)
        fields_names = ['username', 'email', 'first_name', 'last_name', 'phone', 'password1', 'password2']
        fields_placeholders = ['Username', 'Email', 'First name', 'Last name', 'Phone', 'Password', 'Repeat password']
        for fn in fields_names:
            self.fields[fn].widget.attrs['class'] = 'input_field'
        for i in range(len(fields_names)):
            self.fields[fields_names[i]].label = False
            self.fields[fields_names[i]].widget.attrs['placeholder'] = fields_placeholders[i]
        self.fields['password2'].help_text = mark_safe('Enter the same password as before, for verification.<br \>'
                                                       'Choose type of user:<br \>')
        self.fields['image'].required = False

    gender_choices = [
        ('1', 'Male'),
        ('2', 'Female'),
    ]

    passenger = forms.BooleanField(required=False)
    driver = forms.BooleanField(required=False)
    moderator = forms.BooleanField(required=False)
    passenger.widget.attrs['class'] = 'input_field'
    driver.widget.attrs['class'] = 'input_field'
    moderator.widget.attrs['class'] = 'input_field'


    gender = forms.ChoiceField(
        widget=forms.RadioSelect,
        choices=gender_choices
    )

    class Meta:
        model = User
        fields = ['username', 'email', 'first_name', 'last_name', 'phone', 'image']


class CustomAuthenticationForm(AuthenticationForm):
    """
    Form for log in
    """
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.fields['username'].widget.attrs['class'] = 'input_field'
        self.fields['password'].widget.attrs['class'] = 'input_field'
        self.fields['username'].widget.attrs['placeholder'] = 'Username'
        self.fields['password'].widget.attrs['placeholder'] = 'Password'
        self.fields['username'].label = False
        self.fields['password'].label = False


class AdminApproveForm(forms.Form):
    """
    Form for approving and reject moderator request by admin

        Attributes
            button_action : hidden input
    """
    button_action = forms.CharField(widget=forms.HiddenInput(), required=False)


class ChangeUserInfoForm(forms.Form):
    """
    Form for changing user info

        Attributes
            username : input for username
            email : input for e-mail
            first_name : input for first name
            last_name : input for last name
            phone : input for phone number
    """

    email = forms.CharField()
    first_name = forms.CharField()
    last_name = forms.CharField()
    phone = forms.CharField()

    def __init__(self, email, first_name, last_name, phone, *args, **kwargs):
        super().__init__(*args, **kwargs)
        fields_names = ['email', 'first_name', 'last_name', 'phone']
        fields_placeholders = ['Email', 'First name', 'Last name', 'Phone']
        fields_val = [email, first_name, last_name, phone]
        for i in range(len(fields_names)):
            fn = fields_names[i]
            fp = fields_placeholders[i]
            val = fields_val[i]
            self.fields[fn].widget.attrs['class'] = 'input_field'
            self.fields[fn].widget.attrs['placeholder'] = fp
            self.fields[fn].widget.attrs['value'] = val


class ChangePasswordForm(PasswordChangeForm):
    """
    Form for changing password
    """
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        fields_names = ['old_password', 'new_password1', 'new_password2']
        fields_placeholders = ['Old password', 'New password', 'Repeat new password']
        for i in range(len(fields_names)):
            fn = fields_names[i]
            fp = fields_placeholders[i]
            self.fields[fn].widget.attrs['class'] = 'input_field'
            self.fields[fn].widget.attrs['placeholder'] = fp


class TownForm(forms.Form):
    """
    Form for adding new town.
        Attributes
        ----------
        lname : Text
            name for new town

        clocX: Town
            represents the town to which we are adding the road from
            the new town

            here X represents number 0 or greater than 0,
            in this form there could be any number of this fields

        dist_clocX: Integer
            represents the distance length of road to the town with
            input name clocX
    """
    lname = forms.CharField(max_length=128,
                            required=True,
                            widget=forms.TextInput(
                                attrs={'class': 'input_field',
                                       'placeholder': 'Location Name Here'}))
    def __init__(self, data=None):
        """
        This function is the constructor of the TownForm class.
        This function creates custom number of fields for this form.

        @param data: this param is passed to super().__init__
            also this represents a dictionary of input values to this form
            from the Request parameters
        """
        super().__init__(data)
        keys = ["cloc0"]
        if data:
            keys = data.keys()
        towns = Town.objects.all().order_by("name")
        print(towns[0])
        for k in keys:
            if k[:4] == "cloc":
                self.fields[k] = forms.ModelChoiceField(
                    queryset=towns,
                    widget=forms.Select(
                        attrs={'class': 'input_field'}),
                    empty_label=None,
                    required=True)
                self.fields['dist_'+k] = forms.IntegerField(
                    widget=forms.NumberInput(
                        attrs={'class': 'input_field',
                               'style': 'width: 100%',
                               'placeholder': '120'}))

class TownEditForm(forms.Form):
    """
        Form for editin existing town.
            Attributes
            ----------
            edit_lname : Text
                new name for existing town

            del_ID: Boolean
                true if the road should be removed, false if it is not removed

                here ID is the id in the database of the road to be removed

            dist_ID: Integer
                represents the new length of the road with id=ID

                here ID is the id in the database of the road to be changed

            new_road_X: Town
                represents town
        """
    edit_lname = forms.CharField(max_length=128,
                            required=True,
                            widget=forms.TextInput(
                                attrs={'class': 'input_field',
                                       'placeholder': 'Location Name Here'}))
    def __init__(self, id, data=None):
        """
            This function is the constructor of the TownEditForm class.
            This function creates custom number of fields for this form.

            @param id: this is the id of the town to be edited,
                del_ID fields as well as dist_ID fields int this form
                depend on the roads connected with this town

            @param data: this param is passed to super().__init__
                also this represents a dictionary of input values to this form
                from the Request parameters
                """
        keys = []
        if data == None:
            data = {"edit_lname": Town.objects.get(idto=id).name}
        else:
            keys = data.keys()
        super().__init__(data=data)

        roads = Road.objects.filter(Q(idto1=id) | Q(idto2=id))

        self.roads = roads

        for r in roads:
            otown = r.idto2.name
            if r.idto2 == id:
                otown = r.idto1.name
            self.fields['dist_'+str(r.idro)] = forms.IntegerField(
                    widget=forms.NumberInput(
                        attrs={'class': 'input_field',
                               'style': 'width: 100%',
                               'placeholder': '120'}),
                    required=True)
            self.fields['del_'+str(r.idro)]= forms.BooleanField(
                widget=forms.CheckboxInput(),
                label="Delete road to " + otown + "(" + str(r.length) + " km)",
                required=False,
                initial=False)

        for k in keys:
            if k[:8] == "new_road":
                self.fields[k] = forms.ModelChoiceField(
                    queryset=Town.objects.all().order_by("name"),
                    widget=forms.Select(),
                    empty_label=None,
                    required=True)
                self.fields['dist_' + k] = forms.IntegerField(
                    widget=forms.NumberInput(
                        attrs={'class': 'input_field',
                               'style': 'width: 100%',
                               'placeholder': '120'}))

class TownDeleteForm(forms.Form):
    """
        Form for deleting existing town.
            Attributes
            ----------
            town_id : Town
                Town to be deleted
    """
    town_id = forms.ModelChoiceField(queryset=Town.objects.all(),required=True)


class ReservationCancelForm(forms.Form):
    """
        Form for canceling reservations.
            Attributes
            ----------
            id : Rezervastion
                Reservaton to be canceled


    """
    def __init__(self, id, data=None):
        """
        This function is the constructor of the TownEditForm class.

        @param id: the queryset used in the id field is
            depending on this parameter, it represents the id of an user
        @param data: this param is passed to super().__init__
                also this represents a dictionary of input values to this form
                from the Request parameters
        """
        super().__init__(data=data)
        self.fields["id"] = forms.ModelChoiceField(
                    queryset=Rezervation.objects.filter(Q(idt=id) & Q(status="BOOKED")),
                    required=True)

class ReservationUpdateForm(forms.Form):
    """
        Form for editing reservations.
            Attributes
            ----------
            id : Rezervastion
                Reservaton to be edited
            new_seats: Integer
                New number of seats in the reservation.
    """
    def __init__(self, id, data=None):
        """
            This function is the constructor of the TownEditForm class.

            @param id: the queryset used in the id field is
                depending on this parameter, it represents the id of an user
            @param data: this param is passed to super().__init__
                    also this represents a dictionary of input values to this form
                    from the Request parameters
        """
        super().__init__(data=data)
        self.fields["id"] = forms.ModelChoiceField(
                    queryset=Rezervation.objects.filter(Q(idt=id) & Q(status="BOOKED")),
                    required=True,
                    )
        self.fields["new_seats"] = forms.IntegerField(required=True,
                                                      widget=forms.NumberInput(attrs={'class': 'input_field'}))


class CommentForm(forms.Form):
    """
        Form for adding comment on finished reservation.
            Attributes
            ----------
                commend: charField
                    Comment text
                rat: Integer
                    Rating given by the user
                id: Rezervation
                    The rezervation chosen by the user
        """
    comment = forms.CharField(required=True)
    rat = forms.IntegerField(required=True)

    def __init__(self, id, data=None):
        """
            This function is the constructor of the TownEditForm class.

            @param id: the queryset used in the id field is
                depending on this parameter, it represents the id of an user
            @param data: this param is passed to super().__init__
                    also this represents a dictionary of input values to this form
                    from the Request parameters
            """
        super().__init__(data=data)
        self.fields["id"] = forms.ModelChoiceField(queryset=Rezervation.objects.filter(Q(idt=id) & Q(status="FINISHED")), required=True)
