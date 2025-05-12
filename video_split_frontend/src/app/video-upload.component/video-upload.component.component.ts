import {Component} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-video-upload',
  standalone: true,
  templateUrl: './video-upload.component.component.html',
  styleUrls: ['./video-upload.component.component.css'],
  imports: [NgIf, NgForOf, FormsModule, HttpClientModule],
})
export class VideoUploadComponentComponent {
  videoUrl: string | null = null;
  selectedFile: File | null = null;
  showSplitCard = false;
  splitRanges: { start: number; end: number; startFormatted: string; endFormatted: string }[] = [];

  videoDuration: number = 0;
  successMessage: string = '';

  constructor(private http: HttpClient) {
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.videoUrl = URL.createObjectURL(this.selectedFile);

      const videoElement = document.createElement('video');
      videoElement.src = this.videoUrl;
      videoElement.onloadedmetadata = () => {
        this.videoDuration = Math.round(videoElement.duration);
      };
    }
  }

  addRange() {
    this.splitRanges.push({start: 0, end: 0, startFormatted: '00:00', endFormatted: '00:00'});
  }

  removeRange(index: number) {
    this.splitRanges.splice(index, 1);
  }

  onStartChange(index: number) {
    const [minutes, seconds] = this.splitRanges[index].startFormatted.split(':').map(Number);
    this.splitRanges[index].start = minutes * 60 + seconds;
    this.validateRanges();
  }

  onEndChange(index: number) {
    const [minutes, seconds] = this.splitRanges[index].endFormatted.split(':').map(Number);
    this.splitRanges[index].end = minutes * 60 + seconds;
    this.validateRanges();
  }

  validateRanges() {
    for (let i = 0; i < this.splitRanges.length; i++) {
      for (let j = i + 1; j < this.splitRanges.length; j++) {
        if (this.splitRanges[i].end > this.splitRanges[j].start) {
          alert('Time ranges overlap. Please adjust.');
          return;
        }
      }

      if (this.splitRanges[i].end > this.videoDuration) {
        alert('End time exceeds video duration.');
        return;
      }
    }
  }

  submitSplits() {
    if (!this.selectedFile || this.splitRanges.length === 0) return;

    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('timeFrames', JSON.stringify(this.splitRanges));

    this.http.post('http://localhost:8080/api/video/process', formData).subscribe({
      next: () => {
        this.successMessage = 'Video uploaded and split successfully!';
        setTimeout(() => {
          this.successMessage = '';
        }, 10000);
      },
      error: (err) => console.error('Error:', err),
    });
  }
}
