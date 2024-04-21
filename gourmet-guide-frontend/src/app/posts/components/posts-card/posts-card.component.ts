import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/auth/service/auth.service';
import { PostService } from 'src/app/shared/service/posts.service';
import { Post } from '../../model/post.model';
import { CommentsPopUpComponent } from '../comments-pop-up/comments-pop-up.component';
import { DialogOverviewComponent } from '../dialog-overview/dialog-overview.component';
import { CommentService } from '../../service/comment.service';

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
  public clicked: boolean = false;
  router: any;

  constructor(
    public dialog: MatDialog,
    private authService: AuthService,
    private postService: PostService,
    private _snackBar: MatSnackBar,
    private commentService: CommentService
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
    this.postService.deletePost(this.post.id).subscribe({
      next: () => {
        this.deleted.emit();
      },
      error: () => {
        this._snackBar.open('Delete failed!', 'Ok');
      },
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
}
