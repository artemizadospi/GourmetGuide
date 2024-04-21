import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth/service/auth.service';
import { Post } from './posts/model/post.model';
import { CommentService } from './posts/service/comment.service';
import { PostService } from './shared/service/posts.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  postsList: Post[] = [];
  post!: Post;
  title: String = "gourmetguide-frontend";
  searchQuery: string = '';

  constructor(
    public authService: AuthService,
    private postService: PostService,
    public router: Router,
    private commentService: CommentService
  ) {}

  logoutClicked() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

  homepageClicked() {
    this.commentService.pageIndex = 0
    this.router.navigate(['homepage']);
  }

  filterClick(cop: string) {
    this.commentService.pageIndex = 0
    this.router.navigate(['homepage'], {
      queryParams: { CoP: cop },
    });
  }

  onSearch(): void {
    this.commentService.pageIndex = 0
    this.router.navigate(['homepage'], {
      queryParams: { search: this.searchQuery },
    });
  }

  resetSearch(): void {
    this.searchQuery = ""
  }
}
