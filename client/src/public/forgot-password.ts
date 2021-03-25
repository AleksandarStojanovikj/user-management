import { autoinject } from "aurelia-framework";
import { ApiService } from "shared/ApiService";
import { Router } from "aurelia-router";

@autoinject
export class ForgotPassword {
    em: EmailPass;

    constructor(private api: ApiService, private router: Router) { }

    async forgotPassword() {
        let response = await this.api.post('forgot-password', this.em);
        if (response === undefined) {

        }
        else {
            console.log(this.em);
            this.router.navigate("login");
        }
    }

}

class EmailPass {
    email: string;
}
