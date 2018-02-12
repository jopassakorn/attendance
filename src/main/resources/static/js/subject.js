$(function(){
    $(".delete-subject-btn").click(function(){
         event.preventDefault();
         if(confirm("Are you sure to delete the subject ?")){
            deleteSubject($(this).attr("value"));
         }
    });

    $("#worklog-export-pdf-btn").click(function(){
        if(!$("#worklog-spec-date-radio").is(':checked') && !$("#worklog-semester-date-radio").is(':checked')){
            alert("Please select type");
        }else{
            exportPDFWorklogbySemester();
        }
    });

    $("#worklog-pdf-btn").click(function(){
        $("#worklog-pdf-box").slideToggle( "slow", function() {
         // Animation complete.
       });
    });

    $("#worklog-spec-date-radio").click(function(){
        if($("#worklog-semester-date-radio").is(':checked')){
        $("#worklog-semester-date-radio").attr("checked",false)
        $("#worklog-pdf-box").css("margin","0,0");
        $("#worklog-semester-date-radio").attr("checked",false)
            $("#worlog-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }else if(!$("#worklog-spec-date-radio").is(':checked')){
            $("#worlog-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }else{
            $("#worlog-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }
    });

    $("#worklog-semester-date-radio").click(function(){
        if($("#worklog-spec-date-radio").is(':checked')){
            $("#worklog-spec-date-radio").attr("checked",false);
            $("#worklog-pdf-box").css("margin","5vh,5vw");
            $("#worlog-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }else{

        }
    });

     $("#section-pdf-btn").click(function(){
        $("#section-pdf-box").slideToggle( "slow", function() {
         // Animation complete.
       });
    });

    $("#section-spec-date-radio").click(function(){
        if($("#section-semester-date-radio").is(':checked')){
        $("#section-semester-date-radio").attr("checked",false)
        $("#section-pdf-box").css("margin","0,0");
        $("#section-semester-date-radio").attr("checked",false)
            $("#section-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }else if(!$("#section-spec-date-radio").is(':checked')){
            $("#section-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }else{
            $("#section-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }
    });

    $("#section-semester-date-radio").click(function(){
        if($("#section-spec-date-radio").is(':checked')){
            $("#section-spec-date-radio").attr("checked",false);
            $("#section-pdf-box").css("margin","5vh,5vw");
            $("#section-pdf-date").slideToggle( "slow", function() {
              // Animation complete.
            });
        }else{

        }
    });

    $("#section-export-pdf-btn").click(function(){
        if(!$("#section-spec-date-radio").is(':checked') && !$("#section-semester-date-radio").is(':checked')){
            alert("Please select type");
        }else{
            exportPDFSectionbySemester();
        }
    });
});

function createNote(id){
    alert("/worklog/edit/note/" + id);
    $.ajax({
        type : "POST",
        url : "/worklog/edit/note/" + id,
        data : {msg : $("#note-text-field").val()},
        success : function(){
            location.href = '/worklog/view/' + id;
        }
    });

}

function deleteSubject(id){
    $.ajax({
        type : "POST",
        url : "/subject/delete",
        data : {id : id},
        success : function(){
            location.reload();
        }
    });
}

function exportPDFWorklogbySemester(){
   if($("#worklog-semester-date-radio").is(':checked')){
        location.href = '/worklog/semester/pdf';
   }else{
        startedDate = $("#worklog-started").val().toString();
        endedDate = $("#worklog-ended").val().toString();
        location.href = '/worklog/semester/pdf/' + startedDate + '/' + endedDate;
   }
}

function exportPDFSectionbySemester(){
   if($("#section-semester-date-radio").is(':checked')){
        location.href = '/section/semester/pdf';
   }else{
        startedDate = $("#section-started").val().toString();
        endedDate = $("#section-ended").val().toString();
        location.href = '/section/semester/pdf/' + startedDate + '/' + endedDate;
   }
}