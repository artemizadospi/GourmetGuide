import { Component, Inject, Injectable, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from '../../model/post.model';
import { PostService } from 'src/app/shared/service/posts.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { MatPaginator, MatPaginatorIntl } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { CommentService } from '../../service/comment.service';

export interface DialogData {
  postId: number;
  comment: string;
}

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  postsList: Post[] = [];
  pagedPostsList: any[] = [];
  totalPosts: number = 0;
  pageIndex: number = 0;

  constructor(
    private router: Router,
    private postService: PostService,
    private _snackBar: MatSnackBar,
    private activatedRoute: ActivatedRoute,
    public commentService: CommentService
  ) {}

  onPostDelete(postId: number) {
    this.postsList = this.postsList.filter((post) => post.id !== postId);
    this.loadPosts()
  }

  onPageChange(event: any) {
    const pageEvent = event as PageEvent;
    this.commentService.pageIndex = pageEvent.pageIndex;
    this.loadPosts(this.pageIndex);
  }

  loadPosts(page: number = 0): void {
    this.activatedRoute.queryParams.subscribe((params) => {
      if (params['search'] == undefined) {
        if (params['CoP'] == undefined) {
          this.postService.getAllPosts(this.commentService.pageIndex, 4).subscribe((post) => {
            this.postsList = [...post.content];
            this.totalPosts = this.postsList[0]?.totalPosts;
          });
        } else {
          this.postService
            .getAllPostsByCop(this.commentService.pageIndex, 4, params['CoP'])
            .subscribe((post) => {
              this.postsList = [...post.content];
              this.totalPosts = this.postsList[0]?.totalPosts;
            });
        }
      } else {
        this.postService
            .getPostsContaining(this.commentService.pageIndex, 4, params['search'])
            .subscribe((post) => {
              this.postsList = [...post.content];
              this.totalPosts = this.postsList[0]?.totalPosts;
            });
      }
    });
  }

  resetPagination(): void {
    this.pageIndex = 0;
  }

  ngOnInit(): void {
    this.loadPosts();
  }
}
