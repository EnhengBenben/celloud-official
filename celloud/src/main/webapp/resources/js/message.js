var messageUtils = (function(messageUtils) {
	var self = messageUtils || {};
	self.messageChannels = {
		'listenerNumbers' : 0
	};
	self.openListeners = {
		'listenerNumbers' : 0
	};
	self.closeListeners = {
		'listenerNumbers' : 0
	};
	self.timeouts = {};
	self.addOpenListener = function(listener) {
		self.openListeners[self.openListeners.listenerNumbers] = listener;
		self.openListeners.listenerNumbers = self.openListeners.listenerNumbers + 1;
	};
	self.addCloseListener = function(listener) {
		self.closeListeners[self.closeListeners.listenerNumbers] = listener;
		self.closeListeners.listenerNumbers = self.closeListeners.listenerNumbers + 1;
	};
	self.subscribe = function(channel, callback) {
		var channels = self.messageChannels[channel] || {
			'listenerNumbers' : 0
		};
		if ($.isFunction(callback)) {
			channels[channels.listenerNumbers] = callback;
			channels.listenerNumbers = channels.listenerNumbers + 1;
			self.messageChannels[channel] = channels;
		}
	};
	var openWebSocket = function() {
		var contextPath = window.CONTEXT_PATH || "";
		var protocol = window.location.protocol == "https:" ? "wss://"
				: "ws://";
		var hostname = window.location.hostname;
		var port = window.location.port ? ":" + window.location.port : "";
		var wsUrl = protocol + hostname + port + contextPath
				+ "/websocket/message";
		var ws = null;
		if ('WebSocket' in window) {
			ws = new WebSocket(wsUrl);
		} else if (window.MozWebSocket) {
			ws = new MozWebSocket(wsUrl);
		} else {
			ws = new SockJS(contextPath + "/sockjs/message");
		}
		return ws;
	}
	function bindEvents() {
		self.ws.onopen = function() {
			console.log("websocket connect is opened.");
			clearTimeouts();
			for ( var listener in self.openListeners) {
				if ($.isFunction(self.openListeners[listener])) {
					self.openListeners[listener]();
				}
			}
		};
		self.ws.onmessage = function(e) {
			var message = JSON.parse(e.data);
			var channels = self.messageChannels[message.channel] || [];
			if (channels.length <= 0) {
				console.log("no listener for channel: " + message.channel);
				return;
			}
			for ( var channel in channels) {
				if ($.isFunction(channels[channel])) {
					channels[channel](message.message);
				}
			}
		};
		self.ws.onclose = function() {
			console.log("websocket connect is closed");
			for ( var listener in self.closeListeners) {
				if ($.isFunction(self.closeListeners[listener])) {
					self.closeListeners[listener]();
				}
			}
			// reOpenWebsocket();
		};
		self.ws.onerror = function(event){
			console.log('websocket error!');
			console.log(event);
			console.log(event.data);
		};
	}
	self.ws = openWebSocket();
	bindEvents();
	function clearTimeouts() {
		for ( var t in self.timeouts) {
			clearTimeout(self.timeouts[t]);
		}
		self.timeouts = [];
	}
	function reOpenWebsocket() {
		var timeouts = [ 1000, 1000 * 10, 1000 * 30, 1000 * 60, 1000 * 60 * 5,
				1000 * 60 * 10, 1000 * 60 * 15, 1000 * 60 * 20 ];
		clearTimeouts();
		for (var i = 0; i < timeouts.length; i++) {
			self.timeouts[i] = setTimeout(function() {
				console.log("retring open websocket ...");
				self.ws.close();
				self.ws = openWebSocket();
				bindEvents();
			}, timeouts[i]);
		}
	}
	var createNotification = function(title, message, options) {
		var defaultOptions = {
			"icon" : CONTEXT_PATH + "/images/icon/portrait.png"
		};
		options = $.extend({}, defaultOptions, options);
		options.body = message;
		return new Notification(title, options);
	};
	self.notify = function(title, message, options, events) {
		if (!("Notification" in window)) {
			console.log("This browser does not support desktop notification");
			return;
		}
		var notification = null;
		if (Notification.permission === "granted") {
			notification = createNotification(title, message, options);
		} else if (Notification.permission === 'default') {
			Notification.requestPermission(function(permission) {
				if (permission === "granted") {
					notification = createNotification(title, message, options);
				}
			});
		}
		if (notification == null) {
			return;
		}
		var defaultEvents = {
			"onclick" : function() {
				notification.close();
			}
		};
		events = $.extend({}, defaultEvents, events);
		if ($.isFunction(events.onclick)) {
			notification.onclick = events.onclick
		}
	}

	return self;
})(messageUtils);
/**
 * 两个事件，一个订阅。
 * 如果要使用messageUtils，当然首先要在你的js之前加载本js。
 */
/**
 * websocket的open事件，当websocket链接打开时执行，websocket在页面加载时打开。
 * 每次刷新页面都会关闭、打开websocket一次。
 */
messageUtils.addOpenListener(function() {
});
/**
 * websocket的close事件，当websocket链接关闭时执行，websocket在页面关闭或者服务器重启时关闭。
 * 每次刷新页面都会关闭、打开websocket一次。
 * 
 * 每次重启tomcat，都需要刷新一次页面，才会再次建立websocket链接，否则接收不到消息。
 */
messageUtils.addCloseListener(function() {
});
/**
 * 订阅某频道的消息，当有消息发到这个频道时执行。
 */
messageUtils.subscribe("userMessage", function(data) {
	
});