package com.weicoder.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.ImageConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.core.log.Logs;
import com.weicoder.web.constants.HttpConstants;
import com.weicoder.web.params.WebParams;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ImageUtil;
import com.weicoder.common.util.RandomUtil;

/**
 * 生成验证图片,并把验证码保存到sessin中
 * @author WD
 * @since JDK7
 * @version 1.0 2011-05-01
 */
public final class VerifyCodeUtil {
	/**
	 * 校验验证码
	 * @param request
	 * @param response
	 * @param code 校验码
	 * @return true 成功 false 失败
	 */
	public static boolean check(HttpServletRequest request, HttpServletResponse response, String code) {
		// 获得验证码
		String vc = getValue(request);
		// 如果验证码没有 返回成功
		if (!EmptyUtil.isEmpty(vc)) {
			// 清除验证码
			VerifyCodeUtil.removeValue(request, response);
			// 验证验证码
			return vc.equalsIgnoreCase(code);
		}
		return true;
	}

	/**
	 * 功能描述 : 生成验证图片,并把验证码保存
	 * @param request
	 * @param response
	 */
	public static void make(HttpServletRequest request, HttpServletResponse response) {
		make(request, response, true);
	}

	/**
	 * 功能描述 : 生成验证图片,并把验证码保存
	 * @param request
	 * @param response
	 * @param isClose 是否关闭流
	 */
	public static void make(HttpServletRequest request, HttpServletResponse response, boolean isClose) {
		make(request, response, WebParams.VERIFY_KEY, isClose);
	}

	/**
	 * 功能描述 : 生成验证图片,并把验证码保存到sessin中
	 * @param request
	 * @param response
	 * @param key 保存在session中的key
	 * @param isClose 是否关闭流
	 */
	public static void make(HttpServletRequest request, HttpServletResponse response, String key, boolean isClose) {
		try {
			// 设置页面不缓存
			ResponseUtil.noCache(response);
			// 设置文件类型
			response.setContentType(HttpConstants.CONTENT_TYPE_JPEG);
			// 获得验证码
			String rand = randString();
			// 高度
			int height = 20;
			// 宽度
			int width = 20 * WebParams.VERIFY_LENGTH;
			// 字符距左边宽度
			int charWidth = (width - height) / WebParams.VERIFY_LENGTH;
			// 字符距上边高度
			int charHeight = 16;
			// 在内存中生成图象
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 字体
			Font codeFont = new Font(WebParams.VERIFY_FONT, Font.CENTER_BASELINE, 18);
			// 获得Graphics
			Graphics g = image.getGraphics();
			// 画随机颜色的边框
			g.setColor(getRandColor(10, 50));
			g.drawRect(0, 0, width - 1, height - 1);
			// 设定背景色
			g.setColor(getRandColor(200, 240));
			g.fillRect(0, 0, width, height);
			// 设置字体
			g.setFont(codeFont);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = RandomUtil.nextInt(width);
				int y = RandomUtil.nextInt(height);
				int xl = RandomUtil.nextInt(12);
				int yl = RandomUtil.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			for (int i = 0; i < WebParams.VERIFY_LENGTH; i++) {
				// 将认证码显示到图象中
				// 设置颜色
				g.setColor(new Color(20 + RandomUtil.nextInt(110), 20 + RandomUtil.nextInt(110), 20 + RandomUtil.nextInt(110)));
				// 写文字
				g.drawString(rand.substring(i, i + 1), charWidth * i + 10, charHeight);
			}
			// 使图片生效
			g.dispose();

			// 保存属性
			AttributeUtil.set(request, response, key, rand);

			// 写文字到response
			ImageUtil.write(image, ImageConstants.JPEG, response.getOutputStream(), isClose);
		} catch (Exception e) {
			// 记录异常
			Logs.error(e);
		}
	}

	/**
	 * 获得验证码
	 * @param request Request
	 * @return 验证码
	 */
	public static String getValue(HttpServletRequest request) {
		return Conversion.toString(AttributeUtil.get(request, WebParams.VERIFY_KEY));
	}

	/**
	 * 获得验证码
	 * @param request Request
	 * @param response Response
	 * @return 验证码
	 */
	public static void removeValue(HttpServletRequest request, HttpServletResponse response) {
		AttributeUtil.remove(request, response, WebParams.VERIFY_KEY);
	}

	/**
	 * 产生随机字符串
	 * @return
	 */
	private static String randString() {
		// 声明字符数组
		char[] buf = new char[WebParams.VERIFY_LENGTH];
		// 获得验证码数组
		char[] code = WebParams.VERIFY_CODE_CHARS;
		// 循环获得字符
		for (int i = 0; i < WebParams.VERIFY_LENGTH; i++) {
			// 添件字符
			buf[i] = code[RandomUtil.nextInt(code.length)];
		}
		// 获得字符串
		return String.valueOf(buf);
	}

	/**
	 * 给定范围获得随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) {
		// fc不能大于255
		if (fc > 255) {
			fc = 255;
		}
		// bc不能大于255
		if (bc > 255) {
			bc = 255;
		}
		// 获得R
		int r = fc + RandomUtil.nextInt(bc - fc);
		// 获得G
		int g = fc + RandomUtil.nextInt(bc - fc);
		// 获得B
		int b = fc + RandomUtil.nextInt(bc - fc);
		// 返回Color
		return new Color(r, g, b);
	}

	/**
	 * 私有构造
	 */
	private VerifyCodeUtil() {}
}
