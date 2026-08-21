import { Service, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs'

@Service()
export class BrandService {
    private http = inject(HttpClient);
    url = 'http://localhost:8080';

    save(brand: any) {
        return this.http.post(`${this.url}/brands`, brand);
    }

    getBrand(): Observable<any> {
        return this.http.get<any>(`${this.url}/brands`);
    }

    getBrands(param: HttpParams): Observable<any> {
        return this.http.get<any>(`${this.url}/brands`, { params: param });
    }
}
