import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/service/auth.service';
import { PostService } from 'src/app/shared/service/posts.service';
import { Post } from '../../model/post.model';
import { CommentsPopUpComponent } from '../comments-pop-up/comments-pop-up.component';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { DialogOverviewComponent } from '../dialog-overview/dialog-overview.component';
import { CommentService } from '../../service/comment.service';
import { EditPostComponent } from '../edit-post/edit-post.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-posts-card',
  templateUrl: './posts-card.component.html',
  styleUrls: ['./posts-card.component.css'],
})
export class PostCardComponent implements OnInit {
  comment!: string;
  @Input()
  post!: Post;
  @Output()
  deleted: EventEmitter<void> = new EventEmitter();
  @Output()
  edited: EventEmitter<void> = new EventEmitter();
  public clicked: boolean = false;

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private postService: PostService,
    private _snackBar: MatSnackBar,
    private commentService: CommentService,
    private router: Router
    ) {}
    
  openDialog(): void {
    const dialogRef = this.dialog.open(DialogOverviewComponent, {
      width: '250px',
      data: { comment: this.comment, postId: this.post.id },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.comment = result;
    });
  }

  openDialogSeeComments() {
    this.commentService.commentPageIndex = 0;
    this.dialog.open(CommentsPopUpComponent, {
      data: { comment: this.comment, postId: this.post.id },
    });
  }

  deletePost() {
    this.openDeleteConfirmationDialog();
  }

  openDialogEditPosts() {
    const dialogRef = this.dialog.open(EditPostComponent, {
      width: '500px', height: "600px", data: { postId: this.post.id, comment: null, title: this.post.title,
        text: this.post.text, cop: this.post.cop, image: this.post.image }
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.edited.emit()
    });
  }

  pressLikePost() {
    console.log(this.authService.getToken());
      this.postService.putLikePost(this.post.id).subscribe({
        next: (postUpdated: Post) => {
          if (!this.likedByUser()) {
            this._snackBar.open('You liked this post!', 'Ok');
          }
          else {
            this._snackBar.open('You unliked this post!', 'Ok');
          }
          this.post = postUpdated;
        },
        error: () => {
          this._snackBar.open('Like failed!', 'Ok');
        },
      });
  }

  pinPost(): void {
    this.postService.putSelectPin(this.post.id).subscribe({
      next: () => {
        if (this.post.pinned == false) {
          this._snackBar.open('You pinned this post!', 'Ok');
          this.post.pinned = !this.post.pinned;
        } else {
          this._snackBar.open('You unpinned this post!', 'Ok');
          this.post.pinned = !this.post.pinned;
        }
      },
      error: () => {
        this._snackBar.open('Pin failed!', 'Ok');
      },
    });
  }

  likedByUser(): boolean {
    return this.post.likes.filter(like => "" + like.userId === "" + this.authService.getId()).length > 0;
  }
  
  ngOnInit(): void {}

  protected readonly URL = URL;

  private openDeleteConfirmationDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '250px',
      data: { message: 'Are you sure you want to delete this post?' },
    });

    dialogRef.afterClosed().subscribe((confirmed) => {
      if (confirmed) {
        this.postService.deletePost(this.post.id).subscribe({
          next: () => {
            this.deleted.emit();
          },
          error: () => {
            this._snackBar.open('Delete failed!', 'Ok');
          },
        });
      }
    });
  }
}
