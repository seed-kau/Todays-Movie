'use strict';

const SecretsManagerController = require('./SecretsManagerController');
const TheMovieDBController = require('./TheMoiveDBController');
const TranslateController = require('./TranslateController');

const translateController = new TranslateController();
const secretsManagerControllerTheMovieDB = new SecretsManagerController('the_movie_database');


exports.handler = async (event, context, callback) => {
  const keyword = await translateController.translateText(event.keyword);

  let key = await secretsManagerControllerTheMovieDB.getSecretValue();
  key = key.THE_MOVIE_DATABASE;
  const theMovieDBController = new TheMovieDBController(key);

  const id = await theMovieDBController.getKeywordId(keyword);
  const moviesList = await theMovieDBController.getMoviesList(id);

  await callback(null, moviesList);
};