import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/posts/model/post.model';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  postList: Post[] | undefined;
  private _snackBar: any;
  router: any;

  constructor(private http: HttpClient) {}

  createPost(
    title: string,
    text: string,
    cop: string,
    image: File
  ): Observable<Post> {
    const formData = new FormData();
    formData.append('text', text);
    formData.append('title', title);
    formData.append('cop', cop);
    formData.append('image', image);
    return this.http.post<Post>(environment.apiUrl + '/posts/new', formData);
  }

  getAllPosts(page: number, size: number): Observable<any> {
    const token = localStorage.getItem('auth-token');
    const headers = new HttpHeaders({
        Authorization: `${token}`
    });
    const httpOptions = {
        headers: headers
    };
    const url = `${environment.apiUrl + '/posts'}?page=${page}&size=${size}`;
    console.log(token)
    console.log(url)
    return this.http.get(url);
  }

  getAllPostsByCop(page: number, size: number, cop: string): Observable<any> {
    const url = `${environment.apiUrl + '/posts' + '/CoP/' + cop}?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  getPostsContaining(page: number, size: number, contains: string): Observable<any> {
    const url = `${environment.apiUrl + '/posts' + '/search/' + contains}?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  deletePost(postId: number): Observable<any> {
    return this.http.delete(environment.apiUrl + '/posts/' + postId);
  }

  putSelectPin(postId: number): Observable<Post> {
    return this.http.put<Post>(
      environment.apiUrl + '/posts/' + postId + '/pin',
      postId
      );
    }
      
        putLikePost(postId: number): Observable<Post> {
          return this.http.put<Post>(
            environment.apiUrl + '/posts/' + postId + '/like',
            postId
          );
        }
}  
