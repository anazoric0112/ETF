import * as express from "express";
import User from "../models/user";
import { hash } from "argon2";
import Class from "../models/class";
import Subject from "../models/subject";
import News from "../models/news";

import { classes } from "../db/db_classes";
import { news } from "../db/db_news";
import { subjects } from "../db/db_subjects";
import { admin, students, teachers } from "../db/db_users";

let argon2 = require('argon2');
const hashingConfig = {
    parallelism: 1,
    memoryCost: 6000,
    timeCost: 3,
    salt: Buffer.from('Ig0llDdEdbCAiEMA')
}
let fs = require('fs');
let fu = require('express-fileupload')

export class UserController {
    login = (req: express.Request, res: express.Response) => {
        let u = req.body.username;
        let p = req.body.password;

        hashPassword(p!).then((hash) => {
            p = hash;
            User.findOne({ username: u, password: p })
                .then((user) => {
                    res.json(user);
                })
                .catch((err) => console.log(err));

        });

    };

    register = (req: express.Request, res: express.Response) => {
        let user = new User(req.body);

        hashPassword(user.password!).then((hash) => {
            user.password = hash;
            User.insertMany(user)
                .then((u) => {
                    if (user.type == "student") res.json("Successful registration");
                    else {
                        Subject.insertMany(new Subject({ name: user.additional_subject, accepted: false, teachers: [user.username] }));
                        res.json("Successful submission - Your request is being reviewed");
                    }
                })
                .catch((err) => console.log(err));
        });


    };

    upload_photo = (req: express.Request, res: express.Response) => {
        let file = (req as any).files;
        let username = req.body.username;

        User.findOneAndUpdate({ username: username }, { photo: username + ".png" })
            .then((u) => {
                fs.writeFile("photo/" + username + ".png", file.thumbnail.data, function () { });
                res.json(1)
            })
            .catch((err) => console.log(err));
    };

    upload_cv = (req: express.Request, res: express.Response) => {
        let file = (req as any).files;
        let username = req.body.username;

        User.findOneAndUpdate({ username: username }, { cv: "../../../cv/" + username + ".pdf" })
            .then((u) => {
                fs.writeFile("cv/" + username + ".pdf", file.thumbnail.data, function () { });
                res.json(1)
            })
            .catch((err) => console.log(err));
    };

    get_user_by_username = (req: express.Request, res: express.Response) => {
        let username = req.body.username;

        User.findOne({ username: username })
            .then((user) => {
                res.json(user);
            })
            .catch((err) => console.log(err));
    };

    get_user_by_email = (req: express.Request, res: express.Response) => {
        let email = req.body.email;

        User.findOne({ email: email })
            .then((user) => {
                res.json(user);
            })
            .catch((err) => console.log(err));
    };

    reset_password = (req: express.Request, res: express.Response) => {
        let password = "";

        hashPassword(req.body.password!).then((hash) => {
            password = hash;
            User.findOneAndUpdate(
                { username: req.body.username },
                { password: password })
                .then((user) => {
                    res.json("1");
                })
                .catch((err) => console.log(err));
        });


    };

    change_data = (req: express.Request, res: express.Response) => {

        User.findOneAndUpdate({ username: req.body.username },
            {
                name: req.body.name,
                surname: req.body.surname,
                school_type: req.body.school_type,
                year: req.body.year,
                email: req.body.email,
                phone: req.body.phone,
                address: req.body.address,
                ages: req.body.ages,
                subjects: req.body.subjects
            }).then((user) => {
                res.json(user);
            }).catch((err) => console.log(err));
    };

    student_number = (req: express.Request, res: express.Response) => {
        User.find({ type: "student" })
            .then((users) => {
                res.json(users.length)
            }).catch((err) => console.log(err));
    };

    teacher_number = (req: express.Request, res: express.Response) => {
        User.find({ type: "teacher", accepted: 1 })
            .then((users) => {
                res.json(users.length)
            }).catch((err) => console.log(err));
    };

