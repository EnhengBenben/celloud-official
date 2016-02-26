jQuery.validator.addMethod("isPhone", function(value, element) {   
        var tel = /^1[3|4|5|7|8]\d{9}$/;
        var fixedtel=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        return this.optional(element) || (tel.test(value)||fixedtel.test(value));
    }, "请正确填写您的联系电话");
jQuery.validator.addMethod("zipcode", function(value, element) {
	return this.optional(element) || /^[1-9][0-9]{5}$/.test(value);
}, "请正确填写您的邮政编码");

//密码校验
jQuery.validator.addMethod("pwdRE1", function(value, element) {
	return this.optional(element) || !(/^[\d+]{6,16}$/.test(value));
}, "密码不能全为数字");

jQuery.validator.addMethod("pwdRE2", function(value, element) {
	return this.optional(element) || /^[a-zA-Z0-9_]{6,16}$/.test(value);
}, "密码为6-16位的字母、数字及下划线组合");

jQuery.validator.addMethod("appNameExist", function(value, element) {
	var flag=false;
	$.ajax({url:"app/appNameExist",
			data:{appName:value},
			async: false,
			success:function(data){
				if(data==0){
					flag=true;
				}
			}
	});
	return flag;
}, "app名称已经存在");

jQuery.validator.setDefaults({
	errorElement: 'div',
    errorClass: 'help-block',
    ignore:".invisible",
	highlight: function (e) {
        $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
    },

    success: function (e) {
        $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
        $(e).remove();
    },

    errorPlacement: function (error, element) {
         error.insertAfter(element);
    },
    onfocusout: function(element) { $(element).valid(); }
});