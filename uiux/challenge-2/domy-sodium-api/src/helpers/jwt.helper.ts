import jsonwebtoken from 'jsonwebtoken';
import { SignConfig } from '../configs/token.config';

export class JWT {
    public async generateToken(params: any) {
        const privateKEY = process.env.TOKEN_SECURE_KEY
        const tokenOpts  = SignConfig;
        return jsonwebtoken.sign(params, privateKEY, tokenOpts);
    }
}