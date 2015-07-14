<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/pages/16s/js/jquery1.4.js">
		</script>
<script type="text/javascript">
	$(document).ready(function(){		
		$("#svgShow").html("");
		
		if (window.svgweb) {
	        svgweb.addOnLoad(loadFunc);
	    }else {
	        window.addEventListener('load', loadFunc, false);
	    }
	});
	
	function objLoadFunc() {
    	var doc = document.getElementById('svgShow').contentDocument;
    }

    function loadFunc() {
    	var data = $("#filePath").val();
       	var obj = document.createElement('object', true);
       	obj.id = 'mySVG';
       	obj.setAttribute('type', 'image/svg+xml');
       	obj.setAttribute('data', data);
       	obj.setAttribute('width', '700');
       	obj.setAttribute('height', '980');
	
       	obj.addEventListener(window.svgweb ? 'SVGLoad' : 'load', objLoadFunc, false);
       	var container = document.getElementById('svgShow');
       	if (window.svgweb) {
         	svgweb.appendChild(obj, container);
       	} else {
         	container.appendChild(obj);
       	}
     }
</script>
<div id="ajax">
	<div class="box1">
		<input type="hidden" id="filePath" value="<%=request.getContextPath()%>/${requestScope.svgFileName}" />
		<div id="svgShow">
			
		</div>
	</div>
	<div class="box1">
		<div class="box-txt1">
			<table border="0" cellspacing="1" id="myTable" width="750px">
				<tr>
					<th>
						Query_name
					</th>
					<th>
						Sbjct_name
					</th>
					<th>
						Query_len
					</th>
					<th>
						Query_beg
					</th>
					<th>
						Query_end
					</th>
					<th>
						Sbjct_beg
					</th>
					<th>
						Sbjct_end
					</th>
					<th>
						Sbjct_len
					</th>
					<th>
						Identidy
					</th>
				</tr>
				<s:iterator id="c" status="st" value="list">
					<tr>
						<td>
							<s:property value="#c.queryName" />
						</td>
						<td>
							<s:property value="#c.subjectName" />
						</td>
						<td>
							<s:property value="#c.queryLength" />
						</td>
						<td>
							<s:property value="#c.queryBegin" />
						</td>
						<td>
							<s:property value="#c.queryEnd" />
						</td>
						<td>
							<s:property value="#c.subjectBegin" />
						</td>
						<td>
							<s:property value="#c.subjectEnd" />
						</td>
						<td>
							<s:property value="#c.subjectLength" />
						</td>
						<td>
							<s:property value="#c.Identidy" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</div>
</div>