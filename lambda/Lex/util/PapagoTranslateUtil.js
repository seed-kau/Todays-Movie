'use strict';

const request = require('request');
const api_url = 'https://openapi.naver.com/v1/papago/n2mt';

class PapagoTranslate {
  constructor(id, secret) {
    this.id = id;
    this.secret = secret;
  }

  translate(text) {
    console.log('파파고 시작');
    var options = {
      url: api_url,
      form: {source: 'en', target: 'ko', text: text},
      headers: {
        'X-Naver-Client-Id': this.id,
        'X-Naver-Client-Secret': this.secret
      }
    };

    return new Promise((resolve, reject) => {
      request.post(options, function (error, response, body) {
        if (!error && response.statusCode == 200) {
          console.log('success');
          let result = JSON.parse(body).message.result.translatedText;
          resolve(result);
        } else {
          console.log('failed');
          console.log('error = ' + response.statusCode);
          console.log('error = ' + body);
          reject(body);
        }
      });
    });
  }
}

module.exports = PapagoTranslate;