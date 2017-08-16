<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<title>Insert title here</title>
<script>
$(document).ready(function () {

    $("#mood-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var moodData = {}
    
    moodData["userId"] = 1;
    moodData["moodState"]=$("#moodStateId").val();
    moodData["projectName"]="Java";
    alert('hiiii');

    

    $.ajax({
        type: "POST",
        contentType: "application/json",
      url: "#",
        data: JSON.stringify(moodData),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
					
	           /*  var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false); */

        },
        error: function (e) {

          /*   var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false); */

        }
    });

}
</script>

</head>
<body>
<form  id="mood-form">
<select  name="moodState" id="moodStateId">
<option value="">Select Mood</option>
<option value="1">Happy</option>
<option value="2">Sad</option>
<option value="3">Normal</option>
</select>

<br/>
<input type="submit" value="Submit"/>
</form>

</body>
</html>