import * as express from "express";
import User from "../models/user";
import Subject from "../models/subject";
import Class from "../models/class";

export class AdminController {
    get_teacher_requests = (req: express.Request, res: express.Response) => {
        User.find({ type: "teacher", accepted: 0 })
            .then((requests) => {
                res.json(requests);
            })
            .catch((err) => console.log(err));
    };

    get_subject_requests = (req: express.Request, res: express.Response) => {
        Subject.find({ accepted: false })
            .then((requests) => {
                res.json(requests);
            })
            .catch((err) => console.log(err));
    };

    process_request = (req: express.Request, res: express.Response) => {
        User.findOneAndUpdate(
            { username: req.body.username },
            { accepted: req.body.accepted })
            .then((user) => {

                //ako je prihvacen nastavnik, predmetima ga dodajemo
                if (req.body.accepted == 1) {
                    user?.subjects.forEach(element => {
                        Subject.findOneAndUpdate({ name: element },
                            { $push: { teachers: user.username } })
                    });
                }

                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    delete_teacher = (req: express.Request, res: express.Response) => {
        User.findOneAndUpdate(
            { username: req.params.username },
            { accepted: -2 })
            .then((user) => {

                user?.subjects.forEach(element => {
                    Subject.findOneAndUpdate({ name: element },
                        { $pull: { teachers: user.username } })
                });

                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    activate_teacher = (req: express.Request, res: express.Response) => {
        User.findOneAndUpdate(
            { username: req.params.username },
            { accepted: 1 })
            .then((user) => {

                user?.subjects.forEach(element => {
                    Subject.findOneAndUpdate({ name: element },
                        { $push: { teachers: user.username } })
                });

                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    get_students = (req: express.Request, res: express.Response) => {
        User.find({ type: "student" })
            .then((s) => {
                res.json(s);
            })
            .catch((err) => console.log(err));
    };

    get_teachers = (req: express.Request, res: express.Response) => {
        User.find({ type: "teacher", accepted: { $in: [1, -2] } })
            .then((t) => {
                res.json(t);
            })
            .catch((err) => console.log(err));
    };

    get_classes = (req: express.Request, res: express.Response) => {
        Class.find()
            .then((c) => {
                res.json(c);
            })
            .catch((err) => console.log(err));
    }

    accept_subject = (req: express.Request, res: express.Response) => {
        Subject.findOneAndUpdate(
            { name: req.params.name },
            { accepted: true })
            .then((s) => {

                s?.teachers.forEach(element => {
                    User.findOneAndUpdate({ username: element },
                        { $push: { subjects: s?.name } })
                        .then();
                });


                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    reject_subject = (req: express.Request, res: express.Response) => {
        Subject.findOneAndDelete(
            { name: req.params.name })
            .then((s) => {
                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    add_subject = (req: express.Request, res: express.Response) => {
        const new_subject = new Subject({
            name: req.body.name,
            accepted: true,
            teachers: []
        });

        new_subject.save()
            .then(() => {
                res.json(1);
            })
            .catch((err) => console.log(err));
    };

    get_cv = (req: express.Request, res: express.Response) => {
        const file = `cv/${req.params.teacher}.pdf`;
        res.download(file);
    };
}
