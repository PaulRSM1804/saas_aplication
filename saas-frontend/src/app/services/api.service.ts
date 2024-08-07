import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseURL = 'http://localhost:8080/api';

  async get<T>(endpoint: string): Promise<T> {
    const response = await axios.get<T>(`${this.baseURL}/${endpoint}`);
    return response.data;
  }

  async post<T>(endpoint: string, data: any): Promise<T> {
    const response = await axios.post<T>(`${this.baseURL}/${endpoint}`, data);
    return response.data;
  }

  async put<T>(endpoint: string, data: any): Promise<T> {
    const response = await axios.put<T>(`${this.baseURL}/${endpoint}`, data);
    return response.data;
  }

  async delete<T>(endpoint: string): Promise<T> {
    const response = await axios.delete<T>(`${this.baseURL}/${endpoint}`);
    return response.data;
  }
}
