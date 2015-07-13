<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/database.css">
</head>
<body>
<div class="container">
	<div class="header">
		<div class="fl"><img src="<%=request.getContextPath()%>/images/database/DB_logo.png" /></div>
    </div>
    <div class="main-new">
    	<div class="leftside">
			<ul>
		    	<li style="width: 203px;" id="liPrivateDb"><a href="javascript:void(0);" id="privateDb"><i class="celloud"></i>Celloud DB</a></li>
		    	<li class="active" style="width: 203px;" id="liPublicDb"><a href="javascript:void(0);" id="publicDb"><i class="public"></i>Public DB</a></li>
		    </ul>
		</div>
		<div class="mainContent" id="mainContentDiv">
			
		</div>
   	</div>
</div>
<div id="tip" style="display: none;">
	<div class="tip">
		<img src="<%=request.getContextPath()%>/images/database/tips.png" />没有符合条件的应用
	</div>
</div>
<div id="pdb" style="display: none;">
	<ul>
		<li>
        	<a href="http://www.ncbi.nlm.nih.gov/" target="blank">
        		<img src="<%=request.getContextPath()%>/images/database/NCBI.png">
        		The National Center for Biotechnology Informat
        	</a>
	    </li>
		<li>
	        <a href="http://www.embl.org/" target="blank">
	        	<img src="<%=request.getContextPath()%>/images/database/EMBL.png">
	        	EMBL is at the forefront of innovation in life
	        </a>
	    </li>
		<li>
	       	<a href="http://www.ddbj.nig.ac.jp/" target="blank">
	       		<img src="<%=request.getContextPath()%>/images/database/DDBJ.png">
	       		DDBJ： DNA Data Bank of Japan is the sole nucle
	       	</a>
	    </li>
		<li>
	        <a href="http://pubmlst.org/" target="blank">
		        <img src="<%=request.getContextPath()%>/images/database/MLST.png">
		        This site hosts publicly accessible Multilocus
	       </a>
	    </li>
		<li>
	        <a href="http://www.genomeweb.com/" target="blank">
		        <img src="<%=request.getContextPath()%>/images/database/genoWEB.png">
		        genomeweb ：以当前顶尖的基因组产业新闻为特征的网站
	        </a>
	    </li>
		<li>
	        <a href="http://seqanswers.com/" target="blank">
		        <img src="<%=request.getContextPath()%>/images/database/SEQ.png">
		        SEQanswers：高通量测序各个方面的交流和学习论坛
	        </a>
	    </li>
		<li>
	        <a href="http://www.ngsleaders.org/" target="blank">
		        <img src="<%=request.getContextPath()%>/images/database/NGSL.png">
		        NGSLeaders：高通量测序和生物信息学前沿工作者交流平台
	        </a>
	    </li>
	</ul>
</div>
<script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap.js" type="text/javascript" ></script>
<script src="<%=request.getContextPath() %>/js/database.js"></script>
</body>
</html>