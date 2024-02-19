import { Component } from '@angular/core';
import { AdminService } from "../services/admin.service";
import { User } from "../models/user";
import { ClassHelper } from "../models/classhelper";


@Component({
    selector: 'app-admin-statistics',
    templateUrl: './admin-statistics.component.html',
    styleUrl: './admin-statistics.component.css'
})
export class AdminStatisticsComponent {

    constructor(private admin_service: AdminService) { }

    ngOnInit(): void {
        this.admin_service.get_teachers().subscribe((data: User[]) => {
            this.update_teachers_per_subject_age(data);
            this.update_teacher_gender_distribution(data);
        });
        this.admin_service.get_students().subscribe((data: User[]) => {
            this.update_student_gender_distribution(data);
        });
        this.admin_service.get_classes().subscribe((data: ClassHelper[]) => {
            const classes_2023 = data.filter((c: ClassHelper) => new Date(c.start).getFullYear() === 2024);
            this.update_average_classes_per_day(classes_2023);
            this.update_busiest_teachers(classes_2023);
        });
    }

    teachers_per_subject_age_opt = {};
    teachers_gender_distribution_opt = {};
    students_gender_distribution_opt = {};
    average_classes_per_day_opt = {};
    busiest_teachers_opt = {};

    update_teachers_per_subject_age(teachers: User[]) {
        let subject_age_cnt = new Map<string, number>();

        teachers.forEach((t: User) => {
            t.subjects.forEach((s: string) => {
                t.ages.forEach((a: string) => {
                    let subject_age = s + " " + a;
                    if (subject_age_cnt.has(subject_age)) {
                        subject_age_cnt.set(subject_age, subject_age_cnt.get(subject_age)! + 1);
                    } else {
                        subject_age_cnt.set(subject_age, 1);
                    }
                });
            });
        });

        let chart_data = Array.from(subject_age_cnt).map((entry: [string, number]) => {
            return { label: entry[0], y: entry[1] };
        });

        this.teachers_per_subject_age_opt = {
            title: {
                text: "Number of teachers per subject per age group"
            },
            animationEnabled: true,
            data: [{
                type: "column",
                dataPoints: chart_data
            }]
        };
    }

    update_teacher_gender_distribution(teachers: User[]) {
        let gender_cnt = new Map<string, number>();

        teachers.forEach((t: User) => {
            if (gender_cnt.has(t.gender)) {
                gender_cnt.set(t.gender, gender_cnt.get(t.gender)! + 1);
            } else {
                gender_cnt.set(t.gender, 1);
            }
        });

        let chart_data = Array.from(gender_cnt).map((entry: [string, number]) => {
            return { y: entry[1] / teachers.length * 100, name: entry[0] };
        });

        this.teachers_gender_distribution_opt = {
            title: {
                text: "Gender distribution of teachers"
            },
            animationEnabled: true,
            data: [{
                type: "pie",
                startAngle: -90,
                indexLabel: "{name}: {y}",
                yValueFormatString: "#,###.##'%'",
                dataPoints: chart_data
            }]
        }
    }

    update_student_gender_distribution(students: User[]) {
        let gender_cnt = new Map<string, number>();

        students.forEach((s: User) => {
            if (gender_cnt.has(s.gender)) {
                gender_cnt.set(s.gender, gender_cnt.get(s.gender)! + 1);
            } else {
                gender_cnt.set(s.gender, 1);
            }
        });

        let chart_data = Array.from(gender_cnt).map((entry: [string, number]) => {
            return { y: entry[1] / students.length * 100, name: entry[0] };
        });

        this.students_gender_distribution_opt = {
            title: {
                text: "Gender distribution of students"
            },
            animationEnabled: true,
            data: [{
                type: "pie",
                startAngle: -90,
                indexLabel: "{name}: {y}",
                yValueFormatString: "#,###.##'%'",
                dataPoints: chart_data
            }]
        }
    }

    update_average_classes_per_day(classes: ClassHelper[]) {
        let weekday_classes = new Map<string, number>();

        days.forEach((day) => {
            weekday_classes.set(day, 0);
        })

        classes.forEach((c: ClassHelper) => {
            let day = days[new Date(c.start).getDay()];
            weekday_classes.set(day, weekday_classes.get(day)! + 1);
        });

        let chart_data = Array.from(weekday_classes).map((entry: [string, number]) => {
            return { label: entry[0], y: entry[1] / 52 };
        });

        this.average_classes_per_day_opt = {
            title: {
                text: "Average number of classes per weekday"
            },
            animationEnabled: true,
            data: [{
                type: "column",
                dataPoints: chart_data
            }]
        };
    }

    update_busiest_teachers(classes: ClassHelper[]) {
        let teacher_classes = new Map<string, number>();

        classes.forEach((c: ClassHelper) => {
            if (teacher_classes.has(c.teacher)) {
                teacher_classes.set(c.teacher, teacher_classes.get(c.teacher)! + 1);
            } else {
                teacher_classes.set(c.teacher, 1);
            }
        });

        let sorted_teacher_classes = Array.from(teacher_classes).sort((a: [string, number], b: [string, number]) => b[1] - a[1]);
        teacher_classes = new Map(sorted_teacher_classes.slice(0, 10));

        let teacher_classes_month = new Map<string, Map<string, number>>();
        teacher_classes.forEach((_, teacher: string) => {
            const empty_month = new Map<string, number>();
            months.forEach((m: string) => empty_month.set(m, 0));
            teacher_classes_month.set(teacher, empty_month);
        });

        classes.forEach((c: ClassHelper) => {
            let month = months[new Date(c.start).getMonth()];
            if (!teacher_classes.has(c.teacher)) return;

            let teacher_month = teacher_classes_month.get(c.teacher)!;
            teacher_month.set(month, teacher_month.get(month)! + 1);
        });

        let optsData: any = []

        teacher_classes_month.forEach((month_classes: Map<string, number>, teacher: string) => {
            let teacher_month_data = Array.from(month_classes).map((entry: [string, number]) => {
                return { label: entry[0], y: entry[1] };
            });

            optsData.push({
                type: "line",
                name: teacher,
                showInLegend: true,
                dataPoints: teacher_month_data
            });
        });

        this.busiest_teachers_opt = {
            title: {
                text: "Number of classes per teacher per month"
            },
            animationEnabled: true,
            data: optsData
        }
    }
}

const days = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
const months = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];

