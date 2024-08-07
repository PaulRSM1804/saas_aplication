import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RDFService {
  private apiUrl = 'http://localhost:8080/api/rdf';

  constructor(private http: HttpClient) { }

  getCombinedRDF(): Observable<string> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get(`${this.apiUrl}/all`, { headers, responseType: 'text' });
  }
}
