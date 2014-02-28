package com.weicoder.core.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.core.log.Logs;

/**
 * Lucene 相关操作
 * @author WD
 * @since JDK7
 * @version 1.0 2012-11-18
 */
public final class LuceneEngine {
	// 索引目录
	private static Directory		dir;
	// 分词器
	private static Analyzer			analyzer;
	// 索引写入器
	private static IndexWriter		writer;
	// 版本
	private static Version			version	= Version.LUCENE_47;
	// 索引查询器
	private static IndexSearcher	searcher;

	/**
	 * 初始化Lucene
	 * @param path 保存目录 ram使用内存 其它磁盘路径
	 */
	public static void init(String path) {
		try {
			init("ram".equals(path) ? new RAMDirectory() : FSDirectory.open(FileUtil.getFile(path)), new StandardAnalyzer(version), version);
		} catch (IOException e) {
			Logs.debug(e);
		}
	}

	/**
	 * 初始化Lucene
	 * @param dir 保存目录
	 * @param analyzer 分词器
	 * @param version 版本
	 */
	public static void init(Directory dir, Analyzer analyzer, Version version) {
		try {
			LuceneEngine.dir = dir;
			LuceneEngine.analyzer = analyzer;
			LuceneEngine.version = version;
			LuceneEngine.writer = new IndexWriter(dir, new IndexWriterConfig(version, analyzer));
			searcher();
		} catch (IOException e) {
			Logs.debug(e);
		}
	}

	/**
	 * 写入索引
	 * @param data 数据
	 */
	public static void write(Map<String, String> data) {
		try {
			// 声明文档
			Document doc = new Document();
			// 给文档赋值
			for (Map.Entry<String, String> e : data.entrySet()) {
				doc.add(new Field(e.getKey(), e.getValue(), TextField.TYPE_STORED));
			}
			// 写入文档
			writer.addDocument(doc);
			// 提交
			writer.commit();
		} catch (IOException e) {
			Logs.debug(e);
			// 回滚
			try {
				writer.rollback();
			} catch (IOException e1) {}
		}
	}

	/**
	 * 搜索数据
	 * @param field 要查询的字段名
	 * @param value 要查询的字段值
	 * @param n 查询数量
	 * @return
	 */
	public static List<Map<String, String>> search(String field, String value, int n) {
		// 声明返回列表
		List<Map<String, String>> list = Lists.getList();
		// 检查查询器
		if (searcher()) {
			try {
				// 实例化查询器
				Query query = new QueryParser(version, field, analyzer).parse(value);
				// 获得文档索引
				ScoreDoc[] hits = searcher.search(query, null, n).scoreDocs;
				// 循环读取数据
				for (int i = 0; i < hits.length; i++) {
					// 声明返回Map数据
					Map<String, String> map = Maps.getMap();
					// 循环字段
					for (IndexableField f : searcher.doc(hits[i].doc).getFields()) {
						// 添加到数据
						map.put(f.name(), f.stringValue());
					}
					// 添加到列表
					list.add(map);
				}
			} catch (Exception e) {
				Logs.debug(e);
			}
		}
		// 返回列表
		return list;

	}

	/**
	 * searcher是否存在
	 * @return searcher是否存在
	 */
	private static boolean searcher() {
		try {
			return (searcher = (searcher == null ? new IndexSearcher(DirectoryReader.open(dir)) : searcher)) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private LuceneEngine() {}
}
