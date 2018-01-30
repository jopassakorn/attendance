$(function(){
    $(".delete-subject-btn").click(function(){
         event.preventDefault();
         if(confirm("Are you sure to delete the subject ?")){
            deleteSubject($(this).attr("value"));
         }
    });
});

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