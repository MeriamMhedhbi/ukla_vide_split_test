
import { Component } from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-video-upload',
  standalone: true,
  templateUrl: './video-upload.component.component.html',
  styleUrls: ['./video-upload.component.component.css'],
  imports: [
    NgIf,
    NgForOf,
    FormsModule,
    HttpClientModule
  ],
})
export class VideoUploadComponentComponent {
  videoUrl: string | null = null;
  selectedFile: File | null = null;
  showSplitCard = false;
  splitRanges: { start: number, end: number }[] = [];

  constructor(private http: HttpClient) {}

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.videoUrl = URL.createObjectURL(this.selectedFile);
    }
  }

  addRange() {
    this.splitRanges.push({ start: 0, end: 0 });
  }

  removeRange(index: number) {
    this.splitRanges.splice(index, 1);
  }

  submitSplits() {
    if (!this.selectedFile || this.splitRanges.length === 0) return;

    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('timeFrames', JSON.stringify(this.splitRanges));

    this.http.post('http://localhost:8080/api/video/process', formData)
      .subscribe({
        next: () => alert('Video uploaded and split successfully!'),
        error: (err) => console.error('Error:', err)
      });
  }
}
