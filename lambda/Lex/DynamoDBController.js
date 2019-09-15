'use strict';
const AWS = require('aws-sdk');
AWS.config.update({region: 'us-east-1'});
const DDB = new AWS.DynamoDB({apiVersion: '2012-08-10'});

class DynamoDBController {
  constructor() {
    this.params = {
      TableName: process.env.DB_TABLE_NAME
    };
  }

  /* 보낼 데이터 재정의할 것. (전체 데이터 중에서 랜덤으로 몇 개 보내는 방식)*/
  getAllItems() {
    return new Promise((resolve, reject) => {
      let result = {
        reply: "매칭되는 영화를 찾을 수 없네요...",
        year: 0,
        url: ""
      };

      DDB.scan({TableName: process.env.DB_TABLE_NAME}, function (err, data) {
        if (err) {
          console.log(err);
          reject(result);
        } else {
          console.log("Success get item");

          let length = data.Items.length;
          if (length !== 0) {
            data = RandomItem(data.Items);
            result.reply = `${data.movieNm.S}(${data.prdtYear.S}) \n\n장르: ${data.genres.S}\n평점: ${data.rate.S}\n런타임: ${data.runtime.N}분`;
            result.year = data.prdtYear.S;
            result.url = data.url.S;
          }
          resolve(result);
        }
      });
    });
  }

  getItems(query, attribute) {
    this.params.FilterExpression = query;
    this.params.ExpressionAttributeValues = attribute;

    return new Promise((resolve, reject) => {
      let result = {
        reply: "매칭되는 영화를 찾을 수 없네요...",
        year: 0,
        url: ""
      };

      DDB.scan(this.params, function (err, data) {
        if (err) {
          console.log(err);
          reject(result);
        } else {
          console.log("Success get item");

          let length = data.Items.length;
          if (length !== 0) {
            data = RandomItem(data.Items);
            result.reply = `${data.movieNm.S}(${data.prdtYear.S}) \n\n장르: ${data.genres.S}\n평점: ${data.rate.S}\n런타임: ${data.runtime.N}분`;
            result.year = data.prdtYear.S;
            result.url = data.url.S;
          }
          resolve(result);
        }
      });
    });
  }
}

function RandomItem(item) {
  return item[Math.floor(Math.random() * item.length)];
}

module.exports = DynamoDBController;