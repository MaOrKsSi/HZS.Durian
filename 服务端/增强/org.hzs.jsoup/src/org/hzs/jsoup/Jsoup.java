package org.hzs.jsoup;

import org.hzs.jsoup.nodes.Document;
import org.hzs.jsoup.parser.Parser;
import org.hzs.jsoup.safety.Cleaner;
import org.hzs.jsoup.safety.Whitelist;
import org.hzs.jsoup.helper.DataUtil;
import org.hzs.jsoup.helper.HttpConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.hzs.jsoup.nodes.Attribute;
import org.hzs.jsoup.nodes.Element;

/**
 * The core public access point to the jsoup functionality.
 *
 * @author Jonathan Hedley
 */
public final class Jsoup {

   public static String g过滤(final String ciHTML_s) {
      StringBuilder ji_S = null;
      String[] ji_sArray = null;
      String ji_s = null;
      Element jd标签 = null;
      try {
         ji_S = new StringBuilder(4096);
         jd标签 = parse(ciHTML_s.trim()).body();
         g过滤(jd标签);
         ji_s = jd标签.outerHtml();
         ji_sArray = ji_s.split("\n");
         for (int ji_i = 0; ji_i < ji_sArray.length; ji_i++) {
            ji_s = ji_sArray[ji_i].trim();
            if (ji_i == 0 && ji_s.startsWith("<body") || ji_i == ji_sArray.length - 1 && ji_s.startsWith("</body")) {
               continue;
            }
            ji_S.append(ji_s);
         }
         return ji_S.toString();
      } finally {
         ji_S = null;
         ji_sArray = null;
         ji_s = null;
         jd标签 = null;
      }
   }

