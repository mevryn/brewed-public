import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';

@Component({
  selector: 'app-capture',
  templateUrl: './capture.component.html',
  styleUrls: ['./capture.component.css']
})
export class CaptureComponent implements OnInit {
  @ViewChild('video', {static: true}) videoElement: ElementRef;
  @ViewChild('canvas', {static: true}) canvas: ElementRef;
  private videoHeight = 0;
  private videoWidth = 0;
  tempFile: any;
  stream;

  constructor(private renderer: Renderer2) {
  }

  ngOnInit() {
    this.startCamera();
    this.startPreview();
  }

  constraints = {
    video: {
      facingMode: "environment",
      width: {ideal: 300},
      height: {ideal: 400}
    }
  };

  startCamera() {
    if (!!(navigator.mediaDevices && navigator.mediaDevices.getUserMedia)) {
      this.stream = this.attachVideo.bind(this);
      navigator.mediaDevices.getUserMedia(this.constraints).then(this.stream).catch(this.handleError);
    } else {
      alert('Sorry, camera not available.');
    }
  }

  handleError(error) {
    console.log('Error: ', error);
  }

  attachVideo(stream) {
    this.renderer.setProperty(this.videoElement.nativeElement, 'srcObject', stream);
    this.renderer.listen(this.videoElement.nativeElement, 'play', (event) => {
      this.videoHeight = this.videoElement.nativeElement.videoHeight;
      this.videoWidth = this.videoElement.nativeElement.videoWidth;
    });
  }

  capture() {
    this.renderer.setProperty(this.canvas.nativeElement, 'width', this.videoWidth);
    this.renderer.setProperty(this.canvas.nativeElement, 'height', this.videoHeight);
    this.canvas.nativeElement.getContext('2d').drawImage(this.videoElement.nativeElement, 0, 0);
    //   let s = this.canvas.nativeElement.toDataURL();
    let s = this.canvas.nativeElement;
    this.tempFile = s;
  }

  closeDevices() {
    this.videoElement.nativeElement.pause();
  }

  private startPreview() {
    this.renderer.setProperty(this.canvas.nativeElement, 'width', 300);
    this.renderer.setProperty(this.canvas.nativeElement, 'height', 400);
    let context = this.canvas.nativeElement.getContext('2d');
    context.fillStyle = "#b36200";
    context.fillRect(0, 0, 300, 400)
  }
}
