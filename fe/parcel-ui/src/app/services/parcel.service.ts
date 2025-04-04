import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InputData } from '../model/InputData';

@Injectable({
  providedIn: 'root'
})
export class ParcelService {
  private baseUrl = 'http://localhost:8080/api/parcels';

  constructor(private http: HttpClient) {}

  saveParcel(parcel: InputData): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}`, parcel);
  }

  getParcelCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/count`);
  }
}
