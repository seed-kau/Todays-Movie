'use strict';
const AWS = require('aws-sdk');

class LexController {
  constructor(botName, botAlias) {
    this.botName = botName;
    this.botAlias = botAlias;
    this.lexruntime = new AWS.LexRuntime({
      region: 'us-east-1'
    });
  }

  postText(messagingEvent) {
    console.log("postText");

    let params = {
      botAlias: this.botAlias,
      botName: this.botName,
      inputText: messagingEvent.message.text,
      userId: messagingEvent.sender.id,
    };

    return new Promise((resolve, reject) => {
      this.lexruntime.postText(params, (err, data) => {
        if (err) {
          console.log(err, err.stack);
          reject("Sorry...");
        } else {
          messagingEvent.intentName = data.intentName;
          messagingEvent.sessionAttributes = data.sessionAttributes;
          if (data.message) {
            messagingEvent.message.reply = data.message;
            messagingEvent.message.reply = replaceAll(messagingEvent.message.reply, "$", "\n");
          } else {
            messagingEvent.message.reply = "How can I help you? \n \n 1. 스무 고개 \n 2. 오늘 갬성";
          }
          messagingEvent.message.slotToElicit = data.slotToElicit;
          resolve(messagingEvent.message.reply);
        }
      });
    });
  }
}

function replaceAll(str, searchStr, replaceStr) {
  return str.split(searchStr).join(replaceStr);
}

module.exports = LexController;