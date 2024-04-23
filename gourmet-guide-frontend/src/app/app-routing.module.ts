import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/pages/login/login.component';
import { SignupComponent } from './auth/pages/signup/signup.component';
import { HomepageComponent } from './posts/pages/homepage/homepage.component';
import { EditPostComponent } from './posts/components/edit-post/edit-post.component';
import { IsAuthenticatedGuard } from './auth/helpers/is-authenticated.guard';
import { CreatePostComponent } from './admin/pages/create-post/create-post.component';
import { AdminGuard } from './auth/helpers/admin.guard';
import { FeedbackpageComponent } from './posts/pages/feedbackpage/feedbackpage.component';

const routes: Routes = [
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: 'feedback', component: FeedbackpageComponent,
    canActivate: [IsAuthenticatedGuard]
  },
  {
    path: 'homepage',
    component: HomepageComponent,
    canActivate: [IsAuthenticatedGuard],
  },
  {
    path: 'createPost',
    component: CreatePostComponent,
    canActivate: [IsAuthenticatedGuard, AdminGuard],
  },
  {
    path: 'editPost',
    component: EditPostComponent,
    canActivate: [IsAuthenticatedGuard, AdminGuard],
  },
  { path: '**', redirectTo: '/login' },
];

@NgModule({
  declarations: [],
  imports: [[RouterModule.forRoot(routes)]],
  exports: [RouterModule],
})
export class AppRoutingModule {}