   private static void g过滤(Element cd标签) {
      String ji标签名_s = null;
      Element jd标签 = null;
      StringBuilder ji_S = null;
      try {
         for (int ji1_i = cd标签.children().size() - 1; ji1_i >= 0; ji1_i--) {
            jd标签 = cd标签.child(ji1_i);
            ji标签名_s = jd标签.nodeName();
            switch (ji标签名_s) {
               default:
                  jd标签.remove();
                  continue;
               case "abbr"://通过对缩写词语进行标记，您就能够为浏览器、拼写检查程序、翻译系统以及搜索引擎分度器提供有用的信息。
               case "address"://标签定义文档作者或拥有者的联系信息。
               case "article"://标签定义独立的内容。
               case "aside"://标签定义其所处内容之外的内容。
               case "b"://标签定义粗体的文本。
               case "bdi"://标签允许您设置一段文本，使其脱离其父元素的文本方向设置。
               case "bdo"://标签覆盖默认的文本方向。
               case "br"://标签插入简单的换行符。
               case "caption"://标签定义表格的标题。
               case "cite"://标签定义作品（比如书籍、歌曲、电影、电视节目、绘画、雕塑等等）的标题。
               case "code"://
               case "col"://标签为表格中的一个或多个列定义属性值。
               case "colgroup"://标签用于对表格中的列进行组合，以便对其进行格式化。
               case "dl"://标签定义一个定义列表
               case "dfn"://定义一个定义项目。
               case "div"://标签定义 HTML 文档中的分隔（division）或部分（section）。
               case "em"://呈现为被强调的文本。
               case "figure"://标签规定独立的流内容（图像、图表、照片、代码等等）。"
               case "footer"://标签定义 section 或 document 的页脚。
               case "h1"://标题
               case "h2"://标题
               case "h3"://标题
               case "h4"://标题
               case "h5"://标题
               case "h6"://标题
               case "hgroup"://标签用于对网页或区段（section）的标题进行组合。
               case "hr"://标签水平线，它应该定义内容中的主题变化。
               case "i"://标签呈现斜体的文本。
               case "kbd"://定义键盘文本。它表示文本是从键盘上键入的。它经常用在与计算机相关的文档或手册中。
               case "label"://标签为 input 元素定义标签（label）。
               case "mark"://标签定义带有记号的文本。请在需要突出显示文本时使用 <m> 标签。
               case "menu"://标签定义菜单列表。当希望列出表单控件时使用该标签。
               case "nav":// 标签定义导航链接的部分。
               case "p"://标签定义段落。
               case "pre"://元素可定义预格式化的文本。被包围在 pre 元素中的文本通常会保留空格和换行符。而文本也会呈现为等宽字体。<pre> 标签的一个常见应用就是用来表示计算机的源代码。
               case "samp"://定义样本文本。
               case "small"://标签将旁注 (side comments) 呈现为小型文本。
               case "span"://标签用于对文档中的行内元素进行组合。
               case "strong"://定义重要的文本。
               case "sub"://定义下标文本。
               case "summary"://标签包含 details 元素的标题，"details" 元素用于描述有关文档或文档片段的详细信息。
               case "sup"://定义上标文本。
               case "figcaption"://标签定义 figure 元素的标题（caption）。
               case "dd"://
               case "dt"://
               case "li"://标签定义列表项，有序列表和无序列表中都使用 <li> 标签。
               case "legend"://元素为以下元素定义标题（caption）：<fieldset>、<figure>、<details>。
               case "tbody":
               case "tfoot":
               case "scope":
               case "tr":
               case "ul":
               case "var":
               case "wbr":
                  jg过滤属性(jd标签, null);
                  break;
               case "q"://标签定义一个短的引用。
               case "blockquote"://标签定义摘自另一个源的块引用。
                  jg过滤属性(jd标签, new String[]{"cite"});
                  break;
               case "command"://元素表示用户能够调用的命令。
                  jg过滤属性(jd标签, new String[]{"checked", "disabled", "icon", "label", "radiogroup", "type"});
                  break;
               case "del"://标签定义文档中已删除的文本。
               case "ins"://标签定义文档的其余部分之外的插入文本。
                  jg过滤属性(jd标签, new String[]{"cite", "datetime"});
                  break;
               case "details"://标签用于描述文档或文档某个部分的细节。
                  jg过滤属性(jd标签, new String[]{"open"});
                  break;
               case "fieldset"://标签用于从逻辑上将表单中的元素组合起来。标签会在相关表单元素周围绘制边框。
                  jg过滤属性(jd标签, new String[]{"disabled", "name"});
                  break;
               case "img"://标签定义 HTML 页面中的图像。
                  jg过滤属性(jd标签, new String[]{"alt", "src", "height", "width"});
                  break;
               case "meter"://标签定义度量衡。仅用于已知最大和最小值的度量。
                  jg过滤属性(jd标签, new String[]{"height", "low", "max", "min", "optimum", "value"});
                  break;
               case "ol"://标签定义有序列表。
                  jg过滤属性(jd标签, new String[]{"start"});
                  break;
               case "optgroup"://标签定义选项组。此元素允许您组合选项。当您使用一个长的选项列表时，对相关的选项进行组合会使处理更加容易。
                  jg过滤属性(jd标签, new String[]{"label", "disable"});
                  break;
               case "option"://
                  jg过滤属性(jd标签, new String[]{"disabled", "label", "selected", "value"});
                  break;
               case "progress"://标签定义运行中的进度（进程）。可以使用 <progress> 标签来显示 JavaScript 中耗费时间的函数的进度。
                  jg过滤属性(jd标签, new String[]{"max", "value"});
                  break;
               case "select"://标签创建下拉列表。
                  jg过滤属性(jd标签, new String[]{"disabled", "multiple"});
                  break;
               case "table":// 标签定义 HTML 表格。  一个简单的 HTML 表格包括 table 元素，一个或多个 tr、th 以及 td 元素。   tr 元素定义表格行，th 元素定义表头，td 元素定义表格单元。   更复杂的 HTML 表格也可能包含 caption, col, colgroup, thead, tfoot, tbody 等元素。
                  jg过滤属性(jd标签, new String[]{"border"});
                  break;
               case "td":
                  jg过滤属性(jd标签, new String[]{"colspan", "rowspan"});
                  break;
               case "th":
                  jg过滤属性(jd标签, new String[]{"colspan", "rowspan", "scope"});
                  break;
               case "time":
                  jg过滤属性(jd标签, new String[]{"datetime", "pubdate"});
                  break;
            }
         }
         if (cd标签.attributes().size() == 0) {
            ji标签名_s = cd标签.nodeName();
            if (!"br".equals(ji标签名_s) && !"img".equals(ji标签名_s)) {
               if (cd标签.childNodes().size() == 0) {
                  cd标签.remove();
               } else {
                  ji_S = new StringBuilder();
                  for (int ji_i = cd标签.childNodes().size() - 1; ji_i >= 0; ji_i--) {
                     ji_S.append(cd标签.childNode(ji_i).outerHtml().trim());
                  }
                  if (ji_S.length() == 0) {
                     cd标签.remove();
                  }
               }
            }
         }
      } finally {
         ji标签名_s = null;
         jd标签 = null;
         ji_S = null;
      }
   }

