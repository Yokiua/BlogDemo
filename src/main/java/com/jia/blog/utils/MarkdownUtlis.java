package com.jia.blog.utils;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * 解析Markdown格式的工具类,使用 commonmark-java 工具
 */
public class MarkdownUtlis {
    /**
     * 将markdown格式文本转换成html(基础版)
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展(标题锚点,表格生成)
     * markdown转换成html
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的功能
        List<Extension> tableExtensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtensions)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtensions)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    /**
     * 处理标签属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            //如果属于table属性,添加表格样式
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String table = "| hello | hi  | 哈哈哈   |\n" +
                "| ----- | ----- | ----- |\n" +
                "| 啊啦啦那 | 啾啾啾啾 | 为为为为为为维稳 |\n" +
                "| 啊12412414那 | 11414啾 | 12131231 |\n" +
                "\n";
        String a = "[imCoding 爱编程](https://www.baidu.com)";
        System.out.println(markdownToHtmlExtensions(a));
    }

}
