<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--[if lt IE 6]>
.info-box-artic{
    position:relative;
    line-height:1em;
    /* 高度为需要显示的行数*行高，比如这里我们显示两行，则为3 */
    height:2em;
    overflow:hidden;
}
.info-box-artic:after{
    content:"...";
    position:absolute;
    bottom:0;
    right:0;
    padding: 0 5px;
    background-color: #fff;
}
</style>
<![endif]-->
<c:forEach items="${appList}" var="app">
  <div class="col-md-4">
    <div class="info-box">
	  <div class="info-box-icon bg-green">
		<img src="<%=request.getContextPath()%>/images/app/${app.pictureName}" style="width:90px;margin:0px">
	  </div>
	  <div class="info-box-content">
	      <span class="info-box-text">${app.softwareName }</span>
	      <p class="info-box-artic">${app.intro }</p>
<!-- 	      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button> -->
	  </div><!-- /.info-box-content -->
    </div><!-- /.info-box -->
  </div><!-- /.col -->
</c:forEach>
