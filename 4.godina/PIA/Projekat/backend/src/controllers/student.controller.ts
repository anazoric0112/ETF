import * as express from "express";
import Class from "../models/class";
import News from "../models/news";
import User from "../models/user";

export class StudentController {

    request_class = (req: express.Request, res: express.Response) => {
        let c = new Class(req.body);

        Class.insertMany(c)
            .then((c) => {
                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    classes_held = (req: express.Request, res: express.Response) => {
        let now = new Date();

        Class.find(
            {
                student: req.body.username,
                end: { $lt: now },
                status: 1
            })
            .then((classes) => {
                res.json(classes);
            })
            .catch((err) => console.log(err));
    };

    classes_incoming = (req: express.Request, res: express.Response) => {
        let now = new Date();

        Class.find(
            {
                student: req.body.username,
                start: { $gt: now },
                status: 1
            })
            .then((classes) => {
                res.json(classes);
            })
            .catch((err) => console.log(err));
    };

    leave_comment = (req: express.Request, res: express.Response) => {
        Class.findOneAndUpdate(
            { link: req.body.link },
            { grade_s: req.body.grade, comment_s: req.body.comment })
            .then((c) => {
                let avg_grade = 0, num_grades = 0;
                User.findOne({ username: req.body.teacher }).then(
                    (t) => {
                        if (t == null) return;
                        avg_grade = t.avg_grade!;
                        num_grades = t.num_grades!;

                        avg_grade = (avg_grade * num_grades + req.body.grade) / (num_grades + 1);
                        num_grades++;

                        User.findOneAndUpdate(
                            { username: req.body.teacher },
                            { avg_grade: avg_grade, num_grades: num_grades })
                            .then((t) => res.json(1))
                            .catch((err) => console.log(err));
                    }
                ).catch((err) => console.log(err));

            })
            .catch((err) => console.log(err));
    };

    get_news = (req: express.Request, res: express.Response) => {
        News.find({ student: req.body.username })
            .then((news) => {
                res.json(news);
            })
            .catch((err) => console.log(err));
    };

    mark_as_read = (req: express.Request, res: express.Response) => {
        News.findOneAndUpdate({ id: req.body.id }, { seen: true })
            .then((n) => {
                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    get_classes_held_for_teacher = (req: express.Request, res: express.Response) => {
        let now = new Date();

        Class.find({ teacher: req.body.username, status: 1, end: { $lt: now } })
            .then((classes) => {
                res.json(classes);
            })
            .catch((err) => console.log(err));
    };

    get_teachers_on_subject = (req: express.Request, res: express.Response) => {
        User.find({ type: "teacher", accepted: 1, subjects: { $in: req.body.subject } })
            .then((teachers) => {
                res.json(teachers);
            })
            .catch((err) => console.log(err));
    };
}
