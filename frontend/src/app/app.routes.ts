import { Routes } from '@angular/router';
import { Contents } from './components/contents/contents';
import { User } from './components/user/user';

export const routes: Routes = [
    { path: "", component: Contents },
    { path: "users", component: User }
];
