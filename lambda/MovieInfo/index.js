let https = require('https');

exports.handler = function (event, context, callback) {
  let buffer = [];
  let arr = [];
  const naver_key = process.env.naver_key;
  const naver_ID = process.env.naver_ID;
  let query = event.queryStringParameters.title;
  let naver_header = {
    hostname: "openapi.naver.com",
    path: "/v1/search/movie.json?query=" + encodeURI(query),
    timeout: 2000,
    method: 'GET',
    headers: {
      "X-Naver-Client-Id": naver_ID,
      "X-Naver-Client-Secret": naver_key
    }
  };
  let requesting = https.request(naver_header, function (res) {
    res.on('data', chunk => {
      let items = JSON.parse(chunk).items;
      buffer.push(JSON.stringify(items));
      buffer = buffer.toString("utf-8");
      arr = JSON.parse(buffer);
      arr = sortByKey(arr, 'title');
    });

    res.on('end', () => {
      function strip(html) {
        html = html.replace(/<b>/g, "");
        html = html.replace(/<\/b>/g, "");
        return html;
      }

      let response = {
        "isBase64Encoded": true | false,
        "statusCode": 200,
        "body": JSON.stringify(arr)
      }
      callback(null, response);
    });
  });

  requesting.on('error', (e) => {
    console.error(e.message);
  });
  requesting.end();
  
  function sortByKey(array, key) {
    return array.sort(function (a, b) {
      let x = a[key];
      let y = b[key];
      return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
  }
}