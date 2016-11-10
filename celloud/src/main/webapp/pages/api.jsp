<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Swagger UI</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath() %>/api/images/favicon-32x32.png" sizes="32x32" />
  <link rel="icon" type="image/png" href="<%=request.getContextPath() %>/api/images/favicon-16x16.png" sizes="16x16" />
  <link href='<%=request.getContextPath() %>/api/css/typography.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='<%=request.getContextPath() %>/api/css/reset.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='<%=request.getContextPath() %>/api/css/screen.css' media='screen' rel='stylesheet' type='text/css'/>
  <link href='<%=request.getContextPath() %>/api/css/reset.css' media='print' rel='stylesheet' type='text/css'/>
  <link href='<%=request.getContextPath() %>/api/css/print.css' media='print' rel='stylesheet' type='text/css'/>

  <script src='<%=request.getContextPath() %>/api/lib/object-assign-pollyfill.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/jquery-1.8.0.min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/jquery.slideto.min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/jquery.wiggle.min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/jquery.ba-bbq.min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/handlebars-4.0.5.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/lodash.min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/backbone-min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/swagger-ui.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/highlight.9.1.0.pack.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/highlight.9.1.0.pack_extended.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/jsoneditor.min.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/marked.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lib/swagger-oauth.js' type='text/javascript'></script>
  <!-- 汉化 -->
  <script src='<%=request.getContextPath() %>/api/lang/translator.js' type='text/javascript'></script>
  <script src='<%=request.getContextPath() %>/api/lang/zh-cn.js' type='text/javascript'></script>

  <script type="text/javascript">
    $(function () {
      var url = window.location.search.match(/url=([^&]+)/);
      if (url && url.length > 1) {
        url = decodeURIComponent(url[1]);
      } else {
        url = "<%=request.getContextPath() %>/api-docs";
      }

      hljs.configure({
        highlightSizeThreshold: 5000
      });

      // Pre load translate...
      if(window.SwaggerTranslator) {
        window.SwaggerTranslator.translate();
      }
      window.swaggerUi = new SwaggerUi({
        url: url,
        dom_id: "swagger-ui-container",
        supportedSubmitMethods: ['get', 'post', 'put', 'delete', 'patch'],
        onComplete: function(swaggerApi, swaggerUi){
          if(typeof initOAuth == "function") {
            initOAuth({
              clientId: "your-client-id",
              clientSecret: "your-client-secret-if-required",
              realm: "your-realms",
              appName: "your-app-name",
              scopeSeparator: " ",
              additionalQueryStringParams: {}
            });
          }

          if(window.SwaggerTranslator) {
            window.SwaggerTranslator.translate();
          }
        },
        onFailure: function(data) {
          log("Unable to Load SwaggerUI");
        },
        docExpansion: "none",
        jsonEditor: false,
        defaultModelRendering: 'schema',
        showRequestHeaders: false
      });

      window.swaggerUi.load();
      $("#api_selector").css("display","none");
      function log() {
        if ('console' in window) {
          console.log.apply(console, arguments);
        }
      }
  });
  </script>
</head>

<body class="swagger-section">
<div id='header'>
  <div class="swagger-ui-wrap">
    <a id="logo" href="http://swagger.io">
    	<img class="logo__img" alt="swagger" height="34.5" width="87" src="<%=request.getContextPath() %>/images/home/logo@2x.png" style="margin-top: 0"/>
<!--     	<span class="logo__title">CelLoud</span> -->
    </a>
    <form id='api_selector'>
      <div class='input'><input placeholder="http://example.com/api" id="input_baseUrl" name="baseUrl" type="text"/></div>
      <div id='auth_container'></div>
      <div class='input'><a id="explore" class="header__btn" href="#" data-sw-translate>Explore</a></div>
    </form>
  </div>
</div>

<div id="message-bar" class="swagger-ui-wrap" data-sw-translate>&nbsp;</div>
<div id="swagger-ui-container" class="swagger-ui-wrap"></div>
<script type="text/javascript">
$(document).delegate('.toggleOperation', 'click', function() {
  var obj = $(this).parent().parent().parent().next();
  var val = obj.css("display");
  if(val=='none'){
    obj.css("display","");
  }else{
    obj.css("display","none");
  }
});
</script>
</body>
</html>
