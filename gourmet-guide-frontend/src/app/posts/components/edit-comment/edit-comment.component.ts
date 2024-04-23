import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogData } from '../../pages/homepage/homepage.component';
import { CommentService } from '../../service/comment.service';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css']
})
export class EditCommentComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<EditCommentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    private commentService: CommentService,
    private _snackBar: MatSnackBar
  ) {}

  onEditClick(): void {
    this.commentService
      .editComment(this.data.postId, this.data.commentId, this.data.comment)
      .subscribe({
        next: () => {
          this._snackBar.open('Comment edited with success!', 'Ok');
        },
        error: () => {
          this._snackBar.open('Comment editing failed!', 'Ok');
        },
      });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {}

}