   private static void jg过滤属性(Element cd标签, String[] ci属性_sArray) {
      List<Attribute> ji属性列表 = null;
      ji属性列表 = cd标签.attributes().asList();
      String jiKey_s = null;
      for (int ji_i = ji属性列表.size() - 1; ji_i >= 0; ji_i--) {
         jiKey_s = ji属性列表.get(ji_i).getKey();
         switch (jiKey_s) {
            case "dir":
            case "lang":
            case "style":
            case "title":
               continue;
         }
         if (ci属性_sArray != null) {
            for (int ji1_i = 0; ji1_i < ci属性_sArray.length; ji1_i++) {
               if (ci属性_sArray[ji1_i].equals(jiKey_s)) {
                  continue;
               }
            }
         }
         cd标签.removeAttr(jiKey_s);
      }
   }

   /**
    * Parse HTML into a Document. The parser will make a sensible, balanced document tree out of any HTML.
    *
    * @param html HTML to parse
    * @param baseUri The URL where the HTML was retrieved from. Used to resolve relative URLs to absolute URLs, that occur before the HTML declares a
    * {@code <base href>} tag.
    * @return sane HTML
    */
   public static Document parse(final String html, final String baseUri) {
      return Parser.parse(html, baseUri);
   }

   /**
    * Parse HTML into a Document, using the provided Parser. You can provide an alternate parser, such as a simple XML (non-HTML) parser.
    *
    * @param html HTML to parse
    * @param baseUri The URL where the HTML was retrieved from. Used to resolve relative URLs to absolute URLs, that occur before the HTML declares a
    * {@code <base href>} tag.
    * @param parser alternate {@link Parser#xmlParser() parser} to use.
    * @return sane HTML
    */
   public static Document parse(final String html, final String baseUri, final Parser parser) {
      return parser.parseInput(html, baseUri);
   }

   /**
    * Parse HTML into a Document. As no base URI is specified, absolute URL detection relies on the HTML including a {@code <base href>} tag.
    *
    * @param html HTML to parse
    * @return sane HTML
    *
    * @see #parse(String, String)
    */
   public static Document parse(final String html) {
      return Parser.parse(html, "");
   }

   /**
    * Creates a new {@link Connection} to a URL. Use to fetch and parse a HTML page.
    * <p>
    * Use examples: <ul>
    * <li><code>Document doc = Jsoup.connect("http://example.com").userAgent("Mozilla").data("name", "jsoup").get();</code></li>
    * <li><code>Document doc = Jsoup.connect("http://example.com").cookie("auth", "token").post();</code></li> </ul>
    *
    * @param url URL to connect to. The protocol must be {@code http} or {@code https}.
    * @return the connection. You can add data, cookies, and headers; set the user-agent, referrer, method; and then execute.
    */
   public static Connection connect(final String url) {
      return HttpConnection.connect(url);
   }

   /**
    * Parse the contents of a file as HTML.
    *
    * @param in file to load HTML from
    * @param charsetName (optional) character set of file contents. Set to {@code null} to determine from {@code http-equiv} meta tag, if present, or
    * fall back to {@code UTF-8} (which is often safe to do).
    * @param baseUri The URL where the HTML was retrieved from, to resolve relative links against.
    * @return sane HTML
    *
    * @throws IOException if the file could not be found, or read, or if the charsetName is invalid.
    */
   public static Document parse(final File in, final String charsetName, final String baseUri) throws IOException {
      return DataUtil.load(in, charsetName, baseUri);
   }

