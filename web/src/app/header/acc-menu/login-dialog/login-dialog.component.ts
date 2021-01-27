import {Component, OnInit} from '@angular/core';
import {AppService} from '../../../app.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit {
  credentials = {username: '', password: ''};


  constructor(public dialogRef: MatDialogRef<LoginDialogComponent>, private app: AppService, private http: HttpClient, private router: Router) {
  }

  ngOnInit(): void {
  }

  login() {
    this.app.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/admin-panel');
    });
    this.dialogRef.close(this.credentials);
  }
}
