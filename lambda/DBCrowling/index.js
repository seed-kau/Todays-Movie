const got = require('got');
const cheerio = require('cheerio');
const S3Controller = require('./S3Controller');
const s3Controller = new S3Controller();

let arr = new Array();
exports.handler = (event, context, callback) => {
  got('https://m.imdb.com/chart/top').then(res => {
    var re = cheerio.load(res.body);

    let resultList = re('.row.chart-row>div.col-xs-12.col-md-6>div.media>a>span>h4');
    let reText = resultList.text();

    for (let i = 2; i < 1000; i = i + 4) {
      let re = reText.split('\n');
      let reTitle = re[i];
      let reYeartemp = re[i + 1];
      let reYear = reYeartemp.split(/\(|\)/gi)[1];
      arr.push(reTitle + '+' + reYear);
    }

    s3Controller.require(arr);
  });
};