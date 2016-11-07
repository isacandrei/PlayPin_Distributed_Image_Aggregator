/**
    * Created by lex on 01/10/16.
    */

// var webSocketReceive = $.simpleWebSocket({url: " ws://" + window.location.host + "/socket"});

$(document).ready(function(){

    applyAjax("body");

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
    if(typeof socket != 'undefined') socket.close()
    $.ajax({
        url: pageRefInput,

        type: "GET",

        dataType : 'text',

        success: function( response ) {
            // console.log('the page was loaded', response);
            $('.content').html(response);
            applyAjax(".content");
        },

        error: function( error ) {
            console.log('the page was NOT loaded', error);
        },

        complete: function( xhr, status ) {
            console.log("The request is complete!");
        }
    });
}

function setPinLayout(){
    $('#pinto-container').pinto({
        itemWidth:250,
        gapX:10,
        gapY:20,
    });
}



//******** design

