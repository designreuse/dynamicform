<%@ page import="bl.beans.EntryBean" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="../commonHeader.jsp" %>
<head>
    <style>
        fieldset {
            padding: 0;
            margin: 0;
            border: 1px solid gray;
        }

        legend {
            display: block;
            width: auto;
            padding: 0;
            margin-bottom: 0px;
            color: #333333;
            border: 0;
        }
    </style>
</head>
<body>
<!--main content start-->
<section class="panel">
    <div class="panel-body">
        <a id="top0"></a>
        <br>
        <br>
        <%@include file="studyNavigation.jsp" %>
        <form id="studyForm" class="form-horizontal tasi-form" action="${rootPath}/study/savewizardDocument.action"
              method="post">
            <input name="study.id" type="hidden" value="${study.id}"/>
            <input name="activeWizard" type="hidden" value="${activeWizard}"/>

            <div class="mail-box">
                <div class="sm-side" style="float:left;width:280px;">
                    <span><a href="#top0"><h3>模块选择</h3></a></span>
                    <ul class="inbox-nav">
                        <s:iterator value="documentBeanList" status="dindex" var="documentBean">
                            <li>

                                <s:set value="false" name="checkedDocument"/>
                                <s:iterator value="study.studyDocumentBeanList">
                                    <s:if test="%{documentId == #documentBean.id}">
                                        <s:set value="true" name="checkedDocument"/>
                                    </s:if>
                                </s:iterator>
                                <a href="#D<s:property value='id'/>">
                                    <s:if test="%{#checkedDocument==true}">
                                        <input type="checkbox" checked="checked"
                                               name="savedDocumentBeanList[<s:property value="#dindex.index"/>].documentId"
                                               value="<s:property value="id"/>">
                                        <span style="margin-left:20px"><s:property value="name"/></span>
                                    </s:if>
                                    <s:else>
                                        <input type="checkbox"
                                               name="savedDocumentBeanList[<s:property value="#dindex.index"/>].documentId"
                                               value="<s:property value="id"/>">
                                        <span style="margin-left:20px"><s:property value="name"/></span>
                                    </s:else>
                                </a>
                            </li>
                        </s:iterator>
                    </ul>
                </div>
                <div class="inbox-body" style="float:left;margin-left:30px;padding:0px;width:60%">
                    <span><h3>元素选择</h3>
                    </span>
                    <s:set name="entryCounter" value="0"/>
                    <s:iterator value="documentBeanList" status="dindex" var="documentBean">
                        <section clas="panel">
                            <header class="panel-heading" id="basicInfo">
                                <a id="D<s:property value='id'/>"></a>
                                <s:property value="name"/> &nbsp;&nbsp;&nbsp;
                                <input type="button" class="btn btn-info" value="全选" id="selectAll${documentBean.id}"/>
                                <input type="button" class="btn btn-info" value="全不选" id="unSelect${documentBean.id}"/>
                                <input type="button" class="btn btn-info" value="反选" id="reverse${documentBean.id}"/>
                                <script>
                                    $('#selectAll${documentBean.id}').on("click", function () {//全选
                                        $("#checkbox${documentBean.id} :checkbox").each(function () {
                                            this.checked = true;
                                        });
                                    });

                                    $('#unSelect${documentBean.id}').on("click", function () {//全不选
                                        $("#checkbox${documentBean.id} :checkbox").each(function () {
                                            this.checked = false;
                                        });
                                    });

                                    $('#reverse${documentBean.id}').on("click", function () {//反选
                                        $("#checkbox${documentBean.id} :checkbox").each(function () {
                                            this.checked = !this.checked;
                                        });
                                    });
                                </script>
                            </header>
                            <div class="panel-body">
                                <div class="form-group has-success" id="checkbox${documentBean.id}">
                                    <fieldset>
                                        <legend><h5>主元素</h5></legend>
                                        <s:iterator value="entryBeanList" status="eindex" var="entryBeanList">
                                            <s:if test="#entryBeanList.subElementType==0">
                                                <s:set value="false" name="checkedDocumentEntry"/>
                                                <s:iterator value="study.studyDocumentBeanList" var="studyDocumentBean">
                                                    <s:if test="%{documentId == #documentBean.id}">
                                                        <s:iterator
                                                                value="#studyDocumentBean.studyDocumentEntryBeanList"
                                                                var="studyDocumentEntry">
                                                            <s:if test="%{#entryBeanList.id == #studyDocumentEntry.entryId}">
                                                                <s:set value="true" name="checkedDocumentEntry"/>
                                                            </s:if>
                                                        </s:iterator>
                                                    </s:if>
                                                </s:iterator>
                                                <div class="col-lg-4">
                                                    <span><s:property value="sequence"/>.</span>
                                                    <input type="hidden"
                                                           name="savedDocumentEntryBeanList[<s:property value="#entryCounter"/>].documentId"
                                                           value="<s:property value="#documentBean.id"/>">
                                                    <s:if test="%{#checkedDocumentEntry==true}">
                                                        <input type="checkbox" checked="checked"
                                                               name="savedDocumentEntryBeanList[<s:property value="#entryCounter"/>].entryId"
                                                               value="<s:property value="id"/>">
                                                        <span><s:property value="code"/></span> <span><s:property
                                                            value="name"/></span>
                                                    </s:if>
                                                    <s:else>
                                                        <input type="checkbox"
                                                               name="savedDocumentEntryBeanList[<s:property value="#entryCounter"/>].entryId"
                                                               value="<s:property value="id"/>">
                                                        <span><s:property value="code"/></span> <span><s:property
                                                            value="name"/></span>
                                                    </s:else>
                                                </div>
                                                <s:set name="entryCounter" value="%{#entryCounter + 1}"/>
                                            </s:if>
                                        </s:iterator>
                                    </fieldset>
                                    <fieldset style="background-color:#ddddd8">
                                        <legend><h5>子元素</h5></legend>
                                        <s:iterator value="entryBeanList" status="eindex" var="entryBeanList">
                                            <s:if test="#entryBeanList.subElementType!=0">
                                                <s:set value="false" name="checkedDocumentEntry"/>
                                                <s:iterator value="study.studyDocumentBeanList" var="studyDocumentBean">
                                                    <s:if test="%{documentId == #documentBean.id}">
                                                        <s:iterator
                                                                value="#studyDocumentBean.studyDocumentEntryBeanList"
                                                                var="studyDocumentEntry">
                                                            <s:if test="%{#entryBeanList.id == #studyDocumentEntry.entryId}">
                                                                <s:set value="true" name="checkedDocumentEntry"/>
                                                            </s:if>
                                                        </s:iterator>
                                                    </s:if>
                                                </s:iterator>
                                                <div class="col-lg-4">
                                                    <span><s:property value="sequence"/>.</span>
                                                    <input type="hidden"
                                                           name="savedDocumentEntryBeanList[<s:property value="#entryCounter"/>].documentId"
                                                           value="<s:property value="#documentBean.id"/>">
                                                    <s:if test="%{#checkedDocumentEntry==true}">
                                                        <input type="checkbox" checked="checked"
                                                               name="savedDocumentEntryBeanList[<s:property value="#entryCounter"/>].entryId"
                                                               value="<s:property value="id"/>">
                                                        <span><s:property value="code"/></span> <span><s:property
                                                            value="name"/></span>
                                                    </s:if>
                                                    <s:else>
                                                        <input type="checkbox"
                                                               name="savedDocumentEntryBeanList[<s:property value="#entryCounter"/>].entryId"
                                                               value="<s:property value="id"/>">
                                                        <span><s:property value="code"/></span> <span><s:property
                                                            value="name"/></span>
                                                    </s:else>
                                                </div>
                                                <s:set name="entryCounter" value="%{#entryCounter + 1}"/>
                                            </s:if>
                                        </s:iterator>
                                    </fieldset>
                                </div>
                            </div>
                        </section>
                    </s:iterator>
                </div>
            </div>
            <br>
            <br>
            <%@ include file="/pages/study/studyAction.jsp" %>
        </form>
    </div>
</section>
</body>
</html>