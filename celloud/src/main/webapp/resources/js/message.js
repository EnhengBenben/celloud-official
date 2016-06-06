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
		var protocol = window.location.protocol=='http:'?'ws://':'wss://';
		var hostname = window.location.hostname;
		var port = window.location.port || 80;
		var wsUrl = protocol + hostname + ":" + port + contextPath
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
	function bindEvents(){
		self.ws.onopen = function() {
			console.log("websocket connect is opened");
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
			reOpenWebsocket();
		};
	}
	self.ws = openWebSocket();
	bindEvents();
	function clearTimeouts(){
		for(var t in self.timeouts){
			console.log("clear ... "+self.timeouts[t]);
			clearTimeout(self.timeouts[t]);
		}
		self.timeouts = [];
	}
	function reOpenWebsocket(){
		var timeouts = [1000,1000*10,1000*30,1000*60,1000*60*5,1000*60*10,1000*60*15,1000*60*20];
		clearTimeouts();
		for(var i = 0;i<timeouts.length;i++){
			self.timeouts[i] = setTimeout(function(){
				console.log("retring open websocket ...");
				self.ws = openWebSocket();
				bindEvents();
			},timeouts[i]);
		}
	}
	return self;
})(messageUtils);
messageUtils.subscribe("test", function(data) {
	console.log("接收到test频道的消息：" + JSON.stringify(data));
});
