import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, OnInit, Inject, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { PostService } from 'src/app/shared/service/posts.service';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { DialogData } from '../../pages/homepage/homepage.component';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {

  @Output()
  edited: EventEmitter<void> = new EventEmitter();

  selectedFiles?: FileList;
  selectedFileNames: File[] = [];

  progressInfos: any[] = [];
  message: string[] = [];

  previews: string[] = [];
  imageInfos?: Observable<any>;

  localUrl!: any[];

  form: FormGroup;

  constructor(
    private postService: PostService,
    private router: Router,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    public dialogRef: MatDialogRef<EditPostComponent>
  ) {
    this.form = this.fb.group({
      title: [data.title, [Validators.required, Validators.maxLength(288)]],

      text: [data.text, [Validators.required, Validators.maxLength(288)]],

      image: [data.image],

      cop: [data.cop, [Validators.required]],
    });
  }

  homepageClicked() {
    this.router.navigate(['homepage']);
  }

  ngOnInit(): void {}

  saveDetails(form: any) {
    this.postService
      .editPost(
        this.data.postId,
        this.form.controls['title'].value,
        this.form.controls['text'].value,
        this.form.controls['cop'].value,
        this.selectedFileNames[0]
      )
      .subscribe({
        next: () => {
          this._snackBar.open('Post edited with success!', 'Ok');
          this.dialogRef.close()
          // this.router.navigate(['homepage']);
          this.edited.emit()
        },
        error: () => {
          this._snackBar.open('Editing post failed!', 'Ok');
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