   /**
    * Parse the contents of a file as HTML. The location of the file is used as the base URI to qualify relative URLs.
    *
    * @param in file to load HTML from
    * @param charsetName (optional) character set of file contents. Set to {@code null} to determine from {@code http-equiv} meta tag, if present, or
    * fall back to {@code UTF-8} (which is often safe to do).
    * @return sane HTML
    *
    * @throws IOException if the file could not be found, or read, or if the charsetName is invalid.
    * @see #parse(File, String, String)
    */
   public static Document parse(final File in, final String charsetName) throws IOException {
      return DataUtil.load(in, charsetName, in.getAbsolutePath());
   }

   /**
    * Read an input stream, and parse it to a Document.
    *
    * @param in input stream to read. Make sure to close it after parsing.
    * @param charsetName (optional) character set of file contents. Set to {@code null} to determine from {@code http-equiv} meta tag, if present, or
    * fall back to {@code UTF-8} (which is often safe to do).
    * @param baseUri The URL where the HTML was retrieved from, to resolve relative links against.
    * @return sane HTML
    *
    * @throws IOException if the file could not be found, or read, or if the charsetName is invalid.
    */
   public static Document parse(final InputStream in, final String charsetName, final String baseUri) throws IOException {
      return DataUtil.load(in, charsetName, baseUri);
   }

   /**
    * Read an input stream, and parse it to a Document. You can provide an alternate parser, such as a simple XML (non-HTML) parser.
    *
    * @param in input stream to read. Make sure to close it after parsing.
    * @param charsetName (optional) character set of file contents. Set to {@code null} to determine from {@code http-equiv} meta tag, if present, or
    * fall back to {@code UTF-8} (which is often safe to do).
    * @param baseUri The URL where the HTML was retrieved from, to resolve relative links against.
    * @param parser alternate {@link Parser#xmlParser() parser} to use.
    * @return sane HTML
    *
    * @throws IOException if the file could not be found, or read, or if the charsetName is invalid.
    */
   public static Document parse(final InputStream in, final String charsetName, final String baseUri, final Parser parser) throws IOException {
      return DataUtil.load(in, charsetName, baseUri, parser);
   }

   /**
    * Parse a fragment of HTML, with the assumption that it forms the {@code body} of the HTML.
    *
    * @param bodyHtml body HTML fragment
    * @param baseUri URL to resolve relative URLs against.
    * @return sane HTML document
    *
    * @see Document#body()
    */
   public static Document parseBodyFragment(final String bodyHtml, final String baseUri) {
      return Parser.parseBodyFragment(bodyHtml, baseUri);
   }

   /**
    * Parse a fragment of HTML, with the assumption that it forms the {@code body} of the HTML.
    *
    * @param bodyHtml body HTML fragment
    * @return sane HTML document
    *
    * @see Document#body()
    */
   public static Document parseBodyFragment(final String bodyHtml) {
      return Parser.parseBodyFragment(bodyHtml, "");
   }

   /**
    * Fetch a URL, and parse it as HTML. Provided for compatibility; in most cases use {@link #connect(String)} instead.
    * <p>
    * The encoding character set is determined by the content-type header or http-equiv meta tag, or falls back to {@code UTF-8}.
    *
    * @param url URL to fetch (with a GET). The protocol must be {@code http} or {@code https}.
    * @param timeoutMillis Connection and read timeout, in milliseconds. If exceeded, IOException is thrown.
    * @return The parsed HTML.
    *
    * @throws java.net.MalformedURLException if the request URL is not a HTTP or HTTPS URL, or is otherwise malformed
    * @throws HttpStatusException if the response is not OK and HTTP response errors are not ignored
    * @throws UnsupportedMimeTypeException if the response mime type is not supported and those errors are not ignored
    * @throws java.net.SocketTimeoutException if the connection times out
    * @throws IOException if a connection or read error occurs
    *
    * @see #connect(String)
    */
   public static Document parse(final URL url, final int timeoutMillis) throws IOException {
      Connection con = HttpConnection.connect(url);
      con.timeout(timeoutMillis);
      return con.get();
   }

