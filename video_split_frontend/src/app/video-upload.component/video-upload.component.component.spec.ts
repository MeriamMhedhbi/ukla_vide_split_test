import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoUploadComponentComponent } from './video-upload.component.component';

describe('VideoUploadComponentComponent', () => {
  let component: VideoUploadComponentComponent;
  let fixture: ComponentFixture<VideoUploadComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VideoUploadComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VideoUploadComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
