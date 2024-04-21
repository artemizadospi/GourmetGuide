import {
  Directive,
  Input,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';

import { AuthService } from 'src/app/auth/service/auth.service';

@Directive({
  selector: '[appPermission]',
})
export class PermissionDirective implements OnInit {
  @Input('appPermission')
  set appPermission(roleToCheck: string) {
    if (this.authService.getRole()?.toString() === roleToCheck) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
    console.log(roleToCheck);
  }
  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {}
}
