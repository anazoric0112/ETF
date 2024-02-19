import * as express from "express";
import Class from "../models/class";
import News from "../models/news";
import User from "../models/user";

export class TeacherController {

    classes_incoming = (req: express.Request, res: express.Response) => {
        let now = new Date();
        let three_days = new Date(new Date().getTime() + (3 * 24 * 60 * 60 * 1000));
        Class.find(
            {
                teacher: req.body.username,
                end: { $gt: now, $lt: three_days },
                status: 1
            })
            .then((classes) => {
                res.json(classes);
            })
            .catch((err) => console.log(err));
    };

    get_availability = (req: express.Request, res: express.Response) => {
        //to do 
    };

    //for accept , decline and cancel
    //bice poslato -2 ako je canceled, -1 ako je declined, 1 ako je accepted
    set_class_status = (req: express.Request, res: express.Response, status: number) => {
        let status_to_set = status;
        if (status_to_set == -2) status_to_set = -1;

        Class.findOneAndUpdate({ link: req.body.link }, { status: status_to_set })
            .then((c) => {
                let msg = "";
                let date = c?.start;

                if (status == -1) msg = "Class has been declined.";
                if (status == -2) msg = "Class has been canceled.";
                if (status == 1) msg = "Class has been accepted.";

                msg = msg + " Date and time of class: " + (new Date(date!)).toUTCString().slice(5, 25);
                msg = msg + ". Teacher: " + req.body.username + ". ";
                if (req.body.comment != "") {
                    msg = msg + "Additional comment: " + req.body.comment + ".";
                }

                News.find({}).sort({ id: -1 }).limit(1).then((n) => {
                    let news_id = 0;
                    if (n.length > 0) {
                        news_id = n[0].id + 1;
                    }

                    let news_body = {
                        description: msg,
                        seen: false,
                        student: c?.student,
                        teacher: c?.teacher,
                        time: new Date(),
                        id: news_id
                    }

                    let news = new News(news_body);
                    News.insertMany(news)
                        .then((news) => {
                            res.json(1);
                        })
                        .catch((err) => console.log(err));
                })
            })
            .catch((err) => console.log(err));
    };

    class_requests = (req: express.Request, res: express.Response) => {
        Class.find({ teacher: req.body.username, status: 0 })
            .then((c) => {
                res.json(c);
            })
            .catch((err) => console.log(err));
    };

    students = (req: express.Request, res: express.Response) => {
        let now = new Date();
        Class.find(
            {
                teacher: req.body.username,
                status: 1,
                end: { $gt: now }
            })
            .then((classes) => {
                let students: any[] = [];
                classes.forEach(element => {
                    students.push(element.student);
                });
                res.json(students);
            })
            .catch((err) => console.log(err));
    };

    get_teacher_classes_held = (req: express.Request, res: express.Response) => {
        let now = new Date();
        Class.find(
            {
                teacher: req.body.username,
                status: 1,
                end: { $lt: now }
            })
            .then((classes) => {
                res.json(classes);
            })
            .catch((err) => console.log(err));
    };

    leave_comment = (req: express.Request, res: express.Response) => {
        Class.findOneAndUpdate(
            { link: req.body.link },
            { grade_t: req.body.grade, comment_t: req.body.comment })
            .then((c) => {
                let avg_grade = 0, num_grades = 0;
                User.findOne({ username: req.body.student }).then(
                    (t) => {
                        if (t == null) return;
                        avg_grade = t.avg_grade!;
                        num_grades = t.num_grades!;

                        avg_grade = (avg_grade * num_grades + req.body.grade) / (num_grades + 1);
                        num_grades++;

                        User.findOneAndUpdate(
                            { username: req.body.student },
                            { avg_grade: avg_grade, num_grades: num_grades })
                            .then((t) => res.json(1))
                            .catch((err) => console.log(err));
                    }
                ).catch((err) => console.log(err));

            })
            .catch((err) => console.log(err));
    };

    get_teacher_not_available = (req: express.Request, res: express.Response) => {
        let not_working_times: string[] = [];

        Class.find({ teacher: req.body.username, status: -2 })
            .then((classes) => {
                classes.forEach(element => {
                    let start = element.start?.toUTCString();
                    let end = element.end?.toUTCString();
                    not_working_times.push(start + "#" + end);
                });
                res.json(not_working_times);
            }).catch((err) => console.log(err));
    }

    get_teacher_pending = (req: express.Request, res: express.Response) => {
        let pending_times: string[] = [];

        Class.find({ teacher: req.body.username, status: 0 })
            .then((classes) => {
                classes.forEach(element => {
                    let start = element.start?.toUTCString();
                    let end = element.end?.toUTCString();
                    pending_times.push(start + "#" + end);
                });
                res.json(pending_times);
            }).catch((err) => console.log(err));
    }

    get_teacher_booked = (req: express.Request, res: express.Response) => {
        let booked_times: string[] = [];

        Class.find({ teacher: req.body.username, status: 1 })
            .then((classes) => {
                classes.forEach(element => {
                    let start = element.start?.toUTCString();
                    let end = element.end?.toUTCString();
                    booked_times.push(start + "#" + end);
                });
                res.json(booked_times);
            }).catch((err) => console.log(err));
    }

    get_all_classes_of_teacher = (req: express.Request, res: express.Response) => {
        Class.find({ teacher: req.params.username })
            .then((c) => {
                res.json(c);
            })
            .catch((err) => console.log(err));
    };

    add_busy_time = (req: express.Request, res: express.Response) => {
        const cls = new Class({
            teacher: req.body.username,
            student: "",
            subject: "Busy",
            start: req.body.start,
            end: req.body.end,
            status: -2
        });

        Class.insertMany(cls)
            .then((c) => {
                res.json(1);
            })
            .catch((err) => console.log(err));
    }
}
