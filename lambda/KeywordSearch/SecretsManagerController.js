'use strict';
const AWS = require('aws-sdk');
const client = new AWS.SecretsManager({
  region: "us-east-1"
});

class SecretsManagerController {
  constructor(secretName) {
    this.secretName = secretName;
  }

  // API key 가져오기
  getSecretValue() {
    console.log("secret name :", this.secretName);

    return new Promise((resolve, reject) => {
      client.getSecretValue({SecretId: this.secretName}, (err, data) => {
        if (err) {
          console.log(err);
        } else {
          let secret = JSON.parse(data.SecretString);
          resolve(secret);
        }
      });
    });
  }

  // secretName 설정
  setSecretName(secretName) {
    this.secretName = secretName;
  }
}

module.exports = SecretsManagerController;