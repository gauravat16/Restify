
var lamdaRequest = function lambdaRequest(path,alias,commandType,argsCommandType,command,argsCommand){
    this.path = path;
    this.alias = alias;
    this.commandType = commandType;
    this.argsCommandType = argsCommandType;
    this.command = command;
    this.argsCommand = argsCommand;
}

var lamdaResponse = function lamdaResponse(processExecCode,output){
    this.output = output;
    this.processExecCode = processExecCode;
}

function sendGetRequest(urlEndPoint, callback){
    $.get(urlEndPoint,callback(data));
}

function sendPostRequest(urlEndPoint, callback, payload){

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

function getJsonRequestFromTextBox(){
    var jsonString = document.getElementById("json-req").value;
    var req = JSON.parse(jsonString);
    return req;
}
function lamdaPostRequest(){
    hideResultBoxes();
    var url = 'http://localhost:8080/lambda/post/execute';
    sendPostRequest(url,displayResult,getJsonRequestFromTextBox());
}
function displayResult(lamdaResponse){
    if(lamdaResponse.processExecCode==-1){
        var boxFailParent = document.getElementById('result-fail-article');
        var boxFail = document.getElementById('result-fail-msg');
        boxFailParent.style.display = 'inherit';
        boxFail.innerHTML = lamdaResponse.output;

    }else{
        var boxSuccessParent = document.getElementById('result-success-article');
        var boxSuccess = document.getElementById('result-success-msg');
        boxSuccessParent.style.display = 'inherit';
        boxSuccess.innerHTML = lamdaResponse.output;
    }
}

function hideResultBoxes(){
    var boxSuccess = document.getElementById('result-success-article');
    var boxFail = document.getElementById('result-fail-article');
    boxSuccess.style.display = 'none';
    boxFail.style.display = 'none';
}
