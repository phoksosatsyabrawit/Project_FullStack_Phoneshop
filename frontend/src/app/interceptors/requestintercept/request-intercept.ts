import { HttpInterceptorFn } from '@angular/common/http';

export const requestIntercept: HttpInterceptorFn = (req, next) => {
    //const token = localStorage.getItem('token');
    const authReq = req.clone({
        withCredentials: true // TODO withCredentials: true *access with cookie*
    });
    return next(authReq);
}
