const https = require('https');
const QueryString = require('querystring');
const API_DOMAIN = 'apis.justwatch.com';

class JustWatch {

  constructor(options) {
    this._options = Object.assign({locale: 'ko_KR'}, options);
  }

  request(method, endpoint, params) {
    return new Promise((resolve, reject) => {
      params = Object.assign({}, params);
      var reqData = {
        protocol: 'https:',
        hostname: API_DOMAIN,
        path: '/content' + endpoint,
        method: method,
        headers: {}
      };

      var body = null;
      if (method === 'GET') {
        if (Object.keys(params) > 0) {
          reqData.path = reqData.path + '?' + QueryString.stringify(params);
        }
      } else {
        body = JSON.stringify(params);
        reqData.headers['Content-Type'] = 'application/json';
      }
      const req = https.request(reqData, (res) => {
        let buffers = [];
        res.on('data', (chunk) => {
          buffers.push(chunk);
        });
        res.on('end', () => {
          var output = null;
          try {
            output = Buffer.concat(buffers);
            output = output.toString();
            output = JSON.parse(output);
          } catch (error) {
            if (res.statusCode !== 200) {
              reject(new Error("request failed with status " + res.statusCode + ": " + res.statusMessage));
            } else {
              reject(error);
            }
            return;
          }

          if (output.error) {
            reject(new Error(output.error));
          } else {
            resolve({
              output: output,
              statusCode: res.statusCode
            });
          }
        });
      });
      req.on('error', (error) => {
        reject(error);
      });
      req.end(body);
    });
  }

  async search(options = {}) {
    if (typeof options === 'string') {
      options = {query: options};
    } else {
      options = Object.assign({}, options);
    }
    var params = {
      'content_types': null,
      'presentation_types': null,
      'providers': null,
      'genres': null,
      'languages': null,
      'release_year_from': null,
      'release_year_until': null,
      'monetization_types': null,
      'min_price': null,
      'max_price': null,
      'scoring_filter_types': null,
      'cinema_release': null,
      'query': null,
      'page': null,
      'page_size': null
    };
    var paramKeys = Object.keys(params);
    for (const key in options) {
      if (paramKeys.indexOf(key) === -1) {
        throw new Error("invalid option '" + key + "'");
      } else {
        params[key] = options[key];
      }
    }
    var locale = encodeURIComponent(this._options.locale);
    return await this.request('POST', '/titles/' + locale + '/popular', params);
  }
}

module.exports = JustWatch;