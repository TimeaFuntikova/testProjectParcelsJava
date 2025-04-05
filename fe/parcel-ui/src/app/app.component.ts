import { RouterModule } from '@angular/router';
import {Component} from '@angular/core';
import {ParcelFormComponent} from './parcel-form/parcel-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, ParcelFormComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent { }
