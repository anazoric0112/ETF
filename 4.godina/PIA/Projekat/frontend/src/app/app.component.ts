import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from './services/common.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    constructor(private router: Router, public service: CommonService) { }

    ngOnInit(): void {
        if (localStorage.getItem("logged") != null) {
            let user = JSON.parse(localStorage.getItem("logged")!);
            this.service.userType = user.type;
        }
    }

    title = 'frontend';
    dropped = false;
    droppedreg = false;

    drop() {
        this.dropped = !this.dropped;
    }
    dropreg() {
        this.droppedreg = !this.droppedreg;
    }

    logout() {
        localStorage.removeItem("logged");
        if (this.service.userType != "admin") {
            this.service.userType = "guest";
            this.router.navigate(['']);
        } else {
            this.service.userType = "guest";
            this.router.navigate(['admin_login']);
        }

    }
}
