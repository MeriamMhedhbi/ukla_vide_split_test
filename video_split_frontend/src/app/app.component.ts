import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {VideoUploadComponentComponent} from './video-upload.component/video-upload.component.component';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    VideoUploadComponentComponent,],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Video Splitter';
}
