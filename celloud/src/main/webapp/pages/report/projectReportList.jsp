<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<a href="" id="_downFile"></a>
<table class="table_">
    <thead>
        <tr>
            <th width="215">项目信息</th>
            <th>数据列表</th>
        </tr>
    </thead>
    <tbody id="_show">
        <s:if test="%{list.datas.size()>0}">
            <s:iterator value="list.datas" id="report">
                <tr>
                    <td class="_firstTd">
                        <div>
                        项目名称：<span id="showPname<s:property value='#report.project_id'/>">
                               <span id="pnameSpan<s:property value='#report.project_id'/>">
                               	<s:if test="%{#report.project_name.length()>13}">
                               		<s:property value="#report.project_name.substring(0,12)"/>...
                               	</s:if>
                               	<s:else>
                               		<s:property value="#report.project_name"/>
                               	</s:else>
                               </span>
                               <s:if test="%{#report.user_id==#session.userId}">
    	                            <a href="javascript:void()" class="changeImg" onclick="toChangePname(<s:property value='#report.project_id'/>)"></a>
                               </s:if>
                            </span>
                            <span id="changePname<s:property value='#report.project_id'/>" class="none">
	                            <input type="text" value="<s:property value="#report.project_name"/>" id="updatePname<s:property value='#report.project_id'/>" class="changeInput" onblur="changePname(<s:property value='#report.project_id'/>)"/>
	                            <img src="<%=request.getContextPath() %>/images/report/ok_blue.png" id="okUpdateImg<s:property value='#report.project_id'/>" class="okImg" onclick="changePname(<s:property value='#report.project_id'/>)" />
                            </span>
                        </div>
                        <div>
                            App名称：<s:property value="#report.software_name"/>
                        </div>
                        <div>
                           	 文件数量：
                            <span id="rdataNum<s:property value='#report.project_id'/>"><s:if test="%{#report.num==0}">&nbsp; </s:if><s:else><s:property value="#report.num"/></s:else></span>
                        </div>
                        <div>
                           	数据大小：
                            <c:choose>
                                <c:when test="${report.size==0 }">&nbsp;</c:when>
                                <c:when test="${report.size<1024&&report.size>0 }">
                                    <fmt:formatNumber value ="${report.size}" pattern="#,#00.0#"></fmt:formatNumber> B
                                </c:when>
                                <c:when test="${report.size<1048576&&report.size>=1024 }">
                                    <fmt:formatNumber value ="${report.size/1024}" pattern="#,##0.0#"></fmt:formatNumber> KB
                                </c:when>
                                <c:when test="${report.size>=1048576 }">
                                    <fmt:formatNumber value ="${report.size/1048576}" pattern="#,##0.0#"></fmt:formatNumber> MB
                                </c:when>
                            </c:choose>
                        </div>
                        <div>
                        	 启动时间：<s:property value="#report.create_date.substring(0,19)"/>
                        </div>
                        <div>
                           	 结束时间：<s:property value="#report.end_date.substring(0,19)"/>
                        </div>
                        <s:if test="%{#report.user_id!=#session.userId}">
                            <div class="operate">
                                <a class="sharefrom" title="共享" href="javascript:void()"></a><span class="shareU"><s:property value="#report.userName"/></span>
                                <a class="delete" title="删除" onclick="cancelProjectShare(<s:property value="#report.project_id"/>,<s:property value="#report.user_id"/>)" href="javascript:void(0)"></a>
                            </div>
                        </s:if>
                        <s:else>
                            <div class="operate">
                            	<s:if test="#report.software_id>84&&#report.software_id!=89&&#report.software_id!=90&&#report.software_id!=105&&#report.software_id!=106&&#report.software_id!=107&&#report.software_id!=108&&#report.software_id!=109&&#report.software_id!=110&&#report.software_id!=111&&#report.software_id!=112&&#report.software_id!=113&&#report.software_id!=114">
                                	<a class="pdfdown" title="PDF下载" onclick="downPDF(<s:property value="#report.user_id"/>,<s:property value="#report.software_id"/>,<s:property value="#report.project_id"/>)" href="javascript:void(0)"></a>
                            	</s:if>
                                <s:if test="%{#report.share==0}">
                                	<a class="share" title="共享" onclick="toShareModal('<s:property value="#report.project_id"/>','<s:property value="#report.user_id"/>','<s:property value="#report.project_name"/>',<s:property value="#report.num"/>)" href="javascript:void(0);"></a>
                                </s:if>
                                <s:else>
                                	<a class="shared" title="已共享" onclick="shareModal('<s:property value="#report.project_id"/>','<s:property value="#report.user_id"/>','<s:property value="#report.project_name"/>',<s:property value="#report.num"/>)" href="javascript:void(0);"></a>
                                </s:else>
                                <a class="delete" title="删除" onclick="removePro('<s:property value="#report.project_id"/>')" href="javascript:void(0)"></a>
                            </div>
                        </s:else>
                    </td>
                    <td class="none">
                        <s:property value="#report.software_id"/>,<s:property value="#report.software_name"/>,<s:property value="#report.project_id"/>,<s:property value="#report.user_id"/>
                    </td>
                    <td class="no" align="center">
                        <s:if test="%{#report.state==3}">
                            <s:property value="#report.context" escape="false"/>
                        </s:if>
                        <s:else>
                        	<img src="<%=request.getContextPath() %>/images/report/running.png" title="正在运行..."/>
                        </s:else>
                    </td>
                </tr>
            </s:iterator>
        </s:if>
        <s:else>
            <tr><td colspan="8">项目记录为空</td></tr>
        </s:else>
    </tbody>
