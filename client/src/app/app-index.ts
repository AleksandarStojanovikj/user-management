import { PLATFORM } from 'aurelia-pal';
import { autoinject } from "aurelia-framework";
import { Router, RouterConfiguration } from 'aurelia-router'

@autoinject
export class AppIndex {

  constructor(private router: Router) {

  }

  configureRouter(config: RouterConfiguration, router) {
    config.title = 'User management';

    config.options.pushState = true;

    config.map([
      {
        route: ['', 'edit-profile'],
        name: 'edit-profile',
        title: 'Edit profile',
        moduleId: PLATFORM.moduleName('app/edit-profile')
      },
      {
        route: 'change-password',
        name: 'change-password',
        title: 'Change password',
        moduleId: PLATFORM.moduleName('app/change-password')
      }
    ])
  }
}
