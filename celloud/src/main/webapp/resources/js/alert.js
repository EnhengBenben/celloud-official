(function($) {
	var defaults = {
		delay : 3000
	};
	var messages = [];
	var showing = 0;
	var showNext = function() {
		if (showing < 5 && messages.length > 0) {
			show(messages.shift());
		}
	}
	var show = function(message) {
		showing++;
		var $alert = $('<div class="alert message-alert alert-dismissible fade in" role="alert"></div>');
		$alert.addClass("alert-" + message.type);
		var $button = $('<button type="button" data-dismiss="alert" class="close"></button>');
		var $icon = $('<span aria-hidden="true"><i class="fa fa-times-circle"></i></span>');
		$button.append($icon);
		$alert.append($button);
		$alert.append($('<span>' + message.msg + '</span>'));
		$alert.alert();
		$alert.on('closed.bs.alert', function() {
			close($alert);
		});
		$("#alerts").append($alert);
		setTimeout(function() {
			$alert.alert('close');
			close($alert);
		}, message.delay || defaults.delay);
	};
	var close = function($alert) {
		showing--;
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