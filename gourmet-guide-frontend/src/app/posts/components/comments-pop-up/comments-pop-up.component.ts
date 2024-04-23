import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Comment } from '../../model/comment.model';
import { DialogData } from '../../pages/homepage/homepage.component';
import { CommentService } from '../../service/comment.service';
import { PageEvent } from '@angular/material/paginator';
import { AuthService } from '../../../auth/service/auth.service'
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { EditCommentComponent } from '../edit-comment/edit-comment.component'
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-comments-pop-up',
  templateUrl: './comments-pop-up.component.html',
  styleUrls: ['./comments-pop-up.component.css'],
})
export class CommentsPopUpComponent implements OnInit {
  commentList: Comment[] = [];
  comment!: Comment;
  totalComments: number = 0;
  constructor(private commentService: CommentService, private authService: AuthService, 
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,) {}

  onPageChange(event: any) {
      const pageEvent = event as PageEvent;
      this.commentService.commentPageIndex = pageEvent.pageIndex;
      this.loadComments();
  }

  isAdmin(): boolean {
    return this.authService.getRole() == 'admin'
  }

  isOwner(comment: Comment): boolean {
    return comment.userName === this.authService.getName()
  }

  onDelete(comment: Comment) {
    this.openDeleteConfirmationDialog(comment)
  }

  onEdit(comment: Comment) {
    this.openDialogEditComment(comment)
  }

  openDialogEditComment(comment: Comment): void {
    const dialogRef = this.dialog.open(EditCommentComponent, {
      width: '250px',
      data: { commentId: comment.id, postId: this.data.postId, comment: comment.text},
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.loadComments()
    });
  }

  openDeleteConfirmationDialog(comment: Comment): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '250px',
      data: { message: 'Are you sure you want to delete this comment?' },
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.commentService.deleteComment(this.data.postId, comment.id).subscribe({
          next: () => {
            this.loadComments()
          },
          error: () => {
            this._snackBar.open('Delete failed!', 'Ok');
          },
        });
      }
    });
  }
  
  loadComments(): void {
    this.commentService
    .getAllCommentsByPostId(this.commentService.commentPageIndex, 3, this.data.postId)
    .subscribe((comments) => {
      this.commentList = [...comments.content]
      console.log(this.commentList)
      this.totalComments = this.commentList[0]?.totalComments;
    });
  }

  ngOnInit(): void {
    this.loadComments()
  }
}
