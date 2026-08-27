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

    getById(brandId: any) {
        return this.http.get<any>(`${this.url}/brands/${brandId}`);
    }

    update(brand: any) {
        return this.http.put<any>(`${this.url}/brands/${brand.id}`, brand);
    }

    delete(brandId: number) {
        return this.http.delete<any>(`${this.url}/brands/${brandId}`);
    }
}
