import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Service()
export class AuthService {
    private http = inject(HttpClient);

    url = "http://localhost:8080";

    save(user: any) {
        return this.http.post<any>(`${this.url}/user`, user);
    }

    login(cred: any): Observable<any> {
        return this.http.post(`${this.url}/auth/signin`, cred,
            {
                observe: 'response',
                withCredentials: true
            }); //TODO withCredentials: true *access with cookie*
    }

    refreshSession() {
        return this.http.post(`${this.url}/auth/refresh`, {},
            { withCredentials: true });
    }
}
