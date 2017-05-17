$('#btn_submit').click(function(){
    $.ajax({
        url: "/api/user/login",
        type: 'post',
//        data: JSON.stringify(obj),
        data: {
        	owner: $('input[name=owner]').val(),
        	pwd: $('input[name=pwd]').val(),
        	vcode: $('input[name=vcode]').val()
        },
//        contentType: "application/json;charset=UTF-8",
        success: function(data){
			console.info(data)
			if (data.success) window.location.href = "/"
			else $('span').html(data.msg)
		},
		error: function(data){
			console.error(data)
		}
    })
})