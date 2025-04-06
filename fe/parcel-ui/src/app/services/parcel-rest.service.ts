import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ParcelDTO} from '../model/parcel-dto';

@Injectable({
  providedIn: 'root'
})
export class ParcelRestService {
  private apiUrl = 'http://localhost:8080/api/parcels';

  constructor(private http: HttpClient) {}

  getParcelCount(): Observable<{ count: number }> {
    return this.http.get<{ count: number }>(`${this.apiUrl}/count`);
  }

  sendParcel(parcel: ParcelDTO): Observable<string> {
    return this.http.post(`${this.apiUrl}/add`, parcel, { responseType: 'text' });
  }
}
