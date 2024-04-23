import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Feedback } from '../model/feedback.model';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private baseUrl = environment.apiUrl + '/feedback';

  constructor(private http: HttpClient) { }

  saveFeedback(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>(this.baseUrl, feedback);
  }

  getAllFeedback(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(this.baseUrl);
  }
}
