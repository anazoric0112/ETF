import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';
import { Subject } from '../models/subject';
import { StudentService } from '../services/student.service';
import { TeacherService } from '../services/teacher.service';
import { ClassHelper } from '../models/classhelper';
import {EventColor, WeekViewHourSegment} from "calendar-utils";
import {CalendarEvent} from "angular-calendar";
import {addDays, addHours, addMinutes, endOfWeek} from "date-fns";
import {finalize, fromEvent, takeUntil} from "rxjs";

@Component({
    selector: 'app-student-home',
    templateUrl: './student-home.component.html',
    styleUrls: ['./student-home.component.css']
})
export class StudentHomeComponent implements OnInit {

    constructor(private router: Router, private service: CommonService,
        private studentService: StudentService,
        private teacherService: TeacherService,
        private cdr: ChangeDetectorRef) { }


    ngOnInit(): void {
        let user: User = JSON.parse(localStorage.getItem("logged")!);
        this.student = user;
        let user_age = user.year;
        // if (user.school_type != "Primary school") user_age += 8;

        this.service.get_student_number().subscribe(data => { this.n_students = data; });
        this.service.get_teacher_number().subscribe(data => { this.n_teachers = data; });
        this.service.get_classes_number_week().subscribe(data => { this.n_week = data; });
        this.service.get_classes_number_month().subscribe(data => { this.n_month = data; });
        this.service.get_subjects().subscribe(
            data => {
                this.subjects = data;
                for (let i = 0; i < this.subjects.length; i++) {
                    let subject = this.subjects[i];
                    let teachers = subject.teachers;

                    for (let j = 0; j < teachers.length; j++) {
                        let sh = new SubjectHelper();
                        sh.subject_name = subject.name;
                        this.service.get_user_by_username(teachers[j]).subscribe(
                            data => {
                                if (data == null) return;
                                if (!this.check_age(user_age, data.ages) || data.accepted != 1) return;
                                sh.teacher_name = data.name;
                                sh.teacher_surname = data.surname;
                                sh.teacher_username = data.username;
                                sh.teacher_avg_grade = Math.round(data.avg_grade * 100) / 100;
                                sh.teacher_num_grades = data.num_grades;
                                let stars_num = Math.floor(sh.teacher_avg_grade);
                                for (let i = 0; i < stars_num; i++)
                                    sh.stars.push("../../assets/star_full.png");

                                for (let i = stars_num; i < 5; i++)
                                    sh.stars.push("../../assets/star_empty.png");

                                this.subject_helpers.push(sh);
                            }
                        )

                    }
                }
                this.old = this.subject_helpers;
            });
    }

    student: User = new User();

    n_students: number = 0;
    n_teachers: number = 0;
    n_week: number = 0;
    n_month: number = 0;

    subjects: Subject[] = [];
    subject_helpers: SubjectHelper[] = [];
    old: SubjectHelper[] = [];

    search_name: string = "";
    search_surname: string = "";
    search_subject: string = "";
    search_grade: number = 0;
    search_num_grades: number = 0;

    datetime_picked: Date = new Date();
    double: boolean = false;
    subject_picked: string = "";
    teachers_of_subject_picked: User[] = [];
    teacher_picked: string = "";
    schedule_msg: string = "";
    description: string = "";

    show_calendar1: boolean = false;
    show_calendar2: boolean = false;
    show_available: boolean = true;
    show_statistic: boolean = true;


    sort(desc: number, col: number) {
        this.subject_helpers = this.subject_helpers.sort((a, b) => {
            if (col == 0 && desc == 0) return a.subject_name > b.subject_name ? 1 : -1;
            if (col == 1 && desc == 0) return a.teacher_name > b.teacher_name ? 1 : -1;
            if (col == 2 && desc == 0) return a.teacher_surname > b.teacher_surname ? 1 : -1;
            if (col == 3 && desc == 0) return a.teacher_avg_grade > b.teacher_avg_grade ? 1 : -1;
            if (col == 4 && desc == 0) return a.teacher_num_grades > b.teacher_num_grades ? 1 : -1;

            if (col == 0 && desc == 1) return a.subject_name < b.subject_name ? 1 : -1;
            if (col == 1 && desc == 1) return a.teacher_name < b.teacher_name ? 1 : -1;
            if (col == 2 && desc == 1) return a.teacher_surname < b.teacher_surname ? 1 : -1;
            if (col == 3 && desc == 1) return a.teacher_avg_grade < b.teacher_avg_grade ? 1 : -1;
            if (col == 4 && desc == 1) return a.teacher_num_grades < b.teacher_num_grades ? 1 : -1;
            return 0;
        });
    }

