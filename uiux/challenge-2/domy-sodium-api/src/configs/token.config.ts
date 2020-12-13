import { SignOptions } from "jsonwebtoken";

export const SignConfig: SignOptions = {
    issuer:     'DOMYSODIUM',
    subject:    'rocamanuelignacio@gmail.com',
    audience:   'http://github.com/rocamanuelignacio',
    expiresIn:  "12h",
    algorithm:  "RS256"
}