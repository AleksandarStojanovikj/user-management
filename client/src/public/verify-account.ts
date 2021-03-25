import { autoinject } from "aurelia-framework";
import { ApiService } from "shared/ApiService";
import { Router } from "aurelia-router";

@autoinject
export class VerifyAccount {
    code: VerficationCode;

    constructor(private api: ApiService, private router: Router) { }

    async verifyAccount() {
        let response = await this.api.get(`verify?verificationCode=${this.code.verificationCode}`);
        if (response === undefined) {

        }
        else {
            this.router.navigate("login");
        }
    }

}

class VerficationCode {
    verificationCode: string;
}