import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Subscription } from '../models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/api/subscriptions';
  private SecondApiUrl = 'http://localhost:8080/api';


  constructor(private http: HttpClient) { }

  getSubscriptions(): Observable<Subscription[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    return this.http.get<Subscription[]>(this.apiUrl, { headers });
  }

  getSubscriptionsByCourse(courseId: number): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.apiUrl}/course/${courseId}`);
  }

  getSubscription(id: number): Observable<Subscription> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    return this.http.get<Subscription>(`${this.apiUrl}/${id}`, { headers });
  }

  createSubscription(consumerId: number, courseId: number): Observable<Subscription> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    return this.http.post<Subscription>(`${this.apiUrl}/create?consumerId=${consumerId}&courseId=${courseId}`, {}, { headers })
    .pipe(
      catchError(error => {
        return throwError(error);
      })
    );;
  }

  updateSubscription(id: number, subscription: Subscription): Observable<Subscription> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    return this.http.put<Subscription>(`${this.apiUrl}/${id}`, subscription, { headers });
  }


  deleteSubscription(id: number): Observable<void> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }

  getSubscriptionsByCreator(creatorId: number): Observable<Subscription[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${localStorage.getItem('token')}`);
    return this.http.get<Subscription[]>(`${this.apiUrl}/creator/${creatorId}`, { headers });
  }
}
