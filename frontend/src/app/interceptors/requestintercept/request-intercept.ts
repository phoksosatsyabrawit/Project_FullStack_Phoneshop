import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { BehaviorSubject, catchError, filter, switchMap, take, throwError } from 'rxjs';
import { AuthService } from '../../services/authservice/auth-service';

let isRefresh = false;
const refreshTokenSubject = new BehaviorSubject<boolean | null>(null);

export const requestIntercept: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);

    const authReq = req.clone({ withCredentials: true });

    return next(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
            if (error.status === 401 && !req.url.includes('/auth/refresh') && !req.url.includes('/auth/signin')) {
                if (!isRefresh) {
                    isRefresh = true; // prevent other 401 attempt to start their own refresh call
                    refreshTokenSubject.next(null);

                    return authService.refreshSession().pipe(
                        switchMap(() => {
                            isRefresh = false;
                            refreshTokenSubject.next(true);
                            return next(authReq); // retry original request
                        }),
                        // in the failure branch
                        catchError((refreshError) => {
                            isRefresh = false;
                            refreshTokenSubject.next(false); // <-- tell waiter it failed, not just silence
                            authService.isLoggedIn.set(false);
                            return throwError(() => refreshError);
                        })
                    );
                    // handle waiting branch failure
                } else {
                    // A refresh is already in progress - wait for it, then retry
                    return refreshTokenSubject.pipe(
                        filter(result => result !== null),
                        take(1),
                        switchMap(result => {
                            if (result === false) {
                                return throwError(() => new Error('Session refresh failed.'));
                            }
                            return next(authReq);
                        })
                    );
                }
            }
            return throwError(() => error);
        })
    );
}
