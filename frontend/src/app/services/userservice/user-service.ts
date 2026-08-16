import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Service()
export class UserService {
    private http = inject(HttpClient);

    url = "http://localhost:8080/users";

    save(user: any) {
        return this.http.post<any>(this.url, user);
    }
}
