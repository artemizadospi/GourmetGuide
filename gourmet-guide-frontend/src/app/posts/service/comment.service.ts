import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Comment } from '../model/comment.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private http: HttpClient) {}

  pageIndex: number = 0;
  commentPageIndex: number = 0;

  getAllCommentsByPostId(page: number, size: number, postId: number): Observable<any> {
    const url = `${environment.apiUrl + '/posts/' + postId + '/comments'}?page=${page}&size=${size}`;
    console.log("page nrrrrrrr" + page)
    return this.http.get(url);
  }

  createComment(text: string, postId: number): Observable<Comment> {
    return this.http.put<Comment>(
      environment.apiUrl + '/posts/' + postId + '/comment', text
    );
  }

}