   /**
    * Get safe HTML from untrusted input HTML, by parsing input HTML and filtering it through a white-list of permitted tags and attributes.
    *
    * @param bodyHtml input untrusted HTML (body fragment)
    * @param baseUri URL to resolve relative URLs against
    * @param whitelist white-list of permitted HTML elements
    * @return safe HTML (body fragment)
    *
    * @see Cleaner#clean(Document)
    */
   public static String clean(final String bodyHtml, final String baseUri, final Whitelist whitelist) {
      Document dirty = parseBodyFragment(bodyHtml, baseUri);
      Cleaner cleaner = new Cleaner(whitelist);
      Document clean = cleaner.clean(dirty);
      return clean.body().html();
   }

   /**
    * Get safe HTML from untrusted input HTML, by parsing input HTML and filtering it through a white-list of permitted tags and attributes.
    *
    * @param bodyHtml input untrusted HTML (body fragment)
    * @param whitelist white-list of permitted HTML elements
    * @return safe HTML (body fragment)
    *
    * @see Cleaner#clean(Document)
    */
   public static String clean(final String bodyHtml, final Whitelist whitelist) {
      return clean(bodyHtml, "", whitelist);
   }

   /**
    * Get safe HTML from untrusted input HTML, by parsing input HTML and filtering it through a white-list of permitted tags and attributes.
    *
    * @param bodyHtml input untrusted HTML (body fragment)
    * @param baseUri URL to resolve relative URLs against
    * @param whitelist white-list of permitted HTML elements
    * @param outputSettings document output settings; use to control pretty-printing and entity escape modes
    * @return safe HTML (body fragment)
    * @see Cleaner#clean(Document)
    */
   public static String clean(final String bodyHtml, final String baseUri, final Whitelist whitelist, final Document.OutputSettings outputSettings) {
      Document dirty = parseBodyFragment(bodyHtml, baseUri);
      Cleaner cleaner = new Cleaner(whitelist);
      Document clean = cleaner.clean(dirty);
      clean.outputSettings(outputSettings);
      return clean.body().html();
   }

   /**
    * Test if the input HTML has only tags and attributes allowed by the Whitelist. Useful for form validation. The input HTML should still be run
    * through the cleaner to set up enforced attributes, and to tidy the output.
    *
    * @param bodyHtml HTML to test
    * @param whitelist whitelist to test against
    * @return true if no tags or attributes were removed; false otherwise
    * @see #clean(String, org.hzs.jsoup.safety.Whitelist)
    */
   public static boolean isValid(final String bodyHtml, final Whitelist whitelist) {
      Document dirty = parseBodyFragment(bodyHtml, "");
      Cleaner cleaner = new Cleaner(whitelist);
      return cleaner.isValid(dirty);
   }

   public static String g格式化JS代码(final String ciJS代码_s) {
      char[] ji_charArray = null;
      ji_charArray = ciJS代码_s.replaceAll("\n", " ").toCharArray();
      ji_charArray = jg格式化(ji_charArray);
      ji_charArray = jg合并空格(ji_charArray);
      ji_charArray = jg缩进(ji_charArray);
      return new String(ji_charArray);
   }

