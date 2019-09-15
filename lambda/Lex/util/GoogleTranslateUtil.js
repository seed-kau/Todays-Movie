'use strict';

class GoogleTranslateUtil {
  constructor(key) {
    this.googleTranslate = require("google-translate")(key);
  }

  translate(message) {
    return new Promise((resolve, reject) => {
      this.googleTranslate.translate(message, 'ko', 'en', function (err, translation) {
        if (err) {
          console.log(err);
        }

        let result = translation.translatedText;
        resolve(result);
      });
    });
  }
}

module.exports = GoogleTranslateUtil;