import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Service()
export class AuthService {
    private http = inject(HttpClient);

    url = "http://localhost:8080";

    save(user: any) {
        return this.http.post<any>(`${this.url}/users`, user);
    }

    login(loginData: any): Observable<any> {
        return this.http.post(`${this.url}/login`, loginData, { observe: 'response' }); //TODO withCredentials: true *access with cookie*
    }
}
