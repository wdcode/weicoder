package com.weicoder.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams; 

/**
 * 生成验证图片,并把验证码保存到sessin中
 * @author WD
 */
public final class VerifyCodeUtil {
	// 生成字节码的字体
	private final static Font CODE_FONT;
	static {
		CODE_FONT = new Font("Times New Roman", Font.CENTER_BASELINE, 18);
	}

	/**
	 * 功能描述 : 生成验证图片 返回验证码 图片写到流中
	 * @param out 写入图片的流
	 * @return 验证码
	 */
	public static String make(OutputStream out) {
		return make(out, CommonParams.VERIFY_LENGTH);
	}

	/**
	 * 功能描述 : 生成验证图片 返回验证码 图片写到流中
	 * @param out 写入图片的流
	 * @param len 生成验证码的长度
	 * @return 验证码
	 */
	public static String make(OutputStream out, int len) {
		// 获得验证码
		String rand = randString();
		try {
			// 高度
			int height = 20;
			// 宽度
			int width = 20 * len;
			// 字符距左边宽度
			int charWidth = (width - height) / len;
			// 字符距上边高度
			int charHeight = 16;
			// 在内存中生成图象
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 获得Graphics
			Graphics g = image.getGraphics();
			// 画随机颜色的边框
			g.setColor(getRandColor(10, 50));
			g.drawRect(0, 0, width - 1, height - 1);
			// 设定背景色
			g.setColor(getRandColor(200, 240));
			g.fillRect(0, 0, width, height);
			// 设置字体
			g.setFont(CODE_FONT);
			// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = RandomUtil.nextInt(width);
				int y = RandomUtil.nextInt(height);
				int xl = RandomUtil.nextInt(12);
				int yl = RandomUtil.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			for (int i = 0; i < len; i++) {
				// 将认证码显示到图象中
				// 设置颜色
				g.setColor(new Color(20 + RandomUtil.nextInt(110), 20 + RandomUtil.nextInt(110), 20 + RandomUtil.nextInt(110)));
				// 写文字
				g.drawString(rand.substring(i, i + 1), charWidth * i + 10, charHeight);
			}
			// 使图片生效
			g.dispose();

			// 写文字到response
			ImageUtil.write(image, out);
		} catch (Exception e) {
			// 记录异常
			Logs.error(e);
		}
		return rand;
	}

	/**
	 * 产生随机字符串
	 * @return 字符串
	 */
	private static String randString() {
		// 声明字符数组
		char[] buf = new char[CommonParams.VERIFY_LENGTH];
		// 获得验证码数组
		char[] code = CommonParams.VERIFY_CODE;
		// 循环获得字符
		for (int i = 0; i < CommonParams.VERIFY_LENGTH; i++)
			// 添件字符
			buf[i] = code[RandomUtil.nextInt(CommonParams.VERIFY_LENGTH)];
		// 获得字符串
		return String.valueOf(buf);
	}

	/**
	 * 给定范围获得随机颜色
	 * @param fc FC
	 * @param bc BC
	 * @return 颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		// fc不能大于255
		if (fc > 255)
			fc = 255;
		// bc不能大于255
		if (bc > 255)
			bc = 255;
		// 获得R
		int r = fc + RandomUtil.nextInt(bc - fc);
		// 获得G
		int g = fc + RandomUtil.nextInt(bc - fc);
		// 获得B
		int b = fc + RandomUtil.nextInt(bc - fc);
		// 返回Color
		return new Color(r, g, b);
	}

	private VerifyCodeUtil() {}
}
