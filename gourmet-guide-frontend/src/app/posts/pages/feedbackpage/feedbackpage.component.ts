import { Component, OnInit } from '@angular/core';
import { FeedbackService } from '../../service/feedback.service';
import  { Feedback } from '../../model/feedback.model'
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-feedbackpage',
  templateUrl: './feedbackpage.component.html',
  styleUrls: ['./feedbackpage.component.css']
})
export class FeedbackpageComponent implements OnInit {

  feedbackForm: FormGroup;
  feedback: Feedback = {
    name: '',
    email: '',
    message: '',
    selectOption: '',
    radioButtonOption: '',
    checkBoxOption: false
  };

  radioButtonOptions: string[] = ['Excellent', 'Good', 'Average', 'Poor'];
  selectOptions: string[] = ['General Feedback', 'Recipe Request', 'Bug Report'];

  constructor(private feedbackService: FeedbackService,
              private router: Router, private fb: FormBuilder,
              private _snackBar: MatSnackBar) {
                this.feedbackForm = this.fb.group({
                  name: ['', Validators.required],
                  email: ['', [Validators.required]],
                  message: ['', Validators.required],
                  selectOption: ['', Validators.required],
                  radioButtonOption: ['', Validators.required],
                  checkBoxOption: [false]
                });
               }

  ngOnInit(): void {
  }

  submitFeedback() {
    this.feedback = {
      name: this.feedbackForm.controls['name'].value,
      email: this.feedbackForm.controls['email'].value,
      message: this.feedbackForm.controls['message'].value,
      selectOption: this.feedbackForm.controls['selectOption'].value,
      radioButtonOption: this.feedbackForm.controls['radioButtonOption'].value,
      checkBoxOption: this.feedbackForm.controls['checkBoxOption'].value
    };
    this.feedbackService.saveFeedback(this.feedback).subscribe({
      next: () => {
        this.feedbackForm = this.fb.group({
          name: ['', Validators.required],
          email: ['', [Validators.required]],
          message: ['', Validators.required],
          selectOption: ['', Validators.required],
          radioButtonOption: ['', Validators.required],
          checkBoxOption: [false]
        });
        this.router.navigate(['homepage']);
        this._snackBar.open('Feedback sent with success!', 'Ok');
      },
      error: () => {
        this._snackBar.open('Feedback sending failed!', 'Ok');
      },
    });
  }

}
