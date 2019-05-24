import { ApiService } from './shared/ApiService';
import { PLATFORM } from 'aurelia-pal';
import { NavigationInstruction, Next, Redirect, Router, RouterConfiguration, RouteConfig } from 'aurelia-router'
import { autoinject } from "aurelia-framework";
import { HttpClient } from 'aurelia-fetch-client'

@autoinject
export class App {

  constructor(private router: Router, private httpClient: HttpClient) {
    httpClient.configure(config => {
      config.useStandardConfiguration();
      config.withBaseUrl("https://localhost:8080/api");
    })
  }

  public configureRouter(config: RouterConfiguration, router) {
    config.title = 'User Management';

    const handleUnknownRoutes = (instruction: NavigationInstruction): RouteConfig => {
      return {
        route: 'login',
        moduleId: PLATFORM.moduleName('public/login')
      }
    }

    config.options.pushState = true;
    config.mapUnknownRoutes(handleUnknownRoutes);
    config.addAuthorizeStep(AuthorizeStep);

    config.map([
      {
        route: ['', 'login'],
        name: 'login',
        title: 'Login',
        moduleId: PLATFORM.moduleName('public/login'),
      },
      {
        route: 'register',
        name: 'register',
        title: 'Register',
        moduleId: PLATFORM.moduleName('public/register'),
      },
      {
        route: 'forgot-password',
        name: 'forgot-password',
        title: 'forgot-password',
        moduleId: PLATFORM.moduleName('public/forgot-password')
      },
      {
        route: 'app',
        name: 'app',
        moduleId: PLATFORM.moduleName('app/app-index'),
        settings: { auth: true }
      }
    ])

    this.router = router;
  }
}

@autoinject
class AuthorizeStep {

  constructor(private api: ApiService) { }

  run(navigationInstruction: NavigationInstruction, next: Next): Promise<any> {
    if (navigationInstruction.config.name == "admin") {
      // var isAdmin = this.api.isAdmin;
      var isAdmin = true;
      if (!isAdmin) {
        return next.cancel(new Redirect('admin-sign-in'));
      }
    }

    if (navigationInstruction.getAllInstructions().some(i => i.config.settings.auth)) {
      // var isAuthenticated = this.api.isAuthenticated;
      var isAuthenticated = true;
      if (!isAuthenticated) {
        return next.cancel(new Redirect('login'));
      }
    }

    return next();
  }
}
