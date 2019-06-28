# Jacob转Excel文件为PDF



## Jacob环境配置

### 安装Office

### 下载Jacob jar包

- 下载地址: https://sourceforge.net/projects/jacob-project

```htm
解压后获得3个文件：
   jacob.jar
   jacob-1.1.8-x64.dll
   jacob-1.1.8-x86.dll
   （dll文件：Java应用程序中调用COM组件和Win32程序库）
```

### 拷贝dll文件

```html
根据JDK的版本（32位/64位），选择对应版本的dll文件，将文件拷贝到%JAVA_HOME%\bin目录下
例：C:\TOOLS\Java\jdk1.8.0_181\bin\jacob-1.19-x64.dll
```

### 项目中引用jacob.jar

```powershell
# 将本地jar包加入到maven项目：
mvn install:install-file   -Dfile=D:\...\jacob-1.19\jacob.jar  -DgroupId=com.jacob  -DartifactId=jacob -Dversion=1.19   -Dpackaging=jar

-Dfile：指明你当前jar包的位置；
-DgroupId， -DartifactId，  -Dversion：三个参数，就是指明了存放maven仓库中的位置；
-Dpackaging ：指明文件类型；
```

## Jacob示例

```java
import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * COM组件[Word\Excel\PPT]转换PDF 转换器
 * @author Seven
 *
 */
public class PdfConverter {
    
    /**
     * 转换类型
     */
    public static final int TYPE_WORD = 0;
    public static final int TYPE_EXCEL = 1;
    public static final int TYPE_PPT = 2;
    
    /**
     * 转换类型
     */
    public static  Integer converterType ;
    /**
     * 源文件(路径 + 文件名)
     */
    public static  String  source;
    /**
     * 目标文件(路径 + 文件名)
     */
    public static  String  dest;
    
    
    public static void main(String[] args) {
        
        try {
            init(); //初始化参数配置
            converter();//启动转换功能
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private static void init(){
        converterType = PdfConverter.TYPE_PPT;
        source = "C:\\Users\\Seven\\Desktop\\测试demo.pptx" ;
        dest = "C:\\Users\\Seven\\Desktop\\测试demo.pdf" ;
    }
    
    private static void converter() throws Exception{
        File file = new File(source);
        
        if(!file.exists()){
            throw new Exception("转换文件不存在");
        }
        
        switch(converterType){
            case TYPE_WORD :
                word2Pdf(source,dest);
                break;
            case TYPE_EXCEL :
                excel2Pdf(source,dest);
                break;
            case TYPE_PPT :
                ppt2Pdf(source,dest);
                break;
            default:
                throw new Exception("文件转换类型不存在");
        }
    }

    public static void word2Pdf(String sourcePath, String destPath)
            throws Exception {

        ActiveXComponent word = null;
        Dispatch documents = null;
        
        try {
            //初始化线程
            ComThread.InitMTA(true);
            
            //初始化应用
            word = new ActiveXComponent("Word.Application");
            
            //设置应用属性
            Dispatch.put(word.getObject(), "Visible", new Variant(false));
            Dispatch.put(word.getObject(), "DisplayAlerts", new Variant(0));
            
            //通过应用打开文档
            documents = word.invokeGetComponent("Documents")
                              .invokeGetComponent("Open", new Variant(sourcePath));
            //调用对应的方法
            Dispatch.invoke(documents, 
                            "SaveAs",
                            Dispatch.Method, 
                            new Object[] {destPath , new Variant(17) }, 
                            new int[1]);  

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                //退出应用
                if (word != null) {
                    word.invoke("Quit", new Variant(false));
                }
            } catch (Exception e) {
                    e.printStackTrace();
            } finally {
                //关闭文档和应用
                if (documents != null)
                    documents.safeRelease();
                if (word != null)
                    word.safeRelease();
                
                //释放线程
                ComThread.Release();
            }
        }
    }
    
    public static void excel2Pdf(String sourcePath, String destPath)
            throws Exception {

        ActiveXComponent excel = null;
        Dispatch workBooks = null;
        try {
            ComThread.InitMTA(true);
            excel = new ActiveXComponent("Excel.Application");
            Dispatch.put(excel.getObject(), "Visible", new Variant(false));
            Dispatch.put(excel.getObject(), "DisplayAlerts", new Variant(false));

            workBooks = excel.invokeGetComponent("Workbooks")
                                .invokeGetComponent("Open", new Variant(sourcePath));
            
            Dispatch.invoke(workBooks,
                            "ExportAsFixedFormat",  
                            Dispatch.Method, 
                            new Variant[]{new Variant(0),new Variant(destPath)},
                            new int[1]);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (excel != null) {
                    excel.invoke("Quit");
                }
            } catch (Exception e) {
                 e.printStackTrace();
            } finally {
                if (workBooks != null)
                    workBooks.safeRelease();
                if (excel != null)
                    excel.safeRelease();
                
                ComThread.Release();
            }
        }
    }
    
    public static void ppt2Pdf(String sourcePath, String destPath)
            throws Exception {

        ActiveXComponent ppt = null;
        Dispatch presentations = null;
        try {
            ComThread.InitMTA(true);
            ppt = new ActiveXComponent("PowerPoint.application");
            Dispatch.put(ppt.getObject(), "DisplayAlerts", new Variant(false));
        
            presentations = ppt.invokeGetComponent("Presentations")
                    .invokeGetComponent("Open", new Variant(sourcePath));
            
             Dispatch.invoke(presentations, 
                    "SaveAs",
                    Dispatch.Method, 
                    new Object[] {destPath , new Variant(32) }, 
                    new int[1]);  
        
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (ppt != null) {
                    ppt.invoke("Quit");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (presentations != null)
                    presentations.safeRelease();
                if (ppt != null)
                    ppt.safeRelease();
                
                ComThread.Release();
            }
        }
    }
}

```









**参考**

- [Web页面上的报表或列表数据转换成pdf文件下载到本地](https://www.jianshu.com/p/e2bf571598b5)
- 