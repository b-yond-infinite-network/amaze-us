import { createHmac } from 'crypto';

export class EncryptHelper {

  constructor() { }

  public async encryptPassword(password: string) {
    const hmac = createHmac('sha1', process.env.CRYPTO_PASSWORD);
    hmac.write(password);
    hmac.end();
    const hash = hmac.read().toString('hex');
    return hash;
  }

}