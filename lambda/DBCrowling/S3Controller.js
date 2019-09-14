const AWS = require('aws-sdk');
const s3 = new AWS.S3();

class S3Controller {
  require(body) {
    return new Promise((resolve, reject) => {
      try {
        s3.putObject({
          Body: JSON.stringify(body),
          Bucket: 'movietitlelist',
          Key: 'movietitle.txt',
        }, (err, data) => {
          if (err) {
            console.log(err);
          } else {
            console.log("s3에 들어감");
            resolve(data);
          }
        });
      } catch (reject) {
        throw reject;
      }
    });
  }
}

module.exports = S3Controller;