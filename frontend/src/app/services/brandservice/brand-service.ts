import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Service()
export class BrandService {
    private http = inject(HttpClient);
    url = 'http://localhost:8080';

    save(brand: any) {
        return this.http.post(`${this.url}/brands`, brand);
    }
}
