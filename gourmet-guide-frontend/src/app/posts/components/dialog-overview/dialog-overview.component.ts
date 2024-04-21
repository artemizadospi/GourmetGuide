import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogData } from '../../pages/homepage/homepage.component';
import { CommentService } from '../../service/comment.service';

@Component({
  selector: 'app-dialog-overview',
  templateUrl: './dialog-overview.component.html',
  styleUrls: ['./dialog-overview.component.css'],
})
export class DialogOverviewComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<DialogOverviewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private commentService: CommentService,
    private _snackBar: MatSnackBar
  ) {}

  onAddClick(): void {
    this.commentService
      .createComment(this.data.comment, this.data.postId)
      .subscribe({
        next: () => {
          this._snackBar.open('Comment created with success!', 'Ok');
        },
        error: () => {
          this._snackBar.open('Comment creation failed!', 'Ok');
        },
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {}
}
