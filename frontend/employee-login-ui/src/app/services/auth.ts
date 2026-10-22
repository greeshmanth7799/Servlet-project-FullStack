
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl =
    'http://localhost:8080/employee-login/api';

  constructor(private http: HttpClient) {}

  register(data: {
    name: string;
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/register`,
      data,
      { withCredentials: true }
    );
  }

  login(data: {
    email: string;
    password: string;
  }): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/login`,
      data,
      { withCredentials: true }
    );
  }

  getDashboard(): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/dashboard`,
      { withCredentials: true }
    );
  }

  logout(): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/logout`,
      {},
      { withCredentials: true }
    );
  }
}
