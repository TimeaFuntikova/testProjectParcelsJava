import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, ViewChild, PLATFORM_ID} from '@angular/core';
import { FormsModule, NgForm} from '@angular/forms';
import { InputData } from '../model/InputData';
import {ParcelService} from '../services/parcel.service';

@Component({
  selector: 'app-parcel-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parcel-form.component.html',
  styleUrls: ['./parcel-form.component.css']
})
export class ParcelFormComponent implements OnInit {

  parcel: any;
  submittedIds: Set<string> = new Set();
  parcelCount: number = 0;
  formSubmitted: boolean = false;
  parcelsBuffer: InputData[] = [];
  copied: boolean = false;
  showSuccessMessage: boolean = false;
  showNextMessage: boolean = false;
  @ViewChild('form') form!: NgForm;

  constructor(
    @Inject(PLATFORM_ID) private readonly platformId: Object,
    private readonly parcelService: ParcelService
  ) {}

  ngOnInit(): void {
    this.generateNewParcel();
    this.fetchParcelCount();
  }

  fetchParcelCount(): void {
    this.parcelService.getParcelCount().subscribe({
      next: (response:{count: number}): void => {
        this.parcelCount = response.count;
      },
      error: (err) => {
        console.error('Failed to fetch parcel count', err);
      }
    });
  }

  copyParcelId(): void {
    if (navigator.clipboard) {
      navigator.clipboard.writeText(this.parcel.parcelId).then((): void => {
        this.copied = true;
        setTimeout((): boolean => (this.copied = false), 2000);
      }).catch(err => {
        console.error('Failed to copy!', err);
      });
    } else {
      alert('Clipboard not supported on this browser.');
    }
  }

  generateNewParcel(): void {
    const id: string = this.generateParcelId();
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
    let id: string = '';
    while (id.length < 12) {
      id += Math.floor(Math.random() * 10);
    }
    return id;
  }

  submitParcel(): void {
    if (!this.validateForm()) {
      console.warn('Form is invalid.');
      return;
    }

    if (this.submittedIds.has(this.parcel.parcelId)) {
      alert('This parcel has already been submitted.');
      return;
    }

    this.parcelService.sendParcel(this.parcel).subscribe({
      next: (): void => {
        this.submittedIds.add(this.parcel.parcelId);
        this.clearForm();
        this.parcelsBuffer = [];
        this.fetchParcelCount();

        this.showSuccessMessage = true;
        setTimeout(() => (this.showSuccessMessage = false), 3000);
      },
      error: (err) => {
        console.error('Failed to send parcel:', err);
      }
    });
  }

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
      /^[a-zA-Z0-9\-/]{1,15}$/.test(a.number) &&
      /^[0-9]{5}$/.test(a.postcode)
    );
  }

  nextParcel(): void {
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

    this.parcelService.sendParcel(this.parcel).subscribe({
      next: (): void => {
        this.fetchParcelCount();
        this.clearForm();
        this.showNextMessage = true;

        setTimeout(() => this.showNextMessage = false, 2000);
      },
      error: (err) => {
        console.error('Failed to send parcel:', err);
        alert('Something went wrong while sending the parcel.');
      }
    });
  }

  validateForm(): boolean {
    this.formSubmitted = true;

    Object.values(this.form.controls).forEach(control => {
      control?.markAsTouched();
      control?.markAsDirty();
    });

    return this.isFormValid();
  }

  clearForm(): void {
    this.formSubmitted = false;
    this.clearDraft();
    this.generateNewParcel();

    setTimeout((): void => {
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
