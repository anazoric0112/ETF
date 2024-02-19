import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { User } from '../models/user';
import { ClassHelper } from '../models/classhelper';
import { TeacherService } from '../services/teacher.service';
import { CalendarEvent, CalendarEventTitleFormatter } from "angular-calendar";
import { addDays, addHours, addMinutes, endOfWeek, startOfDay, subDays, subHours } from "date-fns";
import { EventColor } from "calendar-utils";
import { WeekViewHourSegment } from 'calendar-utils';
import { finalize, fromEvent, takeUntil } from "rxjs";

function floorToNearest(amount: number, precision: number) {
    return Math.floor(amount / precision) * precision;
}

function ceilToNearest(amount: number, precision: number) {
    return Math.ceil(amount / precision) * precision;
}


@Component({
    selector: 'app-teacher-home',
    templateUrl: './teacher-home.component.html',
    styleUrls: ['./teacher-home.component.css']
})
export class TeacherHomeComponent implements OnInit {

    constructor(private router: Router, private service: CommonService,
        private teacherService: TeacherService, private cdr: ChangeDetectorRef) { }

    ngOnInit(): void {
        this.init();
    }

    init() {

        this.teacher = JSON.parse(localStorage.getItem("logged")!);
        this.teacherService.classes_incoming(this.teacher.username).subscribe(
            data => {
                this.classes_incoming = data.sort((a, b) => {
                    return a.start < b.start ? 1 : -1;
                })
                this.classes_incoming_to_show = this.classes_incoming.map((e) => this.make_row(e));
                this.old = this.classes_incoming_to_show;
                this.update_show(5);
            }
        )

        this.teacherService.get_class_requests(this.teacher.username).subscribe(
            data => {
                data = data.sort((a, b) => {
                    return a.start < b.start ? 1 : -1;
                })
                this.requests = data.map(el => this.make_row(el))
            }
        )

        this.get_classes();
    }

    teacher: User = new User();
    classes_incoming: ClassHelper[] = [];
    classes_incoming_to_show: ClassRow[] = [];
    old: ClassRow[] = [];
    requests: ClassRow[] = [];
    show: number = 5; // 5/10/-1(all)

    show_incoming: boolean = true;
    show_requests: boolean = false;
    show_calendar: boolean = false;

    cancel(c: ClassRow) {
        this.teacherService.cancel_class(c.link, this.teacher.username, c.comment).subscribe(
            data => {
                this.init();
            }
        )
    }

    accept(c: ClassRow) {
        c.msg = "";
        this.teacherService.accept_class(c.link, this.teacher.username, c.comment).subscribe(
            data => {
                this.init();
            }
        )
    }

    decline(c: ClassRow) {
        c.msg = ""
        if (c.comment == "") {
            c.msg = "Please tell us why";
            return;
        }
        this.teacherService.decline_class(c.link, this.teacher.username, c.comment).subscribe(
            data => {
                this.init();
            }
        )
    }

    update_show(num: number) {
        if (num == -1) num = this.old.length;
        this.classes_incoming_to_show = [];
        for (let i = 0; i < num && i < this.old.length; i++) {
            this.classes_incoming_to_show.push(this.old[i]);
        }
    }

    make_row(cls: ClassHelper) {
        let row = new ClassRow();

        row.start = new Date(cls.start).toUTCString();
        row.start = row.start.slice(5, 25);
        row.end = new Date(cls.end).toUTCString();
        row.end = row.end.slice(5, 25);

        row.link = cls.link;
        row.subject = cls.subject;
        row.student_description = cls.student_description;

        let now = (new Date()).getTime()
        let class_start = new Date(cls.start).getTime();

        if (class_start - now <= 1000 * 60 * 15) row.show_link = true;
        if (class_start - now >= 1000 * 60 * 40) row.can_cancel = true;

        this.service.get_user_by_username(cls.student).subscribe(
            data => { row.student = data; }
        )
        return row;
    }

    join(link: string) {
        localStorage.setItem("link", link);
        this.router.navigate(["jitsi"]);
    }

    go_to_profile(user: string) {
        localStorage.setItem("user_profile", user);
        this.router.navigate(["profile_s"]);
    }

    // Calendar
    viewDate: Date = new Date();
    dragToCreateActive: boolean = false;
    events: CalendarEvent[] = [];

    add_busy_time(start: Date, end: Date) {
        this.teacherService.add_busy_time(this.teacher.username, start, end).subscribe(()=> {})
    }

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
                    this.add_busy_time(dragToSelectEvent.start, dragToSelectEvent.end!);
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
        this.teacherService.get_all_classes_of_teacher(this.teacher.username).subscribe(
            data => {
                this.events= data.filter(c => c.status != -1)
                    .map((c: ClassHelper) => {
                        let color;
                        switch (c.status) {
                            case -2: color = colors['red']; break;
                            case 0: color = colors['yellow']; break;
                            case 1: color = colors['green']; break;
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
}

class ClassRow {
    start: string = "";
    end: string = "";
    student: User = new User();
    link: string = "";
    show_link: boolean = false;
    subject: string = "";
    student_description: string = "";

    cancelling: boolean = false;
    comment: string = "";
    can_cancel: boolean = false;
    msg: string = "";
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
