import express from "express";
import { UserController } from "../controllers/user.controller";
const userRouter = express.Router();

userRouter
    .route("/login")
    .post((req, res) => new UserController().login(req, res));

userRouter
    .route("/register")
    .post((req, res) => new UserController().register(req, res));

userRouter
    .route("/get_user_by_username")
    .post((req, res) => new UserController().get_user_by_username(req, res));

userRouter
    .route("/get_user_by_email")
    .post((req, res) => new UserController().get_user_by_email(req, res));

userRouter
    .route("/reset_password")
    .post((req, res) => new UserController().reset_password(req, res));

userRouter
    .route("/change_data")
    .post((req, res) => new UserController().change_data(req, res));

userRouter
    .route("/get_student_number")
    .get((req, res) => new UserController().student_number(req, res));

userRouter
    .route("/get_teacher_number")
    .get((req, res) => new UserController().teacher_number(req, res));

userRouter
    .route("/get_last_week_classes_number")
    .get((req, res) => new UserController().week_cls_num(req, res));

userRouter
    .route("/get_last_month_classes_number")
    .get((req, res) => new UserController().month_cls_num(req, res));

userRouter
    .route("/subjects_number")
    .get((req, res) => new UserController().subjects_number(req, res));

userRouter
    .route("/subjects")
    .get((req, res) => new UserController().subjects(req, res));

userRouter
    .route("/hash")
    .get((req, res) => new UserController().hash(req, res));

userRouter
    .route("/upload_photo")
    .post((req, res) => new UserController().upload_photo(req, res));

userRouter
    .route("/upload_cv")
    .post((req, res) => new UserController().upload_cv(req, res));

userRouter
    .route("/init_db")
    .get((req, res) => new UserController().init(req, res));

export default userRouter;
