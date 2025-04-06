import { Injectable } from '@angular/core';
import { ParcelDTO } from '../model/parcel-dto';

@Injectable({ providedIn: 'root' })
export class ParcelFormService {
  private _parcel: ParcelDTO = this.createEmptyParcel();

  get parcel(): ParcelDTO {
    return this._parcel;
  }

  /**
   * Generates a fresh Parcel object.
   */
  generateNewParcel(): void {
    this._parcel = this.createEmptyParcel();
  }

  /**
   * Replaces the current parcel with a new one and returns it.
   */
  resetForm(): ParcelDTO {
    this._parcel = this.createEmptyParcel();
    return this._parcel;
  }

  /**
   * Validates the contents of the parcel object.
   */
  validateParcel(parcel: ParcelDTO): boolean {
    const a = parcel.address;
    return !!(
      a.name?.trim() &&
      a.street?.trim() &&
      a.number?.trim() &&
      a.city?.trim() &&
      /^[a-zA-Z0-9\-/]{1,15}$/.test(a.number) &&
      /^\d{5}$/.test(a.postcode)
    );
  }

  /**
   * Creates an empty ParcelDTO with a generated ID.
   */
  private createEmptyParcel(): ParcelDTO {
    return {
      parcelId: this.generateParcelId(),
      address: {
        name: '',
        street: '',
        number: '',
        city: '',
        postcode: ''
      }
    };
  }

  /**
   * Generates a random 12-digit parcel ID.
   */
  private generateParcelId(): string {
    let id = '';
    while (id.length < 12) {
      id += Math.floor(Math.random() * 10);
    }
    return id;
  }
}
