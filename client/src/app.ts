import { PLATFORM } from 'aurelia-pal';
import { Router, RouterConfiguration } from 'aurelia-router'
import { autoinject } from "aurelia-framework";

@autoinject
export class App {

  constructor(private router: Router){}

  public configureRouter(config: RouterConfiguration, router) {
    config.title = 'User Management';
    config.options.pushState = true;

    config.map([
      {
        route: ['', 'login'],
        name: 'login',
        title: 'Login',
        moduleId: PLATFORM.moduleName('public/login'),
        nav: true
      },
      {
        route: ['register'],
        name: 'register',
        title: 'Register',
        moduleId: PLATFORM.moduleName('public/register'),
        nav: true
      }
    ])

    this.router = router;
  }
}
