import { Routes } from '@angular/router';
import { Contents } from './components/contents/contents';
import { User } from './components/user/user';
import { Brand } from './components/brand/brand';
import { BrandList } from './components/brand/brand-list/brand-list';

export const routes: Routes = [
    { path: "", component: Contents },
    { path: "users", component: User },
    { path: "brandlist", component: BrandList },
    { path: "brands", component: Brand }
];