    search(col: number) {
        this.subject_helpers = this.old;
        this.subject_helpers = this.subject_helpers.filter((a) => {
            if (col == 0) return a.subject_name.includes(this.search_subject);
            if (col == 1) return a.teacher_name.includes(this.search_name);
            if (col == 2) return a.teacher_surname.includes(this.search_surname);
            if (col == 3) return a.teacher_avg_grade >= this.search_grade;
            if (col == 4) return a.teacher_num_grades >= this.search_num_grades;
            return -1;
        })
    }

    check_age(a: number, ages: string[]) {
        if (this.student.school_type != "Primary school") a += 8;
        if (a <= 4 && ages.includes("1-4")) return true;
        else if (a <= 8 && ages.includes("5-8")) return true;
        else if (a <= 12 && ages.includes("9-12")) return true;
        return false;
    }

    pick_subject() {
        this.schedule_msg = "";
        this.studentService.get_teachers_on_subject(this.subject_picked).subscribe(
            data => {
                this.teachers_of_subject_picked = [];
                data.forEach(element => {
                    if (this.check_age(this.student.year, element.ages)) this.teachers_of_subject_picked.push(element);
                });
            }
        )
    }

    check_date() {
        let date = new Date(this.datetime_picked);

        if (date.getMinutes() != 0 && date.getMinutes() != 30) {
            this.schedule_msg = "Class must begin at full hour or half an hour";
            return;
        } else if ((new Date()).getTime() > date.getTime()) {
            this.schedule_msg = "Picked time must be in the future"
        } else {
            let start = new Date(this.datetime_picked);
            let end = new Date(start.getTime() + 60 * 60 * 1000 * (this.double ? 2 : 1));

            if (end.getHours() > 18 || (end.getHours() == 18 && end.getMinutes() != 0) || start.getHours() < 10) {
                this.schedule_msg = "Work time is 10:00-18:00";
                return;
            }

            this.schedule_msg = "";
            if (this.teacher_picked != "") {
                this.check_teacher_availability();
            }
        }

    }

    check_teacher_availability() {
        this.schedule_msg = "";
        this.teacherService.get_teacher_not_available(this.teacher_picked).subscribe(
            data => {
                data.forEach(element => {
                    if (this.check_overlap([element.slice(0, 29), element.slice(30, 60)], this.datetime_picked, this.double)) {
                        this.schedule_msg = "Teacher is unavailable at chosen time";
                        return;
                    }
                });
            }
        )
        this.teacherService.get_teacher_booked(this.teacher_picked).subscribe(
            data => {
                data.forEach(element => {
                    if (this.check_overlap([element.slice(0, 29), element.slice(30, 60)], this.datetime_picked, this.double)) {
                        this.schedule_msg = "Teacher is unavailable at chosen time";
                        return;
                    }
                });
            }
        )
        this.get_classes()
    }

    check_overlap(interval: string[], desired: Date, double: boolean) {
        let start = (new Date(desired)).toUTCString();
        let end = (new Date((new Date(desired)).getTime() + 60 * 60 * 1000 * (this.double ? 2 : 1))).toUTCString();
        let istart = interval[0], iend = interval[1];
        if (start >= iend || end <= istart) return false;
        else return true;
    }

    schedule() {
        this.check_date();
        if (this.schedule_msg != "") return;

        let start = new Date(this.datetime_picked);
        let end = new Date(start.getTime() + 60 * 60 * 1000 * (this.double ? 2 : 1));

        let c = new ClassHelper();
        c.student = this.student.username;
        c.teacher = this.teacher_picked;
        c.subject = this.subject_picked;
        c.student_description = this.description;
        c.double = this.double;
        c.status = 0;
        c.start = start;
        c.end = end;
        c.link = c.student + c.teacher + c.subject + this.datetime_picked;
        this.studentService.request_class(c).subscribe(
            data => {
                if (data == 1) {
                    this.schedule_msg = "Requested successfully";
                }
            }
        );
    }

    go_to_profile(user: string) {
        localStorage.setItem("user_profile", user);
        this.router.navigate(["profile_t"]);
    }

