/**
    * Created by lex on 01/10/16.
    */

var webSocket = $.simpleWebSocket({url: " ws://" + window.location.host + "/ws/getNewImages"});

function webSocketImage(){

    console.log("test");
    // reconnected listening
    webSocket.listen(function (message) {
        console.log(" Received ");
//                    var prettyJson = JSON.stringify(JSON.parse(message), null, 2);
//                    $("#response").val(prettyJson + "\r\n" + $("#response").val())
        console.log(message);
        $("#response").html(message);

    });
}

$(document).ready(function(){

    applyAjax("body");
    webSocketImage();


});


function applyAjax(container){
    $(container + ' a').on('click', function(e){
        e.preventDefault();
        var pageRef = $(this).attr('href');

        if (pageRef.split("/")[1] == "board"){
            callPage(pageRef, true)
        } else{
            callPage(pageRef, false)
        }
    });
}

function callPage(pageRefInput, ws){
    // Using the core $.ajax() method
    $.ajax({
        url: pageRefInput,

        type: "GET",

        dataType : 'text',

        success: function( response ) {
            // console.log('the page was loaded', response);
            $('.content').html(response);
            applyAjax(".content");
            if (ws){
            }
        },

        error: function( error ) {
            console.log('the page was NOT loaded', error);
        },

        complete: function( xhr, status ) {
            console.log("The request is complete!");
        }
    });
}
