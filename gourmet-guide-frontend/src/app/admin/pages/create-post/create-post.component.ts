import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { PostService } from 'src/app/shared/service/posts.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
})
export class CreatePostComponent implements OnInit {
  selectedFiles?: FileList;
  selectedFileNames: File[] = [];
  progressInfos: any[] = [];
  message: string[] = [];
  previews: string[] = [];
  imageInfos?: Observable<any>;
  localUrl!: any[];
  form: FormGroup;
  categories: string[] = ['Starter', 'MainCourse', 'Dessert'];

  constructor(
    private postService: PostService,
    private router: Router,
    private fb: FormBuilder,

    private _snackBar: MatSnackBar
  ) {
    this.form = this.fb.group({
      title: [null, [Validators.required, Validators.maxLength(288)]],

      text: [null, [Validators.required]],

      image: [null],

      cop: [null, [Validators.required]],
    });
  }

  homepageClicked() {
    this.router.navigate(['homepage']);
  }

  ngOnInit(): void {}

  submitDetails(form: any) {
    console.log('submit details');
    this.postService
      .createPost(
        this.form.controls['title'].value,
        this.form.controls['text'].value,
        this.form.controls['cop'].value,
        this.selectedFileNames[0]
      )
      .subscribe({
        next: () => {
          this._snackBar.open('Post created with success!', 'Ok');
          this.router.navigate(['homepage']);
        },
        error: () => {
          this._snackBar.open('Post creation failed!', 'Ok');
        },
      });
  }

  selectFiles(event: any): void {
    this.message = [];
    this.progressInfos = [];
    this.selectedFileNames = [];
    this.selectedFiles = event.target.files;

    this.previews = [];
    if (this.selectedFiles && this.selectedFiles[0]) {
      const numberOfFiles = this.selectedFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();

        reader.onload = (e: any) => {
          console.log(e.target.result);
          this.previews.push(e.target.result);
        };

        reader.readAsDataURL(this.selectedFiles[i]);

        this.selectedFileNames.push(this.selectedFiles[i]);
      }
    }
  }
}
