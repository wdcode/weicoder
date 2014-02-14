package com.weicoder.tag.ui;

import com.weicoder.base.params.BaseParams;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.base.bean.Pagination;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.tag.base.BaseSimpleTag;

/**
 * 分页标签
 * @author WD
 * @since JDK7
 * @version 1.0 2009-05-16
 */
public final class PaginationTag extends BaseSimpleTag {
	// 分页Bean
	private Pagination	pager;

	/**
	 * 获得分页Bean
	 * @return 分页Bean
	 */
	public Pagination getPager() {
		return pager;
	}

	/**
	 * 设置分页Bean
	 * @param pager
	 */
	public void setPager(Pagination pager) {
		this.pager = pager;
	}

	/**
	 * 判断分页Bean是否为空
	 * @return
	 */
	private boolean isEmpty() {
		return EmptyUtil.isEmpty(pager) || pager.getTotalSize() == 0;
	}

	@Override
	protected String getInfo() {
		// 如果page为空
		if (isEmpty()) {
			// 查询作用域里的page
			pager = (Pagination) getJspContext().findAttribute("pager");
			// 如果page为空
			if (isEmpty()) {
				// 直接返回不做处理
				return StringConstants.EMPTY;
			}
		}

		// 当前页
		int currentPage = pager.getCurrentPage();
		// 每页显示多少
		int pageSize = pager.getPageSize();
		// 总页数
		int totalPage = pager.getTotalPage();
		// 总数量
		int totalSize = pager.getTotalSize();
		// 开始页
		int startPage = pager.getStartPage();
		// 结束页
		int endPage = pager.getEndPage();
		// 当前页的标识
		String pageFlag = BaseParams.PAGE_FLAG;
		// 可变字符串缓存 用于保存 分页条
		StringBuilder info = new StringBuilder("<div id='pagination'>");

		// 隐藏表单域 保存当前页
		info.append("<input type='hidden' id='");
		info.append(pageFlag);
		info.append("' name='");
		info.append(pageFlag);
		info.append("' value='");
		info.append(currentPage);
		info.append("'/>");

		// 分页条 begin
		// 添加分页显示的信息
		info.append("<dl id='page_main'>");
		info.append("<dd id='page_info'>");
		info.append("共<span class='page_cue_span'> ");
		info.append(totalSize);
		info.append(" </span>条记录,");
		info.append("每页显示<span class='page_cue_span'> ");
		info.append(pageSize);
		info.append(" </span>条记录,");
		info.append("当前显示第<span class='page_cue_span'> ");
		info.append(currentPage);
		info.append(" </span>页,共<span class='page_cue_span'>");
		info.append("<label id='totalPage'> ");
		info.append(totalPage);
		info.append(" </label>");
		info.append("</span>页");

		info.append("&nbsp;&nbsp;");

		// 添加 首 页 和 上一页
		if (currentPage > 1) {
			info.append("<a href='javascript:go(1)' class='page_link'>首 页</a>&nbsp;");
			info.append("<a href='javascript:go(");
			info.append(currentPage - 1);
			info.append(")' class='page_link'>上一页</a>");
		} else {
			info.append("首 页&nbsp;上一页 ");
		}
		info.append("&nbsp;&nbsp;");

		// 添加向前箭头
		if (startPage > 1) {
			info.append("<a href='javascript:go(");
			info.append(currentPage - 10 < 1 ? 1 : currentPage - 10);
			info.append(")' class='page_link'>＜＜</a>");
		}

		// 添加 数字标签
		for (int i = startPage; i <= endPage; i++) {
			if (i != currentPage) {
				info.append("<a href='javascript:go(");
				info.append(i);
				info.append(")' class='page_link'>");
				info.append(i);
				info.append("</a>");
			} else {
				info.append("[<span id='page_curr'>");
				info.append(i);
				info.append("</span>]");
			}
		}

		// 添加向后箭头
		if (endPage < totalPage) {
			info.append("<a href='javascript:go(");
			info.append(currentPage + 10 > totalPage ? totalPage : currentPage + 10);
			info.append(")' class='page_link'>＞＞</a>");
		}

		// 添加 下一页 和 首 页
		info.append("&nbsp;&nbsp;");
		if (currentPage < endPage) {
			info.append("<a href='javascript:go(");
			info.append(currentPage + 1);
			info.append(")' class='page_link'>下一页</a>");
			info.append("<a href='javascript:go(");
			info.append(totalPage);
			info.append(")' class='page_link'>尾 页</a>&nbsp;");
		} else {
			info.append("下一页&nbsp;尾页");
		}

		info.append("&nbsp;&nbsp;");

		// 添加选择页码
		info.append("<input type='text' name='page_num' id='page_num' />&nbsp;&nbsp;");
		info.append("<input type='button' onclick='goPage();' name='page_go' id='page_go' value='GO' />");

		info.append("</dd>");
		info.append("</dl>\n");
		// 分页条 end

		// 添加JavaScript begin
		info.append("<script language='Javascript' type='text/javascript'>\n");
		info.append("function goPage(){\n");
		info.append("var num = document.getElementById('page_num').value;\n");
		info.append("var totalPage = document.getElementById('totalPage').innerText;\n");

		info.append("if(!(/^\\d+$/.test(num))){");
		info.append("return false;");
		info.append("}\n");

		info.append("if(parseInt(num) > parseInt(totalPage)){\n");
		info.append("num = totalPage;\n");
		info.append("}\n");

		info.append("if(parseInt(num) < 1){\n");
		info.append("num = 1;\n");
		info.append("}\n");
		info.append("go(num);");
		info.append("}\n");

		info.append("function go(num){\n");
		info.append("document.getElementById('");
		info.append(pageFlag);
		info.append("').value = num;\n");
		info.append("document.getElementsByTagName('form')[0].submit();\n");
		info.append("}\n");
		info.append("</script>");
		// JavaScript end
		info.append("</div>");
		return info.toString();
	}
}
