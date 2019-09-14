const JustWatch = require('./JustWatch');
const justWatch = new JustWatch();

exports.handler = async (event, context, callback) => {
  const title = event.title;
  const years = Number(event.years);
  const jyears = years - 1;
  const data = [];

  try {
    let searchResult = await justWatch.search({query: title, release_year_from: jyears});
    let stat = searchResult.statusCode;

    const platform = searchResult.output.items[0].offers.map(offer => {
      return offer.provider_id;
    });

    const monet = searchResult.output.items[0].offers.map(offer => {
      return offer.monetization_type;
    });

    for (let i = 0; i < platform.length; i++) {
      if (platform[i] === 98) {
        platform[i] = -1;
      }
    }
    platform.sort(function (a, b) {
      return a - b;
    });

    for (let i = 0; i < platform.length - 1; i++) {
      let platnum = platform[platform.length - 1];
      let monarr = [];
      let what;
      for (let j = i; j < platform.length; j++) {
        if (platform[i] === platform[j]) {
          platnum = platform[i];
          break;
        }
      }

      for (let j = i; j < platform.length; j++) {
        if (platform[i] === platform[j]) {
          monarr.push(monet[i]);
          i = j;
        } else {
          i = j - 1;
          console.log(i);
          break;
        }
      }

      const montarr = monarr.filter((value, idx, arr) => monarr.indexOf(value) === idx);
      let platemp = {"platform_number": platnum};
      let montemp = {"monetization_type": montarr};
      what = Object.assign(platemp, montemp);
      data.push(what);
    }
    callback(null, data);

  } catch (error) {
    callback(null, data);
  }
};
