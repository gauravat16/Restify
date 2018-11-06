
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

$.ajaxSetup({ timeout: 1000 });

/**
 * Execute an get request.
 * @param {*} urlEndPoint
 * @param params for get request
 * @param {*} callback 
 */
function sendGetRequest(urlEndPoint, params, callback, failCallback) {
    $.get(urlEndPoint, params, callback).fail(failCallback);
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
var currentRowNumber = 0;

/**
 * Function to get history data for page number.
 */
function getHistoryPageForPageNumber() {
    var url = "/lambda/history";
    sendGetRequest(url, { 'page': currentPageNumber++ }, consumeHistoryJson, handleErrorsInHistoryRequest);
}

/**
 * Key - row id
 * Value - Object
 */
var lambdaHistoryMap = {};

/**
 * Consumes history json data and will convert them into LambdaHistory objects.
 * @param {*} jsonData 
 */
function consumeHistoryJson(jsonData) {
    if (handleMissingHisory(jsonData)) {
        return;
    }
    var lambdaHistoryList = [];
    jsonData.forEach(element => {
        var historyObj = Object.assign(new LambdaHistory, element);
        historyObj.timeStamp = formatDate(historyObj.timeStamp);
        lambdaHistoryList.push(historyObj);
    });

    //Sort in reverse order.
    lambdaHistoryList.sort(function (a, b) {
        return b.timeStamp - a.timeStamp;
    })
    displayHistory(lambdaHistoryList);
}

/**
 * Displays history in a table.
 * @param {*} lambdaHistoryList 
 */
function displayHistory(lambdaHistoryList) {
    var tableBody = $("#history-table-body");
    var rowIdFormat = "history-row-";

    lambdaHistoryList.forEach(historyObj => {

        //cache the data
        lambdaHistoryMap[currentRowNumber] = historyObj;

        var currRowId = rowIdFormat + currentRowNumber;
        var markup = "<tr class='clickable-row' id='" + currRowId + "'>";

        Object.keys(historyObj).forEach(key => {
            if (key !== 'response') {
                if (historyObj[key] === null) {
                    historyObj[key] = "n/a";
                }
                markup += '<td>' + historyObj[key] + '</td>';
            }
        });
        markup += '</tr>';
        tableBody.append(markup);

        setUpModal(currRowId, historyObj);

        scrollToRow(rowIdFormat);

        currentRowNumber += 1;
    });

}

/**
 * This function sets up the response modal.
 * 
 * @param {The row that is currently being appended} currRowId 
 * @param {The historyObj that is being converted to a row} historyObj 
 */
function setUpModal(currRowId, historyObj) {
    $("#" + currRowId).click(function () {
        var modalBody = document.getElementsByClassName('modal-card-body-data')[0];
        modalBody.innerHTML = JSON.stringify(historyObj['response']);
        $(".modal").addClass("is-active");
    });

    $(".delete").click(function () {
        $(".modal").removeClass("is-active");
    });
}

/**
 * This function scrolls down to an newly added row.
 * @param {The format of a row' Id} rowIdFormat 
 */
function scrollToRow(rowIdFormat) {
    var scrollToElemId = rowIdFormat + currentRowNumber;
    var scrollTo = $('#' + scrollToElemId);
    $("html,body").stop().animate({ scrollTop: scrollTo.offset().top }, 500);
}

/**
 * This function converts dot separated date string into date Object.
 * 
 * @param {dot separated date} dotDate 
 */
function formatDate(dotDate) {
    var splitDate = dotDate.split(".");
    var dd = splitDate[2], MM = splitDate[1], yyyy = splitDate[0], hh = splitDate[3], mm = splitDate[4], ss = splitDate[5];
    var date = new Date(yyyy + "-" + MM + "-" + dd + "T" + hh + ":" + mm + ":" + ss + "Z");
    return date;
}

/**
 * This function handles 500 & 0 response codes.
 * 
 * @param {response from sendGetRequest} responseStatus 
 */
function handleErrorsInHistoryRequest(responseStatus) {
    if (responseStatus.status == 500 || responseStatus.status == 0) {
        handleUIForBadResponse(true);
    }
}

/**
 * This function handles empty history data.
 * 
 * @param {data obtained by consumeHistoryJson} historyList 
 */
function handleMissingHisory(historyList) {
    if (historyList.length == 0) {
        handleUIForBadResponse(currentRowNumber == 0);
        return true;
    }

    return false;
}

/**
 * This function hides the loadMore button & historyTable(depends on shouldHideTable).
 * Also displays an error msg.
 * 
 * @param {a boolean that dictates if the history table is to be hidden} shouldHideTable 
 */
function handleUIForBadResponse(shouldHideTable) {
    var loadMoreBtn = document.getElementById("loadMore");
    var hiddenErrorDiv = document.getElementById("error-msg");
    var historyTable = document.getElementById("history-table");

    loadMoreBtn.style.visibility = 'hidden';
    if (shouldHideTable) {
        hiddenErrorDiv.innerHTML = "Sorry there is no data to display.";
        historyTable.classList.add("hidden");
        hiddenErrorDiv.classList.remove("hidden");
    } else {
        hiddenErrorDiv.innerHTML = "You have reached the end of history.";
        hiddenErrorDiv.classList.remove("hidden");
    }
}

/**
 * Log data
 * @param {*} data 
 */
function logData(data) {
    console.log(data);
}

/**
 * Functions ran at document load. 
 */
$(document).ready(function () {
    $("#header").load("/html/header.html");

    $("#loadMore").click(function (event) {
        event.preventDefault();
        getHistoryPageForPageNumber();
    });
});





