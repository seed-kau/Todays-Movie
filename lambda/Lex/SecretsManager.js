'use strict';
const AWS = require('aws-sdk');
const client = new AWS.SecretsManager({
  region: "us-east-1"
});

class SecretsManagerController {
  constructor(secretName) {
    this.secretName = secretName;
  }

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
}

module.exports = SecretsManagerController;