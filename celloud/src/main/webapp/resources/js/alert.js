(function($) {
	var defaults = {
		delay : 3000
	};
	var messages = [];
	var showing = 0;
	var showNext = function() {
		if ($("#alerts").children().length < 5 && messages.length > 0) {
			show(messages.shift());
		}
	}
	var show = function(message) {
		message.msg = message.msg || '';
		var $alert = $('<div class="alert message-alert alert-dismissible fade in" role="alert">'
				+ '<button type="button" data-dismiss="alert" class="close">'
				+ '<span aria-hidden="true"><i class="fa fa-times-circle"></i></span>'
				+ '</button>' + '<span>' + message.msg + '</span>' + '</div>');
		$alert.addClass("alert-" + message.type);
		$alert.on('closed.bs.alert', function() {
			close($alert);
		});
		$("#alerts").append($alert);
		setTimeout(function() {
			close($alert);
		}, message.delay || defaults.delay);
	};
	var close = function($alert) {
		$alert.remove();
		showNext();
	}
	var push = function(msg, type, delay) {
		messages.push({
			msg : msg,
			type : type,
			delay : delay
		});
		showNext();
	}
	$.extend({
		success : function(msg, delay) {
			push(msg, 'success', delay);
		},
		info : function(msg, delay) {
			push(msg, 'info', delay);
		},
		warn : function(msg, delay) {
			push(msg, 'warning', delay);
		},
		danger : function(msg, delay) {
			push(msg, 'danger', delay);
		},
		alert : function(msg, delay) {
			push(msg, 'success', delay);
		}
	});
})(jQuery);