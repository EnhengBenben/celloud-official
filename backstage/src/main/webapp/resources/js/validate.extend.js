jQuery.validator.addMethod("isPhone", function(value, element) {   
        var tel = /^1[3|4|5|7|8]\d{9}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的联系电话");
jQuery.validator.addMethod("zipcode", function(value, element) {
	return this.optional(element) || /^[1-9][0-9]{5}$/.test(value);
}, "请正确填写您的邮政编码");
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