   private static char[] jg格式化(final char[] ci_charArray) {
      char[] ji_charArray = new char[0];
      int jiCASE层數_i = 0;
      int ji小括号层數_i = 0;
      int ji当前位置_i = 0;
      while (ji当前位置_i < ci_charArray.length) {
         switch (ci_charArray[ji当前位置_i]) {
//            case 'c':
//               //case
//               if ((ji当前位置_i == 0 || ci_charArray[ji当前位置_i - 1] == ' ' || ci_charArray[ji当前位置_i - 1] == '\n' || ci_charArray[ji当前位置_i - 1] == ';' || ci_charArray[ji当前位置_i - 1] == '{' || ci_charArray[ji当前位置_i - 1] == '}' || ci_charArray[ji当前位置_i - 1] == ':')
//                       && (ji当前位置_i + 4 < ci_charArray.length && ci_charArray[ji当前位置_i + 1] == 'a' && ci_charArray[ji当前位置_i + 2] == 's' && ci_charArray[ji当前位置_i + 3] == 'e' && (ci_charArray[ji当前位置_i + 4] == ' ' || ci_charArray[ji当前位置_i + 4] == '\'' || ci_charArray[ji当前位置_i + 4] == '"' || ci_charArray[ji当前位置_i + 4] == '('))) {
//                  jiCASE层數_i++;
//               }
//               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
//               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
//               break;
            case '=':
            case '!':
               if (ji当前位置_i + 1 < ci_charArray.length && ci_charArray[ji当前位置_i + 1] == '=') {
                  if (ji当前位置_i + 2 < ci_charArray.length && ci_charArray[ji当前位置_i + 2] == '=') {
                     ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 5);
                     ji_charArray[ji_charArray.length - 5] = ' ';
                     ji_charArray[ji_charArray.length - 4] = ci_charArray[ji当前位置_i];
                     ji_charArray[ji_charArray.length - 3] = ci_charArray[ji当前位置_i + 1];
                     ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i + 2];
                     ji_charArray[ji_charArray.length - 1] = ' ';
                     ji当前位置_i++;
                  } else {
                     ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 4);
                     ji_charArray[ji_charArray.length - 4] = ' ';
                     ji_charArray[ji_charArray.length - 3] = ci_charArray[ji当前位置_i];
                     ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i + 1];
                     ji_charArray[ji_charArray.length - 1] = ' ';
                  }
                  ji当前位置_i++;
               } else {
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 3);
                  ji_charArray[ji_charArray.length - 3] = ' ';
                  ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
                  ji_charArray[ji_charArray.length - 1] = ' ';
               }
               break;
            case '>':
            case '<':
               if (ji当前位置_i < ci_charArray.length && ci_charArray[ji当前位置_i + 1] == '=') {
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 4);
                  ji_charArray[ji_charArray.length - 4] = ' ';
                  ji_charArray[ji_charArray.length - 3] = ci_charArray[ji当前位置_i];
                  ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i + 1];
                  ji_charArray[ji_charArray.length - 1] = ' ';
                  ji当前位置_i++;
               } else {
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 3);
                  ji_charArray[ji_charArray.length - 3] = ' ';
                  ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
                  ji_charArray[ji_charArray.length - 1] = ' ';
               }
               break;
            case '(':
               ji小括号层數_i++;
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 3);
               ji_charArray[ji_charArray.length - 3] = ' ';
               ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
               ji_charArray[ji_charArray.length - 1] = ' ';
               break;
            case ')':
               ji小括号层數_i--;
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 3);
               ji_charArray[ji_charArray.length - 3] = ' ';
               ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
               ji_charArray[ji_charArray.length - 1] = ' ';
               break;
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 3);
               ji_charArray[ji_charArray.length - 3] = ' ';
               ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
               ji_charArray[ji_charArray.length - 1] = ' ';
               break;
            case ':':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
               ji_charArray[ji_charArray.length - 2] = ':';
               if (jiCASE层數_i > 0) {
                  ji_charArray[ji_charArray.length - 1] = '\n';
                  jiCASE层數_i--;
               } else {
                  ji_charArray[ji_charArray.length - 1] = ' ';
               }
               break;
            case ',':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
               ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
//               if (ji小括号层數_i < 0) {
               ji_charArray[ji_charArray.length - 1] = '\n';
