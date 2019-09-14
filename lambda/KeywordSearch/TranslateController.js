'use strict';
const AWS = require('aws-sdk');
const translate = new AWS.Translate();

class TranslateController {

  constructor() {
    this.params = {
      SourceLanguageCode: 'ko',
      TargetLanguageCode: 'en'
    };
  }

  translateText(text) {
    return new Promise((resolve, reject) => {
      this.params.Text = text;
      translate.translateText(this.params, (err, result) => {
        if (err) {
          reject();
        } else {
          resolve(result.TranslatedText);
        }
      });
    });
  }
}

module.exports = TranslateController;