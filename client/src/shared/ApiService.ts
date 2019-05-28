import { HttpClient, RequestInit } from 'aurelia-fetch-client'

export class ApiService {
  private httpClient: HttpClient;
  private baseUrl = "http://localhost:8080/";
  public _currentUserId: string;

  public persistStorage: boolean;

  constructor() {
    this.httpClient = new HttpClient();
    this.httpClient.configure(config => {
      config.withBaseUrl(this.baseUrl);
      config.withInterceptor({
        response(response) {
          return response;
        },
        responseError(error) {
          return error;
        }
      })
    });
  }

  public get currentUserId(): string {
    return (this.persistStorage ? localStorage.getItem('currentUserId') : sessionStorage.getItem('currentUserId'));
  }

  async authenticate(request: any = {}) {
    // request.grant_type = "password";

    let response = await this.httpClient
      .fetch("/auth/token", {
        method: "post",
        body: JSON.stringify(request)
      })
      .then(httpResponse => httpResponse.json());

    // this.accessToken = response.access_token;
    // this._refreshToken = response.refresh_token;

    // if (this.accessToken === undefined || this._refreshToken === undefined) {
    // return;
    // }

    // if (this.persistStorage) {
    //   localStorage.setItem('access_token', this.accessToken);
    //   localStorage.setItem('refresh_token', this._refreshToken);
    // } else {
    //   sessionStorage.setItem('access_token', this.accessToken);
    //   sessionStorage.setItem('refresh_token', this._refreshToken);
    // }

    // this._isAuthenticated = true;
    await this.getCurrentUser(); // write to local/session storage asap.
    return response;
  }

  async getCurrentUser() {
    let currentUser = await this.fetch("/login");

    this._currentUserId = currentUser.id;
    // this._isAdmin = currentUser.isAdmin;

    if (this.persistStorage) {
      localStorage.setItem("currentUserId", this._currentUserId);
      //   localStorage.setItem('isAdmin', String(this._isAdmin));
    } else {
      sessionStorage.setItem("currentUserId", this._currentUserId);
      //   sessionStorage.setItem('isAdmin', String(this._isAdmin));
    }

    return currentUser;
  }

  public get isAdmin(): boolean {
    return false;
  }

  public get isAuthenticated(): boolean {
    // return false;
    return true;
  }

  get(input: Request | string): Promise<any> {
    return this.fetch(input);
  }

  post(input: Request | string, body: any = {}): Promise<any> {
    let init: RequestInit = {
      method: "post",
      body: JSON.stringify(body)
    };
    return this.fetch(input, init);
  }

  login(body: any = {}): Promise<any> {
    let init: RequestInit = {
      method: "post",
      body: JSON.stringify(body)
    };
 init.headers = {}; // otherwise headers are undefinedreturn this.httpClient.fetch(input, init)
    let resp;
    return this.httpClient.fetch('login', init)
      .then(async httpResponse => {
        // if token exists but we get a 401, means token is expired.
        if (httpResponse.status === 401 && this.shouldRetry && this.isAuthenticated) {
          this.shouldRetry = false;
          return this.fetch('login', init);
        }
        this.shouldRetry = true;
        resp = await httpResponse.json();
        localStorage.setItem('userId', resp.id)
        console.log(resp);
        return httpResponse.json();
      })
      .catch(errors => errors);

  }

  delete(input: Request | string): Promise<any> {
    let init: RequestInit = {
      method: "delete"
    };
   return this.fetch(input, init);
  }

  shouldRetry = true;
  private fetch(input: Request | string, init: RequestInit = {}) {
    init.headers = {}; // otherwise headers are undefined

    // if (this.isAuthenticated) {
    //   if (this.persistStorage) {
    //     init.headers["Authorization"] = `Bearer ${localStorage.getItem('access_token')}`;
    //   } else {
    //     init.headers["Authorization"] = `Bearer ${sessionStorage.getItem('access_token')}`;
    //   }
    // }

    return this.httpClient.fetch(input, init)
      .then(async httpResponse => {
        // if token exists but we get a 401, means token is expired.
        if (httpResponse.status === 401 && this.shouldRetry && this.isAuthenticated) {
          // await this.refreshToken();
          this.shouldRetry = false;
          return this.fetch(input, init);
        }
        this.shouldRetry = true;
        return httpResponse.json();
      })
      .catch(errors => errors);
  }

}