    week_cls_num = (req: express.Request, res: express.Response) => {
        let date = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000);
        Class.find({ end: { $gt: date }, status: 1 })
            .then((classes) => {
                res.json(classes.length)
            }).catch((err) => console.log(err));
    };

    month_cls_num = (req: express.Request, res: express.Response) => {
        let date = new Date(Date.now() - 30 * 24 * 60 * 60 * 1000);
        Class.find({ end: { $gt: date }, status: 1 })
            .then((classes) => {
                res.json(classes.length)
            }).catch((err) => console.log(err));
    };

    subjects_number = (req: express.Request, res: express.Response) => {
        Subject.find({ accepted: true })
            .then((subjects) => {
                res.json(subjects.length)
            }).catch((err) => console.log(err));
    };

    subjects = (req: express.Request, res: express.Response) => {
        Subject.find({ accepted: true })
            .then((subjects) => {
                res.json(subjects)
            }).catch((err) => console.log(err));
    };

    hash = (req: express.Request, res: express.Response) => {

        hashPassword(req.body.password).then((hash) => {
            res.json(hash);
        });
    };

    init = (req: express.Request, res: express.Response) => {
        Class.deleteMany({})
            .then((c) => {
                Subject.deleteMany({})
                    .then((s) => {
                        News.deleteMany({})
                            .then((n) => {
                                User.deleteMany({})
                                    .then((u) => {
                                        add_documents();
                                        res.json(1);
                                    });
                            });
                    });
            });
    }

}

async function hashPassword(password: String) {
    try {
        const hash = await argon2.hash(password, hashingConfig);
        return hash;
    } catch (err) {
        console.error(err);
    }
}

function add_documents() {
    classes.forEach(c => {
        Class.insertMany(
            new Class({
                student: c.student,
                teacher: c.teacher,
                subject: c.subject,
                link: c.link,
                student_description: c.student_description,
                double: c.double,
                status: c.status,
                start: new Date(c.start),
                end: new Date(c.end),
                grade_s: c.grade_s,
                comment_s: c.comment_s,
                grade_t: c.grade_t,
                comment_t: c.comment_t
            })
        )
    });
    news.forEach(n => {
        News.insertMany(
            new News({
                id: n.id,
                time: new Date(n.time),
                description: n.description,
                seen: n.seen,
                student: n.student,
                teacher: n.teacher
            })
        )
    });
    admin.forEach(u => {
        User.insertMany({
            username: u.username,
            password: u.password,
            sec_question: u.sec_question,
            sec_answer: u.sec_answer,
            name: u.name,
            surname: u.surname,
            gender: u.gender,
            type: u.type,
        });
    });
    students.forEach(u => {
        User.insertMany({
            username: u.username,
            password: u.password,
            sec_question: u.sec_question,
            sec_answer: u.sec_answer,
            name: u.name,
            surname: u.surname,
            gender: u.gender,
            address: u.address,
            phone: u.phone,
            email: u.email,
            school_type: u.school_type,
            year: u.year,
            type: u.type,
            cv: "",
            photo: "",
            avg_grade: u.avg_grade,
            num_grades: u.num_grades,
        });
    });
    teachers.forEach(u => {
        User.insertMany({
            username: u.username,
            password: u.password,
            sec_question: u.sec_question,
            sec_answer: u.sec_answer,
            name: u.name,
            surname: u.surname,
            gender: u.gender,
            address: u.address,
            phone: u.phone,
            email: u.email,
            type: u.type,
            accepted: u.accepted,
            subjects: u.subjects,
            additional_subject: u.additional_subject,
            ages: u.ages,
            heard_from: u.heard_from,
            cv: "",
            photo: "",
            avg_grade: u.avg_grade,
            num_grades: u.num_grades,
        });
    });
    subjects.forEach((s) => {
        Subject.insertMany({
            name: s.name,
            teachers: s.teachers,
            accepted: s.accepted
        })
    })
}