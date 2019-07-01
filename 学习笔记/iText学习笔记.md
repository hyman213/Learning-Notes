# iText学习笔记

## IText简单应用

### HelloWorld

简单创建一个PDF文件，并写入一段文字(英文)

```java
public class HelloWorld {

    public static void main(String[] args) {
        // 创建document对象
        Document document = new Document();
        try {
            // create a write that listens to the docuument and directs a PDF-stream to a file
            PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
            // 打开文件
            document.open();
            // 在文件中加入段落
            document.add(new Paragraph("Hello World"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

}
```

### 创建Document实例

Document有三个构造方法

```java
public Document() {
    this(PageSize.A4);
}
// PageSize类中有各种尺寸
public Document(Rectangle pageSize) {
    this(pageSize, 36, 36, 36, 36);
}

public Document(Rectangle pageSize, float marginLeft, float marginRight,
                float marginTop, float marginBottom) {
    this.pageSize = pageSize;
    this.marginLeft = marginLeft;
    this.marginRight = marginRight;
    this.marginTop = marginTop;
    this.marginBottom = marginBottom;
}
```

设置页面尺寸及背景颜色

```java
Rectangle pageSize = new Rectangle(PageSize.A4.rotate());
pageSize.setBackgroundColor(new BaseColor(128, 128, 128));
Document document = new Document(pageSize);
```

设置插入段落的样式

```java
Paragraph paragraph = new Paragraph("HelloWorld");
paragraph.setAlignment(Element.ALIGN_CENTER);
document.add(paragraph);
```

### 创建Writer实例

- PdfWriter - 生成PDF文档
- RtfWriter - RTF文档
- HtmlWriter - HTML文档

都继承自`com.itextpdf.text.DocWriter`

创建PdfWriter实例

```java
public static PdfWriter getInstance(final Document document, final OutputStream os)
    throws DocumentException {
    PdfDocument pdf = new PdfDocument();
    document.addDocListener(pdf);
    PdfWriter writer = new PdfWriter(pdf, os);
    pdf.addWriter(writer);
    return writer;
}
```

### 打开Document

```java
// 打开文件
document.addTitle("hw example");
document.addSubject("this is a hello world example");
document.addKeywords("itext helloworld");
document.addCreator("Hyman");
document.addAuthor("Huhan");
document.open();
```

页面初始化的操作需要在打开文档前进行设置

### 添加内容

```java
com.itextpdf.text.Element的实现类

 * @see Anchor
 * @see Chapter
 * @see Chunk
 * @see Header
 * @see Image
 * @see Jpeg
 * @see List
 * @see ListItem
 * @see Meta
 * @see Paragraph
 * @see Phrase
 * @see Rectangle
 * @see Section
```

### 关闭document

放在finally块中

## 块、短句和段落

### 块 - Chunk

`com.itextpdf.text.Chunk`是能被添加到文档的文本的最小单位，块可以用于构建其他基础元素如短语、段落、锚点等，块是一个有确定字体的字符串，要添加到文档中时，所有的布局属性均要被定义。

```java
// 红色、斜体、20的一个块
Chunk chunk = new Chunk("hello itext", FontFactory.getFont(FontFactory.COURIER, 20, Font.ITALIC, new BaseColor(255,0,0)));

// Font构造
public static Font getFont(final String fontname, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color)
```

### 短句 - Phrases

`com.itextpdf.text.Phrase`是一系列一特定间距作为参数的块

### 段落 - Paragraph

段落是一系列块和短语

## 锚点、列表和注释

### 锚点

类似于链接，可使用`com.itextpdf.text.Anchor`对象(extends Phrase)。

Anchor具有额外的两个变量： name和reference

```java
Anchor anchor = new Anchor("baidu", FontFactory.getFont(FontFactory.COURIER_BOLD, 15, Font.UNDERLINE, new BaseColor(0,255,0)));
anchor.setName("bd");
anchor.setReference("https://www.baidu.com/");
document.add(anchor);
```

当使用内部连接时，需要添加一个"#"

### 列表

使用`com.itextpdf.text.List`和`com.itextpdf.text.ListItem`可以添加列表到PDF文件中。

```java
List list = new List(true, 20);
list.add(new ListItem("one"));
list.add(new ListItem("two"));
list.add(new ListItem("three"));
list.add(new ListItem("four"));
document.add(list);
```

### 注释

**文本注释**

```java
// 注释
Annotation annotation = new Annotation("author","Huhan");
document.add(annotation);
```

## 页眉页脚、章节、区域和绘图对象

### 章节

```java
Chapter chapter = new Chapter("ch01", 1);
chapter.addSection("sec01",2);
document.add(chapter);
```

![](https://raw.githubusercontent.com/hyman213/FigureBed/master/2019/06/20190630174231.png)

### 图形



## 表格

使用`com.itextpdf.text.pdf.PdfPTable`构建表格



## 图片

使用`com.itextpdf.text.Image`构建图片

```java
// 图片
Image image = Image.getInstance("D:\\123456.png");
//Image.getInstance();
image.setAlignment(Element.ALIGN_BOTTOM);
image.scaleAbsolute(new Rectangle(360, 120));
document.add(image);
```

图片在表格中

## 字体

```java
BaseFont bfHei = BaseFont.createFont("D:\\...\\FONT\\local\\ttf\\BLACK.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
Font font = new Font(bfHei, 32);
document.add(new Paragraph("黑体字测试", font));
```



## 未完待续。。。。





