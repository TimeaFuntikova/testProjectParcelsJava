import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { PLATFORM_ID } from '@angular/core';
import { InputData } from '../model/InputData';

import { ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-parcel-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parcel-form.component.html',
  styleUrls: ['./parcel-form.component.css']
})
export class ParcelFormComponent {

  @ViewChild('form') form!: NgForm;

  parcel: any;
  submittedIds: Set<string> = new Set();
  parcelCount: number = 0;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}


  ngOnInit(): void {
    this.generateNewParcel();
    //TODO: fetch from server and send to server
    //this.fetchParcelCount();
  }

  copied: boolean = false;

  copyParcelId() {
    if (navigator.clipboard) {
      navigator.clipboard.writeText(this.parcel.parcelId).then(() => {
        this.copied = true;
        setTimeout(() => (this.copied = false), 2000);
      }).catch(err => {
        console.error('Failed to copy!', err);
      });
    } else {
      alert('Clipboard not supported on this browser.');
    }
  }

  generateNewParcel(): void {
    const id = this.generateParcelId();
    this.parcel = {
      parcelId: id,
      address: {
        name: '',
        street: '',
        number: '',
        city: '',
        postcode: ''
      }
    };
  }

  generateParcelId(): string {
    let id = '';
    while (id.length < 12) {
      id += Math.floor(Math.random() * 10);
    }
    return id;
  }
  submitParcel() {
    if (!this.validateForm()) {
      console.warn('Form is invalid.');
      return;
    }

    if (this.submittedIds.has(this.parcel.parcelId)) {
      alert('This parcel has already been submitted.');
      return;
    }

    this.parcelsBuffer.push({ ...this.parcel });
    this.submittedIds.add(this.parcel.parcelId);
    this.parcelCount++;
    this.parcelsBuffer = [];

    this.resetFormUI();

    this.showSuccessMessage = true;
    setTimeout(() => (this.showSuccessMessage = false), 3000);
  }
  showSuccessMessage: boolean = false;

  clearDraft(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('parcelDraft');
    }
  }

  isFormValid(): boolean {
    const a = this.parcel.address;

    return !!(
      a.name?.trim() &&
      a.street?.trim() &&
      a.number?.trim() &&
      a.city?.trim() &&
      /^[a-zA-Z0-9\-\/]{1,15}$/.test(a.number) &&
      /^[0-9]{5}$/.test(a.postcode)
    );
  }

  formSubmitted: boolean = false;
  parcelsBuffer: InputData[] = [];

  nextParcel() {
    if (!this.validateForm()) {
      console.warn('Form is invalid.');
      return;
    }

    if (this.submittedIds.has(this.parcel.parcelId)) {
      alert('This parcel has already been submitted.');
      return;
    }

    this.parcelsBuffer.push({ ...this.parcel });
    this.submittedIds.add(this.parcel.parcelId);
    this.parcelCount++;

    this.resetFormUI();

    this.showNextMessage = true;
    setTimeout(() => (this.showNextMessage = false), 2000);
  }
  showNextMessage: boolean = false;

  validateForm(): boolean {
    this.formSubmitted = true;

    Object.values(this.form.controls).forEach(control => {
      control?.markAsTouched();
      control?.markAsDirty();
    });

    return this.isFormValid();
  }

  resetFormUI() {
    this.formSubmitted = false;
    this.clearDraft();
    this.generateNewParcel();

    setTimeout(() => {
      this.form?.resetForm({
        parcelId: this.parcel.parcelId,
        address: {
          name: '',
          street: '',
          number: '',
          city: '',
          postcode: ''
        }
      });
    });
  }

  clearForm() {
    this.formSubmitted = false;
    this.clearDraft();
    this.generateNewParcel();

    setTimeout(() => {
      this.form?.resetForm({
        parcelId: this.parcel.parcelId,
        address: {
          name: '',
          street: '',
          number: '',
          city: '',
          postcode: ''
        }
      });
    });
  }
}
