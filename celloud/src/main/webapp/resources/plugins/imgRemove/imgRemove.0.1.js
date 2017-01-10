var imgRemove = (function(imgRemove) {
    var self = imgRemove || {};
    var defaults = {
        left: 120,
        top: 0,
        title: "删除图片"
    };
    self.show = function(obj) {
        $("._img_remove").remove();
        $(".wait_remove").removeClass("wait_remove");
        $(obj).addClass("wait_remove");
        var divObj = $("<div onclick=imgRemove.remove();>×</div>");
        divObj.addClass("_img_remove");
        divObj.attr("title", defaults.title);
        divObj.css({
            position: "absolute",
            left: $(obj).position().left + defaults.left,
            top: $(obj).position().top + defaults.top
        });
        $(obj).parent().append(divObj)
    };
    self.remove = function() {
    		$("img[class='wait_remove']").next().remove();
        $("img[class='wait_remove']").remove();
        $("._img_remove").remove()
    };
    self.hideRemove = function() {
      $("._img_remove").remove()
    };
    self.setOptions = function(options) {
        $.extend(defaults, options);
        return self
    };
    return self
})(imgRemove);
