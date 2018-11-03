
/**
 * Lambda request Object
 * @param {*} path 
 * @param {*} alias 
 * @param {*} commandType 
 * @param {*} argsForCommandType 
 * @param {*} command 
 * @param {*} argsForCommand 
 */
var LamdaRequest = function lambdaRequest(path, alias, commandType, argsForCommandType, command, argsForCommand, waitTime) {
    this.path = path;
    this.alias = alias;
    this.commandType = commandType;
    this.argsForCommandType = argsForCommandType;
    this.command = command;
    this.argsForCommand = argsForCommand;
    this.waitTime = waitTime;
}

/**
 * Lambda History Object.
 * @param {*} path 
 * @param {*} alias 
 * @param {*} commandType 
 * @param {*} argsForCommandType 
 * @param {*} command 
 * @param {*} argsForCommand 
 * @param {*} waitTime 
 * @param {*} lamdaResponse 
 * @param {*} timeStamp 
 */
var LambdaHistory = function lambdaHistory(path, alias, commandType, argsForCommandType, command, argsForCommand, waitTime, lamdaResponse, timeStamp) {
    this.path = path;
    this.alias = alias;
    this.commandType = commandType;
    this.argsForCommandType = argsForCommandType;
    this.command = command;
    this.argsForCommand = argsForCommand;
    this.waitTime = waitTime;
    this.response = lamdaResponse;
    this.timeStamp = timeStamp;
}

/**
 * Lambda response object
 * @param {*} processExecCode 
 * @param {*} output 
 */
var LamdaResponse = function lamdaResponse(processExecCode, output) {
    this.output = output;
    this.processExecCode = processExecCode;
}

/**
 * Execute an get request.
 * @param {*} urlEndPoint
 * @param params for get request
 * @param {*} callback 
 */
function sendGetRequest(urlEndPoint, params, callback) {
    $.get(urlEndPoint, params, callback);
}

/**
 * Execute an post request.
 * @param {*} urlEndPoint 
 * @param {*} callback 
 * @param {*} payload 
 */
function sendPostRequest(urlEndPoint, callback, payload) {

    $.ajax({
        type: 'post',
        dataType: 'json',
        url: urlEndPoint,
        data: JSON.stringify(payload),
        contentType: "application/json; charset=utf-8",
        traditional: true,
        success: callback
    });
}

/**
 * Get json text from text box
 */
function getJsonRequestFromTextBox() {
    var jsonString = document.getElementById("json-req").value;
    var req = JSON.parse(jsonString);
    return req;
}

/**
 * Hides previous results and send a post request for data in getJsonRequestFromTextBox()
 */
function lamdaPostRequest() {
    hideResultBoxes();
    var url = '/lambda/post/execute';
    sendPostRequest(url, displayResult, getJsonRequestFromTextBox());
}

/**
 * Call back function, displays result of lambda.
 * @param {*} lamdaResponse 
 */
function displayResult(lamdaResponse) {
    if (lamdaResponse.processExecCode == -1) {
        var boxFailParent = document.getElementById('result-fail-article');
        var boxFail = document.getElementById('result-fail-msg');
        boxFailParent.style.display = 'inherit';
        boxFail.innerHTML = lamdaResponse.output;

    } else {
        var boxSuccessParent = document.getElementById('result-success-article');
        var boxSuccess = document.getElementById('result-success-msg');
        boxSuccessParent.style.display = 'inherit';
        boxSuccess.innerHTML = lamdaResponse.output;
    }
}

/**
 * Hides boxes.
 */
function hideResultBoxes() {
    var boxSuccess = document.getElementById('result-success-article');
    var boxFail = document.getElementById('result-fail-article');
    boxSuccess.style.display = 'none';
    boxFail.style.display = 'none';
}

/**
 * Current page number
 */
var currentPageNumber = 0;

/**
 * Function to get history data for page number.
 */
function getHistoryPageForPageNumber() {
    var url = "/lambda/history";
    sendGetRequest(url, { 'page': currentPageNumber++ }, consumeHistoryJson);
}

/**
 * Consumes history json data and will convert them into LambdaHistory objects.
 * @param {*} jsonData 
 */
function consumeHistoryJson(jsonData) {
    var lambdaHistoryList = [];
    jsonData.forEach(element => {
        var historyObj = Object.assign(new LambdaHistory, element);
        lambdaHistoryList.push(historyObj);
    });
    displayHistory(lambdaHistoryList);
}

/**
 * Displays history in a table.
 * @param {*} lambdaHistoryList 
 */
function displayHistory(lambdaHistoryList) {
    var tableBody = $("#history-table-body");

    lambdaHistoryList.forEach(historyObj => {
        var markup = "<tr>";
        Object.keys(historyObj).forEach(key => {
            if (key === 'response') {
                markup += '<td>' + JSON.stringify(historyObj[key]) + '</td>';

            } else {
                markup += '<td>' + historyObj[key] + '</td>';

            }
        })
        markup += '</tr>';
        tableBody.append(markup);
    });
}

/**
 * Log data
 * @param {*} data 
 */
function logData(data) {
    console.log(data);
}

/**
 * Loads header
 */
$(document).ready(function () {
    $("#header").load("/html/header.html");
});

