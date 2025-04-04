import { Component } from '@angular/core';
import { ParcelFormComponent } from './parcel-form/parcel-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ParcelFormComponent],
  template: `<app-parcel-form></app-parcel-form>`,
})
export class AppComponent {}
