/**
 * Created by Unger on 10/18/15.
 */

$(document).ready(function(){
    $("input:checkbox").change(function() {

        var enable = $(this).is(":checked");
        var output = $(this).attr("outputId");

        if(enable) {
            var input = $(this).attr("inputId");
            var url = "tie/"+output+"/"+input;
            $.ajax({
                url: url,
                type: 'POST'
                //data: { strID:$(this).attr("id"), strState:state }
            });
        } else {
            var url = "tie/"+output;
            $.ajax({
                url: url,
                type: 'DELETE'
            });
        }

    });
});

$(document).ready(function(){
    $("#allOff").click(function() {
        var url = "tie";
        $.ajax({
            url: url,
            type: 'DELETE'
        });
        location.reload();
    })
});

$(document).ready(function(){
    $("#refresh").click(function() {
        location.reload();
    })
});