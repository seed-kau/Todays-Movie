const request = require("request");

class TheMoiveDB {
  constructor(key) {
    this.options = {
      method: 'GET',
      url: 'https://api.themoviedb.org/3/search/keyword',
      qs:
          {
            page: '1',
            api_key: key
          },
      body: '{}'
    };

    this.movie_options = {
      method: 'GET',
      url: '',
      qs:
          {
            language: 'korean',
            api_key: key,
            include_adult: true
          },
      body: '{}'
    };
  }

  getKeywordId(keyword) {
    this.options.qs.query = keyword;
    return new Promise((resolve, reject) => {
      request(this.options, function (error, response, body) {
        if (error) throw new Error(error);

        let keywordId = null;
        if (JSON.parse(body).results[0]) {
          keywordId = JSON.parse(body).results[0].id;
        }
        resolve(keywordId);
      });
    });
  }

  getMoviesList(id) {
    this.movie_options.url = 'https://api.themoviedb.org/3/keyword/' + id + '/movies';
    return new Promise((resolve, reject) => {
      request(this.movie_options, function (error, response, body) {
        if (error) throw new Error(error);

        let list = null;
        if (JSON.parse(body).status_code !== 34) {
          list = JSON.parse(body).results
        }
        resolve(list);
      });
    })
  }
}

module.exports = TheMoiveDB;