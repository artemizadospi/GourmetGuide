import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Comment } from '../../model/comment.model';
import { DialogData } from '../../pages/homepage/homepage.component';
import { CommentService } from '../../service/comment.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-comments-pop-up',
  templateUrl: './comments-pop-up.component.html',
  styleUrls: ['./comments-pop-up.component.css'],
})
export class CommentsPopUpComponent implements OnInit {
  commentList: Comment[] = [];
  comment!: Comment;
  totalComments: number = 0;
  constructor(private commentService: CommentService, 
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onPageChange(event: any) {
      const pageEvent = event as PageEvent;
      this.commentService.commentPageIndex = pageEvent.pageIndex;
      this.loadComments();
    }
  
  loadComments(): void {
    this.commentService
    .getAllCommentsByPostId(this.commentService.commentPageIndex, 4, this.data.postId)
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
