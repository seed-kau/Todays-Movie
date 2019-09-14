const http = require('http');

class movielist {

  constructor(options) {
    this._options = Object.assign(options);
  }

  request(title, years) {
    return new Promise((resolve, reject) => {
      let movie_header = {
        hostname: "www.kobis.or.kr",
        path: "/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=키값&movieNm=" + encodeURI(title) + "&openStartDt=" + years + "&openEndDt=" + years,
        timeout: 2000,
        method: 'GET'
      };
      let buffer = [];

      let requesting = http.request(movie_header, function (res) {
        res.on('data', chunk => {
          buffer.push(chunk);
        });
        res.on('end', () => {
          let output = null;
          output = Buffer.concat(buffer);
          output = output.toString();
          output = JSON.parse(output);
          output = output.movieListResult.movieList[0];
          resolve({
            output: output
          });
        });
      });
      requesting.on('error', (e) => {
        console.error(e.message);
      });
      requesting.end(null);
    });
  }

  async search(title, years) {
    return await this.request(title, years);
  }
}

module.exports = movielist;