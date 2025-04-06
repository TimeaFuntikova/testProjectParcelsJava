import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ParcelDTO } from '../model/parcel-dto';
import { ParcelRestService } from '../services/parcel-rest.service';
import { ParcelFormService } from '../services/parcel-form.service';
import { ParcelBufferService } from '../services/parcel-buffer.service';

@Component({
  selector: 'app-parcel-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './parcel-form.component.html',
  styleUrls: ['./parcel-form.component.css']
})
export class ParcelFormComponent implements OnInit {

  @ViewChild('form') form!: NgForm;

  parcelCount: number = 0;
  copied: boolean = false;
  formSubmitted: boolean = false;
  showSuccessMessage: boolean = false;
  showNextMessage: boolean = false;

  constructor(
    @Inject(PLATFORM_ID) private readonly platformId: Object,
    private readonly parcelRestService: ParcelRestService,
    public formService: ParcelFormService,
    protected bufferService: ParcelBufferService
  ) {}

  get parcel(): ParcelDTO {
    return this.formService.parcel; // 🔥 direct reference for binding!
  }

  ngOnInit(): void {
    this.formService.generateNewParcel();
    this.fetchParcelCount();
  }

  fetchParcelCount(): void {
    this.parcelRestService.getParcelCount().subscribe({
      next: (response) => this.parcelCount = response.count,
      error: (err) => console.error('Failed to fetch parcel count', err)
    });
  }

  copyParcelId(): void {
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

  submitParcel(): void {
    if (!this.validateForm()) return;

    if (this.bufferService.isAlreadySubmitted(this.parcel.parcelId)) {
      alert('This parcel has already been submitted.');
      return;
    }

    this.parcelRestService.sendParcel(this.parcel).subscribe({
      next: (): void => {
        this.bufferService.clear();
        this.clearForm();
        this.fetchParcelCount();
        this.showSuccessMessage = true;

        setTimeout(() => (this.showSuccessMessage = false), 3000);
      },
      error: (err) => console.error('Failed to send parcel:', err)
    });
  }

  nextParcel(): void {
    if (!this.validateForm()) return;
    if (this.bufferService.isAlreadySubmitted(this.parcel.parcelId)) {
      alert('This parcel has already been submitted.');
      return;
    }

    this.parcelRestService.sendParcel(this.parcel).subscribe({
      next: () => {
        this.bufferService.add(this.parcel);
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
    return this.formService.validateParcel(this.parcel);
  }

  clearForm(): void {
    this.formSubmitted = false;
    this.clearDraft();
    const newParcel = this.formService.resetForm();

    setTimeout(() => {
      this.form?.resetForm({
        parcelId: newParcel.parcelId,
        address: { ...newParcel.address }
      });
    });
  }

  clearDraft(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('parcelDraft');
    }
  }
}
