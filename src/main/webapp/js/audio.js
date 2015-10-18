/**
 * Created by Unger on 10/18/15.
 */

$(document).ready(function(){
    $("input:checkbox").change(function() {
        var state = $(this).is(":checked") ? "on" : "off"
        $.ajax({
            url: 'set',
            type: 'POST',
            data: { strID:$(this).attr("id"), strState:state }
        });
    });
});