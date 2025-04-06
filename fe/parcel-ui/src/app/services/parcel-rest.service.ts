import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ParcelDTO } from '../model/parcel-dto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ParcelRestService {
  private apiUrl: string = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getParcelCount(): Observable<{ count: number }> {
    return this.http.get<{ count: number }>(`${this.apiUrl}/count`);
  }

  sendParcel(parcel: ParcelDTO): Observable<string> {
    return this.http.post(`${this.apiUrl}/add`, parcel, { responseType: 'text' });
  }
}
