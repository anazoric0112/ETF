import express from "express";
import { AdminController } from "../controllers/admin.controller";
const adminRouter = express.Router();

adminRouter
    .route("/get_teacher_requests")
    .get((req, res) => new AdminController().get_teacher_requests(req, res));

adminRouter
    .route("/process_request")
    .post((req, res) => new AdminController().process_request(req, res));

adminRouter
    .route("/get_students")
    .get((req, res) => new AdminController().get_students(req, res));

adminRouter
    .route("/get_teachers")
    .get((req, res) => new AdminController().get_teachers(req, res));

adminRouter
    .route("/delete_teacher/:username")
    .delete((req, res) => new AdminController().delete_teacher(req, res));

adminRouter
    .route("/activate_teacher/:username")
    .patch((req, res) => new AdminController().activate_teacher(req, res));

adminRouter
    .route("/get_subject_requests")
    .get((req, res) => new AdminController().get_subject_requests(req, res));

adminRouter
    .route("/accept_subject/:name")
    .patch((req, res) => new AdminController().accept_subject(req, res));

adminRouter
    .route("/reject_subject/:name")
    .delete((req, res) => new AdminController().reject_subject(req, res));

adminRouter
    .route("/add_subject")
    .post((req, res) => new AdminController().add_subject(req, res));

adminRouter
    .route("/get_classes")
    .get((req, res) => new AdminController().get_classes(req, res));

adminRouter
    .route("/get_cv/:teacher")
    .get((req, res) => new AdminController().get_cv(req, res));

export default adminRouter;
