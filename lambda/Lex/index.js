'use strict';

const SecretsManagerController = require('./SecretsManagerController');
const LexController = require('./LexController');
const GoogleTranslateUtil = require('./util/GoogleTranslateUtil');
const PapagoTranslateUtil = require('./util/PapagoTranslateUtil');
const QueryGenerator = require('./QueryGenerator');
const DynamoDBController = require('./DynamoDBController');

const secretsManagerControllerGoogle = new SecretsManagerController('todays_google_translation');
const secretsManagerControllerPapago = new SecretsManagerController('todays_papago_translation');

const BOT_ALIAS = [process.env.BOT_ALIAS1, process.env.BOT_ALIAS2, process.env.BOT_ALIAS3];
const BOT_NAME = [process.env.BOT_NAME1, process.env.BOT_NAME2, process.env.BOT_NAME3];
const dynamoDBController = new DynamoDBController();
const queryGenerator = new QueryGenerator();

exports.handler = async (event, context, callback) => {
  const lexType = event.bot_type;
  const lexController = new LexController(BOT_NAME[lexType], BOT_ALIAS[lexType]);

  let googleAPIKey = await secretsManagerControllerGoogle.getSecretValue();
  googleAPIKey = googleAPIKey.GOOGLE_TRANSLATION;
  let papagoAPIId = await secretsManagerControllerPapago.getSecretValue();
  papagoAPIId = papagoAPIId.PAPAGO_TRANSLATION_ID;
  let papagoAPIPw = await secretsManagerControllerPapago.getSecretValue();
  papagoAPIPw = papagoAPIPw.PAPAGO_TRANSLATION_SECRET;

  const googleTranslate = new GoogleTranslateUtil(googleAPIKey);
  let message = await googleTranslate.translate(event.message);

  let messagingEvent = {
    sender: {
      id: event.id
    },
    recipient: {
      id: event.id
    },
    message: {
      text: message
    }
  };

  let result = await lexController.postText(messagingEvent);

  let response = {
    isFinish: false,
    reply: result,
    url: "",
    year: 0
  };

  // 스무 고개
  if (result.includes("twenty_result : ")) {
    result = result.split("twenty_result : ")[1];
    result = await queryGenerator.getTwentyQuestionQuery(result);

    if (result[0] === "") {
      result = await dynamoDBController.getAllItems();
    } else {
      result = await dynamoDBController.getItems(result[0], result[1]);
    }

    response.isFinish = true;
    response.reply = result.reply;
    response.url = result.url;
    response.year = result.year;
  }
  // 커스텀 1
  else if (result.includes("first_result : ")) {
    result = result.split("first_result : ")[1];
    result = await queryGenerator.getFirstQuery(result);

    if (result[0] === "") {
      result = await dynamoDBController.getAllItems();
    } else {
      result = await dynamoDBController.getItems(result[0], result[1]);
      if (result.year === 0) {
        result = await dynamoDBController.getAllItems();
      }
    }

    response.isFinish = true;
    response.reply = result.reply;
    response.url = result.url;
    response.year = result.year;
  }

  // 커스텀 2
  else if (result.includes("second_result : ")) {
    result = result.split("second_result : ")[1];
    result = await queryGenerator.getSecondQuery(result);

    if (result[0] === "") {
      result = await dynamoDBController.getAllItems();
    } else {
      result = await dynamoDBController.getItems(result[0], result[1]);
      if (result.year === 0) {
        result = await dynamoDBController.getAllItems();
      }
    }

    response.isFinish = true;
    response.reply = result.reply;
    response.url = result.url;
    response.year = result.year;
  }

  // 커스텀 3 - 랜덤 처리
  else if (result.includes("third_result : ")) {
    result = await dynamoDBController.getAllItems();

    response.isFinish = true;
    response.reply = result.reply;
    response.url = result.url;
    response.year = result.year;
  }
  // 질의
  else {
    const papagoTranslate = new PapagoTranslateUtil(papagoAPIId, papagoAPIPw);
    result = await papagoTranslate.translate(result);
    response.reply = result;
  }

  callback(null, response);

};