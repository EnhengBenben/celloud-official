<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
  <meta charset="UTF-8">
  <title>SVG时钟</title>
  <style type="text/css">
  html,body{width:156px;height:156px;margin:0;padding:0;}
  </style>
</head>
<body>
  <svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="156" height="156">
    <defs>
      <radialGradient id="clock-border" cx="50%" cy="50%" r="40%"
      fx="50%" fy="50%">
        <stop offset="85%" style="stop-color:#57585B;
        stop-opacity:0"/>
        <stop offset="100%" style="stop-color:#000000;
        stop-opacity:1"/>
      </radialGradient>
    </defs>
    <circle cx="78" cy="78" r="76" fill="#ffffff" stroke="none" />
    <circle cx="78" cy="78" r="76" style="fill:url(#clock-border)" stroke="none" fill-opacity="0.8" />
    <rect x="76" y="19" width="2" height="6" fill="#2e2e2e" stroke="none" transform="" />
    <rect x="76" y="19" width="2" height="6" fill="#2e2e2e" stroke="none" transform="rotate(90 78 78)" />
    <rect x="76" y="19" width="2" height="6" fill="#2e2e2e" stroke="none" transform="rotate(180 78 78)" />
    <rect x="76" y="19" width="2" height="6" fill="#2e2e2e" stroke="none" transform="rotate(270 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(30 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(60 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(120 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(150 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(210 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(240 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(300 78 78)" />
    <rect x="76" y="19" width="1" height="5" fill="#666666" stroke="none" transform="rotate(330 78 78)" />
    <circle cx="78" cy="78" r="10" fill="#57585B" stroke-width="1" />
    <rect id="hourPointer" x="73" y="43" width="10" height="26" fill="#000000" stroke="none" transform="rotate(0 78 78)" />
    <rect id="minPointer" x="75" y="35" width="6" height="34" fill="#000000" stroke="none" transform="rotate(90 78 78)" />
    <rect id="secPointer" x="76" y="30" width="4" height="40" fill="#000000" stroke="none" transform="rotate(180 78 78)" />
  </svg>  
  <script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
  <script type="text/javascript">
    (function(){
      var showTime = function(){
        var now = new Date();
        var nowSec = now.getSeconds();
        var nowMin = now.getMinutes();
        var nowHour = now.getHours();
        $("#hourPointer").attr("transform","rotate(" + nowHour*30 + " 78 78)");  
        $("#minPointer").attr("transform","rotate(" + nowMin*6 + " 78 78)");
        $("#secPointer").attr("transform","rotate(" + nowSec*6 + " 78 78)"); 
        setTimeout(showTime,1000);
      }
      showTime();
    })();
  </script>
</body>
</html>