<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default feedback_list">
    <div class="panel-heading">
        <h3 class="panel-title">问题反馈</h3>
    </div>
    <div class="panel-body">
        <div class="row list-group">
            <ul id="sort-listUl" data-spm="202" class="sort-list">
            <li id="defaultSort" <c:if test="${empty sortFiled }">class="current"</c:if>>
              <a id="defaultSort" href="javascript:feedback.sortDefault();">默认排序</a>
            </li>
            <li id="sortByCreateDate" data-spm="1" <c:if test="${sortFiled=='f.create_date' }">class="current"</c:if>>
              <a id="sortByCreateDate" href="javascript:feedback.sortFeedback('f.create_date');" target="_self">创建时间
              <c:choose>
              <c:when test="${sortFiled=='f.create_date' && sortType=='asc' }">
                <i class="up"></i>
              </c:when>
              <c:otherwise>
                <i class="down"></i>
              </c:otherwise>
              </c:choose>
              </a>
            </li>
             <li id="sortByReply" data-spm="1" <c:if test="${sortFiled=='isResponse' }">class="current"</c:if>>
              <a id="sortByReply" href="javascript:feedback.sortFeedback('isResponse');" target="_self">是否回复
                <c:choose>
	              <c:when test="${sortFiled=='isResponse' && sortType=='asc' }">
	                <i class="up"></i>
	              </c:when>
	              <c:otherwise>
	                <i class="down"></i>
	              </c:otherwise>
	             </c:choose>
              </a>
            </li>
             <li id="sortBySolve" data-spm="1" <c:if test="${sortFiled=='f.solve' }">class="current"</c:if>>
              <a id="sortBySolve" href="javascript:feedback.sortFeedback('f.solve');" target="_self">是否解决
              <c:choose>
                <c:when test="${sortFiled=='f.solve' && sortType=='asc' }">
                  <i class="up"></i>
                </c:when>
                <c:otherwise>
                  <i class="down"></i>
                </c:otherwise>
               </c:choose>
              </a>
            </li>
          </ul>
            <c:forEach items="${pageList.datas }" var="feedback">
               <a onclick="feedback.detail('${feedback.id}')" class="list-group-item">
                <p>${feedback.title }  提问时间：<fmt:formatDate value="${feedback.createDate }" pattern="yyyy-MM-dd HH:mm" /> 
                    <c:if test="${feedback.isSolved() }">
                        <span class="badge pull-right">已解决</span>
                    </c:if>
                    <c:if test="${not empty feedback.hasAttachment && feedback.hasAttachment!=0}">
                        <span class="badge glyphicon glyphicon-paperclip pull-right" aria-hidden="true"> </span>
                    </c:if>
                   </p>
                <div>${feedback.content }</div>
               </a>
            </c:forEach>
        </div>
        <c:if test="${pageList.datas.size()>0}">
		    <div class="row-fluid">
		        <div class="col-md-6 keepRight">
		            <nav id="pageView">
		                <ul class="pagination">
		                    <c:choose>
		                        <c:when test="${pageList.page.hasPrev}">
		                            <li><a href="javascript:feedback.getFeedbackList(${pageList.page.prevPage })">&lt;</a></li>
		                        </c:when>
		                        <c:otherwise>
		                            <li><a href="javascript:void(0)">&lt;</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                    <c:choose>
		                        <c:when test="${pageList.page.currentPage==1}">
		                            <li class="active"><a href="javascript:void(0)">1</a></li>
		                        </c:when>
		                        <c:otherwise>
		                            <li><a href="javascript:feedback.getFeedbackList(1)">1</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                    <c:if test="${pageList.page.currentPage>3}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>2}">
		                        <li><a href="javascript:feedback.getFeedbackList(${pageList.page.prevPage })">${pageList.page.prevPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
		                        <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
		                        <li><a href="javascript:feedback.getFeedbackList(${pageList.page.nextPage })">${pageList.page.nextPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:choose>
		                        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
		                            <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                        <c:when test="${pageList.page.totalPage>1}">
		                            <li><a href="javascript:feedback.getFeedbackList(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                    </c:choose>
		                    <c:choose>
		                        <c:when test="${pageList.page.hasNext}">
		                            <li><a href="javascript:feedback.getFeedbackList(${pageList.page.nextPage })">&gt;</a></li>
		                        </c:when>
		                        <c:otherwise>
		                            <li><a href="javascript:void(0)">&gt;</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                    <li>
		                        <a>共${pageList.page.totalPage }页&nbsp;|&nbsp;合计${pageList.page.rowCount }条</a>
		                    </li>
		                </ul>
		            </nav>
		        </div>
		    </div>
		</c:if>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" id="feedback-Modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    </div>
  </div>
</div>

