$(function(){
    $(".delete-section-btn").click(function(){
         event.preventDefault();
         if(confirm("Are you sure to delete the subject ?")){
            deleteSection($(this).attr("value"));
         }
    });
});

function deleteSection(id){
    $.ajax({
        type : "POST",
        url : "/section/delete",
        data : {id : id},
        success : function(){
            location.reload();
        }
    });
}

function checkPdfList(interval){
    alert(interval);
}