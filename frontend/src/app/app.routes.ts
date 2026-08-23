import { Routes } from '@angular/router';
import { Contents } from './components/contents/contents';
import { User } from './components/user/user';
import { Brand } from './components/brands/brand/brand';
import { BrandList } from './components/brands/brand-list/brand-list';
import { BrandForm } from './components/brands/brand-form/brand-form';


export const routes: Routes = [
    { path: "", component: Contents },
    { path: "users", component: User },
    {
        path: "brand", component: Brand,
        children: [
            { path: "", redirectTo: "list", pathMatch: "full" },
            { path: "list", component: BrandList },
            { path: "form", component: BrandForm },
            { path: "form/:id", component: BrandForm }
        ]
    }
];
