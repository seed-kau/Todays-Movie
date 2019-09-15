'use strict';

class QueryGenerator {
  getTwentyQuestionQuery(data) {
    let values = data.split(", ");
    let expression = "";
    let attribute = {};

    let params1 = ["", "한국", "일본", "미국"];
    let params2 = ["", "0", "1980", "2000", "2010", "2100"];
    let params3 = ["", "액션", "코미디", "드라마", "공포", "로맨스", "애니메이션"];
    let params4 = ["", "0", "90", "120", "150", "1000"];

    // nation
    if (values[0] === '1' || values[0] === '2' || values[0] === '3') {
      expression += "(prdtNation = :nation1)";
      attribute[":nation1"] = {S: params1[values[0]]};
    } else if (values[0] == 4) {
      expression += "not contains(prdtNation, :nation1) and not contains(prdtNation, :nation2) and not contains(prdtNation, :nation3)";
      attribute[":nation1"] = {S: params1[1]};
      attribute[":nation2"] = {S: params1[2]};
      attribute[":nation3"] = {S: params1[3]};
    }

    // year
    if (values[1] !== '5') {
      if (expression !== "") {
        expression += " and ";
      }
      expression += "prdtYear between :year1 and :year2";
      attribute[":year1"] = {S: params2[values[1]]};
      attribute[":year2"] = {S: (params2[parseInt(values[1]) + 1] - 1).toString()};
    }

    // genre
    if (values[2] !== '7') {
      if (expression !== "") {
        expression += " and ";
      }
      expression += "contains(genres, :genre)";
      attribute[":genre"] = {S: params3[values[2]]};
    }

    // runtime
    if (values[3] !== '5') {
      if (expression !== "") {
        expression += " and ";
      }
      expression += "runtime between :time1 and :time2";
      attribute[":time1"] = {N: params4[values[3]]};
      attribute[":time2"] = {N: (params4[parseInt(values[3]) + 1] - 1).toString()};
    }

    let result = [expression, attribute];
    return result;
  }

  getFirstQuery(data) {
    let values = data.split(", ");
    let expression = "";
    let attribute = {};

    let params1 = ["", "한국", "일본", "미국", "영국"];
    let params2 = ["", "드라마", "액션", "어드벤처", "로맨스", "스릴러"];
    let params3 = ["", "0", "8.8", "9.2", "10"];
    let params4 = ["", "전체", "12", "15", "18"];

    // nation
    if (values[0] === '1' || values[0] === '2' || values[0] === '3' || values[0] === '4') {
      expression += "(prdtNation = :nation1)";
      attribute[":nation1"] = {S: params1[values[0]]};
    } else if (values[0] == 4) {
      expression += "not contains(prdtNation, :nation1) and not contains(prdtNation, :nation2) and not contains(prdtNation, :nation3) and not contains(prdtNation, :nation4)";
      attribute[":nation1"] = {S: params1[1]};
      attribute[":nation2"] = {S: params1[2]};
      attribute[":nation3"] = {S: params1[3]};
      attribute[":nation4"] = {S: params1[4]};
    }

    // genre
    expression += " and ";
    expression += "contains(genres, :genres)";
    attribute[":genres"] = {S: params2[values[1]]};

    // rate
    expression += " and ";
    expression += "rate between :rate1 and :rate2";
    attribute[":rate1"] = {S: params3[1]};
    attribute[":rate2"] = {S: (params3[parseInt(values[2]) + 1])};

    // limit
    expression += " and ";
    if (values[3] === '1' || values[3] === '2') {
      expression += "contains(limits, :limit1) and contains(limits, :limit2)";
      attribute[":limit1"] = {S: params4[1]};
      attribute[":limit2"] = {S: params4[2]};
    }
    if (values[3] === '3') {
      expression += "contains(limits, :limit1) and contains(limits, :limit2)";
      attribute[":limit1"] = {S: params4[2]};
      attribute[":limit2"] = {S: params4[3]};
    }
    if (values[3] === '4') {
      expression += "contains(limits, :limit1) and contains(limits, :limit2)";
      attribute[":limit1"] = {S: params4[3]};
      attribute[":limit2"] = {S: params4[4]};
    }

    let result = [expression, attribute];
    return result;
  }

  getSecondQuery(data) {
    let values = data.split(", ");
    let expression = "";
    let attribute = {};

    let params1 = ["", "한국", "미국", "일본", "한국"];
    let params2 = ["", "0", "10", "9"];
    let params3 = ["", "스릴러", "액션", "드라마", "애니메이션", "미스터리"];
    // nation
    expression += "(prdtNation = :nation1)";
    attribute[":nation1"] = {S: params1[values[0]]};

    // rate
    expression += " and ";
    expression += "rate between :rate1 and :rate2";
    attribute[":rate1"] = {S: params2[1]};
    attribute[":rate2"] = {S: (params2[parseInt(values[1]) + 1])};

    // runtime
    expression += " and ";
    expression += "runtime between :time1 and :time2";
    if (values[2] === '1') {
      attribute[":time1"] = {N: "0"};
      attribute[":time2"] = {N: "110"};
    }
    if (values[2] === '2') {
      attribute[":time1"] = {N: "80"};
      attribute[":time2"] = {N: "120"};
    }
    if (values[2] === '3') {
      attribute[":time1"] = {N: "120"};
      attribute[":time2"] = {N: "1000"};
    }
    if (values[2] === '4') {
      attribute[":time1"] = {N: "90"};
      attribute[":time2"] = {N: "150"};
    }

    // genre
    expression += " and ";
    expression += "contains(genres, :genre)";
    attribute[":genre"] = {S: params3[values[3]]};

    let result = [expression, attribute];
    return result;
  }

  // 랜덤 처리
  // getThirdQuery(data) {
  //     return result;
  // }
}

module.exports = QueryGenerator;