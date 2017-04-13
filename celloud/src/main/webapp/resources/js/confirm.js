(function($) {
  var show = function(type,title,confirmBtn,cancelBtn,message,callback) {
    if(!$("#confirm-modal-div").html()){
      $("BODY").append('<div id="confirm-modal-div"></div>');
    }else{
      $("#confirm-modal-div").html("")
    }
    var $alert = $('<div id="confirm-modal" class="modal fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" role="alert"></div>');
    $alert.addClass(type + "-modal");
    var $dialog = $('<div class="modal-dialog modal-sm"></div>');
    var $content = $('<div class="modal-content"></div>');
    var $header = $('<div class="modal-header"><button type="button" class="close" aria-label="Close" id="close-confirm"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button><h4 class="modal-title">'+title+'</h4></div>');
    var $body = $('<div class="modal-body"></div>');
    var $h5 = $('<h5></h5>');
    var $footer = $('<div class="modal-footer"><button type="reset" class="btn btn-cancel -low pull-left" data-dismiss="modal" id="confirm-cancle">'+cancelBtn+'</button><button type="submit" class="btn -low pull-right" id="confirm-ok">'+confirmBtn+'</button></div>');
    $h5.append($('<i class="fa fa-exclamation-circle" aria-hidden="true"></i>'));
    $h5.append($('<span>' + message + '</span>'));
    $body.append($h5);
    $content.append($header);
    $content.append($body);
    switch( type ) {
      case 'confirm':
        $content.append($footer);
        $dialog.append($content);
        $alert.append($dialog);
        $alert.modal("show");
        $("#confirm-modal-div").append($alert);
        $("#confirm-ok").click(function() {
          hide();
          callback();
        });
        $("#confirm-cancle").click(function(){
          hide();
        });
      break;
      case 'tips':
        $dialog.append($content);
        $alert.append($dialog);
        $alert.modal("show");
        $("#confirm-modal-div").append($alert);
      break;
    }
    $("#close-confirm").click(function(){
        hide();
      });
  };
  var hide = function(){
    $("#confirm-modal").modal("hide");
  }
  var confirm = function(message, title, confirmBtn, cancelBtn, callback) {
    if( title == null ) title = '确认框';
    if( confirmBtn == null ) confirmBtn = '确认';
    if( cancelBtn == null ) cancelBtn = '取消';
    show("confirm",title,confirmBtn,cancelBtn,message,function(result) {
      if( callback ) callback(result);
    });
  }
  var tips = function(message, title) {
    if( title == null ) title = '提示框';
    show("tips",title,null,null, message,null);
  }
  $.extend({
    confirm : function(message, title, callback) {
      confirm(message, title, callback);
    },
    tips : function(message, title) {
      tips(message, title);
    }
  });
})(jQuery);