<!-- JS -->
<#macro js name>
<script type="text/javascript" src="${base}/wdstatic/js/${name}.js"></script>
</#macro>
<!-- CSS -->
<#macro css name>
<link href="${base}/wdstatic/css/${name}.css" rel="stylesheet" type="text/css">
</#macro>
<!-- Editor -->
<#macro editor name>
<script type="text/javascript" src="${base}/wdstatic/editor/${name}/${name}.js"></script>
</#macro>
<!-- 验证码 -->
<#macro verifycode css='' url='verify'>
<img id="verifyCode" src="<@s.url action=url/>" 
<#if css != ''>
 class="${css}"<#rt/>
</#if>
onclick='javascript:var src=this.src;src.indexOf("?")==-1?this.src=src+"?":this.src=src.substring(0,src.indexOf("?")'
style='cursor:pointer;*cursor:hand;'/>
</#macro> 
<!-- 分页 -->
<#macro pagination flag='pager.currentPage' id='pagerForm'>
<#if pager?? && pager.totalSize &gt; 0 && pager.totalSize &gt; pager.pageSize>
<#assign currentPage = pager.currentPage>
<#assign pageSize = pager.pageSize>
<#assign totalPage = pager.totalPage>
<#assign totalSize = pager.totalSize>
<#assign startPage = pager.startPage>
<#assign endPage = pager.endPage>
<div id='pagination'>
	<input type='hidden' id='${flag}' name='${flag}' value='${currentPage}'/>
	<dl id='page_main'>
	<dd id='page_info'>
	共<span class='page_cue_span'> 
	${totalSize}
	</span>条记录,
	每页显示<span class='page_cue_span'> 
	${pageSize}
	 </span>条记录,
	当前显示第<span class='page_cue_span'> 
	${currentPage}
	 </span>页,共<span class='page_cue_span'>
	<label id='totalPage'> 
	${totalPage}
	 </label>
	</span>页
	&nbsp;&nbsp;
	<#if currentPage &gt; 1> 
		<a href='javascript:go(1)' class='page_link'>首 页</a>&nbsp;
		<a href='javascript:go(${currentPage} - 1)' class='page_link'>上一页</a>
	<#else>
		首 页&nbsp;上一页
	</#if>
	&nbsp;&nbsp;
	<#if currentPage &gt; 1>
		<a href='javascript:go(${currentPage} - 10 < 1 ? 1 : currentPage - 10)' class='page_link'>＜＜</a>
	</#if> 
	<#list startPage..endPage as i>
		<#if i != currentPage>
			<a href='javascript:go(${i})' class='page_link'>${i}</a> 
		<#else>
			[<span id='page_curr'>${i}</span>]
		</#if>
	</#list> 
	<#if endPage &lt; totalPage>
		<a href='javascript:go(${currentPage} + 10 > ${totalPage} ? ${totalPage} : ${currentPage} + 10)' class='page_link'>＞＞</a>
	</#if>  
	&nbsp;&nbsp;
	<#if currentPage &lt; endPage>
		<a href='javascript:go(${currentPage} + 1)' class='page_link'>下一页</a>
		<a href='javascript:go(${totalPage})' class='page_link'>尾 页</a>&nbsp;
	<#else>
		下一页&nbsp;尾页
	</#if>
	&nbsp;&nbsp; 
	<!--
	<input type='text' name='page_num' id='page_num' />&nbsp;&nbsp;
	<input type='button' onclick='goPage();' name='page_go' id='page_go' value='GO'/>
	-->
	</dd>
	</dl>	
	<script language='Javascript' type='text/javascript'>
	<!--
	function goPage(){
		var num = document.getElementById('page_num').value;
		var totalPage = ${totalPage};
		if(!(/^\\d+$/.test(num))){
			return false;
		}	
		if(parseInt(num) > parseInt(totalPage)){
			num = totalPage;
		}	
		if(parseInt(num) < 1){
			num = 1;
		}
		go(num);
	}
	-->
	function go(num){
		document.getElementById('${flag}').value = num;
		document.getElementById('${id}').submit();
	}
	</script> 
</div> 
</#if>
</#macro>
<!-- 类型 -->
<#macro type field>  
<#if field.div?exists>
	<div css="${field.div}">
</#if>
<#switch field.type>
	<#case "select">
		<@s.select name=field.name!null cssClass=field.cssClass!null value=field.value!null id=field.id!null headerKey=field.headerKey!null headerValue=field.headerValue!null list=field.list!null listKey=field.listKey!null listValue=field.listValue!null />
		<#break>
	<#case "textfield">
		<@s.textfield cssClass=field.cssClass!null name=field.name!null id=field.id!null readonly=field.readonly!null value=field.value!null/>
		<#break>
	<#case "label">
		<@s.label name=field.name!null value=field.value!null/>
		<#break>
	<#case "password">
		<@s.password cssClass=field.cssClass!null name=field.name!null value=field.value!null/>
		<#break>
	<#case "file">
		<@s.file cssClass=field.cssClass!null name=field.name!null/>
		<#break>
	<#case "textarea">
		<@s.textarea cssClass=field.cssClass!null name=field.name!null/>
		<#break>
	<#case "button">
		<@s.submit type="button" cssClass=field.cssClass!null value=field.value!null accesskey=field.accesskey!null id=field.id!null action=field.action!null/>
		<#break>
	<#case "checkbox">
		<@s.checkbox cssClass=field.cssClass!null value=field.value!null id=field.id!null name=field.name!null/> 
		<#break>
	<#case "checkboxlist">
		<@s.checkboxlist name=field.name!null cssClass=field.cssClass!null value=field.value!null id=field.id!null headerKey=field.headerKey!null headerValue=field.headerValue!null list=field.list!null listKey=field.listKey!null listValue=field.listValue!null accesskey=field.accesskey!null/>
		<#break>
	<#case "submit"> 
		<@s.submit cssClass=field.cssClass!null value=field.value!null accesskey=field.accesskey!null id=field.id!null action=field.action!null/>
		<#break>
	<#case "set">
		<@s.set value=field.value!null name=field.name!null/>
		<#break> 
	<#case "hidden">
		<@s.hidden value=field.value!null name=field.name!null/>
		<#break> 
	<#case "none">
		${(stack.findString(field.value))!field.default}
		<#break>  
</#switch>		
<#if field.div?exists>
	</div>
</#if>
</#macro>