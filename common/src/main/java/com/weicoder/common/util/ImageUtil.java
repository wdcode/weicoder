package com.weicoder.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.weicoder.common.io.FileUtil;
import com.weicoder.common.log.Logs;

/**
 * 对普通图片处理
 * @author WD
 */
public final class ImageUtil {
	// 图片格式名
	private final static String	FORMAT;
	// 字体
	private final static Font	FONT;
	// 颜色
	private final static Color	COLOR;
	static {
		FORMAT = "JPEG";
		FONT = new Font("宋体", Font.PLAIN, 15);
		COLOR = Color.WHITE;
	}

	/**
	 * 判断是否为图片
	 * @param img 图片文件
	 * @return 是否为图片
	 */
	public static boolean isImage(File img) {
		try {
			return isImage(ImageIO.read(img));
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 判断图片是否为空
	 * @param img 图片对象
	 * @return 是否为空
	 */
	public static boolean isImage(Image img) {
		return img != null && img.getWidth(null) > -1 && img.getHeight(null) > -1;
	}

	/**
	 * 压缩图片 rate 比例 * rate / 100
	 * @param input 图片文件
	 * @param out 输出流
	 * @param rate 缩小比例
	 * @param scale 压缩级别 参照Image.SCALE_*
	 */
	public static void compress(File input, OutputStream out, int rate, int scale) {
		compress(input, out, rate, -1, scale);
	}

	/**
	 * 压缩图片
	 * @param input 图片文件
	 * @param out 输出流
	 * @param width 宽度
	 * @param height 高度
	 * @param scale 压缩级别 参照Image.SCALE_*
	 */
	public static void compress(File input, OutputStream out, int width, int height, int scale) {
		try {
			// 读取文件
			Image img = ImageIO.read(input);
			// 判断图片格式是否正确
			if (isImage(img)) {
				// 设置宽高
				if (height == -1) {
					// 高为-1是 width为比例缩放
					height = img.getHeight(null) * width / 100;
					width = img.getWidth(null) * width / 100;
				}
				// 声明缓存图片
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				// Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				tag.getGraphics().drawImage(img.getScaledInstance(width, height, scale), 0, 0, null);
				// 写入图片
				write(tag, out);
			}
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 抓屏保存图片
	 * @param out 输出流
	 */
	public static void captureScreen(OutputStream out) {
		try {
			ImageIO.write(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), FORMAT, out);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 添加文字到图片
	 * @param text 要添加的文字
	 * @param file 添加文字的图片文件
	 */
	public static void writeString(String text, File file) {
		writeString(text, file, -1, -1);
	}

	/**
	 * 添加文字到图片
	 * @param text 要添加的文字
	 * @param file 添加文字的图片文件
	 * @param x 添加位置的X轴
	 * @param y 添加位置的Y轴
	 */
	public static void writeString(String text, File file, int x, int y) {
		try {
			// 添加文字
			writeString(text, ImageIO.read(file), FileUtil.getOutputStream(file), x, y);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 添加文字到图片
	 * @param text 要添加的文字
	 * @param image 添加文字的图片对象
	 * @param out 输出流
	 */
	public static void writeString(String text, BufferedImage image, OutputStream out) {
		writeString(text, image, out, -1, -1);
	}

	/**
	 * 添加文字到图片
	 * @param text 要添加的文字
	 * @param image 添加文字的图片对象
	 * @param out 输出流 把图片输出到这个流上
	 * @param x 添加位置的X轴
	 * @param y 添加位置的Y轴
	 */
	public static void writeString(String text, BufferedImage image, OutputStream out, int x, int y) {
		// 获得Graphics 用于写图片
		Graphics g = image.getGraphics();
		// 设置字体
		g.setFont(FONT);
		// 设置颜色
		g.setColor(COLOR);
		// 如果x==-1
		if (x == -1)
			// 把X设置为图片中央
			x = (image.getWidth(null) - getStringWidth(text, g.getFontMetrics())) / 2;
		// 如果y==-1
		if (y == -1)
			// 把X设置为图片中央
			y = (image.getHeight(null) + FONT.getSize()) / 2;
		// 写内容
		g.drawString(text, x, y);
		// 释放资源使图片生效
		g.dispose();
		// 写图片
		write(image, out);
	}

	/**
	 * 写图片
	 * @param image 图片对象
	 * @param out 输出流
	 */
	public static void write(BufferedImage image, OutputStream out) {
		try {
			ImageIO.write(image, FORMAT, out);
		} catch (IOException e) {
			Logs.warn(e);
		}
	}

	/**
	 * 添加图片到图片上
	 * @param draw 要添加的图片
	 * @param image 写到的图片
	 */
	public static void writeImage(File draw, File image) {
		writeImage(draw, image, -1, -1);
	}

	/**
	 * 添加图片到图片上
	 * @param draw 要添加的图片
	 * @param image 写到的图片
	 * @param x X坐标
	 * @param y Y坐标
	 */
	public static void writeImage(File draw, File image, int x, int y) {
		try {
			writeImage(ImageIO.read(draw), ImageIO.read(image), FileUtil.getOutputStream(image), x, y);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 添加图片到图片上
	 * @param draw 要添加的图片
	 * @param image 写到的图片
	 * @param out 输出流
	 */
	public static void writeImage(Image draw, BufferedImage image, OutputStream out) {
		writeImage(draw, image, out, -1, -1);
	}

	/**
	 * 添加图片到图片上
	 * @param draw 要添加的图片
	 * @param image 写到的图片
	 * @param out 输出流
	 * @param x 添加位置的X轴
	 * @param y 添加位置的Y轴
	 */
	public static void writeImage(Image draw, BufferedImage image, OutputStream out, int x, int y) {
		// 获得Graphics 用于写图片
		Graphics g = image.getGraphics();
		// 设置字体
		g.setFont(FONT);
		// 设置颜色
		g.setColor(COLOR);
		// 如果x==-1
		if (x == -1)
			// 把X设置为图片中央
			x = (image.getWidth(null) - draw.getWidth(null)) / 2;
		// 如果y==-1
		if (y == -1)
			// 把X设置为图片中央
			y = (image.getHeight(null) - draw.getHeight(null)) / 2;
		// 写内容
		g.drawImage(draw, x, y, null);
		// 释放资源使图片生效
		g.dispose();
		// 写图片
		write(image, out);
	}

	/**
	 * 获得文字高度
	 * @param text 文字内容
	 * @param fm FontMetrics对象
	 * @return 宽度
	 */
	private static int getStringWidth(String text, FontMetrics fm) {
		// 初始化宽度值
		int intReturn = 0;
		// 获得文字的字节
		char[] chars = text.toCharArray();
		// 循环字节
		for (int i = 0; i < chars.length; i++)
			// 添加到宽度中
			intReturn += fm.charWidth(chars[i]);
		// 返回宽度
		return intReturn;
	}

	private ImageUtil() {}
}