//               } else {
//                  ji_charArray[ji_charArray.length - 1] = ' ';
//               }
               break;
            case ';':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
               ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
               ji_charArray[ji_charArray.length - 1] = '\n';
               break;
            case '{':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
               ji_charArray[ji_charArray.length - 2] = '{';
               ji_charArray[ji_charArray.length - 1] = '\n';
               break;
            case '}':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
               ji_charArray[ji_charArray.length - 2] = '\n';
               ji_charArray[ji_charArray.length - 1] = '}';
               break;
            case '\'':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '\'';
               while (ji当前位置_i + 1 < ci_charArray.length) {
                  ji当前位置_i++;
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
                  if (ci_charArray[ji当前位置_i] == '\'') {
                     break;
                  }
               }
               break;
            case '"':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '"';
               while (ji当前位置_i + 1 < ci_charArray.length) {
                  ji当前位置_i++;
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
                  if (ci_charArray[ji当前位置_i] == '"') {
                     break;
                  }
               }
               break;
            default:
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
               break;
         }
         ji当前位置_i++;
      }
      return ji_charArray;
   }

   private static char[] jg合并空格(final char[] ci_charArray) {
      char[] ji_charArray = new char[0];
      int ji当前位置_i = 0;
      while (ji当前位置_i < ci_charArray.length) {
         switch (ci_charArray[ji当前位置_i]) {
            case '\'':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '\'';
               while (ji当前位置_i + 1 < ci_charArray.length) {
                  ji当前位置_i++;
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
                  if (ci_charArray[ji当前位置_i] == '\'') {
                     break;
                  }
               }
               break;
            case '"':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '"';
               while (ji当前位置_i + 1 < ci_charArray.length) {
                  ji当前位置_i++;
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
                  if (ci_charArray[ji当前位置_i] == '"') {
                     break;
                  }
               }
               break;
            case ' ':
               while (ji当前位置_i + 1 < ci_charArray.length && ci_charArray[ji当前位置_i + 1] == ' ') {
                  ji当前位置_i++;
               }
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = ' ';
               break;
            default:
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
               break;
         }
         ji当前位置_i++;
      }
      return ji_charArray;
   }

   private static char[] jg缩进(final char[] ci_charArray) {
      char[] ji_charArray = new char[0];
//      int jiCASE层數_i = 0;
//      int ji小括号层數_i = 0;
      int ji当前位置_i = 0;
      int ji缩进_i = 0, ji_i = 0;
      while (ji当前位置_i < ci_charArray.length) {
         switch (ci_charArray[ji当前位置_i]) {
            case '\n':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '\n';
               for (ji_i = 0; ji_i < ji缩进_i; ji_i++) {
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ' ';
               }
               break;
//            case 'c':
//               //case
//               if ((ji当前位置_i == 0 || ci_charArray[ji当前位置_i - 1] == ' ' || ci_charArray[ji当前位置_i - 1] == '\n' || ci_charArray[ji当前位置_i - 1] == ';' || ci_charArray[ji当前位置_i - 1] == '{' || ci_charArray[ji当前位置_i - 1] == '}' || ci_charArray[ji当前位置_i - 1] == ':')
//                       && (ji当前位置_i + 4 < ci_charArray.length && ci_charArray[ji当前位置_i + 1] == 'a' && ci_charArray[ji当前位置_i + 2] == 's' && ci_charArray[ji当前位置_i + 3] == 'e' && (ci_charArray[ji当前位置_i + 4] == ' ' || ci_charArray[ji当前位置_i + 4] == '\'' || ci_charArray[ji当前位置_i + 4] == '"' || ci_charArray[ji当前位置_i + 4] == '('))) {
//                  jiCASE层數_i++;
//               }
//               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
//               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
//               break;
//            case ':':
//               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
//               ji_charArray[ji_charArray.length - 2] = ':';
//               if (jiCASE层數_i > 0) {
//                  ji_charArray[ji_charArray.length - 1] = '\n';
//                  jiCASE层數_i--;
//               } else {
//                  ji_charArray[ji_charArray.length - 1] = ' ';
//               }
//               break;
            case ';':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 2);
               ji_charArray[ji_charArray.length - 2] = ci_charArray[ji当前位置_i];
               ji_charArray[ji_charArray.length - 1] = '\n';
               break;
            case '{':
               ji缩进_i += 3;
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
               break;
            case '}':
               ji缩进_i -= 3;
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
               break;
            case '\'':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '\'';
               while (ji当前位置_i + 1 < ci_charArray.length) {
                  ji当前位置_i++;
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
                  if (ci_charArray[ji当前位置_i] == '\'') {
                     break;
                  }
               }
               break;
            case '"':
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = '"';
               while (ji当前位置_i + 1 < ci_charArray.length) {
                  ji当前位置_i++;
                  ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
                  ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
                  if (ci_charArray[ji当前位置_i] == '"') {
                     break;
                  }
               }
               break;
            default:
               ji_charArray = java.util.Arrays.copyOf(ji_charArray, ji_charArray.length + 1);
               ji_charArray[ji_charArray.length - 1] = ci_charArray[ji当前位置_i];
               break;
         }
         ji当前位置_i++;
      }
      return ji_charArray;
   }
}
