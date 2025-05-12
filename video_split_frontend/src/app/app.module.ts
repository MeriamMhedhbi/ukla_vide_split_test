import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {VideoUploadComponentComponent} from './video-upload.component/video-upload.component.component';

@NgModule({
  declarations: [
    AppComponent,
    VideoUploadComponentComponent 
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
