import { Injectable } from '@angular/core';
import { ParcelDTO } from '../model/parcel-dto';

@Injectable({ providedIn: 'root' })
export class ParcelBufferService {
  private buffer: ParcelDTO[] = [];
  private readonly submittedIds: Set<string> = new Set();

  add(parcel: ParcelDTO): void {
    this.buffer.push(parcel);
    this.submittedIds.add(parcel.parcelId);
  }

  isAlreadySubmitted(parcelId: string): boolean {
    return this.submittedIds.has(parcelId);
  }

  clear(): void {
    this.buffer = [];
    this.submittedIds.clear();
  }

  getBuffer(): ParcelDTO[] {
    return [...this.buffer];
  }
}