    // Calendar
    viewDate: Date = new Date();
    events: CalendarEvent[] = [];
    dragToCreateActive: boolean = false;

    start_drag_to_create(
        segment: WeekViewHourSegment,
        mouseDownEvent: MouseEvent,
        segmentElement: HTMLElement
    ) {
        const dragToSelectEvent: CalendarEvent = {
            id: this.events.length,
            title: 'New event',
            start: segment.date,
            end: addHours(segment.date, 0.5),
            meta: {
                tmpEvent: true,
            },
        };
        this.events = [...this.events, dragToSelectEvent];
        const segmentPosition = segmentElement.getBoundingClientRect();
        this.dragToCreateActive = true;
        const endOfView = endOfWeek(this.viewDate, {
            weekStartsOn: 0,
        });

        fromEvent(document, 'mousemove')
            .pipe(
                finalize(() => {
                    this.schedule_for_date(dragToSelectEvent.start, dragToSelectEvent.end!);
                    delete dragToSelectEvent.meta.tmpEvent;
                    this.events.pop();
                    this.dragToCreateActive = false;
                    this.get_classes();
                    this.refresh();
                }),
                takeUntil(fromEvent(document, 'mouseup'))
            ) // @ts-ignore
            .subscribe((mouseMoveEvent: MouseEvent) => {
                const minutesDiff = ceilToNearest(
                    mouseMoveEvent.clientY - segmentPosition.top,
                    30
                );

                const daysDiff =
                    floorToNearest(
                        mouseMoveEvent.clientX - segmentPosition.left,
                        segmentPosition.width
                    ) / segmentPosition.width;

                const newEnd = addDays(addMinutes(segment.date, minutesDiff), daysDiff);
                if (newEnd > segment.date && newEnd < endOfView) {
                    dragToSelectEvent.end = newEnd;
                }
                this.refresh();
            });
    }

    get_classes() {
        this.teacherService.get_all_classes_of_teacher(this.teacher_picked).subscribe(
            data => {
                this.events= data.filter(c => c.status != -1)
                    .map((c: ClassHelper) => {
                        let color;
                        if (c.student != this.student.username) {
                            switch (c.status) {
                                case -2:
                                    color = colors['red'];
                                    break;
                                case 0:
                                    color = colors['yellow'];
                                    break;
                                case 1:
                                    color = colors['red'];
                                    break;
                            }
                        }

                        return {
                            start: new Date(c.start),
                            end: new Date(c.end),
                            title: c.subject,
                            color: color,
                        }
                    });
            }
        )
    }

    refresh() {
        this.events = [...this.events];
        this.cdr.detectChanges();
    }

    schedule_for_date(start: Date, end: Date) {
        this.check_duration(start, end);
        if (this.schedule_msg != "") return;

        const double = end.getTime() - start.getTime() == 60 * 60 * 1000 * 2;

        let c = new ClassHelper();
        c.student = this.student.username;
        c.teacher = this.teacher_picked;
        c.subject = this.subject_picked;
        c.student_description = this.description;
        c.double = double;
        c.status = 0;
        c.start = start;
        c.end = end;
        c.link = c.student + c.teacher + c.subject + start;
        this.studentService.request_class(c).subscribe(
            data => {
                if (data == 1) {
                    this.schedule_msg = "Requested successfully";
                }
                this.get_classes()
            }
        );
    }

    check_duration(start: Date, end: Date) {
        if ((new Date()).getTime() > start.getTime()) {
            this.schedule_msg = "Picked time must be in the future"
            return
        }

        if (end.getTime() - start.getTime() != 60 * 60 * 1000 && end.getTime() - start.getTime() != 60 * 60 * 1000 * 2){
            this.schedule_msg = "Class must last 1 or 2 hours";
            return;
        }

        this.schedule_msg = "";
    }
}

class SubjectHelper {
    subject_name: string = "";
    teacher_name: string = "";
    teacher_surname: string = "";
    teacher_avg_grade: number = 0;
    teacher_num_grades: number = 0;
    teacher_username: string = "";
    stars: string[] = [];
}

function floorToNearest(amount: number, precision: number) {
    return Math.floor(amount / precision) * precision;
}

function ceilToNearest(amount: number, precision: number) {
    return Math.ceil(amount / precision) * precision;
}

const colors: Record<string, EventColor> = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3',
    },
    green: {
        primary: '#1bcc1e',
        secondary: '#d2fca8',
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FDF1BA',
    },
};