</table>
<div class="pagination-new center">
    <s:if test="%{list.datas.size()>0}">
        <ul class="pages">
            <!-- 显示prev -->
            <li>
                <p>每页
                    <select id="proPageRecordSel" onchange="javascript:changePageSize();">
                        <option value="10" <s:if test="%{list.page.pageSize==10}">selected="selected"</s:if>>10</option>
                        <option value="20" <s:if test="%{list.page.pageSize==20}">selected="selected"</s:if>>20</option>
                        <option value="30" <s:if test="%{list.page.pageSize==30}">selected="selected"</s:if>>30</option>
                        <option value="40" <s:if test="%{list.page.pageSize==40}">selected="selected"</s:if>>40</option>
                        <option value="50" <s:if test="%{list.page.pageSize==50}">selected="selected"</s:if>>50</option>
                    </select>
                条</p>
            </li>
            <s:if test="%{list.page.hasPrev}">
                <li><a href="javascript:changePage('<s:property value="list.page.currentPage-1" />')" id="prevReportList">&lt;</a></li>
            </s:if>
            <!-- 显示第一页 -->
            <s:if test="%{list.page.currentPage==1}">
                <li class="active"><a href="#">1</a></li>
            </s:if>
            <s:else>
                <li><a href="javascript:changePage(1)">1</a></li>
            </s:else>
            
            <s:if test="%{list.page.currentPage>4&&list.page.totalPage>10}">
                <li>...</li>
            </s:if>
            
            <s:if test="%{list.page.totalPage-list.page.currentPage>=7}">
                <s:if test="%{list.page.currentPage==3}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage-1"/>)"><s:property value="list.page.currentPage-1"/></a></li>
                </s:if>
                <s:if test="%{list.page.currentPage==4}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage-2"/>)"><s:property value="list.page.currentPage-2"/></a></li>
                </s:if>
                <s:if test="%{list.page.currentPage>3}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage-1"/>)"><s:property value="list.page.currentPage-1"/></a></li>
                </s:if>
                <s:if test="%{list.page.currentPage>1&&list.page.currentPage<list.page.totalPage}">
                    <li class="active"><a href="#"><s:property value="list.page.currentPage"/></a></li>
                </s:if>
                <s:if test="%{list.page.totalPage-list.page.currentPage>1}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage+1"/>)"><s:property value="list.page.currentPage+1"/></a></li>
                </s:if>
                <s:if test="%{list.page.totalPage-list.page.currentPage>2}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage+2"/>)"><s:property value="list.page.currentPage+2"/></a></li>
                </s:if>
                <s:if test="%{list.page.totalPage-list.page.currentPage>3}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage+3"/>)"><s:property value="list.page.currentPage+3"/></a></li>
                </s:if>
                <s:if test="%{list.page.totalPage-list.page.currentPage>4}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage+4"/>)"><s:property value="list.page.currentPage+4"/></a></li>
                </s:if>
                <s:if test="%{list.page.totalPage-list.page.currentPage>5}">
                    <li><a href="javascript:changePage(<s:property value="list.page.currentPage+5"/>)"><s:property value="list.page.currentPage+5"/></a></li>
                </s:if>
                <s:if test="%{list.page.currentPage<4}">
                    <s:if test="%{list.page.totalPage-list.page.currentPage>6}">
                        <li><a href="javascript:changePage(<s:property value="list.page.currentPage+6"/>)"><s:property value="list.page.currentPage+6"/></a></li>
                    </s:if>
                </s:if>
                <s:if test="%{list.page.currentPage==1}">
                    <s:if test="%{list.page.totalPage-list.page.currentPage>7}">
                        <li><a href="javascript:changePage(<s:property value="list.page.currentPage+7"/>)"><s:property value="list.page.currentPage+7"/></a></li>
                    </s:if>
                    <s:if test="%{list.page.totalPage-list.page.currentPage>8}">
                        <li><a href="javascript:changePage(<s:property value="list.page.currentPage+8"/>)"><s:property value="list.page.currentPage+8"/></a></li>
                    </s:if>
                </s:if>
                <s:elseif test="%{list.page.currentPage==2}">
                    <s:if test="%{list.page.totalPage-list.page.currentPage>7}">
                        <li><a href="javascript:changePage(<s:property value="list.page.currentPage+7"/>)"><s:property value="list.page.currentPage+7"/></a></li>
                    </s:if>
                </s:elseif>
                <s:elseif test="%{list.page.currentPage>4}">
                    <s:if test="%{list.page.totalPage-list.page.currentPage>6}">
                        <li><a href="javascript:changePage(<s:property value="list.page.currentPage+6"/>)"><s:property value="list.page.currentPage+6"/></a></li>
                    </s:if>
                </s:elseif>
            </s:if>
            <s:else>
                <s:if test="%{list.page.totalPage-8>0}">
                    <s:iterator begin="%{list.page.totalPage-8}" step="1" end="%{list.page.totalPage-1}" var="step">
                        <s:if test="#step==list.page.currentPage">
                            <li class="active"><a href="#"><s:property value="#step"/></a></li>
                        </s:if>
                        <s:else>
                            <li><a href="javascript:changePage(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
                        </s:else>
                    </s:iterator>
                </s:if>
                <s:else>
                    <s:iterator begin="2" step="1" end="%{list.page.totalPage-1}" var="step">
                        <s:if test="#step==list.page.currentPage">
                            <li class="active"><a href="#"><s:property value="#step"/></a></li>
                        </s:if>
                        <s:else>
                            <li><a href="javascript:changePage(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
                        </s:else>
                    </s:iterator>
                </s:else>
            </s:else>
            
            <s:if test="%{list.page.totalPage-list.page.currentPage>=8&&list.page.totalPage>10}">
                <li>...</li>
            </s:if>
            
            <s:if test="%{list.page.currentPage==list.page.totalPage&&list.page.totalPage>1}">
                <li class="active"><a href="#"><s:property value="list.page.totalPage"/></a></li>
            </s:if>
            <s:elseif test="%{list.page.totalPage>1}">
                <li><a href="javascript:changePage(<s:property value="list.page.totalPage"/>)"><s:property value="list.page.totalPage"/></a></li>
            </s:elseif>
            <s:if test="%{list.page.hasNext}">
                <li><a href="javascript:changePage(<s:property value="list.page.currentPage+1"/>)" id="nextReportList">&gt;</a></li>
            </s:if>
            <li>
                共<s:property value="list.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="list.page.rowCount"/>条
            </li>
        </ul>
    </s:if>
</div>